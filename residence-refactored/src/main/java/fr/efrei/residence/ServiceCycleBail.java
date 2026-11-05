package fr.efrei.residence;

import java.time.LocalDate;
import java.time.YearMonth;

public class ServiceCycleBail {

    private static final int JOUR_ECHEANCE_LOYER = 5;
    private static final int DELAI_RESTITUTION_CAUTION_JOURS = 30;

    private final BailRepository database;
    private final ServiceNotification notifications;

    public ServiceCycleBail(BailRepository database, ServiceNotification notifications) {
        this.database = database;
        this.notifications = notifications;
    }

    public boolean entrerDansLesLieux(String idBail, LocalDate date) {
        Bail bail = database.trouverBail(idBail);
        if (bail == null || !bail.getStatut().equals(StatutBail.SIGNE)) {
            System.out.println("Entrée impossible pour " + idBail);
            return false;
        }
        if (date.isBefore(bail.getDateSignature())) {
            System.out.println("Entrée impossible avant la signature du bail");
            return false;
        }
        bail.setDateEntree(date);
        bail.setStatut(StatutBail.ACTIF);
        database.mettreAJourBail(bail);
        System.out.println("Entrée dans les lieux : " + idBail + " le " + Utils.date(date));
        notifications.notifier(bail.getEtudiant(), "État des lieux d'entrée",
                "Votre état des lieux d'entrée du logement " + bail.getLogement().getNumero() + " est fait le "
                        + Utils.date(date) + ". Remise des clés effectuée. Pensez à souscrire une assurance habitation si ce n'est pas déjà fait.");
        return true;
    }

    public boolean payerLoyer(String idBail, String mois, LocalDate datePaiement, double montant) {
        Bail bail = database.trouverBail(idBail);
        if (bail == null || !(bail.getStatut().equals(StatutBail.ACTIF) || bail.getStatut().equals(StatutBail.PREAVIS))) {
            System.out.println("Paiement refusé pour " + idBail);
            return false;
        }
        LocalDate echeance = YearMonth.parse(mois).atDay(JOUR_ECHEANCE_LOYER);
        double penalite = 0;
        if (datePaiement.isAfter(echeance)) {
            penalite = SelecteurStrategieProfil.choisir(bail.getEtudiant().getProfil())
                    .calculerPenalite(bail.getLoyerChargesComprises());
        }
        double montantDu = Utils.arrondir(bail.getLoyerChargesComprises() + penalite);
        bail.paiements.add(new Paiement(mois, datePaiement, montant, penalite, montantDu));
        bail.soldeDu = Utils.arrondir(bail.soldeDu + montantDu - montant);
        database.mettreAJourBail(bail);

        System.out.println("Paiement " + idBail + " " + mois + " : " + Utils.euros(montant) + " / " + Utils.euros(montantDu)
                + (penalite > 0 ? " (dont pénalité " + Utils.euros(penalite) + ")" : ""));
        if (montant < montantDu) {
            notifications.notifier(bail.getEtudiant(), "Paiement incomplet " + mois,
                    "Nous avons reçu " + Utils.euros(montant) + " sur " + Utils.euros(montantDu) + ". Reste dû : "
                            + Utils.euros(Utils.arrondir(montantDu - montant)) + ".");
        } else {
            notifications.notifier(bail.getEtudiant(), "Quittance " + mois, "Merci, votre loyer de " + mois + " est réglé.");
        }
        return true;
    }

    public boolean donnerPreavis(String idBail, LocalDate date) {
        Bail bail = database.trouverBail(idBail);
        if (bail == null || !bail.getStatut().equals(StatutBail.ACTIF)) {
            System.out.println("Préavis impossible pour " + idBail);
            return false;
        }
        int jours = SelecteurStrategieProfil.choisir(bail.getEtudiant().getProfil()).dureePreavis();
        bail.setDateFin(date.plusDays(jours));
        bail.setStatut(StatutBail.PREAVIS);
        database.mettreAJourBail(bail);
        System.out.println("Préavis " + idBail + " : fin du bail le " + Utils.date(bail.getDateFin()));
        notifications.notifier(bail.getEtudiant(), "Préavis de départ",
                "Votre préavis de " + jours + " jours est enregistré. Fin du bail le " + Utils.date(bail.getDateFin()) + ".");
        return true;
    }

    public boolean sortir(String idBail, LocalDate date, double retenuesDegats) {
        Bail bail = database.trouverBail(idBail);
        if (bail == null || !bail.getStatut().equals(StatutBail.PREAVIS)) {
            System.out.println("Sortie impossible pour " + idBail);
            return false;
        }
        if (date.isBefore(bail.getDateFin())) {
            System.out.println("Sortie impossible avant la fin du préavis (" + Utils.date(bail.getDateFin()) + ")");
            return false;
        }
        Logement logement = bail.getLogement();
        if (logement instanceof Colocation) {
            logement.retirerColocataire(bail.getEtudiant());
            logement.nettoyerPartiesCommunes();
        } else {
            logement.liberer(bail.getEtudiant());
        }

        double impayes = bail.soldeDu > 0 ? bail.soldeDu : 0;
        double restitution = bail.getCaution() - retenuesDegats - impayes;
        double resteAPayer = 0;
        if (restitution < 0) {
            resteAPayer = -restitution;
            restitution = 0;
        }
        bail.retenues = Utils.arrondir(retenuesDegats);
        bail.cautionRestituee = Utils.arrondir(restitution);
        bail.soldeDu = Utils.arrondir(resteAPayer);
        bail.setStatut(StatutBail.TERMINE);
        database.mettreAJourBail(bail);

        System.out.println("Sortie " + idBail + " le " + Utils.date(date) + " : caution " + Utils.euros(bail.getCaution())
                + ", retenues " + Utils.euros(bail.retenues) + ", impayés " + Utils.euros(impayes)
                + " -> restitué " + Utils.euros(bail.cautionRestituee));
        String message = "Votre dépôt de garantie : " + Utils.euros(bail.cautionRestituee) + " vous sera restitué sous " + DELAI_RESTITUTION_CAUTION_JOURS + " jours.";
        if (resteAPayer > 0) {
            message = message + " Il reste " + Utils.euros(bail.soldeDu) + " à régler.";
        }
        notifications.notifier(bail.getEtudiant(), "Sortie du logement " + logement.getNumero(), message);
        return true;
    }

    public boolean annulerBail(String idBail) {
        Bail bail = database.trouverBail(idBail);
        if (bail == null || !bail.getStatut().equals(StatutBail.SIGNE)) {
            System.out.println("Annulation impossible pour " + idBail);
            return false;
        }
        Logement logement = bail.getLogement();
        if (logement instanceof Colocation) {
            logement.retirerColocataire(bail.getEtudiant());
        } else {
            logement.liberer(bail.getEtudiant());
        }
        bail.setStatut(StatutBail.ANNULE);
        database.mettreAJourBail(bail);
        System.out.println("Bail " + idBail + " annulé, frais de dossier conservés : " + Utils.euros(bail.getFraisDossier()));
        notifications.notifier(bail.getEtudiant(), "Annulation du bail " + idBail,
                "Votre bail est annulé. Les frais de dossier (" + Utils.euros(bail.getFraisDossier()) + ") restent acquis.");
        return true;
    }
}
