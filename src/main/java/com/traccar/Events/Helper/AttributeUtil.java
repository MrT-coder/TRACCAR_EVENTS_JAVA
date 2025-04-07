package com.traccar.Events.Helper;

import com.traccar.Events.Servicies.Cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeUtil {

    private static final Logger logger = LoggerFactory.getLogger(AttributeUtil.class);

    /**
     * Busca un atributo numérico (como umbrales) en el CacheManager para un deviceId específico.
     *
     * @param cacheManager El manejador de cache que contiene los atributos.
     * @param key La clave del atributo (por ejemplo: "event.fuelDropThreshold").
     * @param deviceId El ID del dispositivo.
     * @return El valor numérico del atributo si existe y es válido; de lo contrario, 0.0.
     */
    public static double lookup(CacheManager cacheManager, String key, long deviceId) {
        Object attribute = cacheManager.getObject(key, deviceId);
        logger.info("Buscando atributo '{}' para deviceId {}: {}", key, deviceId, attribute);

        if (attribute instanceof Number) {
            double value = ((Number) attribute).doubleValue();
            logger.info("Atributo '{}' encontrado para deviceId {}: {}", key, deviceId, value);
            return value;
        }

        logger.warn("Atributo '{}' para deviceId {} no es numérico o no fue encontrado. Se devuelve 0.0", key, deviceId);
        return 0.0;
    }
}
