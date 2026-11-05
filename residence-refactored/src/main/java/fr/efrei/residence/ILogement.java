package fr.efrei.residence;

/**
 * Opérations communes à tous les logements de la résidence.
 */
public interface ILogement {

    String getNumero();

    TypeLogement getType();

    double getSurface();

    int getEtage();

    boolean isMeuble();

    boolean estLibre();

    boolean estAccessiblePMR();

    void loger(Etudiant e);

    void liberer(Etudiant e);

    void annuler(Etudiant e);

    Etudiant getOccupant();
}
