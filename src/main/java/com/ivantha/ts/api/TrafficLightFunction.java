package com.ivantha.ts.api;

import com.ivantha.ts.common.Session;
import com.ivantha.ts.model.TrafficLight;

public class TrafficLightFunction {
    public static void setNorthLane1Traffic(TrafficLight.State state){
        Session.getNorthLane1TrafficLight().setState(state);
    }

    public static void setNorthLane2Traffic(TrafficLight.State state){
        Session.getNorthLane2TrafficLight().setState(state);
    }

    public static void setNorthLane3Traffic(TrafficLight.State state){
        Session.getNorthLane3TrafficLight().setState(state);
    }

    public static void setEastLane1Traffic(TrafficLight.State state){
        Session.getEastLane1TrafficLight().setState(state);
    }

    public static void setEastLane2Traffic(TrafficLight.State state){
        Session.getEastLane2TrafficLight().setState(state);
    }

    public static void setEastLane3Traffic(TrafficLight.State state){
        Session.getEastLane3TrafficLight().setState(state);
    }

    public static void setSouthLane1Traffic(TrafficLight.State state){
        Session.getSouthLane1TrafficLight().setState(state);
    }

    public static void setSouthLane2Traffic(TrafficLight.State state){
        Session.getSouthLane2TrafficLight().setState(state);
    }

    public static void setSouthLane3Traffic(TrafficLight.State state){
        Session.getSouthLane3TrafficLight().setState(state);
    }

    public static void setWestLane1Traffic(TrafficLight.State state){
        Session.getWestLane1TrafficLight().setState(state);
    }

    public static void setWestLane2Traffic(TrafficLight.State state){
        Session.getWestLane2TrafficLight().setState(state);
    }

    public static void setWestLane3Traffic(TrafficLight.State state){
        Session.getWestLane3TrafficLight().setState(state);
    }
}
