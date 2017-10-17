package com.ivantha.ts.model;

import com.ivantha.ts.constant.LaneType;

import java.util.ArrayList;

import static com.ivantha.ts.common.Global.*;

public class Lane {
    private final int laneId;
    private final LaneType laneType;
    private final double length;

    private final ArrayList<Vehicle> vehicles = new ArrayList<>();
    private final TrafficLight trafficLight = new TrafficLight();

    public Lane(int laneId) {
        this.laneId = laneId;

        switch (laneId) {
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

    public double getLength() {
        return length;
    }

    public ArrayList<Vehicle> getVehicleArrayList() {
        return vehicles;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public void addVehicleToQueue(Vehicle vehicle) {
        vehicles.add(vehicle);
        vehicle.getTrajectory().setLane(this);
        vehicle.getTrajectory().setLaneIndex(vehicles.size() - 1);
    }

    public boolean isSapceAvailable(Vehicle vehicle) {
        if (getVehicleArrayList().size() > 0) {
            Vehicle frontVehicle = getVehicleArrayList().get(getVehicleArrayList().size() - 1);
            if (vehicle.getLength() < frontVehicle.getTrajectory().getLocation() - frontVehicle.getLength() - AVERAGE_GAP.get()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
