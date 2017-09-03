package model;

import java.util.HashMap;

import static main.Global.AVERAGE_SPACING;
import static main.Global.LANE_POPULATION;

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
                laneHashMap.get(3).getVehicles().size() < LANE_POPULATION.get()){
            generateInVehicle();
        }
    }

    public void generateInVehicle(){
        int origin = roadId;
        int preDestination = origin + 1 + (int)((Math.random() + 0.4) * 2);

        int destination = preDestination;
        if(destination > 4){
            destination = destination - 4;
        }

        Vehicle vehicle = new Vehicle(origin, destination);

        appendVehicleToInLane(vehicle, laneHashMap.get(preDestination - origin));
    }

    private void appendVehicleToInLane(Vehicle vehicle, Lane lane){
        if(lane.getVehicles().size() > 0) {
            Vehicle frontVehicle = lane.getVehicles().get(lane.getVehicles().size() - 1);
            if (vehicle.getTrajectory().getLocation() < frontVehicle.getTrajectory().getLocation() - frontVehicle.getLength()- AVERAGE_SPACING.get()) {
                vehicle.getTrajectory().setLocation(vehicle.getLength());
                vehicle.setVelocity(0.0);
                lane.addVehicleToQueue(vehicle);
            }
        }else{
            vehicle.getTrajectory().setLocation(vehicle.getLength());
            vehicle.setVelocity(0.0);
            lane.addVehicleToQueue(vehicle);
        }
    }

    public boolean isOutLaneFree(int laneId, Vehicle vehicle){
        Lane outLane = laneHashMap.get(laneId);

        if(outLane.getVehicles().size() > 0){
            Vehicle frontVehicle = outLane.getVehicles().get(outLane.getVehicles().size() - 1);
            if (vehicle.getLength() < frontVehicle.getTrajectory().getLocation() - frontVehicle.getLength() - AVERAGE_SPACING.get()) {
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }

    public void appendVehicleToOutLane(Vehicle vehicle, int laneId){
        vehicle.getTrajectory().setLocation(vehicle.getLength());
        laneHashMap.get(laneId).addVehicleToQueue(vehicle);
    }

    public void appendVehicleToOutIntLane(Vehicle vehicle, int laneId){
        vehicle.getTrajectory().setLocation(0);
        laneIntHashMap.get(laneId).addVehicleToQueue(vehicle);
    }
}