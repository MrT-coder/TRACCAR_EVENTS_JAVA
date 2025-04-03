package com.traccar.Events.Handler.events;

import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class MediaEventHandler extends BaseEventHandler {

    @Override
    public List<Event> analyze(Position currentPosition, Position previousPosition) {
        List<Event> events = new ArrayList<>();

        // Comprobamos si la posición tiene algún tipo de medio (imagen, audio o video)
        Stream.of(Position.KEY_IMAGE, Position.KEY_VIDEO, Position.KEY_AUDIO)
                .filter(currentPosition::hasAttribute)  // Filtramos por los atributos presentes
                .map(type -> {
                    // Establecemos el tipo de media
                    Event event = new Event(Event.TYPE_MEDIA, currentPosition.getDeviceId(), currentPosition.getId(), currentPosition.getFixTime().toString());
                    event.set("media", type);  // Establecemos el tipo de media
                    event.set("file", currentPosition.getString(type));  // Establecemos el archivo
                    return event;
                })
                .forEach(events::add);  // Agregamos el evento a la lista de eventos

        return events;
    }

    // Versión simple que delega a la versión con dos parámetros
    @Override
    public List<Event> analyze(Position currentPosition) {
        return analyze(currentPosition, null);
    }
}
