package model;

import java.util.HashMap;

public class Intersection {
    private final HashMap<Integer, Lane> northIntRoad = new HashMap<>();
    private final HashMap<Integer, Lane> eastIntRoad = new HashMap<>();
    private final HashMap<Integer, Lane> southIntRoad = new HashMap<>();
    private final HashMap<Integer, Lane> westIntRoad = new HashMap<>();

    private final HashMap<Integer, HashMap<Integer, Lane>> intLaneMap = new HashMap<>();

    public Intersection() {
        intLaneMap.put(1, northIntRoad);
        intLaneMap.put(2, eastIntRoad);
        intLaneMap.put(3, southIntRoad);
        intLaneMap.put(4, westIntRoad);

        for(HashMap.Entry<Integer, HashMap<Integer, Lane>> entry : intLaneMap.entrySet()) {
            HashMap<Integer, Lane> road = entry.getValue();
            road.put(7, new Lane(7));
            road.put(8, new Lane(8));
            road.put(9, new Lane(9));
        }
    }

    public HashMap<Integer, Lane> getNorthIntRoad() {
        return northIntRoad;
    }

    public HashMap<Integer, Lane> getEastIntRoad() {
        return eastIntRoad;
    }

    public HashMap<Integer, Lane> getSouthIntRoad() {
        return southIntRoad;
    }

    public HashMap<Integer, Lane> getWestIntRoad() {
        return westIntRoad;
    }

    public void appendVehicleToIntLane(Vehicle vehicle, int roadId, int laneId){
        vehicle.trajectory.setLocation(0);
        intLaneMap.get(roadId).get(laneId).addVehicleToQueue(vehicle);
    }
}
