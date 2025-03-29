package com.traccar.Events.Handler.events;

import java.util.List;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;

public abstract class BaseEventHandler {
    public abstract List<Event> analyze(Position position);
}
