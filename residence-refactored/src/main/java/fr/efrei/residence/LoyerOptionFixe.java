package fr.efrei.residence;

public class LoyerOptionFixe extends DecorateurLoyer {

    private final double montant;

    public LoyerOptionFixe(ComposantLoyer composant, double montant) {
        super(composant);
        this.montant = montant;
    }

    @Override
    public double calculer() {
        return composant.calculer() + montant;
    }
}