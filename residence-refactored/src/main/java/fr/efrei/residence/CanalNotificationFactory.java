package fr.efrei.residence;

public final class CanalNotificationFactory {

    private CanalNotificationFactory() {
    }

    public static CanalNotification creer(Canal canal) {
        switch (canal) {
            case EMAIL:
                return new EmailService();
            case SMS:
                return new SmsService();
            case COURRIER:
                return new CourrierService();
            default:
                throw new IllegalArgumentException("Canal inconnu : " + canal);
        }
    }
}