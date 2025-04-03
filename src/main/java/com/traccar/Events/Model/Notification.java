package com.traccar.Events.Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long deviceId;          // Identificador del dispositivo
    private String eventType;       // Tipo de evento (ej. "geofence", "speeding")
    private String action;          // Acción: "create", "update", "delete"
    private String message;         // Mensaje de la notificación

    // Campos adicionales para integrar con la lógica de Traccar original
    private String description;     // Descripción de la notificación
    private long calendarId;        // Identificador del calendario asociado
    private boolean always;         // Si la notificación debe ser siempre activa
    private String type;            // Tipo de la notificación
    private long commandId;         // Identificador del comando asociado
    private String notificators;    // Tipos de notificadores (ej. email, sms)

    // Getters y Setters

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    }

    public boolean isAlways() {
        return always;
    }

    public void setAlways(boolean always) {
        this.always = always;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCommandId() {
        return commandId;
    }

    public void setCommandId(long commandId) {
        this.commandId = commandId;
    }

    public String getNotificators() {
        return notificators;
    }

    public void setNotificators(String notificators) {
        this.notificators = notificators;
    }

    @JsonIgnore
    public Set<String> getNotificatorsTypes() {
        final Set<String> result = new HashSet<>();
        if (notificators != null) {
            final String[] transportsList = notificators.split(",");
            for (String transport : transportsList) {
                result.add(transport.trim());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "deviceId=" + deviceId +
                ", eventType='" + eventType + '\'' +
                ", action='" + action + '\'' +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                ", calendarId=" + calendarId +
                ", always=" + always +
                ", type='" + type + '\'' +
                ", commandId=" + commandId +
                ", notificators='" + notificators + '\'' +
                '}';
    }
}
