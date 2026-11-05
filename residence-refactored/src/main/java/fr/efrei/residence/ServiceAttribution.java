package fr.efrei.residence;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public class ServiceAttribution {

    private int compteurBail = 0;

    private final BailRepository database;
    private final LogementRepository logements;
    private final CalculateurLoyer calculateur;
    private final ServiceNotification notifications;

    public ServiceAttribution(BailRepository database, LogementRepository logements,
                              CalculateurLoyer calculateur, ServiceNotification notifications) {
        this.database = database;
        this.logements = logements;
        this.calculateur = calculateur;
        this.notifications = notifications;
    }

    public Logement ajouterLogement(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        if (logements.trouver(numero) != null) {
            System.out.println("Logement " + numero + " existe déjà");
            return null;
        }
        Logement logement = LogementFactory.creer(type, numero, surface, etage, meuble, param);
        if (logement == null) {
            System.out.println("Type de logement inconnu : " + type);
            return null;
        }
        logements.ajouter(logement);
        System.out.println("Logement ajouté : " + numero + " (" + type + ")");
        return logement;
    }

    public Bail attribuerLogement(Etudiant etudiant, TypeLogement typeSouhaite, boolean parking, boolean menage, boolean laverie, boolean fibre, boolean assurance, LocalDate dateSignature) {
        StrategieProfil strategieProfil = SelecteurStrategieProfil.choisir(etudiant.getProfil());
        Set<Option> options = options(parking, menage, laverie, fibre, assurance);
        System.out.println("--- Attribution d'un " + typeSouhaite + " à " + etudiant.getNomComplet());
        Logement choisi = null;
        double loyer = 0;
        double charges = 0;
        for (Logement logement : logements.tousLesLogements().stream()
                .filter(logement -> logement.getType() == typeSouhaite).toList()) {
            if (!logement.estLibre()) continue;
            if (etudiant.isPmr() && !logement.estAccessiblePMR()) continue;

            double loyerCandidat = calculateur.calculerLoyer(logement, etudiant.getProfil(), etudiant.getEchelonBourse(), options);
            double chargesCandidat = calculateur.calculerCharges(logement);
            double total = loyerCandidat + chargesCandidat;

            boolean solvable = strategieProfil.estSolvable(etudiant.getRessourcesMensuelles(), total);
            if (!solvable) {
                System.out.println("  " + logement.getNumero() + " : ressources insuffisantes (" + Utils.euros(total) + "/mois)");
                continue;
            }
            choisi = logement;
            loyer = loyerCandidat;
            charges = chargesCandidat;
            break;
        }
        if (choisi == null) {
            System.out.println("  Aucun logement disponible pour " + etudiant.getNomComplet());
            return null;
        }

        choisi.loger(etudiant);

        compteurBail++;
        String id = "BAIL-" + String.format("%03d", compteurBail);
        Bail bail = new Bail(id, etudiant, choisi, dateSignature);
        bail.setOptions(options);
        bail.setLoyerHorsCharges(loyer);
        bail.setCharges(charges);

        bail.setCaution(Utils.arrondir(strategieProfil.calculerCaution(loyer)));
        bail.setFraisDossier(strategieProfil.fraisDossier());

        database.sauvegarderBail(bail);
        System.out.println("  Bail " + id + " signé : " + choisi.getNumero() + ", " + Utils.euros(bail.getLoyerChargesComprises())
                + " CC/mois, caution " + Utils.euros(bail.getCaution()) + ", frais de dossier " + Utils.euros(bail.getFraisDossier()));
        notifications.notifier(etudiant, "Votre bail " + id,
                "Bienvenue à la " + Residence.NOM + " ! Logement " + choisi.getNumero() + ", loyer "
                        + Utils.euros(bail.getLoyerChargesComprises()) + " charges comprises.");
        return bail;
    }

    private Set<Option> options(boolean parking, boolean menage, boolean laverie, boolean fibre, boolean assurance) {
        Set<Option> options = EnumSet.noneOf(Option.class);
        if (parking) options.add(Option.PARKING);
        if (menage) options.add(Option.MENAGE);
        if (laverie) options.add(Option.LAVERIE);
        if (fibre) options.add(Option.FIBRE);
        if (assurance) options.add(Option.ASSURANCE);
        return options;
    }
}
