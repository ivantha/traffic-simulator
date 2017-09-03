package model;

import static main.Global.AVERAGE_SPACING;
import static main.Global.LANE_POPULATION;

public class Road {
    private final int roadId;

    private final Lane inLane1;
    private final Lane inLane2;
    private final Lane inLane3;

    private final Lane outLane4;
    private final Lane outLane5;
    private final Lane outLane6;

    public Road(int roadId) {
        this.roadId = roadId;

        inLane1 = new Lane(1);
        inLane2 = new Lane(2);
        inLane3 = new Lane(3);

        outLane4 = new Lane(4);
        outLane5 = new Lane(5);
        outLane6 = new Lane(6);
    }

    public int getRoadId() {
        return roadId;
    }
    public Lane getInLane1() {
        return inLane1;
    }
    public Lane getInLane2() {
        return inLane2;
    }
    public Lane getInLane3() {
        return inLane3;
    }
    public Lane getOutLane4() {
        return outLane4;
    }
    public Lane getOutLane5() {
        return outLane5;
    }
    public Lane getOutLane6() {
        return outLane6;
    }

    public void populateRoad(){
        if(inLane1.getVehicles().size() + inLane2.getVehicles().size() < LANE_POPULATION.get()){
            generateInVehicle();
        }
    }

    public void generateInVehicle(){
        int origin = roadId;
        int destination = origin + 1 + (int)((Math.random() + 0.4) * 2);
        destination = origin + 1;
        if(destination > 4){
            destination = destination - 4;
        }

        Vehicle vehicle = new Vehicle(origin, destination);

        int des1 = origin + 1;
        if(des1 > 4){
            des1 = des1 - 4;
        }
        int des2 = origin + 2;
        if(des2 > 4){
            des2 = des2 - 4;
        }
        int des3 = origin + 3;
        if(des3 > 4){
            des3 = des3 - 4;
        }

        if(des1 == destination){
            appendVehicleToInLane(vehicle, inLane1);
        }else if(des2 == destination){
            appendVehicleToInLane(vehicle, inLane2);
        }else if(des3 == destination){
            appendVehicleToInLane(vehicle, inLane3);
        }
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
        Lane outLane;
        switch (laneId){
            case 4:
                outLane = outLane4;
                break;
            case 5:
                outLane = outLane5;
                break;
            case 6:
                outLane = outLane6;
                break;
            default:
                outLane = null;
                break;
        }

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

        switch (laneId){
            case 4:
                outLane4.addVehicleToQueue(vehicle);
                break;
            case 5:
                outLane5.addVehicleToQueue(vehicle);
                break;
            case 6:
                outLane6.addVehicleToQueue(vehicle);
                break;
        }
    }
}