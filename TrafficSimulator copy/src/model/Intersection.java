package model;

import java.util.HashMap;

public class Intersection {
    private final HashMap<Integer, HashMap<Integer, Lane>> intLaneMap = new HashMap<>();

    public Intersection() {
        HashMap<Integer, Lane> northRoadMap = new HashMap<>();
        northRoadMap.put(7, new Lane(7));
        northRoadMap.put(8, new Lane(8));
        northRoadMap.put(9, new Lane(9));

        HashMap<Integer, Lane> eastRoadMap = new HashMap<>();
        eastRoadMap.put(7, new Lane(7));
        eastRoadMap.put(8, new Lane(8));
        eastRoadMap.put(9, new Lane(9));

        HashMap<Integer, Lane> southRoadMap = new HashMap<>();
        southRoadMap.put(7, new Lane(7));
        southRoadMap.put(8, new Lane(8));
        southRoadMap.put(9, new Lane(9));

        HashMap<Integer, Lane> westRoadMap = new HashMap<>();
        westRoadMap.put(7, new Lane(7));
        westRoadMap.put(8, new Lane(8));
        westRoadMap.put(9, new Lane(9));

        intLaneMap.put(1, northRoadMap);
        intLaneMap.put(2, eastRoadMap);
        intLaneMap.put(3, southRoadMap);
        intLaneMap.put(4, westRoadMap);
    }

    public Lane getIntLane(int roadId, int laneId){
        return intLaneMap.get(roadId).get(laneId);
    }

    public void appendVehicleToIntLane(Vehicle vehicle, int roadId, int laneId){
        vehicle.trajectory.setLocation(0);
        intLaneMap.get(roadId).get(laneId).addVehicleToQueue(vehicle);
    }
}
