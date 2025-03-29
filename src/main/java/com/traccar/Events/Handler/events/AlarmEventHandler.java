package com.traccar.Events.Handler.events;

import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import com.traccar.Events.Servicies.Cache.CacheManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AlarmEventHandler extends BaseEventHandler {

    private final CacheManager cacheManager;
    private final boolean ignoreDuplicates;

    // Se inyecta la dependencia de CacheManager y la propiedad de configuración para ignorar duplicados
    public AlarmEventHandler(CacheManager cacheManager,
                             @Value("${events.ignoreDuplicateAlerts:true}") boolean ignoreDuplicates) {
        this.cacheManager = cacheManager;
        this.ignoreDuplicates = ignoreDuplicates;
    }

    @Override
    public List<Event> analyze(Position position) {
        List<Event> detectedEvents = new ArrayList<>();
        String alarmString = position.getString(Position.KEY_ALARM);
        if (alarmString != null) {
            // Separamos las alarmas que vienen en el string (se asume que están separadas por comas)
            Set<String> alarms = new HashSet<>(Arrays.asList(alarmString.split(",")));
            if (ignoreDuplicates) {
                Position lastPosition = cacheManager.getPosition(position.getDeviceId());
                if (lastPosition != null) {
                    String lastAlarmString = lastPosition.getString(Position.KEY_ALARM);
                    if (lastAlarmString != null) {
                        Set<String> lastAlarms = new HashSet<>(Arrays.asList(lastAlarmString.split(",")));
                        alarms.removeAll(lastAlarms);
                    }
                }
            }
            // Para cada alarma restante, se crea un evento de tipo ALARM
            for (String alarm : alarms) {
                Event event = new Event();
                event.setType(Event.TYPE_ALARM);
                event.setDeviceId(position.getDeviceId());
                event.setPositionId(position.getId());
                // Se asume que el modelo Event tiene un método set(key, value) para agregar atributos
                event.set(Position.KEY_ALARM, alarm);
                detectedEvents.add(event);
            }
        }
        return detectedEvents;
    }
}
