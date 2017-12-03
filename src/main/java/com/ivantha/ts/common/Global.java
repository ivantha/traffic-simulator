package com.ivantha.ts.common;

public class Global {
    public static final int REFRESH_INTERVAL = 30;

    public static final double CANVAS_RADIUS = 400.0;
    public static final double ROAD_RADIUS = 50.0;
    public static final double ROAD_LENGTH = CANVAS_RADIUS - ROAD_RADIUS;

    public static final int SCPL = 10;                                          // Sensor count per lane
    public static final double DPS = ROAD_LENGTH / SCPL;                        // Distance per sensor

    public static final int TL_RADIUS = 4;
}
