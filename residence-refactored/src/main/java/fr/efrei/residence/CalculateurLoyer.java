package fr.efrei.residence;

public class CalculateurLoyer {

    private static final double COEFFICIENT_MEUBLE = 1.15;
    private static final double COEFFICIENT_REZ_DE_CHAUSSEE = 0.95;
    private static final int REZ_DE_CHAUSSEE = 0;
    private static final double PRIX_PARKING = 45;
    private static final double PRIX_LAVERIE = 12;
    private static final double PRIX_ASSURANCE = 9;


    /**
     * Loyer mensuel hors charges, options comprises, remise déduite.
     */
    public double calculerLoyer(Logement l, Profil profil, int echelon, boolean parking, boolean menage, boolean laverie, boolean fibre, boolean assurance) {
        StrategieLogement strategieLogement = SelecteurStrategieLogement.choisir(l.getType());
        StrategieProfil strategieProfil = SelecteurStrategieProfil.choisir(profil);
        double loyer = strategieLogement.loyerBase(l);

        if (l.isMeuble()) loyer = loyer * COEFFICIENT_MEUBLE;
        if (l.getEtage() == REZ_DE_CHAUSSEE) loyer = loyer * COEFFICIENT_REZ_DE_CHAUSSEE;

        // options
        if (menage) loyer = loyer * strategieLogement.coefficientMenage();
        if (parking) loyer = loyer + PRIX_PARKING;
        if (laverie) loyer = loyer + PRIX_LAVERIE;
        if (fibre) loyer = loyer + strategieLogement.prixFibre();
        if (assurance) loyer = loyer + PRIX_ASSURANCE;

        loyer = strategieProfil.appliquerRemise(loyer, echelon);

        return Utils.arrondir(loyer);
    }

    public double calculerCharges(Logement l) {
        return SelecteurStrategieLogement.choisir(l.getType()).charges();
    }
}
