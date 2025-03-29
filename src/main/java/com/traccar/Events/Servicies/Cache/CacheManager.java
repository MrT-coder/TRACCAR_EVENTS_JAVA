package com.traccar.Events.Servicies.Cache;

import com.traccar.Events.Model.Position;

public interface CacheManager {
    Position getPosition(long deviceId);
}
