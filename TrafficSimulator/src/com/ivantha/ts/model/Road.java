package com.ivantha.ts.model;

import java.util.HashMap;

import static com.ivantha.ts.common.Global.AVERAGE_GAP;
import static com.ivantha.ts.common.Global.VEHICLE_DENSITY;

public class Road {
    private final int roadId;

    private final HashMap<Integer, Lane> laneHashMap = new HashMap<>();
    private final HashMap<Integer, Lane> laneIntHashMap = new HashMap<>();


    public Road(int roadId) {
        this.roadId = roadId;

        laneHashMap.put(1, new Lane(1));            //In-lane 1
        laneHashMap.put(2, new Lane(2));            //In-lane 2
        laneHashMap.put(3, new Lane(3));            //In-lane 3
        laneHashMap.put(4, new Lane(4));            //Out-lane 1
        laneHashMap.put(5, new Lane(5));            //Out-lane 2
        laneHashMap.put(6, new Lane(6));            //Out-lane 3

        laneIntHashMap.put(4, new Lane(4));         //Out-lane-int 4
        laneIntHashMap.put(5, new Lane(5));         //Out-lane-int 5
        laneIntHashMap.put(6, new Lane(6));         //Out-lane-int 6
    }

    public int getRoadId() {
        return roadId;
    }
    public Lane getLane(int laneId){
        return laneHashMap.get(laneId);
    }
    public Lane getIntLane(int laneId){
        return laneIntHashMap.get(laneId);
    }

    public void populateRoad(){
        if(laneHashMap.get(1).getVehicles().size() + laneHashMap.get(2).getVehicles().size() +
                laneHashMap.get(3).getVehicles().size() < VEHICLE_DENSITY.get()){
            generateInVehicle();
        }
    }

    public void generateInVehicle(){
        int origin = roadId;
        int destinationDiff = 1 + (int)((Math.random() + 0.4) * 2);
        int preDestination = origin + destinationDiff;

        int destination = preDestination;
        if(destination > 4){
            destination = destination - 4;
        }

        Lane startLane = laneHashMap.get(destinationDiff);
        Vehicle vehicle = new Vehicle(origin, destination, destinationDiff, startLane.getLaneId());

        appendVehicleToInLane(vehicle, startLane);
    }

    private void appendVehicleToInLane(Vehicle vehicle, Lane lane){
        if(lane.getVehicles().size() > 0) {
            Vehicle frontVehicle = lane.getVehicles().get(lane.getVehicles().size() - 1);
            if (vehicle.trajectory.getLocation() < frontVehicle.trajectory.getLocation() - frontVehicle.length - AVERAGE_GAP.get()) {
                vehicle.trajectory.setLocation(vehicle.length);
                vehicle.setVelocity(0.0);
                lane.addVehicleToQueue(vehicle);
            }
        }else{
            vehicle.trajectory.setLocation(vehicle.length);
            vehicle.setVelocity(0.0);
            lane.addVehicleToQueue(vehicle);
        }
    }

    public void appendVehicleToOutLane(Vehicle vehicle, int laneId){
        vehicle.trajectory.setLocation(vehicle.length);
        laneHashMap.get(laneId).addVehicleToQueue(vehicle);
    }
}