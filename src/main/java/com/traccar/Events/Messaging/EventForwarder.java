package com.traccar.Events.Messaging;

public interface EventForwarder {
    void forward(EventData eventData, ResultHandler resultHandler);
}
