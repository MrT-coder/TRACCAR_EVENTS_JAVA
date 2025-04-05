package com.traccar.Events.Handler.events;

import com.traccar.Events.Helper.PositionUtil;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import com.traccar.Events.Servicies.Cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IgnitionEventHandler extends BaseEventHandler {

    private final CacheManager cacheManager;

    public IgnitionEventHandler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<Event> analyze(Position currentPosition, Position previousPosition) {
        List<Event> events = new ArrayList<>();

        // Usa PositionUtil para verificar que currentPosition es la más reciente.
        if (!PositionUtil.isLatest(cacheManager, currentPosition)) {
            return events;
        }

        // Verifica si la posición tiene el atributo de ignición
        if (currentPosition.hasAttribute(Position.KEY_IGNITION)) {
            boolean ignition = currentPosition.getBoolean(Position.KEY_IGNITION);


            // Obtén la última posición si no se pasa la posición previa
            Position lastPosition = (previousPosition != null) 
                ? previousPosition 
                : cacheManager.getPosition(currentPosition.getDeviceId());

            if (lastPosition != null && lastPosition.hasAttribute(Position.KEY_IGNITION)) {
                boolean oldIgnition = lastPosition.getBoolean(Position.KEY_IGNITION);

                // Si la ignición cambió de apagado a encendido, genera el evento de "IGNITION_ON"
                if (ignition && !oldIgnition) {
                    Event event = new Event(Event.TYPE_IGNITION_ON, currentPosition.getDeviceId(), currentPosition.getId(), currentPosition.getDeviceTime());
                    events.add(event);
                }
                // Si la ignición cambió de encendido a apagado, genera el evento de "IGNITION_OFF"
                else if (!ignition && oldIgnition) {
                    Event event = new Event(Event.TYPE_IGNITION_OFF, currentPosition.getDeviceId(), currentPosition.getId(), currentPosition.getDeviceTime());
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
