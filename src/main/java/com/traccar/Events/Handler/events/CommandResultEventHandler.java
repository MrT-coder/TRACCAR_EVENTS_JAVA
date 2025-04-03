package com.traccar.Events.Handler.events;

import com.traccar.Events.Model.Event;
import com.traccar.Events.Repository.EventRepository;  // Asegúrate de importar el repositorio
import com.traccar.Events.Model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandResultEventHandler extends BaseEventHandler {

    @Autowired
    private EventRepository eventRepository;  // Inyectamos el repositorio de eventos

    @Override
    public List<Event> analyze(Position currentPosition) {
        List<Event> events = new ArrayList<>();
        Object commandResult = currentPosition.getAttributes().get(Position.KEY_RESULT);
        if (commandResult != null) {
            Event event = new Event();
            event.setType(Event.TYPE_COMMAND_RESULT);
            event.setDeviceId(currentPosition.getDeviceId());
            event.setPositionId(currentPosition.getId());
            event.set(Position.KEY_RESULT, (String) commandResult);

            // Guardamos el evento en la base de datos
            eventRepository.save(event);  // Esto guardará el evento en la base de datos MongoDB

            events.add(event);
        } else {
            System.out.println("No hay resultado de comando para el dispositivo " + currentPosition.getDeviceId());
        }

        return events;
    }
}
