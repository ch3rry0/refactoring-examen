package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class CourrierService implements CanalNotification {

    public static final double PRIX_TIMBRE = 1.29;
    private double totalAffranchissement = 0;
    private final List<String[]> journal = new ArrayList<String[]>();

    public void imprimerEtPoster(String nom, String adresse, String objet, String texte) {
        totalAffranchissement = totalAffranchissement + PRIX_TIMBRE;
        journal.add(new String[]{adresse, objet, texte});
        System.out.println("[COURRIER] " + nom + ", " + adresse + " | Objet : " + objet);
        System.out.println("        " + texte);
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        imprimerEtPoster(etudiant.getNomComplet(), etudiant.getAdresse(), sujet, message);
    }

    @Override
    public void relancer(Etudiant etudiant, Bail bail) {
        imprimerEtPoster(etudiant.getNomComplet(), etudiant.getAdresse(), "Relance loyer impayé",
                "Il reste " + Utils.euros(bail.soldeDu) + " à régler sur le bail " + bail.getId() + ".");
    }
    @Override
    public List<String[]> getJournal() {
        return List.copyOf(journal);
    }

    @Override
    public double coutTotal() {
        return totalAffranchissement;
    }
}
