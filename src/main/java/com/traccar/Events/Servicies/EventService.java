package com.traccar.Events.Servicies;

import com.traccar.Events.Messaging.EventData;
import com.traccar.Events.Messaging.EventForwarder;
import com.traccar.Events.Messaging.RabbitMQEventForwarder;
import com.traccar.Events.Messaging.ResultHandler;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Notification;
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
        System.out.println("Guardando evento: " + event);
        Event savedEvent = eventRepository.save(event);
        System.out.println("Evento guardado en Mongo con ID: " + savedEvent.getId());

        // Reenvía a la cola "eventsQueue" (p. ej. para que Notifications lo procese)
        EventData eventData = new EventData();
        eventData.setEvent(savedEvent);

        eventForwarder.forward(eventData, new ResultHandler() {
            @Override
            public void onResult(boolean success, Exception e) {
                if (!success) {
                    System.err.println("Error al reenviar evento: " + e.getMessage());
                } else {
                    System.out.println("Evento reenviado exitosamente");
                }
            }
        });

 // Una vez que el evento se ha almacenado y reenviado, se crea una notificación
        Notification notification = new Notification();
        // Asumimos que el objeto Event tiene los métodos getDeviceId(), getEventType() y getMessage()
        notification.setDeviceId(savedEvent.getDeviceId());
        notification.setEventType(savedEvent.getType()); 

        // Enviar la notificación a la cola de notificaciones.
        // Verificamos si el eventForwarder es una instancia de RabbitMQEventForwarder, ya que es allí donde se implementa forwardNotification.
        if (eventForwarder instanceof RabbitMQEventForwarder) {
            ((RabbitMQEventForwarder) eventForwarder).forwardNotification(notification);
        } else {
            System.err.println("El forwarder actual no soporta envío de notificaciones.");
        }

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
