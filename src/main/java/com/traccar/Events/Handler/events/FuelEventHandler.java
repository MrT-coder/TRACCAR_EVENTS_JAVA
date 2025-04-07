package com.traccar.Events.Handler.events;

import com.traccar.Events.Helper.AttributeUtil;
import com.traccar.Events.Helper.PositionUtil;
import com.traccar.Events.Model.Event;
import com.traccar.Events.Model.Position;
import com.traccar.Events.Servicies.Cache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FuelEventHandler extends BaseEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(FuelEventHandler.class);
    private final CacheManager cacheManager;

    // 🔧 Inyectamos explícitamente InMemoryCacheManager
    public FuelEventHandler(@Qualifier("inMemoryCacheManager") CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        logger.info("💡 FuelEventHandler inyectado con clase cacheManager: {}", cacheManager.getClass().getSimpleName());
    }

    @Override
    public List<Event> analyze(Position currentPosition, Position previousPosition) {
        List<Event> events = new ArrayList<>();

        if (!PositionUtil.isLatest(cacheManager, currentPosition)) {
            logger.debug("Ignorando posición antigua para deviceId {}", currentPosition.getDeviceId());
            return events;
        }

        if (currentPosition.hasAttribute(Position.KEY_FUEL_LEVEL)) {
            double before = 0;
            double after = currentPosition.getDouble(Position.KEY_FUEL_LEVEL);

            Position lastPosition = (previousPosition != null)
                    ? previousPosition
                    : cacheManager.getPosition(currentPosition.getDeviceId());

            if (lastPosition != null && lastPosition.hasAttribute(Position.KEY_FUEL_LEVEL)) {
                before = lastPosition.getDouble(Position.KEY_FUEL_LEVEL);
            }

            double change = after - before;
            logger.info("📊 Evaluando cambio de combustible para deviceId {}: before = {}, after = {}, cambio = {}",
                        currentPosition.getDeviceId(), before, after, change);

            if (change > 0) {
                double threshold = AttributeUtil.lookup(
                        cacheManager, Keys.EVENT_FUEL_INCREASE_THRESHOLD, currentPosition.getDeviceId());

                logger.info("✅ Threshold de aumento para deviceId {}: {}", currentPosition.getDeviceId(), threshold);

                if (threshold > 0 && change >= threshold) {
                    Event event = new Event(Event.TYPE_DEVICE_FUEL_INCREASE,
                                            currentPosition.getDeviceId(),
                                            currentPosition.getId(),
                                            currentPosition.getDeviceTime());

                    event.set("before", before);
                    event.set("after", after);
                    events.add(event);

                    logger.info("🚨 Evento de AUMENTO de combustible generado para deviceId {}", currentPosition.getDeviceId());
                }

            } else if (change < 0) {
                double threshold = AttributeUtil.lookup(
                        cacheManager, Keys.EVENT_FUEL_DROP_THRESHOLD, currentPosition.getDeviceId());

                logger.info("✅ Threshold de caída para deviceId {}: {}", currentPosition.getDeviceId(), threshold);

                if (threshold > 0 && Math.abs(change) >= threshold) {
                    Event event = new Event(Event.TYPE_DEVICE_FUEL_DROP,
                                            currentPosition.getDeviceId(),
                                            currentPosition.getId(),
                                            currentPosition.getDeviceTime());

                    event.set("before", before);
                    event.set("after", after);
                    events.add(event);

                    logger.info("🚨 Evento de CAÍDA de combustible generado para deviceId {}", currentPosition.getDeviceId());
                }
            } else {
                logger.debug("⚠️ Cambio de combustible insignificante para deviceId {}", currentPosition.getDeviceId());
            }
        }

        return events;
    }

    @Override
    public List<Event> analyze(Position currentPosition) {
        return analyze(currentPosition, null);
    }
}
