package com.traccar.Events.Helper;

import com.traccar.Events.Model.Position;
import com.traccar.Events.Servicies.Cache.CacheManager;

public class PositionUtil {

    // Método para verificar si la posición actual es la más reciente.
    // Se compara el fixTime de la posición actual con la última posición registrada en la cache.
    public static boolean isLatest(CacheManager cacheManager, Position position) {
        Position lastPosition = cacheManager.getPosition(position.getDeviceId());
        if (lastPosition == null) {
            return true; // No hay posición previa, por lo que es la más reciente.
        }
        // Compara si el fixTime de la posición actual es posterior al de la posición almacenada.
        return position.getFixTime().after(lastPosition.getFixTime());
    }
}
