package model;

public class Road {
    private final int id;
    private final Lane inLane;
    private final Lane outLane;

    public Road(int id) {
        this.id = id;
        inLane = new Lane(LaneType.IN_LANE);
        outLane = new Lane(LaneType.OUT_LANE);
    }

    public int getId() {
        return id;
    }

    public Lane getInLane() {
        return inLane;
    }

    public Lane getOutLane() {
        return outLane;
    }

    public void generateInVehicle(){
        Vehicle vehicle = new Vehicle(inLane.getVehicles().size() + 1, id, id + 1);
        inLane.addVehicleToQueue(vehicle);
    }

    public void generateOutVehicle(){

    }
}
