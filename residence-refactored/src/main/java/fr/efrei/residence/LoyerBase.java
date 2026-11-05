package fr.efrei.residence;

public class LoyerBase implements ComposantLoyer {

    private final Logement logement;

    public LoyerBase(Logement logement) {
        this.logement = logement;
    }

    @Override
    public double calculer() {
        return SelecteurStrategieLogement.choisir(logement.getType()).loyerBase(logement);
    }
}