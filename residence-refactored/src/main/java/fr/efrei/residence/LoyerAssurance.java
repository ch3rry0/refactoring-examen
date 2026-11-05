package fr.efrei.residence;

public class LoyerAssurance extends LoyerOptionFixe {

    private static final double PRIX_ASSURANCE = 9;

    public LoyerAssurance(ComposantLoyer composant) {
        super(composant, PRIX_ASSURANCE);
    }
}