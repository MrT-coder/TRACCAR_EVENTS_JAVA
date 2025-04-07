package com.traccar.Events.Servicies.Cache;

import com.traccar.Events.Model.Position;
import com.traccar.Events.Model.Maintenance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

@Component
public class InMemoryCacheManager implements CacheManager {

    private final ConcurrentHashMap<Long, Position> positions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, List<Maintenance>> deviceMaintenanceMap = new ConcurrentHashMap<>();

    // Nuevo: Mapa para atributos personalizados por deviceId
    private final ConcurrentHashMap<Long, ConcurrentHashMap<String, Object>> deviceAttributes = new ConcurrentHashMap<>();

    // Método para actualizar la posición de un dispositivo
    public void updatePosition(Position position) {
        Position previousPosition = positions.get(position.getDeviceId());

        if (previousPosition == null) {
            System.out.println("No hay posición previa registrada para deviceId " + position.getDeviceId());
        } else {
            System.out.println("Posición previa para deviceId " + position.getDeviceId() + " encontrada.");
        }

        positions.put(position.getDeviceId(), position);
    }

    @Override
    public Position getPosition(long deviceId) {
        return positions.get(deviceId);
    }

    @Override
    public List<Maintenance> getDeviceObjects(long deviceId, Class<Maintenance> type) {
        return deviceMaintenanceMap.getOrDefault(deviceId, new ArrayList<>());
    }

    // Nuevo: guardar atributo personalizado para un deviceId
    public void set(String key, Object value, long deviceId) {
        deviceAttributes
            .computeIfAbsent(deviceId, id -> new ConcurrentHashMap<>())
            .put(key, value);
    }

    // Mejorado: ahora también devuelve atributos personalizados por clave
    @Override
    public Object getObject(String key, long deviceId) {
        // Primero: buscar en atributos personalizados
        ConcurrentHashMap<String, Object> attributes = deviceAttributes.get(deviceId);
        if (attributes != null && attributes.containsKey(key)) {
            return attributes.get(key);
        }

        // Luego: soporte anterior para position y maintenance
        if ("position".equals(key)) {
            return positions.get(deviceId);
        } else if ("maintenance".equals(key)) {
            return deviceMaintenanceMap.get(deviceId);
        }

        return null;
    }
}
