package fr.efrei.residence;

import java.util.List;

public interface LogementRepository {
    Logement trouver(String numero);
    void ajouter(Logement logement);
    List<Logement> tousLesLogements();
}
