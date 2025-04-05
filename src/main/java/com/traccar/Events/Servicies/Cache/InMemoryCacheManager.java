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

    // Método para actualizar la posición de un dispositivo
    public void updatePosition(Position position) {
        // Verifica si ya existe una posición previa en memoria
        Position previousPosition = positions.get(position.getDeviceId());

        // Si no existe una posición previa, lo indicamos en los logs
        if (previousPosition == null) {
            System.out.println("No hay posición previa registrada para deviceId " + position.getDeviceId());
        } else {
            System.out.println("Posición previa para deviceId " + position.getDeviceId() + " encontrada.");
        }

        // Actualiza la posición en la memoria
        positions.put(position.getDeviceId(), position);
    }

    @Override
    public Position getPosition(long deviceId) {
        // Devuelve la posición almacenada para el deviceId
        return positions.get(deviceId);
    }

    @Override
    public List<Maintenance> getDeviceObjects(long deviceId, Class<Maintenance> type) {
        // Recupera los objetos de mantenimiento asociados con el deviceId
        return deviceMaintenanceMap.getOrDefault(deviceId, new ArrayList<>());
    }

    @Override
    public Object getObject(String key, long deviceId) {
        // Implementación del método getObject
        // Este método devuelve diferentes objetos basados en el tipo de clave
        if ("position".equals(key)) {
            return positions.get(deviceId);  // Si la clave es "position", devuelve la posición del dispositivo
        } else if ("maintenance".equals(key)) {
            return deviceMaintenanceMap.get(deviceId);  // Si la clave es "maintenance", devuelve la lista de mantenimientos
        }
        return null;  // Si no se encuentra la clave, se retorna null
    }
}
