package com.traccar.Events;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.traccar.Events.Servicies.NotificationConfigCache;

@SpringBootApplication
public class EventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsApplication.class, args);
	}

// Configura la notificación para el dispositivo 101 y el tipo "deviceOverspeed"
    @Bean
    public CommandLineRunner initNotificationConfig(NotificationConfigCache notificationConfigCache) {
        return args -> {
            notificationConfigCache.enableNotification(101, "alarm");
            notificationConfigCache.enableNotification(102, "alarm");
            System.out.println("Notificación para device 101 de tipo 'alarm' habilitada.");
            System.out.println("Notificación para device 102 de tipo 'deviceOverspeed' habilitada.");
        };
    }

}
// Este método se ejecutará al iniciar la aplicación y habilitará la notificación para el dispositivo 101 y el tipo "deviceOverspeed".