package fr.efrei.residence;

public class ProfilBoursier extends ProfilStandard {
    private static final double REMISE_PAR_ECHELON = 0.05;
    private static final double REMISE_MAXIMALE = 0.30;
    private static final int PREAVIS_JOURS = 15;
    private static final int ECHELON_MINIMUM = 0;
    private static final int ECHELON_MAXIMUM = 7;

    @Override
    public double appliquerRemise(double loyer, int echelon) {
        return loyer * (1 - Math.min(echelon * REMISE_PAR_ECHELON, REMISE_MAXIMALE));
    }

    @Override
    public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return true;
    }

    @Override
    public double calculerCaution(double loyerHorsCharges) {
        return 0;
    }

    @Override
    public double fraisDossier() {
        return 0;
    }

    @Override
    public int dureePreavis() {
        return PREAVIS_JOURS;
    }

    @Override
    public double calculerPenalite(double loyerChargesComprises) {
        return 0;
    }

    @Override
    public String erreurInscription(int echelon, String telephone) {
        if (echelon < ECHELON_MINIMUM || echelon > ECHELON_MAXIMUM) {
            return "Inscription refusée : échelon de bourse invalide";
        }
        return null;
    }
}
