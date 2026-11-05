package fr.efrei.residence;

import java.util.Map;

public final class CanalNotificationFactory {
    private final Map<Canal, CanalNotification> canaux;

    public CanalNotificationFactory(CanalNotification email, CanalNotification sms, CanalNotification courrier) {
        canaux = Map.of(Canal.EMAIL, email, Canal.SMS, sms, Canal.COURRIER, courrier);
    }

    public CanalNotification creer(Canal canal) {
        CanalNotification notification = canaux.get(canal);
        if (notification == null) {
            throw new IllegalArgumentException("Canal inconnu : " + canal);
        }
        return notification;
    }
}
