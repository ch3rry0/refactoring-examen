package fr.efrei.residence;

public final class SelecteurStrategieProfil {
    private SelecteurStrategieProfil() {
    }

    public static StrategieProfil choisir(Profil profil) {
        return switch (profil) {
            case STANDARD -> new ProfilStandard();
            case BOURSIER -> new ProfilBoursier();
            case ALTERNANT -> new ProfilAlternant();
            case INTERNATIONAL -> new ProfilInternational();
        };
    }
}
