package main;

import java.util.HashMap;

public class Global {
    public static final int REFRESH_INTERVAL = 10;

    public static final double CANVAS_RADIUS = 350.0;
    public static final double ROAD_RADIUS = 40.0;

    public static final double ROAD_LENGTH = CANVAS_RADIUS - ROAD_RADIUS;

    public static final int NORMAL_LANE_POPULATION = 5;

    public static final int NORMAL_VEHICLE_SPACING = 5;

    public static final HashMap<String, Integer> VEHICLE_HASH_MAP = new HashMap<>();
}
