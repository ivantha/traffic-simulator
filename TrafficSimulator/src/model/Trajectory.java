package model;

import main.Global;

public class Trajectory {
    private final int origin;
    private final int destination;
    private double location;

    private int laneIndex;
    private Lane lane;

    public Trajectory(int origin, int destination, int vehicleLength) {
        this.origin = origin;
        this.destination = destination;
        this.location = vehicleLength;
    }

    public int getOrigin() {
        return origin;
    }
    public int getDestination() {
        return destination;
    }
    public double getLocation() {
        return location;
    }
    public void setLocation(double location) {
        this.location = location;
    }
    public int getLaneIndex() {
        return laneIndex;
    }
    public void setLaneIndex(int laneIndex) {
        this.laneIndex = laneIndex;
    }
    public Lane getLane() {
        return lane;
    }
    public void setLane(Lane lane) {
        this.lane = lane;
    }
    public boolean isAtFront() {
        return laneIndex == 0;
    }

    public Vehicle getFrontVehicle() {
        if (isAtFront()) {
            return null;
        } else {
            return getLane().getVehicles().get(getLaneIndex() - 1);
        }
    }

    public double getDistanceToStopLine(){
        return Math.abs(Global.CANVAS_RADIUS - location);
    }
}
