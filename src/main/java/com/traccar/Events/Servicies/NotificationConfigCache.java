package com.traccar.Events.Servicies;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationConfigCache {

    // Clave: deviceId:eventType => true (existe notificación)
    private final ConcurrentHashMap<String, Boolean> activeNotifications = new ConcurrentHashMap<>();

    public void enableNotification(long deviceId, String eventType) {
        activeNotifications.put(deviceId + ":" + eventType, true);
    }

    public void disableNotification(long deviceId, String eventType) {
        activeNotifications.remove(deviceId + ":" + eventType);
    }

    public boolean isNotificationActive(long deviceId, String eventType) {
        return activeNotifications.getOrDefault(deviceId + ":" + eventType, false);
    }
}
