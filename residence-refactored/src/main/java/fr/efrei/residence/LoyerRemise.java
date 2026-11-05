package fr.efrei.residence;

public class LoyerRemise extends DecorateurLoyer {

    private final StrategieProfil strategie;
    private final int echelon;

    public LoyerRemise(ComposantLoyer composant, StrategieProfil strategie, int echelon) {
        super(composant);
        this.strategie = strategie;
        this.echelon = echelon;
    }

    @Override
    public double calculer() {
        return strategie.appliquerRemise(composant.calculer(), echelon);
    }
}