package com.traccar.Events.Handler.events;

import com.traccar.Events.Helper.PositionUtil;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Maintenance;
import com.traccar.Events.Model.Position;
import com.traccar.Events.Servicies.Cache.CacheManager;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class MaintenanceEventHandler extends BaseEventHandler {

    private final CacheManager cacheManager;

    public MaintenanceEventHandler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<Event> analyze(Position currentPosition, Position previousPosition) {
        List<Event> events = new ArrayList<>();

        // Usa PositionUtil para verificar que currentPosition es la más reciente.
        if (!PositionUtil.isLatest(cacheManager, currentPosition)) {
            return events;
        }

        // Obtiene la última posición
        Position lastPosition = (previousPosition != null) ? previousPosition : cacheManager.getPosition(currentPosition.getDeviceId());
        if (lastPosition == null || currentPosition.getFixTime().compareTo(lastPosition.getFixTime()) < 0) {
            return events;
        }

        // Compara los valores de mantenimiento
        for (Maintenance maintenance : cacheManager.getDeviceObjects(currentPosition.getDeviceId(), Maintenance.class)) {
            if (maintenance.getPeriod() != 0) {
                double oldValue = getValue(lastPosition, maintenance.getType());
                double newValue = getValue(currentPosition, maintenance.getType());

                if (oldValue != 0.0 && newValue != 0.0 && newValue >= maintenance.getStart()) {
                    if (oldValue < maintenance.getStart() ||
                        (long) ((oldValue - maintenance.getStart()) / maintenance.getPeriod())
                        < (long) ((newValue - maintenance.getStart()) / maintenance.getPeriod())) {
                        
                        // Crear el evento de mantenimiento
                        Event event = new Event(Event.TYPE_MAINTENANCE, currentPosition.getDeviceId(), currentPosition.getId(), currentPosition.getDeviceTime());
                        event.setMaintenanceId(maintenance.getId());
                        event.set(maintenance.getType(), newValue);
                        events.add(event);
                    }
                }
            }
        }

        return events;
    }

    @Override
    public List<Event> analyze(Position currentPosition) {
        return analyze(currentPosition, null);
    }

    
    private double getValue(Position position, String type) {
        return switch (type) {
            case "serverTime" -> position.getFixTime().getTime();  // Usa getFixTime()
            
            case "deviceTime" -> {
                Object deviceTimeObject = position.getDeviceTime(); // Primero obtenemos el valor de deviceTime
                if (deviceTimeObject instanceof String) {
                    // Si deviceTime es un String, intentamos parsearlo a Date
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Date deviceTime = sdf.parse((String) deviceTimeObject); // Convertimos el String en Date
                        yield deviceTime.getTime(); // Usamos getTime() para obtener el tiempo en milisegundos
                    } catch (ParseException e) {
                        e.printStackTrace();
                        yield 0.0;  // Si ocurre un error al parsear el String, devolvemos 0.0
                    }
                } else if (deviceTimeObject instanceof Date) {
                    // Si ya es un Date, simplemente usamos getTime()
                    yield ((Date) deviceTimeObject).getTime();
                } else {
                    yield 0.0;  // Si no es ni String ni Date, devolvemos 0.0
                }
            }
            
            case "fixTime" -> position.getFixTime().getTime();  // Usa getFixTime()
            
            default -> position.getDouble(type);  // Continúa con el resto de tipos
        };
    }
    
    
}
