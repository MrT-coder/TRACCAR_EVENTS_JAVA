package com.traccar.Events.Servicies.Cache;

import com.traccar.Events.Model.Position;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryCacheManager implements CacheManager {

    private final ConcurrentHashMap<Long, Position> positions = new ConcurrentHashMap<>();

    public void updatePosition(Position position) {
        positions.put(position.getDeviceId(), position);
    }

    @Override
    public Position getPosition(long deviceId) {
        return positions.get(deviceId);
    }
}