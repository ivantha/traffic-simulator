package com.ivantha.ts.common;

import com.ivantha.ts.model.*;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Session {
    public static final HashMap<String, Integer> VEHICLE_HASH_MAP = new HashMap<>();

    private static IntegerProperty vehicleDensity = new SimpleIntegerProperty(10);
    private static IntegerProperty averageGap = new SimpleIntegerProperty(5);
    private static IntegerProperty averageSpeed = new SimpleIntegerProperty(8);

    // Road map and its associated variables
    private static RoadMap roadMap;
    private static Junction junction;
    private static Road nRoad;
    private static Road eRoad;
    private static Road sRoad;
    private static Road wRoad;
    private static Intersection intersection;
    private static HashMap<Integer, Lane> nIntRoad;
    private static HashMap<Integer, Lane> eIntRoad;
    private static HashMap<Integer, Lane> sIntRoad;
    private static HashMap<Integer, Lane> wIntRoad;
    private static TrafficLight northLane1TrafficLight;
    private static TrafficLight northLane2TrafficLight;
    private static TrafficLight northLane3TrafficLight;
    private static TrafficLight eastLane1TrafficLight;
    private static TrafficLight eastLane2TrafficLight;
    private static TrafficLight eastLane3TrafficLight;
    private static TrafficLight southLane1TrafficLight;
    private static TrafficLight southLane2TrafficLight;
    private static TrafficLight southLane3TrafficLight;
    private static TrafficLight westLane1TrafficLight;
    private static TrafficLight westLane2TrafficLight;
    private static TrafficLight westLane3TrafficLight;

    private static Timeline uiUpdater;
    private static Timer mainTimer;
    private static TimerTask mainTimerTask;

    private static boolean isStarted = false;

    public static int getVehicleDensity() {
        return vehicleDensity.get();
    }
    public static IntegerProperty vehicleDensityProperty() {
        return vehicleDensity;
    }
    public static void setVehicleDensity(int vehicleDensity) {
        Session.vehicleDensity.set(vehicleDensity);
    }
    public static int getAverageGap() {
        return averageGap.get();
    }
    public static IntegerProperty averageGapProperty() {
        return averageGap;
    }
    public static void setAverageGap(int averageGap) {
        Session.averageGap.set(averageGap);
    }
    public static int getAverageSpeed() {
        return averageSpeed.get();
    }
    public static IntegerProperty averageSpeedProperty() {
        return averageSpeed;
    }
    public static void setAverageSpeed(int averageSpeed) {
        Session.averageSpeed.set(averageSpeed);
    }
    public static RoadMap getRoadMap() {
        return roadMap;
    }
    public static void setRoadMap(RoadMap roadMap) {
        Session.roadMap = roadMap;

        junction = roadMap.getJunction();
        nRoad = roadMap.getJunction().getRoad(1);
        eRoad = roadMap.getJunction().getRoad(2);
        sRoad = roadMap.getJunction().getRoad(3);
        wRoad = roadMap.getJunction().getRoad(4);

        intersection = roadMap.getJunction().getIntersection();
        nIntRoad = intersection.getNorthIntRoad();
        eIntRoad = intersection.getEastIntRoad();
        sIntRoad = intersection.getSouthIntRoad();
        wIntRoad = intersection.getWestIntRoad();

        northLane1TrafficLight = nRoad.getLane(1).getTrafficLight();
        northLane2TrafficLight = nRoad.getLane(2).getTrafficLight();
        northLane3TrafficLight = nRoad.getLane(3).getTrafficLight();
        eastLane1TrafficLight = eRoad.getLane(1).getTrafficLight();
        eastLane2TrafficLight = eRoad.getLane(2).getTrafficLight();
        eastLane3TrafficLight = eRoad.getLane(3).getTrafficLight();
        southLane1TrafficLight = sRoad.getLane(1).getTrafficLight();
        southLane2TrafficLight = sRoad.getLane(2).getTrafficLight();
        southLane3TrafficLight = sRoad.getLane(3).getTrafficLight();
        westLane1TrafficLight = wRoad.getLane(1).getTrafficLight();
        westLane2TrafficLight = wRoad.getLane(2).getTrafficLight();
        westLane3TrafficLight = wRoad.getLane(3).getTrafficLight();
    }
    public static Junction getJunction() {
        return junction;
    }
    public static Road getnRoad() {
        return nRoad;
    }
    public static Road geteRoad() {
        return eRoad;
    }
    public static Road getsRoad() {
        return sRoad;
    }
    public static Road getwRoad() {
        return wRoad;
    }
    public static Intersection getIntersection() {
        return intersection;
    }
    public static HashMap<Integer, Lane> getnIntRoad() {
        return nIntRoad;
    }
    public static HashMap<Integer, Lane> geteIntRoad() {
        return eIntRoad;
    }
    public static HashMap<Integer, Lane> getsIntRoad() {
        return sIntRoad;
    }
    public static HashMap<Integer, Lane> getwIntRoad() {
        return wIntRoad;
    }
    public static TrafficLight getNorthLane1TrafficLight() {
        return northLane1TrafficLight;
    }
    public static TrafficLight getNorthLane2TrafficLight() {
        return northLane2TrafficLight;
    }
    public static TrafficLight getNorthLane3TrafficLight() {
        return northLane3TrafficLight;
    }
    public static TrafficLight getEastLane1TrafficLight() {
        return eastLane1TrafficLight;
    }
    public static TrafficLight getEastLane2TrafficLight() {
        return eastLane2TrafficLight;
    }
    public static TrafficLight getEastLane3TrafficLight() {
        return eastLane3TrafficLight;
    }
    public static TrafficLight getSouthLane1TrafficLight() {
        return southLane1TrafficLight;
    }
    public static TrafficLight getSouthLane2TrafficLight() {
        return southLane2TrafficLight;
    }
    public static TrafficLight getSouthLane3TrafficLight() {
        return southLane3TrafficLight;
    }
    public static TrafficLight getWestLane1TrafficLight() {
        return westLane1TrafficLight;
    }
    public static TrafficLight getWestLane2TrafficLight() {
        return westLane2TrafficLight;
    }
    public static TrafficLight getWestLane3TrafficLight() {
        return westLane3TrafficLight;
    }

    // Thread timing variables
    public static Timeline getUiUpdater() {
        return uiUpdater;
    }
    public static void setUiUpdater(Timeline uiUpdater) {
        Session.uiUpdater = uiUpdater;
    }
    public static Timer getMainTimer() {
        return mainTimer;
    }
    public static void setMainTimer(Timer mainTimer) {
        Session.mainTimer = mainTimer;
    }
    public static TimerTask getMainTimerTask() {
        return mainTimerTask;
    }
    public static void setMainTimerTask(TimerTask mainTimerTask) {
        Session.mainTimerTask = mainTimerTask;
    }

    public static boolean isStarted() {
        return isStarted;
    }
    public static void setStarted(boolean started) {
        isStarted = started;
    }
}
