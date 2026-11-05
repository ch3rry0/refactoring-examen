package fr.efrei.residence;

public class LoyerLaverie extends LoyerOptionFixe {

    private static final double PRIX_LAVERIE = 12;

    public LoyerLaverie(ComposantLoyer composant) {
        super(composant, PRIX_LAVERIE);
    }
}