package com.traccar.Events.Handler.events;

import com.traccar.Events.Helper.PositionUtil; // Importa la clase que acabamos de crear
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import com.traccar.Events.Servicies.Cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DriverEventHandler extends BaseEventHandler {

    private final CacheManager cacheManager;

    public DriverEventHandler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    // Versión que recibe tanto la posición actual como la previa
    @Override
    public List<Event> analyze(Position currentPosition, Position previousPosition) {
        List<Event> events = new ArrayList<>();

        // Usa PositionUtil para verificar que currentPosition es la más reciente.
        if (!PositionUtil.isLatest(cacheManager, currentPosition)) {
            return events;
        }

        String driverUniqueId = currentPosition.getString(Position.KEY_DRIVER_UNIQUE_ID);
        if (driverUniqueId != null) {
            String oldDriverUniqueId = null;
            // Si se pasa una posición previa, la usamos; de lo contrario, consultamos la cache.
            Position lastPosition = (previousPosition != null) 
                ? previousPosition 
                : cacheManager.getPosition(currentPosition.getDeviceId());
            if (lastPosition != null) {
                oldDriverUniqueId = lastPosition.getString(Position.KEY_DRIVER_UNIQUE_ID);
            }
            // Si no había identificador previo o ha cambiado, se genera el evento.
            if (oldDriverUniqueId == null || !driverUniqueId.equals(oldDriverUniqueId)) {
                Event event = new Event();
                event.setType(Event.TYPE_DRIVER_CHANGED);
                event.setDeviceId(currentPosition.getDeviceId());
                event.setPositionId(currentPosition.getId());
                event.set(Position.KEY_DRIVER_UNIQUE_ID, driverUniqueId);
                events.add(event);
            }
        }
        return events;
    }

    // Versión simple que delega a la versión con dos parámetros.
    @Override
    public List<Event> analyze(Position currentPosition) {
        return analyze(currentPosition, null);
    }
}
