package com.ivantha.ts.api;

import com.ivantha.ts.common.Session;

public class MapFunction {
    public static void enableNorth(){
        Session.getRoadMap().setNorthEnabled(true);
    }

    public static void enableEast(){
        Session.getRoadMap().setEastEnabled(true);
    }

    public static void enableSouth(){
        Session.getRoadMap().setSouthEnabled(true);
    }

    public static void enableWest(){
        Session.getRoadMap().setWestEnabled(true);
    }
}
