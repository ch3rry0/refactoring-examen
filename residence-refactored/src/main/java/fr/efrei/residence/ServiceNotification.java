package fr.efrei.residence;

public class ServiceNotification {

    private final BailRepository database;
    private final CanalNotificationFactory canaux;

    public ServiceNotification(BailRepository database, CanalNotificationFactory canaux) {
        this.database = database;
        this.canaux = canaux;
    }

    public void notifier(Etudiant etudiant, String sujet, String message) {
        Canal canal = etudiant.getCanalPrefere();
        canaux.creer(canal).envoyer(etudiant, sujet, message);
        // les internationaux reçoivent toujours un email en plus (demande de l'administration)
        if (SelecteurStrategieProfil.choisir(etudiant.getProfil()).emailSupplementaire() && !canal.equals(Canal.EMAIL)) {
            canaux.creer(Canal.EMAIL).envoyer(etudiant, "[EN] " + sujet, message);
        }
    }

    public void relancerImpayes() {
        System.out.println("--- Relance des impayés");
        for (Bail bail : database.tousLesBaux()) {
            if (bail.soldeDu <= 0) continue;
            if (bail.getStatut().equals(StatutBail.ANNULE)) continue;
            Etudiant etudiant = bail.getEtudiant();
            canaux.creer(etudiant.getCanalPrefere()).relancer(etudiant, bail);
        }
    }
}
