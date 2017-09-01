package model;

import java.util.ArrayList;
import java.util.Iterator;

import static main.Global.LANE_POPULATION;
import static main.Global.AVERAGE_SPACING;

public class Road {
    private final int roadId;
    private final Lane inLane1;
    private final Lane inLane2;
    private final Lane inLane3;
    private final Lane outLane1;
    private final Lane outLane2;
    private final Lane outLane3;

    private final ArrayList<Vehicle> outLaneQueue;

    public Road(int roadId) {
        this.roadId = roadId;
        inLane1 = new Lane(LaneType.IN_LANE);
        inLane2 = new Lane(LaneType.IN_LANE);
        inLane3 = new Lane(LaneType.IN_LANE);
        outLane1 = new Lane(LaneType.OUT_LANE);
        outLane2 = new Lane(LaneType.OUT_LANE);
        outLane3 = new Lane(LaneType.OUT_LANE);
        outLaneQueue = new ArrayList<>();
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
    public Lane getOutLane1() {
        return outLane1;
    }
    public Lane getOutLane2() {
        return outLane2;
    }
    public Lane getOutLane3() {
        return outLane3;
    }
    public ArrayList<Vehicle> getOutLaneQueue() {
        return outLaneQueue;
    }

    public void populateRoad(){
        if(inLane1.getVehicles().size() + inLane2.getVehicles().size() < LANE_POPULATION.get()){
            generateInVehicle();
        }
    }

    public void generateInVehicle(){
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Randomize vehicle destinations.
        int origin = roadId;
        int destination = roadId + 1;
        if(destination == 5){
            destination = 1;
        }
        Vehicle vehicle = new Vehicle(origin, destination);
        appendVehicleToRandomInLane(vehicle);
    }

    public void appendVehicleToRandomInLane(Vehicle vehicle){
        double seed = Math.random();
        if(0.3 > seed){
            appendVehicleToInLane(vehicle, inLane1);
        }else if(0.6 > seed){
            appendVehicleToInLane(vehicle, inLane2);
        }else{
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

    public void appendVehicleToRandomOutLane(Vehicle vehicle){
        outLaneQueue.add(vehicle);

        refreshOutLaneQueue();
    }

    public void refreshOutLaneQueue(){
        Iterator<Vehicle> vehicleIterator = outLaneQueue.iterator();
        while (vehicleIterator.hasNext()){
            Vehicle v = vehicleIterator.next();

            if(outLane1.getVehicles().size() > 0){
                Vehicle lastVehicle = outLane1.getVehicles().get(outLane1.getVehicles().size() - 1);
                if(v.getLength() > lastVehicle.getTrajectory().getLocation() - lastVehicle.getLength() - AVERAGE_SPACING.get()){
                    break;
                }
            }

            vehicleIterator.remove();

            v.getTrajectory().setLocation(v.getLength());
            outLane1.addVehicleToQueue(v);
        }
    }
}