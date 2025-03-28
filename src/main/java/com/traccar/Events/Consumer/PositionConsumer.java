package com.traccar.Events.Consumer;

import com.traccar.Events.Model.Position;
import com.traccar.Events.Pipeline.EventPipeline;
import com.traccar.Events.Servicies.EventService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PositionConsumer {

    private final EventPipeline eventPipeline;
    private final EventService eventService;

    public PositionConsumer(EventPipeline eventPipeline, EventService eventService) {
        this.eventPipeline = eventPipeline;
        this.eventService = eventService;
    }

    @RabbitListener(queues = "positionsQueue")
    public void onPositionReceived(Position position) {
        // Procesa la posición con todos los handlers
        List<com.traccar.Events.Model.Event> events = eventPipeline.process(position);
        // Guarda y reenvía cada evento
        for (com.traccar.Events.Model.Event event : events) {
            eventService.storeEvent(event);
        }
    }
}
