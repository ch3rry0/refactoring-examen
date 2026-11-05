package fr.efrei.residence;

import java.util.List;

public interface LogementColocation {

    void ajouterColocataire(Etudiant etudiant);

    void retirerColocataire(Etudiant etudiant);

    List<Etudiant> getColocataires();

    void nettoyerPartiesCommunes();
}