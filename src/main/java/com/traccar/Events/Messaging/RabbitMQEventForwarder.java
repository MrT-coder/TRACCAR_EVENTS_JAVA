package com.traccar.Events.Messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traccar.Events.Config.RabbitMQConfig;
import com.traccar.Events.Model.Notification;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventForwarder implements EventForwarder {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String notificationQueue = "NotificationQueue";

     /**
     * Método para enviar un mensaje a la cola de notificaciones
     */
    public void forwardNotification(Notification notification) {
        try {
            // Convertir la notificación a JSON
            String payload = objectMapper.writeValueAsString(notification);
            
            // Enviar el mensaje a la cola de notificaciones
            amqpTemplate.convertAndSend(
                RabbitMQConfig.NOTIFICATION_EXCHANGE,  // Asegúrate de tener el exchange configurado para notificaciones
                notificationQueue,  // Cola de notificaciones
                payload  // El mensaje en formato JSON
            );
            System.out.println("Notificación enviada a la cola: " + notification);
        } catch (JsonProcessingException e) {
            System.err.println("Error al convertir la notificación a JSON: " + e.getMessage());
        }
    }

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
