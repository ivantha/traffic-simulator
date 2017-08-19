package a;

import model.Junction;

public class RoadMap {
    private Junction junction;

    public RoadMap() {
        junction = new Junction();
    }

    public Junction getJunction() {
        return junction;
    }

    public void setJunction(Junction junction) {
        this.junction = junction;
    }

    public void changeRoad(Vehicle vehicle){
        if(vehicle.getTrajectory().getDestination() == 1){
            junction.getnRoad().getOutLane().addVehicleToQueue(vehicle);
        }else if(vehicle.getTrajectory().getDestination() == 2){
            junction.geteRoad().getOutLane().addVehicleToQueue(vehicle);
        }else if(vehicle.getTrajectory().getDestination() == 3){
            junction.getsRoad().getOutLane().addVehicleToQueue(vehicle);
        }else if(vehicle.getTrajectory().getDestination() == 4){
            junction.getwRoad().getOutLane().addVehicleToQueue(vehicle);
        }
    }

    public void populateRoadMap(){
        junction.getnRoad().populateRoad();
        junction.geteRoad().populateRoad();
        junction.getsRoad().populateRoad();
        junction.getwRoad().populateRoad();
    }
}
