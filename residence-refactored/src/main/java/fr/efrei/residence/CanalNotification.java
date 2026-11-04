package fr.efrei.residence;

public interface CanalNotification {

    void envoyer(Etudiant etudiant, String sujet, String message);

    void relancer(Etudiant etudiant, Bail bail);
}