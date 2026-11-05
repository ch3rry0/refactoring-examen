package fr.efrei.residence;

import java.util.List;

public interface CanalNotification {

    void envoyer(Etudiant etudiant, String sujet, String message);

    void relancer(Etudiant etudiant, Bail bail);
    List<String[]> getJournal();

    default int nombreEnvois() {
        return getJournal().size();
    }

    default double coutTotal() {
        return 0;
    }
}