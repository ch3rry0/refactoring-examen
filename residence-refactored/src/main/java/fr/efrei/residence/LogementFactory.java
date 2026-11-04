package fr.efrei.residence;

public final class LogementFactory {

    private static final int PLACES_MINIMUM_COLOCATION = 2;

    private LogementFactory() {
    }

    public static Logement creer(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        switch (type) {
            case STUDIO:
                return new Studio(numero, surface, etage, meuble);
            case T1:
                return new T1(numero, surface, etage, meuble);
            case T2:
                return new T2(numero, surface, etage, meuble, param);
            case COLOCATION:
                return new Colocation(numero, surface, etage, meuble, Math.max(param, PLACES_MINIMUM_COLOCATION));
            default:
                return null;
        }
    }
}