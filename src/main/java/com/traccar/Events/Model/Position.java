package com.traccar.Events.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Position {

    private long id;
    private long deviceId;
    private double speed;
    private double latitude;
    private double longitude;
    private String deviceTime; // Puede ser String o Date según tus necesidades
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'") // Formato de fecha, ajusta según tu necesidad
    private Date fixTime; // Asumimos que este es un objeto Date, puedes cambiarlo según tu implementación
    private Map<String, Object> attributes = new HashMap<>();
// Define la constante para el driverUniqueId:
    public static final String KEY_DRIVER_UNIQUE_ID = "driverUniqueId"; 
    // Constante para la clave de alarma
    public static final String KEY_ALARM = "alarm";
    public static final String ALARM_ACCELERATION = "acceleration";
    public static final String ALARM_BRAKING = "braking";
    public static final String KEY_RESULT = "result";
    public static final String KEY_FUEL_LEVEL = "fuelLevel";
    public static final String KEY_IGNITION ="ignition";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_VIDEO = "video";
    public static final String KEY_AUDIO = "audio";

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(String deviceTime) {
        this.deviceTime = deviceTime;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    // Método para obtener un atributo como String
    public String getString(Object type) {
        Object value = attributes.get(type);
        return value != null ? value.toString() : null;
    }

    // Método para establecer un atributo
    public void set(String key, Object value) {
        attributes.put(key, value);
    }

   public Date getFixTime() {
        return fixTime;
    }

    public void setFixTime(Date fixTime) {
        this.fixTime = fixTime;
    }
    public boolean hasAttribute(Object type) {
        return attributes.containsKey(type);
    }

   // Método para obtener un valor como Double
   public boolean getBoolean(String key) {
    Object value = attributes.get(key);
    if (value instanceof Boolean) {
        return (Boolean) value;  // Si el valor es un booleano, retorna ese valor
    } else if (value instanceof String) {
        // Si el valor es una cadena, intenta convertirla a booleano
        return Boolean.parseBoolean((String) value);
    }
    return false;  // Si no es ni Boolean ni String, retorna false por defecto
}

public double getDouble(String key) {
    Object value = attributes.get(key);
    if (value instanceof Number) {
        return ((Number) value).doubleValue();
    } else if (value instanceof String) {
        try {
            return Double.parseDouble((String) value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("No se puede convertir el valor a double: " + value);
        }
    }
    throw new IllegalArgumentException("El atributo '" + key + "' no es un número válido.");
}



    

    
}