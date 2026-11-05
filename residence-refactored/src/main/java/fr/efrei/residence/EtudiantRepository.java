package fr.efrei.residence;

public interface EtudiantRepository {
    void sauvegarderEtudiant(Etudiant etudiant);
    Etudiant trouverEtudiantParEmail(String email);
    Etudiant trouverEtudiantParId(String id);
}
