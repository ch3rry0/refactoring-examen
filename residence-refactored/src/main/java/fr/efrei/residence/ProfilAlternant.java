package fr.efrei.residence;

public class ProfilAlternant extends ProfilStandard {
    private static final double COEFFICIENT_REMISE = 0.90;
    private static final int MULTIPLICATEUR_SOLVABILITE = 2;
    private static final int PREAVIS_JOURS = 15;

    @Override
    public double appliquerRemise(double loyer, int echelon) {
        return loyer * COEFFICIENT_REMISE;
    }

    @Override
    public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return ressources >= loyerChargesComprises * MULTIPLICATEUR_SOLVABILITE;
    }

    @Override
    public int dureePreavis() {
        return PREAVIS_JOURS;
    }
}
