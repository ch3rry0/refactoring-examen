package fr.efrei.residence;

import java.time.LocalDate;

public class GestionResidence {

    private final ServiceInscription inscriptions;
    private final ServiceAttribution attributions;
    private final ServiceCycleBail cycleBail;
    private final ServiceNotification notifications;
    private final ServiceRapport rapports;
    private final ServiceQuittance quittances;

    public GestionResidence(ServiceInscription inscriptions, ServiceAttribution attributions,
                            ServiceCycleBail cycleBail, ServiceNotification notifications,
                            ServiceRapport rapports, ServiceQuittance quittances) {
        this.inscriptions = inscriptions;
        this.attributions = attributions;
        this.cycleBail = cycleBail;
        this.notifications = notifications;
        this.rapports = rapports;
        this.quittances = quittances;
    }

    public Etudiant inscrireEtudiant(String nom, String prenom, String email, String telephone, String adresse,
                                     Profil profil, int echelonBourse, double ressources, boolean pmr,
                                     Canal canalPrefere) {
        return inscriptions.inscrireEtudiant(nom, prenom, email, telephone, adresse, profil, echelonBourse, ressources, pmr, canalPrefere);
    }

    public Logement ajouterLogement(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        return attributions.ajouterLogement(type, numero, surface, etage, meuble, param);
    }

    public Bail attribuerLogement(Etudiant e, TypeLogement typeSouhaite, boolean parking, boolean menage, boolean laverie, boolean fibre, boolean assurance, LocalDate dateSignature) {
        return attributions.attribuerLogement(e, typeSouhaite, parking, menage, laverie, fibre, assurance, dateSignature);
    }

    public boolean entrerDansLesLieux(String idBail, LocalDate date) {
        return cycleBail.entrerDansLesLieux(idBail, date);
    }

    public boolean payerLoyer(String idBail, String mois, LocalDate datePaiement, double montant) {
        return cycleBail.payerLoyer(idBail, mois, datePaiement, montant);
    }

    public boolean donnerPreavis(String idBail, LocalDate date) {
        return cycleBail.donnerPreavis(idBail, date);
    }

    public boolean sortir(String idBail, LocalDate date, double retenuesDegats) {
        return cycleBail.sortir(idBail, date, retenuesDegats);
    }

    public boolean annulerBail(String idBail) {
        return cycleBail.annulerBail(idBail);
    }

    public void relancerImpayes() {
        notifications.relancerImpayes();
    }

    public void afficherRapport() {
        rapports.afficherRapport();
    }

    public String genererRapport() {
        return rapports.genererRapport();
    }

    public String genererQuittance(Bail bail, String mois) {
        return quittances.genererQuittance(bail, mois);
    }
}
