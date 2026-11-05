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
    void ajouteLePrixDeLaLaverie() {
        assertEquals(112, new LoyerLaverie(() -> 100).calculer(), DELTA);
    }

    @Test
    void ajouteLePrixDeLaFibreSaufEnColocation() {
        Logement studio = new Studio("A001", 20, 1, false);
        Logement colocation = new Colocation("B001", 80, 1, false, 4);
        assertEquals(120, new LoyerFibre(() -> 100, studio).calculer(), DELTA);
        assertEquals(100, new LoyerFibre(() -> 100, colocation).calculer(), DELTA);
    }

    @Test
    void ajouteLePrixDeLAssurance() {
        assertEquals(109, new LoyerAssurance(() -> 100).calculer(), DELTA);
    }

    @Test
    void appliqueLaRemiseDuProfil() {
        assertEquals(90, new LoyerRemise(() -> 100, new ProfilAlternant(), 0).calculer(), DELTA);
    }
}