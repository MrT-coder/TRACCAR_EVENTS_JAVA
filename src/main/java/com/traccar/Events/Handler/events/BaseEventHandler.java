package com.traccar.Events.Handler.events;

import java.util.List;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;

public abstract class BaseEventHandler {
    public abstract List<Event> analyze(Position position);
    
    public List<Event> analyze(Position current, Position previous) {
        // Por defecto, si previous es null, delega a la versión que solo necesita la posición actual.
        return analyze(current);
    }
}
