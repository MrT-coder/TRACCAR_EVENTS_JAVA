package com.traccar.Events.Servicies.Cache;

import com.traccar.Events.Model.Maintenance;
import com.traccar.Events.Model.Position;
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
        positions.put(position.getDeviceId(), position);
    }

    @Override
    public Position getPosition(long deviceId) {
        return positions.get(deviceId);
    }

    // Método para obtener objetos de tipo Maintenance
    @Override
    public List<Maintenance> getDeviceObjects(long deviceId, Class<Maintenance> type) {
        return deviceMaintenanceMap.getOrDefault(deviceId, new ArrayList<>());
    }

    // Método para agregar un mantenimiento a un dispositivo
    public void addMaintenance(long deviceId, Maintenance maintenance) {
        deviceMaintenanceMap.computeIfAbsent(deviceId, k -> new ArrayList<>()).add(maintenance);
    }

    @Override
    public Object getObject(String key, long deviceId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getObject'");
    }
}
