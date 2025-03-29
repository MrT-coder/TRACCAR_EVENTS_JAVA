package com.traccar.Events.Helper;

public class UnitsConverter {
    // Convierte velocidad de nudos a metros por segundo (m/s)
    public static double mpsFromKnots(double speedKnots) {
        return speedKnots * 0.514444;
    }
}