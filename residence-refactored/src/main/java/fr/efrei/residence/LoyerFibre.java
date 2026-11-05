package fr.efrei.residence;

public class LoyerFibre extends LoyerOptionFixe {

    public LoyerFibre(ComposantLoyer composant, Logement logement) {
        super(composant, SelecteurStrategieLogement.choisir(logement.getType()).prixFibre());
    }
}