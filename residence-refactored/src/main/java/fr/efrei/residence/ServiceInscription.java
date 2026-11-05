package fr.efrei.residence;

public class ServiceInscription {

    private final EtudiantRepository database;
    private int compteurEtudiant = 0;

    public ServiceInscription(EtudiantRepository database) {
        this.database = database;
    }

    public Etudiant inscrireEtudiant(String nom, String prenom, String email, String telephone, String adresse,
                                     Profil profil, int echelonBourse, double ressources, boolean pmr,
                                     Canal canalPrefere) {
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println("Inscription refusée : nom obligatoire");
            return null;
        }
        if (!Utils.emailValide(email)) {
            System.out.println("Inscription refusée : email invalide (" + email + ")");
            return null;
        }
        if (database.trouverEtudiantParEmail(email) != null) {
            System.out.println("Inscription refusée : " + email + " déjà inscrit");
            return null;
        }
        if (profil == null) {
            System.out.println("Inscription refusée : profil inconnu " + profil);
            return null;
        }
        String erreur = SelecteurStrategieProfil.choisir(profil).erreurInscription(echelonBourse, telephone);
        if (erreur != null) {
            System.out.println(erreur);
            return null;
        }
        if (canalPrefere.equals(Canal.SMS) && telephone == null) {
            System.out.println("Inscription refusée : téléphone obligatoire pour les SMS");
            return null;
        }
        if (canalPrefere.equals(Canal.COURRIER) && (adresse == null || adresse.isEmpty())) {
            System.out.println("Inscription refusée : adresse obligatoire pour le courrier");
            return null;
        }
        compteurEtudiant++;
        Etudiant etudiant = new Etudiant("ETU-" + compteurEtudiant, nom, prenom, email, telephone, adresse, profil, echelonBourse, ressources, pmr,
                canalPrefere);
        database.sauvegarderEtudiant(etudiant);
        System.out.println("Étudiant inscrit : " + etudiant.getId() + " " + etudiant.getNomComplet() + " (" + profil + ")");
        return etudiant;
    }
}
