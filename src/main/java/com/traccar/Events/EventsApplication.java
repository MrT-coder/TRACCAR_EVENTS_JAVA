package com.traccar.Events;

import java.sql.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.traccar.Events.Model.Position;
import com.traccar.Events.Pipeline.EventPipeline;
import com.traccar.Events.Servicies.NotificationConfigCache;
import com.traccar.Events.Servicies.Cache.CacheManager;

@SpringBootApplication
public class EventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsApplication.class, args);
	}

// Configura la notificación para el dispositivo 101 y el tipo "deviceOverspeed"
    @Bean
    public CommandLineRunner initNotificationConfig(NotificationConfigCache notificationConfigCache) {
        return args -> {
            notificationConfigCache.enableNotification(101, "deviceOverspeed");
            System.out.println("Notificación para device 101 de tipo 'deviceOverspeed' habilitada.");
        };
    }

}
// Este método se ejecutará al iniciar la aplicación y habilitará la notificación para el dispositivo 101 y el tipo "deviceOverspeed".