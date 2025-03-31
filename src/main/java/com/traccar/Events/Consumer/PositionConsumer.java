package com.traccar.Events.Consumer;

import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import com.traccar.Events.Pipeline.EventPipeline;
import com.traccar.Events.Servicies.Cache.InMemoryCacheManager;
import com.traccar.Events.Servicies.EventService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PositionConsumer {

    private final EventPipeline eventPipeline;
    private final EventService eventService;
    private final InMemoryCacheManager cacheManager;

    public PositionConsumer(EventPipeline eventPipeline, EventService eventService, InMemoryCacheManager cacheManager) {
        this.eventPipeline = eventPipeline;
        this.eventService = eventService;
        this.cacheManager = cacheManager;
    }

    @RabbitListener(queues = { 
        "positionsQueue1", 
        "positionsQueue2", 
        "positionsQueue3", 
        "positionsQueue4", 
        "positionsQueue5" 
    }, concurrency = "5-10")
    public void onPositionReceived(Position currentPosition) {
        System.out.println("Mensaje recibido: " + currentPosition);
        
        // Recupera la posición previa antes de actualizar la cache
        Position previousPosition = cacheManager.getPosition(currentPosition.getDeviceId());
        if (previousPosition == null) {
            System.out.println("No hay posición previa registrada para deviceId " + currentPosition.getDeviceId());
        } else {
            System.out.println("Posición previa: " + previousPosition);
        }
        
        // Procesa la posición actual utilizando la posición previa
        // Asegúrate de que en tu EventPipeline tengas un método processUsingPrevious
        List<Event> events = eventPipeline.processUsingPrevious(currentPosition, previousPosition);
        for (Event event : events) {
            eventService.storeEvent(event);
        }
        
        // Finalmente, actualiza la cache con la posición actual para el siguiente mensaje
        cacheManager.updatePosition(currentPosition);
    }
}
