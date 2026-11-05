package fr.efrei.residence;

public class LoyerMeuble extends DecorateurLoyer {

    private static final double COEFFICIENT = 1.15;

    public LoyerMeuble(ComposantLoyer composant) {
        super(composant);
    }

    @Override
    public double calculer() {
        return composant.calculer() * COEFFICIENT;
    }
}