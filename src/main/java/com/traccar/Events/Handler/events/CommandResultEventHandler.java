package com.traccar.Events.Handler.events;

import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CommandResultEventHandler extends BaseEventHandler {

    public CommandResultEventHandler() {
    }

    @Override
    public List<Event> analyze(Position currentPosition) {
        List<Event> events = new ArrayList<>();
        Object commandResult = currentPosition.getAttributes().get(Position.KEY_RESULT);
        if (commandResult != null) {
            Event event = new Event();
            event.setType(Event.TYPE_COMMAND_RESULT);
            event.setDeviceId(currentPosition.getDeviceId());
            event.setPositionId(currentPosition.getId());
            event.set(Position.KEY_RESULT, (String) commandResult);
            events.add(event);
        }
        return events;
    }
}
