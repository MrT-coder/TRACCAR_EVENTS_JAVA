package com.traccar.Events;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.traccar.Events.Servicies.NotificationConfigCache;
import com.traccar.Events.Servicies.Cache.InMemoryCacheManager;

@SpringBootApplication
public class EventsApplication {

    private static final Logger logger = LoggerFactory.getLogger(EventsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

    @Bean
    public CommandLineRunner initNotificationConfig(NotificationConfigCache notificationConfigCache, InMemoryCacheManager cacheManager) {
        return args -> {
            logger.info("Inicializando las notificaciones...");

            // Habilitar notificaciones comunes
            notificationConfigCache.enableNotification(101, "alarm");
            notificationConfigCache.enableNotification(102, "alarm");
            notificationConfigCache.enableNotification(104, "commandResult");
            notificationConfigCache.enableNotification(105, "commandResult");
            notificationConfigCache.enableNotification(105, "driverChanged");

            // Notificaciones de combustible
            notificationConfigCache.enableNotification(106, "deviceFuelIncrease");
            notificationConfigCache.enableNotification(106, "deviceFuelDrop");

            // Setear thresholds personalizados
            cacheManager.set("event.fuelIncreaseThreshold", 10.0, 106);
            cacheManager.set("event.fuelDropThreshold", 10.0, 106);

            // Confirmar que el threshold fue guardado correctamente
            Object fuelThreshold = cacheManager.getObject("event.fuelIncreaseThreshold", 106);
            logger.info("🚀 Threshold configurado en InMemoryCacheManager para deviceId 106: {}", fuelThreshold);

            // Logs informativos
            logger.info("Notificación para device 101 de tipo 'alarm' habilitada.");
            logger.info("Notificación para device 102 de tipo 'alarm' habilitada.");
            logger.info("Notificación para device 104 de tipo 'commandResult' habilitada.");
            logger.info("Notificación para device 105 de tipo 'commandResult' habilitada.");
            logger.info("Notificación para device 105 de tipo 'driverChanged' habilitada.");
            logger.info("Notificación para device 106 de tipo 'deviceFuelIncrease' habilitada.");
            logger.info("Notificación para device 106 de tipo 'deviceFuelDrop' habilitada.");
            logger.info("✔ Thresholds de fuel configurados correctamente para device 106.");
        };
    }
}
