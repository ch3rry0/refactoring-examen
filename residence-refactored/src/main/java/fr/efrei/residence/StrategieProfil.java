package fr.efrei.residence;

public interface StrategieProfil {
    double appliquerRemise(double loyer, int echelon);
    boolean estSolvable(double ressources, double loyerChargesComprises);
    double calculerCaution(double loyerHorsCharges);
    double fraisDossier();
    int dureePreavis();
    double calculerPenalite(double loyerChargesComprises);

    default String erreurInscription(int echelon, String telephone) {
        return null;
    }

    default boolean emailSupplementaire() {
        return false;
    }
}
