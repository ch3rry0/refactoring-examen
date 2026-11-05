package fr.efrei.residence;

import java.util.Set;

public class CalculateurLoyer {


    /**
     * Loyer mensuel hors charges, options comprises, remise déduite.
     */
    public double calculerLoyer(Logement l, Profil profil, int echelon, Set<Option> options) {
        return Utils.arrondir(AssemblageLoyer.assembler(l, profil, echelon, options).calculer());
    }

    public double calculerCharges(Logement l) {
        return SelecteurStrategieLogement.choisir(l.getType()).charges();
    }
}
