package model;

import java.util.ArrayList;

public class Lane {
    private final int laneId;
    private final ArrayList<Vehicle> vehicles;

    public Lane(int laneId) {
        this.laneId = laneId;
        this.vehicles = new ArrayList<>();
    }

    public int getLaneId() {
        return laneId;
    }
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicleToQueue(Vehicle vehicle){
        getVehicles().add(vehicle);
        vehicle.getTrajectory().setLane(this);
        vehicle.getTrajectory().setLaneIndex(getVehicles().size() - 1);
    }
}
