package com.traccar.Events.Servicies.Cache;

import com.traccar.Events.Model.Position;

import java.util.List;

import com.traccar.Events.Model.Maintenance;  // Importa la clase Maintenance

public interface CacheManager {
    Position getPosition(long deviceId);
    
    // Agrega el método para obtener objetos Maintenance
    List<Maintenance> getDeviceObjects(long deviceId, Class<Maintenance> type);

    Object getObject(String key, long deviceId);
}
