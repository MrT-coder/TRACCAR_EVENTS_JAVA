package com.traccar.Events.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.traccar.Events.Model.Event;
import com.traccar.Events.Servicies.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;


    /**
     * Endpoint para obtener un Event por su id.
     * Se utiliza el currentUserId obtenido (provisionalmente) para verificar permisos.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(
            @PathVariable String id,
            @RequestHeader("currentUserId") Long currentUserId) {
        Event event = eventService.getEventById(id, currentUserId);
        return ResponseEntity.ok(event);
    }
}
