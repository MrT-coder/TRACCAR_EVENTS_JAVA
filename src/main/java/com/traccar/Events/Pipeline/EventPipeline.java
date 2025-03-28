package com.traccar.Events.Pipeline;

import com.traccar.Events.Handler.events.BaseEventHandler;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import com.traccar.Events.Servicies.NotificationConfigCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventPipeline {

    @Autowired
    private List<BaseEventHandler> eventHandlers;

    @Autowired
    private NotificationConfigCache notificationConfigCache;

    public List<Event> process(Position position) {
        List<Event> result = new ArrayList<>();
        for (BaseEventHandler handler : eventHandlers) {
            Event event = handler.analyze(position);
            if (event != null) {
                // Verifica si hay notificación activa para deviceId + eventType
                boolean active = notificationConfigCache.isNotificationActive(
                        event.getDeviceId(), event.getType());
                if (active) {
                    // Solo agregamos el evento si hay notificación configurada
                    result.add(event);
                }
            }
        }
        return result;
    }
}
