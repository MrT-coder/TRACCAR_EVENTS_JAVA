package com.traccar.Events.Messaging;

public interface ResultHandler {
    void onResult(boolean success, Exception e);
}
