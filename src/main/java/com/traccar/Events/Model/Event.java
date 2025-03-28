package com.traccar.Events.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Document(collection = "mcs_events")
public class Event {

    @Id
    private String id;

    // Datos que provienen originalmente de Message y ExtendedModel
    private long deviceId;
    private String type;
    private Map<String, Object> attributes = new LinkedHashMap<>();

    // Datos propios de Event
    private String eventTime;
    private long positionId;
    private long geofenceId = 0;
    private long maintenanceId = 0;

    // Constantes de tipos de evento (se mantienen iguales)
    public static final String ALL_EVENTS = "allEvents";
    public static final String TYPE_COMMAND_RESULT = "commandResult";
    public static final String TYPE_DEVICE_ONLINE = "deviceOnline";
    public static final String TYPE_DEVICE_UNKNOWN = "deviceUnknown";
    public static final String TYPE_DEVICE_OFFLINE = "deviceOffline";
    public static final String TYPE_DEVICE_INACTIVE = "deviceInactive";
    public static final String TYPE_QUEUED_COMMAND_SENT = "queuedCommandSent";
    public static final String TYPE_DEVICE_MOVING = "deviceMoving";
    public static final String TYPE_DEVICE_STOPPED = "deviceStopped";
    public static final String TYPE_DEVICE_OVERSPEED = "deviceOverspeed";
    public static final String TYPE_DEVICE_FUEL_DROP = "deviceFuelDrop";
    public static final String TYPE_DEVICE_FUEL_INCREASE = "deviceFuelIncrease";
    public static final String TYPE_GEOFENCE_ENTER = "geofenceEnter";
    public static final String TYPE_GEOFENCE_EXIT = "geofenceExit";
    public static final String TYPE_ALARM = "alarm";
    public static final String TYPE_IGNITION_ON = "ignitionOn";
    public static final String TYPE_IGNITION_OFF = "ignitionOff";
    public static final String TYPE_MAINTENANCE = "maintenance";
    public static final String TYPE_TEXT_MESSAGE = "textMessage";
    public static final String TYPE_DRIVER_CHANGED = "driverChanged";
    public static final String TYPE_MEDIA = "media";

    // Constructor por defecto
    public Event() {
    }

    // Constructor para crear un Event usando datos obtenidos del servicio de Position.
    // Aquí, en lugar de recibir un objeto Position, se espera que el servicio de events
    // reciba un DTO o los datos necesarios ya extraídos de Position.
    public Event(String type, long deviceId, long positionId, String eventTime) {
        this.type = type;
        this.deviceId = deviceId;
        this.positionId = positionId;
        this.eventTime = eventTime;
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
       this.id = id;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public long getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(long geofenceId) {
        this.geofenceId = geofenceId;
    }

    public long getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(long maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = Objects.requireNonNullElseGet(attributes, LinkedHashMap::new);
    }

    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    public void set(String key, Boolean value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, Byte value) {
        if (value != null) {
            attributes.put(key, value.intValue());
        }
    }

    public void set(String key, Short value) {
        if (value != null) {
            attributes.put(key, value.intValue());
        }
    }

    public void set(String key, Integer value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, Long value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, Float value) {
        if (value != null) {
            attributes.put(key, value.doubleValue());
        }
    }

    public void set(String key, Double value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, String value) {
        if (value != null && !value.isEmpty()) {
            attributes.put(key, value);
        }
    }
}
