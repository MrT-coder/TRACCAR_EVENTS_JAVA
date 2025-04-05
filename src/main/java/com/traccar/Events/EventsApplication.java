package com.traccar.Events;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.traccar.Events.Servicies.NotificationConfigCache;

@SpringBootApplication
public class EventsApplication {

    private static final Logger logger = LoggerFactory.getLogger(EventsApplication.class);  // Logger de Spring

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

    // Configuración inicial de las notificaciones usando CommandLineRunner
    @Bean
    public CommandLineRunner initNotificationConfig(NotificationConfigCache notificationConfigCache) {
        return args -> {
            logger.info("Inicializando las notificaciones...");

            // Habilitar notificaciones
            notificationConfigCache.enableNotification(101, "alarm"); 
            notificationConfigCache.enableNotification(102, "alarm"); 
            notificationConfigCache.enableNotification(105, "commandResult");
            notificationConfigCache.enableNotification(104, "commandResult");

            // Registrar en los logs las notificaciones habilitadas
            logger.info("Notificación para device 101 de tipo 'alarm' habilitada.");
            logger.info("Notificación para device 102 de tipo 'alarm' habilitada.");
            logger.info("Notificación para device 105 de tipo 'driverChanged' habilitada.");
        };
    }
}
