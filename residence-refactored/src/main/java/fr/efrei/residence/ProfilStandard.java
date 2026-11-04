package fr.efrei.residence;

public class ProfilStandard implements StrategieProfil {
    private static final int MULTIPLICATEUR_SOLVABILITE = 3;
    private static final double FRAIS_DOSSIER = 100;
    private static final int PREAVIS_JOURS = 30;
    private static final double TAUX_PENALITE = 0.05;
    private static final double PENALITE_MINIMUM = 10;

    @Override
    public double appliquerRemise(double loyer, int echelon) {
        return loyer;
    }

    @Override
    public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return ressources >= loyerChargesComprises * MULTIPLICATEUR_SOLVABILITE;
    }

    @Override
    public double calculerCaution(double loyerHorsCharges) {
        return loyerHorsCharges;
    }

    @Override
    public double fraisDossier() {
        return FRAIS_DOSSIER;
    }

    @Override
    public int dureePreavis() {
        return PREAVIS_JOURS;
    }

    @Override
    public double calculerPenalite(double loyerChargesComprises) {
        return Utils.arrondir(Math.max(loyerChargesComprises * TAUX_PENALITE, PENALITE_MINIMUM));
    }
}
