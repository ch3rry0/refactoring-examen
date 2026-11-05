package fr.efrei.residence;

import java.util.List;
import java.util.EnumMap;
import java.util.Map;

public class ServiceRapport {

    private static final double POURCENTAGE = 100.0;

    private final BailRepository database;
    private final LogementRepository logements;
    private final CanalNotification sms;
    private final CanalNotification courrier;

    public ServiceRapport(BailRepository database, LogementRepository logements,
                          CanalNotification sms, CanalNotification courrier) {
        this.database = database;
        this.logements = logements;
        this.sms = sms;
        this.courrier = courrier;
    }

    public void afficherRapport() {
        System.out.println();
        System.out.println(genererRapport());
    }

    public String genererRapport() {
        String rapport = "";
        rapport += "=============== RAPPORT " + Residence.NOM + " ===============" + "\n";
        Map<TypeLogement, Integer> places = new EnumMap<>(TypeLogement.class);
        Map<TypeLogement, Integer> occupants = new EnumMap<>(TypeLogement.class);
        double surfaceBalcons = 0;
        for (Logement logement : logements.tousLesLogements()) {
            StrategieLogement strategie = SelecteurStrategieLogement.choisir(logement.getType());
            TypeLogement type = logement.getType();
            int nombrePlaces = places.getOrDefault(type, 0) + logement.getNombrePlaces();
            int nombreOccupants = occupants.getOrDefault(type, 0) + strategie.nombreOccupants(logement);
            places.put(type, nombrePlaces);
            occupants.put(type, nombreOccupants);
            try {
                surfaceBalcons += logement.getSurfaceBalcon();
            } catch (UnsupportedOperationException ex) {
            }
        }
        int nbStudios = places.getOrDefault(TypeLogement.STUDIO, 0);
        int nbStudiosOccupes = occupants.getOrDefault(TypeLogement.STUDIO, 0);
        int nbT1 = places.getOrDefault(TypeLogement.T1, 0);
        int nbT1Occupes = occupants.getOrDefault(TypeLogement.T1, 0);
        int nbT2 = places.getOrDefault(TypeLogement.T2, 0);
        int nbT2Occupes = occupants.getOrDefault(TypeLogement.T2, 0);
        int nbPlacesColoc = places.getOrDefault(TypeLogement.COLOCATION, 0);
        int nbPlacesColocOccupees = occupants.getOrDefault(TypeLogement.COLOCATION, 0);
        rapport += "Studios      : " + nbStudiosOccupes + "/" + nbStudios + "\n";
        rapport += "T1           : " + nbT1Occupes + "/" + nbT1 + "\n";
        rapport += "T2           : " + nbT2Occupes + "/" + nbT2 + "\n";
        rapport += "Colocation   : " + nbPlacesColocOccupees + "/" + nbPlacesColoc + " places" + "\n";
        int total = nbStudios + nbT1 + nbT2 + nbPlacesColoc;
        int occupes = nbStudiosOccupes + nbT1Occupes + nbT2Occupes + nbPlacesColocOccupees;
        rapport += "Occupation   : " + Math.round(occupes * POURCENTAGE / total) + " %" + "\n";
        rapport += "Balcons      : " + surfaceBalcons + " m²" + "\n";

        double loyersMensuels = 0;
        double impayes = 0;
        double cautions = 0;
        List<Bail> baux = database.tousLesBaux();
        for (Bail bail : baux) {
            if (bail.getStatut().equals(StatutBail.ACTIF) || bail.getStatut().equals(StatutBail.PREAVIS)) {
                loyersMensuels += bail.getLoyerChargesComprises();
                cautions += bail.getCaution();
            }
            if (bail.soldeDu > 0) impayes += bail.soldeDu;
        }
        rapport += "Loyers CC    : " + Utils.euros(Utils.arrondir(loyersMensuels)) + " / mois" + "\n";
        rapport += "Cautions     : " + Utils.euros(Utils.arrondir(cautions)) + "\n";
        rapport += "Impayés      : " + Utils.euros(Utils.arrondir(impayes)) + "\n";
        rapport += "SMS envoyés  : " + sms.nombreEnvois() + "\n";
        rapport += "Timbres      : " + Utils.euros(Utils.arrondir(courrier.coutTotal())) + "\n";
        rapport += "============================================================";
        return rapport;
    }
}
