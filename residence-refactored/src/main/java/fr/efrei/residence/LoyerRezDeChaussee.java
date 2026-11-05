package fr.efrei.residence;

public class LoyerRezDeChaussee extends DecorateurLoyer {

    private static final double COEFFICIENT = 0.95;

    public LoyerRezDeChaussee(ComposantLoyer composant) {
        super(composant);
    }

    @Override
    public double calculer() {
        return composant.calculer() * COEFFICIENT;
    }
}