package com.traccar.Events.Servicies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.traccar.Events.Model.Event;
import com.traccar.Events.Repository.EventRepository;

import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event getEventById(String id, long currentUserId) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (!eventOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with id: " + id);
        }
        Event event = eventOpt.get();

        // Aquí iría la lógica de permisos:
        // permissionsService.checkPermission(Device.class, currentUserId, event.getDeviceId());

        return event;
    }
}
