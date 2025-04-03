package com.traccar.Events;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.traccar.Events.Servicies.NotificationConfigCache;
import com.traccar.Events.Handler.events.CommandResultEventHandler;  // Asegúrate de importar tu Event Handler

@SpringBootApplication
public class EventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

    // Configuración inicial de las notificaciones usando CommandLineRunner
    @Bean
    public CommandLineRunner initNotificationConfig(NotificationConfigCache notificationConfigCache) {
        return args -> {
            notificationConfigCache.enableNotification(101, "alarm"); // Notificación para el dispositivo 101 y el tipo "alarm"
            notificationConfigCache.enableNotification(102, "alarm"); // Notificación para el dispositivo 102 y el tipo "alarm" 
            notificationConfigCache.enableNotification(105, "driverChanged"); // Notificación para el dispositivo 105 y el tipo "driverChanged"
            
            // Imprime en consola las notificaciones habilitadas
            System.out.println("Notificación para device 101 de tipo 'alarm' habilitada.");
            System.out.println("Notificación para device 102 de tipo 'alarm' habilitada.");
            System.out.println("Notificación para device 105 de tipo 'driverChanged' habilitada.");
        };
    }

    // Si necesitas usar el CommandResultEventHandler en algún otro lugar, puedes integrarlo aquí
    @Bean
    public CommandResultEventHandler commandResultEventHandler() {
        return new CommandResultEventHandler();  // Crea una instancia de tu handler para que sea manejado por Spring
    }
}
