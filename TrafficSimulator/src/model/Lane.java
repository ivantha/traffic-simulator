package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
        vehicles.add(vehicle);
    }

    public Vehicle getFrontVehicle(Vehicle vehicle){
        Vehicle frontVehicle = null;
        for (Vehicle v: vehicles){
            if(vehicle == v){
                break;
            }else{
                frontVehicle = v;
            }
        }

        return frontVehicle;
    }
}
