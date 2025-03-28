package com.traccar.Events.Handler.events;

import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;

public abstract class BaseEventHandler {
    public abstract Event analyze(Position position);
}
