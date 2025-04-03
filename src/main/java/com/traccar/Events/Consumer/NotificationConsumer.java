package com.traccar.Events.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.traccar.Events.Model.Notification;
import com.traccar.Events.Servicies.NotificationConfigCache;  // Tu caché de notificaciones

@Component
public class NotificationConsumer {

    private final NotificationConfigCache notificationConfigCache;

    public NotificationConsumer(NotificationConfigCache notificationConfigCache) {
        this.notificationConfigCache = notificationConfigCache;
    }

    // Escuchar la cola de notificaciones
    @RabbitListener(queues = "notificationQueue")
    public void onNotificationReceived(Notification notification) {
        // Aquí procesamos la notificación según el tipo de acción (create, update, delete)
        switch (notification.getAction()) {
            case "create":
                enableNotification(notification);
                break;
            case "delete":
                disableNotification(notification);
                break;
            case "update":
                // Si fuera necesario, podrías agregar lógica para manejar actualizaciones de notificaciones
                break;
            default:
                throw new IllegalArgumentException("Acción no soportada: " + notification.getAction());
        }
    }

    /**
     * Habilita la notificación en la caché (cuando se crea una notificación)
     */
    private void enableNotification(Notification notification) {
        // Llamamos a la caché para habilitar la notificación para ese deviceId y eventType
        notificationConfigCache.enableNotification(notification.getDeviceId(), notification.getEventType());
        System.out.println("Notificación habilitada para deviceId: " + notification.getDeviceId() + ", eventType: " + notification.getEventType());
    }

    /**
     * Deshabilita la notificación en la caché (cuando se elimina una notificación)
     */
    private void disableNotification(Notification notification) {
        // Llamamos a la caché para deshabilitar la notificación para ese deviceId y eventType
        notificationConfigCache.disableNotification(notification.getDeviceId(), notification.getEventType());
        System.out.println("Notificación deshabilitada para deviceId: " + notification.getDeviceId() + ", eventType: " + notification.getEventType());
    }
}
