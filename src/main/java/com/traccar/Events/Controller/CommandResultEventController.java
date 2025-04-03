package com.traccar.Events.Controller;

import com.traccar.Events.Handler.events.CommandResultEventHandler;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/command-events")
public class CommandResultEventController {

    private final CommandResultEventHandler commandResultEventHandler;

    @Autowired
    public CommandResultEventController(CommandResultEventHandler commandResultEventHandler) {
        this.commandResultEventHandler = commandResultEventHandler;
    }

    // Endpoint para generar eventos de resultado de comando para la posición actual
    @PostMapping("/generate")
    public List<Event> generateCommandResultEvents(@RequestBody Position currentPosition) {
        return commandResultEventHandler.analyze(currentPosition);
    }

}
