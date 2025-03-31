package com.traccar.Events.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    // Exchange para las posiciones con consistent hash
    public static final String POSITIONS_EXCHANGE = "positionsExchange";
    // Declaramos 5 colas para distribuir la carga
    public static final String POSITIONS_QUEUE_1 = "positionsQueue1";
    public static final String POSITIONS_QUEUE_2 = "positionsQueue2";
    public static final String POSITIONS_QUEUE_3 = "positionsQueue3";
    public static final String POSITIONS_QUEUE_4 = "positionsQueue4";
    public static final String POSITIONS_QUEUE_5 = "positionsQueue5";

    // Exchange y cola para eventos (como antes)
    public static final String EVENTS_EXCHANGE = "eventsExchange";
    public static final String EVENTS_QUEUE = "eventsQueue";
    public static final String EVENTS_ROUTING_KEY = "event.routing.key";

    // Define un CustomExchange de tipo x-consistent-hash
    @Bean
    public CustomExchange positionsExchange() {
        Map<String, Object> args = new HashMap<>();
        // El argumento "hash-header" le dice al exchange qué header usar (si lo deseas)
        // Si no se especifica, usará el routing key como valor a hash.
        // args.put("hash-header", "deviceId"); // opcional
        return new CustomExchange(POSITIONS_EXCHANGE, "x-consistent-hash", true, false, args);
    }

    // Define las cinco colas
    @Bean
    public Queue positionsQueue1() {
        return new Queue(POSITIONS_QUEUE_1, true);
    }
    @Bean
    public Queue positionsQueue2() {
        return new Queue(POSITIONS_QUEUE_2, true);
    }
    @Bean
    public Queue positionsQueue3() {
        return new Queue(POSITIONS_QUEUE_3, true);
    }
    @Bean
    public Queue positionsQueue4() {
        return new Queue(POSITIONS_QUEUE_4, true);
    }
    @Bean
    public Queue positionsQueue5() {
        return new Queue(POSITIONS_QUEUE_5, true);
    }

    // Cada binding se define con un peso; por simplicidad, usamos "1" para cada cola.
    @Bean
    public Binding bindingPositionsQueue1(CustomExchange positionsExchange, Queue positionsQueue1) {
        return BindingBuilder.bind(positionsQueue1).to(positionsExchange).with("1").noargs();
    }
    @Bean
    public Binding bindingPositionsQueue2(CustomExchange positionsExchange, Queue positionsQueue2) {
        return BindingBuilder.bind(positionsQueue2).to(positionsExchange).with("1").noargs();
    }
    @Bean
    public Binding bindingPositionsQueue3(CustomExchange positionsExchange, Queue positionsQueue3) {
        return BindingBuilder.bind(positionsQueue3).to(positionsExchange).with("1").noargs();
    }
    @Bean
    public Binding bindingPositionsQueue4(CustomExchange positionsExchange, Queue positionsQueue4) {
        return BindingBuilder.bind(positionsQueue4).to(positionsExchange).with("1").noargs();
    }
    @Bean
    public Binding bindingPositionsQueue5(CustomExchange positionsExchange, Queue positionsQueue5) {
        return BindingBuilder.bind(positionsQueue5).to(positionsExchange).with("1").noargs();
    }

    // Exchange, cola y binding para eventos (sin cambios)
    @Bean
    public Queue eventsQueue() {
        return new Queue(EVENTS_QUEUE, true);
    }
    @Bean
    public org.springframework.amqp.core.TopicExchange eventsExchange() {
        return new org.springframework.amqp.core.TopicExchange(EVENTS_EXCHANGE);
    }
    @Bean
    public Binding bindingEventsQueue(Queue eventsQueue, org.springframework.amqp.core.TopicExchange eventsExchange) {
        return BindingBuilder.bind(eventsQueue)
                .to(eventsExchange)
                .with(EVENTS_ROUTING_KEY);
    }

    // Configuración del convertidor de mensajes
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
