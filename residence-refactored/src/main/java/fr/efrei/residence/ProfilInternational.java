package fr.efrei.residence;

public class ProfilInternational extends ProfilStandard {
    private static final int MOIS_CAUTION = 2;
    private static final double FRAIS_DOSSIER = 150;

    @Override
    public double calculerCaution(double loyerHorsCharges) {
        return loyerHorsCharges * MOIS_CAUTION;
    }

    @Override
    public double fraisDossier() {
        return FRAIS_DOSSIER;
    }

    @Override
    public String erreurInscription(int echelon, String telephone) {
        if (telephone == null || !telephone.startsWith("+")) {
            return "Inscription refusée : numéro international (+XX) obligatoire";
        }
        return null;
    }

    @Override
    public boolean emailSupplementaire() {
        return true;
    }
}
