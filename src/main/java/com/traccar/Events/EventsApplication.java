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

    @Bean
    public CommandLineRunner initNotificationConfig(NotificationConfigCache notificationConfigCache) {
        return args -> {
            notificationConfigCache.enableNotification(101, "alarm"); // notificación para el dispositivo 101 y el tipo "alarm"
            notificationConfigCache.enableNotification(102, "alarm"); // notificación para el dispositivo 102 y el tipo "alarm" 
           // notificationConfigCache.enableNotification(104, "commandResult"); // notificación para el dispositivo 104 y el tipo "commandResult"
            notificationConfigCache.enableNotification(105, "driverChanged"); // notificación para el dispositivo 105 y el tipo "driverChanged"
            
            System.out.println("Notificación para device 101 de tipo 'alarm' habilitada.");
            System.out.println("Notificación para device 102 de tipo 'alarm' habilitada.");
            System.out.println("Notificación para device 104 de tipo 'commandResult' habilitada.");
            System.out.println("Notificación para device 105 de tipo 'driverChanged' habilitada.");
        };
    }

}
