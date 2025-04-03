package com.traccar.Events.Helper;

import com.traccar.Events.Servicies.Cache.CacheManager;

public class AttributeUtil {

    // Método para buscar un atributo en el cache
    public static double lookup(CacheManager cacheManager, String key, long deviceId) {
        // Lógica para buscar el valor en el cache basado en la clave y deviceId
        // Esto depende de cómo tengas estructurado tu CacheManager

        // Por ejemplo, si el CacheManager tiene un método que devuelve atributos para un dispositivo:
        Object attribute = cacheManager.getObject(key, deviceId);
        
        // Si el atributo es numérico, devolverlo como un double
        if (attribute instanceof Number) {
            return ((Number) attribute).doubleValue();
        }

        // Si no se encuentra el atributo, devolver un valor por defecto (ej. 0.0)
        return 0.0;
    }
}
