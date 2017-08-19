package model;

import a.Lane;
import a.LaneType;
import a.Vehicle;

import static main.Global.NORMAL_LANE_POPULATION;
import static main.Global.NORMAL_VEHICLE_SPACING;

public class Road {
    private final int roadId;
    private final Lane inLane;
    private final Lane outLane;

    public Road(int roadId) {
        this.roadId = roadId;
        inLane = new Lane(LaneType.IN_LANE);
        outLane = new Lane(LaneType.OUT_LANE);
    }

    public int getRoadId() {
        return roadId;
    }
    public Lane getInLane() {
        return inLane;
    }
    public Lane getOutLane() {
        return outLane;
    }

    public void populateRoad(){
        if(inLane.getVehicles().size() < NORMAL_LANE_POPULATION){
            generateInVehicle();
        }
    }

    public void generateInVehicle(){
        Vehicle vehicle = new Vehicle(roadId, roadId + 1);
        appendVehicleToInLane(vehicle);
    }

    public void appendVehicleToInLane(Vehicle vehicle){
        if(inLane.getVehicles().size() > 0) {
            Vehicle frontVehicle = inLane.getVehicles().get(inLane.getVehicles().size() - 1);
            if (vehicle.getTrajectory().getLocation() >= frontVehicle.getTrajectory().getLocation() - frontVehicle.getLength()- NORMAL_VEHICLE_SPACING) {
                vehicle.getTrajectory().setLocation(frontVehicle.getTrajectory().getLocation() - frontVehicle.getLength() - NORMAL_VEHICLE_SPACING);
            }
        }

        inLane.addVehicleToQueue(vehicle);
    }
}