package fr.efrei.residence;

public final class SelecteurStrategieLogement {
    private SelecteurStrategieLogement() {
    }

    public static StrategieLogement choisir(TypeLogement type) {
        return switch (type) {
            case STUDIO -> new LoyerStudio();
            case T1 -> new LoyerT1();
            case T2 -> new LoyerT2();
            case COLOCATION -> new LoyerColocation();
            default -> throw new IllegalArgumentException("Type de logement inconnu : " + type);
        };
    }
}
