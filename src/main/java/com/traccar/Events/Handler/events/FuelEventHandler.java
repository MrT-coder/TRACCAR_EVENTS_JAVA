package com.traccar.Events.Handler.events;

import com.traccar.Events.Helper.AttributeUtil; // Importa la clase AttributeUtil
import com.traccar.Events.Helper.PositionUtil;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import com.traccar.Events.Servicies.Cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FuelEventHandler extends BaseEventHandler {

    private final CacheManager cacheManager;

    public FuelEventHandler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<Event> analyze(Position currentPosition, Position previousPosition) {
        List<Event> events = new ArrayList<>();

        // Usa PositionUtil para verificar que currentPosition es la más reciente.
        if (!PositionUtil.isLatest(cacheManager, currentPosition)) {
            return events;
        }

        // Si la posición tiene el atributo de nivel de combustible
        if (currentPosition.hasAttribute(Position.KEY_FUEL_LEVEL)) {
            double before = 0;
            double after = currentPosition.getDouble(Position.KEY_FUEL_LEVEL);
            // Si no se pasa una posición previa, consultamos la cache
            Position lastPosition = (previousPosition != null)
                ? previousPosition
                : cacheManager.getPosition(currentPosition.getDeviceId());

            if (lastPosition != null && lastPosition.hasAttribute(Position.KEY_FUEL_LEVEL)) {
                before = lastPosition.getDouble(Position.KEY_FUEL_LEVEL);
            }

            double change = after - before;

            // Si el combustible ha aumentado, se genera un evento
            if (change > 0) {
                double threshold = AttributeUtil.lookup(
                        cacheManager, Keys.EVENT_FUEL_INCREASE_THRESHOLD, currentPosition.getDeviceId());
                if (threshold > 0 && change >= threshold) {
                    // Crear el evento de aumento de combustible
                    Event event = new Event(Event.TYPE_DEVICE_FUEL_INCREASE, currentPosition.getDeviceId(), currentPosition.getId(), currentPosition.getDeviceTime());
                    event.set("before", before);
                    event.set("after", after);
                    events.add(event);
                }
            } else if (change < 0) {
                double threshold = AttributeUtil.lookup(
                        cacheManager, Keys.EVENT_FUEL_DROP_THRESHOLD, currentPosition.getDeviceId());
                if (threshold > 0 && Math.abs(change) >= threshold) {
                    // Crear el evento de disminución de combustible
                    Event event = new Event(Event.TYPE_DEVICE_FUEL_DROP, currentPosition.getDeviceId(), currentPosition.getId(), currentPosition.getDeviceTime());
                    event.set("before", before);
                    event.set("after", after);
                    events.add(event);
                }
            }
        }

        return events;
    }

    @Override
    public List<Event> analyze(Position currentPosition) {
        return analyze(currentPosition, null);
    }
}
