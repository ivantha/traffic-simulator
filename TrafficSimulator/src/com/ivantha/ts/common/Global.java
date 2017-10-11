package com.ivantha.ts.common;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.HashMap;

public class Global {
    public static final int REFRESH_INTERVAL = 30;

    public static final double CANVAS_RADIUS = 400.0;
    public static final double ROAD_RADIUS = 50.0;
    public static final double ROAD_LENGTH = CANVAS_RADIUS - ROAD_RADIUS;

    public static IntegerProperty VEHICLE_DENSITY = new SimpleIntegerProperty(10);
    public static IntegerProperty AVERAGE_GAP = new SimpleIntegerProperty(5);
    public static IntegerProperty AVERAGE_SPEED = new SimpleIntegerProperty(5);

    public static final HashMap<String, Integer> VEHICLE_HASH_MAP = new HashMap<>();
}