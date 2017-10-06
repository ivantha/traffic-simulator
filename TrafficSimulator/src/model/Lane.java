package model;

import constant.LaneType;
import main.Global;

import java.util.ArrayList;

import static main.Global.AVERAGE_GAP;
import static main.Global.ROAD_LENGTH;
import static main.Global.ROAD_RADIUS;

public class Lane {
    public final int laneId;
    public final LaneType laneType;
    public final double length;

    private final ArrayList<Vehicle> vehicles = new ArrayList<>();

    public Lane(int laneId) {
        this.laneId = laneId;

        switch (laneId){
            case 1:
            case 2:
            case 3:
                laneType = LaneType.IN_LANE;
                length = ROAD_LENGTH + ROAD_RADIUS;
                break;
            case 4:
            case 5:
            case 6:
                laneType = LaneType.OUT_LANE;
                length = ROAD_LENGTH + ROAD_RADIUS;
                break;
            case 7:
                laneType = LaneType.INTERSECTION_LANE;
                length = (Math.PI * (ROAD_RADIUS / 6) / 2) + ROAD_RADIUS;
                break;
            case 8:
                laneType = LaneType.INTERSECTION_LANE;
                length = (ROAD_RADIUS * 2) + ROAD_RADIUS;
                break;
            case 9:
                laneType = LaneType.INTERSECTION_LANE;
                length = (Math.PI * (ROAD_RADIUS + ROAD_RADIUS / 6) / 2) + ROAD_RADIUS;
                break;
            default:            // This case should not happen
                length = 0;
                laneType = null;
                break;
        }
    }

    public int getLaneId() {
        return laneId;
    }
    public LaneType getLaneType() {
        return laneType;
    }
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicleToQueue(Vehicle vehicle){
        vehicles.add(vehicle);
        vehicle.trajectory.setLane(this);
        vehicle.trajectory.setLaneIndex(vehicles.size() - 1);
    }

    public boolean isSapceAvailable(Vehicle vehicle){
        if(getVehicles().size() > 0){
            Vehicle frontVehicle = getVehicles().get(getVehicles().size() - 1);
            if (vehicle.length < frontVehicle.trajectory.getLocation() - frontVehicle.length - AVERAGE_GAP.get()) {
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
}
