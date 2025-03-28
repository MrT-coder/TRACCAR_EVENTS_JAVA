package com.traccar.Events.Servicies;

import com.traccar.Events.Messaging.EventData;
import com.traccar.Events.Messaging.EventForwarder;
import com.traccar.Events.Messaging.ResultHandler;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventForwarder eventForwarder;

    public Event storeEvent(Event event) {
        // Persiste en MongoDB
        Event savedEvent = eventRepository.save(event);

        // Reenvía a la cola "eventsQueue" (p. ej. para que Notifications lo procese)
        EventData eventData = new EventData();
        eventData.setEvent(savedEvent);

        eventForwarder.forward(eventData, new ResultHandler() {
            @Override
            public void onResult(boolean success, Exception e) {
                if (!success) {
                    System.err.println("Error al reenviar evento: " + e.getMessage());
                }
            }
        });
        return savedEvent;
    }

    public Event getEventById(String id, long currentUserId) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Event not found with id: " + id);
        }
        return eventOpt.get();
    }
}
