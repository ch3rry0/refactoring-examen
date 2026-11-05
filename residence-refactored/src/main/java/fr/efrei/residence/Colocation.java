package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class Colocation extends Logement implements LogementColocation {

    private int places;
    private List<Etudiant> colocataires = new ArrayList<Etudiant>();

    public Colocation(String numero, double surface, int etage, boolean meuble, int places) {
        super(numero, TypeLogement.COLOCATION, surface, etage, meuble);
        this.places = places;
    }

    @Override
    public void loger(Etudiant e) {
        ajouterColocataire(e);
    }

    @Override
    public void liberer(Etudiant e) {
        retirerColocataire(e);
        nettoyerPartiesCommunes();
    }

    @Override
    public boolean estLibre() {
        return colocataires.size() < places;
    }

    @Override
    public Etudiant getOccupant() {
        if (colocataires.isEmpty()) return null;
        return colocataires.get(0);
    }

    @Override
    public int getNombrePlaces() {
        return places;
    }

    @Override
    public int getNombreOccupants() {
        return colocataires.size();
    }

    public void ajouterColocataire(Etudiant e) {
        if (colocataires.size() >= places) {
            throw new IllegalStateException("Colocation " + numero + " complète");
        }
        colocataires.add(e);
    }

    public void retirerColocataire(Etudiant e) {
        colocataires.remove(e);
    }

    public List<Etudiant> getColocataires() {
        return colocataires;
    }

    public void nettoyerPartiesCommunes() {
        System.out.println("[ENTRETIEN] Nettoyage des parties communes de la colocation " + numero);
    }

    @Override
    public void annuler(Etudiant e) {
        retirerColocataire(e);
    }
}
