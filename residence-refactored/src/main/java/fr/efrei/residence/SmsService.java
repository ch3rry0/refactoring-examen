package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class SmsService implements CanalNotification {

    private static final int LONGUEUR_MINIMUM_NUMERO = 10;
    private static final int LONGUEUR_MAXIMUM_SMS = 160;
    private static final String MARQUE_TRONCATURE = "...";


    public static int compteurSms = 0;
    public static List<String[]> journal = new ArrayList<String[]>();

    public void envoyerSms(String numero, String message) {
        if (numero == null || numero.length() < LONGUEUR_MINIMUM_NUMERO) {
            System.out.println("[SMS] numéro invalide : " + numero);
            return;
        }
        if (message.length() > LONGUEUR_MAXIMUM_SMS) {
            message = message.substring(0, LONGUEUR_MAXIMUM_SMS - MARQUE_TRONCATURE.length()) + MARQUE_TRONCATURE;
        }
        compteurSms++;
        journal.add(new String[]{numero, message});
        System.out.println("[SMS] -> " + numero + " : " + message);
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        envoyerSms(etudiant.getTelephone(), sujet + " - " + message);
    }

    @Override
    public void relancer(Etudiant etudiant, Bail bail) {
        envoyerSms(etudiant.getTelephone(),
                "Campus Vert : " + Utils.euros(bail.soldeDu) + " impayés sur " + bail.getId());
    }
}
