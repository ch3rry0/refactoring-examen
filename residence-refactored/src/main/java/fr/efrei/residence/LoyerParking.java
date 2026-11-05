package fr.efrei.residence;

public class LoyerParking extends LoyerOptionFixe {

    private static final double PRIX_PARKING = 45;

    public LoyerParking(ComposantLoyer composant) {
        super(composant, PRIX_PARKING);
    }
}