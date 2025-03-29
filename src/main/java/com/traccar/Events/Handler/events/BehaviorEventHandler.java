package com.traccar.Events.Handler.events;

import com.traccar.Events.Helper.UnitsConverter;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import com.traccar.Events.Servicies.Cache.CacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BehaviorEventHandler extends BaseEventHandler {

    private final double accelerationThreshold;
    private final double brakingThreshold;
    private final CacheManager cacheManager;

    public BehaviorEventHandler(@Value("${events.behavior.accelerationThreshold:0}") double accelerationThreshold,
                                @Value("${events.behavior.brakingThreshold:0}") double brakingThreshold,
                                CacheManager cacheManager) {
        this.accelerationThreshold = accelerationThreshold;
        this.brakingThreshold = brakingThreshold;
        this.cacheManager = cacheManager;
    }

    @Override
    public List<Event> analyze(Position position) {
        List<Event> events = new ArrayList<>();

        // Obtiene la última posición del dispositivo
        Position lastPosition = cacheManager.getPosition(position.getDeviceId());
        // Para evitar división por cero, verificamos que la última posición exista y que el fixTime sea distinto
        if (lastPosition != null && !position.getFixTime().equals(lastPosition.getFixTime())) {
            long timeDiff = position.getFixTime().getTime() - lastPosition.getFixTime().getTime();
            if (timeDiff <= 0) {
                return events;
            }
            // Calcula la diferencia de velocidad (en nudos) y la convierte a m/s
            double speedDiffKnots = position.getSpeed() - lastPosition.getSpeed();
            double speedDiffMps = UnitsConverter.mpsFromKnots(speedDiffKnots);
            // Calcula la aceleración (m/s²)
            double acceleration = (speedDiffMps * 1000) / timeDiff;

            if (accelerationThreshold != 0 && acceleration >= accelerationThreshold) {
                Event event = new Event();
                event.setType(Event.TYPE_ALARM);
                event.setDeviceId(position.getDeviceId());
                event.setPositionId(position.getId());
                event.set(Position.KEY_ALARM, Position.ALARM_ACCELERATION);
                events.add(event);
            } else if (brakingThreshold != 0 && acceleration <= -brakingThreshold) {
                Event event = new Event();
                event.setType(Event.TYPE_ALARM);
                event.setDeviceId(position.getDeviceId());
                event.setPositionId(position.getId());
                event.set(Position.KEY_ALARM, Position.ALARM_BRAKING);
                events.add(event);
            }
        }
        return events;
    }
}