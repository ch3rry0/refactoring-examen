package fr.efrei.residence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LoyerDecorateurTest {

    private static final double DELTA = 0.0001;

    @Test
    void appliqueLaMajorationMeuble() {
        assertEquals(115, new LoyerMeuble(() -> 100).calculer(), DELTA);
    }

    @Test
    void appliqueLaReductionDuRezDeChaussee() {
        assertEquals(95, new LoyerRezDeChaussee(() -> 100).calculer(), DELTA);
    }

    @Test
    void appliqueLeCoefficientMenageDuTypeDeLogement() {
        Logement logement = new Colocation("B001", 80, 1, false, 4);
        assertEquals(104, new LoyerMenage(() -> 100, logement).calculer(), DELTA);
    }

    @Test
    void ajouteUneOptionFixe() {
        assertEquals(145, new LoyerParking(() -> 100).calculer(), DELTA);
    }

    @Test
    void appliqueLaRemiseDuProfil() {
        assertEquals(90, new LoyerRemise(() -> 100, new ProfilAlternant(), 0).calculer(), DELTA);
    }

    @Test
    void assembleLesCouchesDansLeBonOrdre() {
        Logement logement = new Studio("A001", 20, 0, true);
        assertEquals(457.965, AssemblageLoyer.assembler(logement, Profil.STANDARD, 0,
                java.util.Set.of(Option.MENAGE, Option.PARKING)).calculer(), DELTA);
    }
}