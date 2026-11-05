package fr.efrei.residence;

public abstract class DecorateurLoyer implements ComposantLoyer {

    protected final ComposantLoyer composant;

    protected DecorateurLoyer(ComposantLoyer composant) {
        this.composant = composant;
    }
}