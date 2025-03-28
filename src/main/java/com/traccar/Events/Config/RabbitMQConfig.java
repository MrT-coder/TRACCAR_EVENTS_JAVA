package com.traccar.Events.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EVENTS_EXCHANGE = "eventsExchange";
    public static final String EVENTS_QUEUE = "eventsQueue";
    public static final String EVENTS_ROUTING_KEY = "event.routing.key";

    public static final String POSITIONS_QUEUE = "positionsQueue";
    // Podrías definir también exchange y routing key para posiciones

    @Bean
    public Queue eventsQueue() {
        return new Queue(EVENTS_QUEUE, true);
    }

    @Bean
    public TopicExchange eventsExchange() {
        return new TopicExchange(EVENTS_EXCHANGE);
    }

    @Bean
    public Binding bindingEventsQueue(Queue eventsQueue, TopicExchange eventsExchange) {
        return BindingBuilder
                .bind(eventsQueue)
                .to(eventsExchange)
                .with(EVENTS_ROUTING_KEY);
    }

    // Nota: La cola positionsQueue la define el microservicio de Position,
    // pero si deseas consumirla aquí, podrías declararla también:
    @Bean
    public Queue positionsQueue() {
        return new Queue(POSITIONS_QUEUE, true);
    }
}