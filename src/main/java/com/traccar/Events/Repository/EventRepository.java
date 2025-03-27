package com.traccar.Events.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.traccar.Events.Model.Event;

public interface EventRepository extends MongoRepository<Event, String> {
    // Se pueden definir métodos de consulta adicionales si es necesario.
}