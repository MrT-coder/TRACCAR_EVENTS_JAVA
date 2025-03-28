package com.traccar.Events.Messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traccar.Events.Config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventForwarder implements EventForwarder {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void forward(EventData eventData, ResultHandler resultHandler) {
        try {
            String payload = objectMapper.writeValueAsString(eventData);
            // Envía a la cola "eventsQueue" mediante exchange y routing key
            amqpTemplate.convertAndSend(
                RabbitMQConfig.EVENTS_EXCHANGE, 
                RabbitMQConfig.EVENTS_ROUTING_KEY, 
                payload
            );
            resultHandler.onResult(true, null);
        } catch (JsonProcessingException e) {
            resultHandler.onResult(false, e);
        }
    }
}
