package fr.efrei.residence;

import java.util.Set;

public final class AssemblageLoyer {

    private AssemblageLoyer() {
    }

    public static ComposantLoyer assembler(Logement logement, Profil profil, int echelon, Set<Option> options) {
        ComposantLoyer loyer = new LoyerBase(logement);
        if (logement.isMeuble()) {
            loyer = new LoyerMeuble(loyer);
        }
        if (logement.getEtage() == 0) {
            loyer = new LoyerRezDeChaussee(loyer);
        }
        if (options.contains(Option.MENAGE)) {
            loyer = new LoyerMenage(loyer, logement);
        }
        if (options.contains(Option.PARKING)) {
            loyer = new LoyerParking(loyer);
        }
        if (options.contains(Option.LAVERIE)) {
            loyer = new LoyerLaverie(loyer);
        }
        if (options.contains(Option.FIBRE)) {
            loyer = new LoyerFibre(loyer, logement);
        }
        if (options.contains(Option.ASSURANCE)) {
            loyer = new LoyerAssurance(loyer);
        }
        return new LoyerRemise(loyer, SelecteurStrategieProfil.choisir(profil), echelon);
    }
}