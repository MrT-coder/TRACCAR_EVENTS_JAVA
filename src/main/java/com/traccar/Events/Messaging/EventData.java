package com.traccar.Events.Messaging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traccar.Events.Model.Event;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventData {

    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
