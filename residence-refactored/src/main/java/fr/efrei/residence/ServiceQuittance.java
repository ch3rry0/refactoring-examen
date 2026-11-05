package fr.efrei.residence;

import java.util.StringJoiner;

public class ServiceQuittance {

    public String genererQuittance(Bail bail, String mois) {
        Paiement paiement = null;
        for (Paiement paiementDuBail : bail.paiements) {
            if (paiementDuBail.mois.equals(mois)) paiement = paiementDuBail;
        }
        if (paiement == null) {
            return "Aucun paiement pour " + mois;
        }
        Etudiant etudiant = bail.getEtudiant();
        Logement logement = bail.getLogement();
        String quittance = "";
        quittance += "==========================================\n";
        quittance += "   RÉSIDENCE CAMPUS VERT - QUITTANCE\n";
        quittance += "==========================================\n";
        quittance += "Bail      : " + bail.getId() + "\n";
        quittance += "Locataire : " + etudiant.getNomComplet() + " (" + etudiant.getProfil() + ")\n";
        quittance += "Logement  : " + logement.getNumero() + " - " + logement.getType() + " " + logement.getSurface() + " m²";
        if (logement.isMeuble()) quittance += " meublé";
        quittance += "\n";
        quittance += "Période   : " + mois + "\n";
        quittance += "Options   : " + listerOptions(bail) + "\n";
        quittance += "------------------------------------------\n";
        quittance += "Loyer HC  : " + Utils.euros(bail.getLoyerHorsCharges()) + "\n";
        quittance += "Charges   : " + Utils.euros(bail.getCharges()) + "\n";
        if (paiement.penalite > 0) {
            quittance += "Pénalité  : " + Utils.euros(paiement.penalite) + "\n";
        }
        quittance += "Total dû  : " + Utils.euros(paiement.du) + "\n";
        quittance += "Payé le   : " + Utils.date(paiement.date) + " : " + Utils.euros(paiement.montant) + "\n";
        if (paiement.montant < paiement.du) {
            quittance += "RESTE DÛ  : " + Utils.euros(Utils.arrondir(paiement.du - paiement.montant)) + "\n";
        }
        quittance += "==========================================";
        return quittance;
    }

    private String listerOptions(Bail bail) {
        StringJoiner options = new StringJoiner(", ");
        for (Option option : Option.values()) {
            if (bail.getOptions().contains(option)) {
                options.add(option.getLibelle());
            }
        }
        if (options.length() == 0) return "aucune";
        return options.toString();
    }
}
