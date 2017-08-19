package a;

import java.util.ArrayList;

public class Lane {
    private final LaneType laneType;
    private final ArrayList<Vehicle> vehicles;

    public Lane(LaneType laneType) {
        this.laneType = laneType;
        this.vehicles = new ArrayList<>();
    }

    public LaneType getLaneType() {
        return laneType;
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
