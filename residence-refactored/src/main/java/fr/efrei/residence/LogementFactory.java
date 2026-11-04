package fr.efrei.residence;

import java.util.Map;

public final class LogementFactory {

    private static final int PLACES_MINIMUM_COLOCATION = 2;

    private interface ConstructeurLogement {
        Logement creer(String numero, double surface, int etage, boolean meuble, int param);
    }

    private static final Map<TypeLogement, ConstructeurLogement> CONSTRUCTEURS = Map.of(
            TypeLogement.STUDIO, (numero, surface, etage, meuble, param) ->
                    new Studio(numero, surface, etage, meuble),
            TypeLogement.T1, (numero, surface, etage, meuble, param) ->
                    new T1(numero, surface, etage, meuble),
            TypeLogement.T2, (numero, surface, etage, meuble, param) ->
                    new T2(numero, surface, etage, meuble, param),
            TypeLogement.COLOCATION, (numero, surface, etage, meuble, param) ->
                    new Colocation(numero, surface, etage, meuble, Math.max(param, PLACES_MINIMUM_COLOCATION))
    );

    private static final ConstructeurLogement TYPE_INCONNU =
            (numero, surface, etage, meuble, param) -> null;

    private LogementFactory() {
    }

    public static Logement creer(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        ConstructeurLogement constructeur = CONSTRUCTEURS.getOrDefault(type, TYPE_INCONNU);
        return constructeur.creer(numero, surface, etage, meuble, param);
    }
}
