package fr.efrei.residence;

public class LoyerMenage extends DecorateurLoyer {

    private final double coefficient;

    public LoyerMenage(ComposantLoyer composant, Logement logement) {
        super(composant);
        this.coefficient = SelecteurStrategieLogement.choisir(logement.getType()).coefficientMenage();
    }

    @Override
    public double calculer() {
        return composant.calculer() * coefficient;
    }
}