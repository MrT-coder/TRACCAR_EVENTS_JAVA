package com.traccar.Events.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Position {

    private long id;
    private long deviceId;
    private double speed;
    private double latitude;
    private double longitude;
    private String deviceTime; // Puede ser String o Date según tus necesidades
    private Date fixTime; // Asumimos que este es un objeto Date, puedes cambiarlo según tu implementación
    private Map<String, Object> attributes = new HashMap<>();

    // Constante para la clave de alarma
    public static final String KEY_ALARM = "alarm";
    public static final String ALARM_ACCELERATION = "acceleration";
    public static final String ALARM_BRAKING = "braking";

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
    public String getString(String key) {
        Object value = attributes.get(key);
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
}