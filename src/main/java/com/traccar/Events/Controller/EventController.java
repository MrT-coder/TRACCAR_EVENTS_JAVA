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
     * Método provisional para simular la obtención del currentUserId.
     * En producción, este valor se deberá obtener mediante un middleware que
     * consuma el endpoint del microservicio de Users, integrado en un entorno
     * de contenedores Docker.
     */
    private long getCurrentUserId() {
        // Simulación para pruebas: en un entorno real, se extraería del contexto de seguridad
        return 1L;
    }

    /**
     * Endpoint para obtener un Event por su id.
     * Se utiliza el currentUserId obtenido (provisionalmente) para verificar permisos.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable String id) {
        long currentUserId = getCurrentUserId();
        Event event = eventService.getEventById(id, currentUserId);
        return ResponseEntity.ok(event);
    }
}
