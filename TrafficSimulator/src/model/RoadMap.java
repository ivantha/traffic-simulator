package model;

public class RoadMap {
    private final Junction junction;

    public RoadMap() {
        junction = new Junction();
    }

    public Junction getJunction() {
        return junction;
    }

    public void populateRoadMap(){
        junction.getnRoad().populateRoad();
        junction.geteRoad().populateRoad();
        junction.getsRoad().populateRoad();
        junction.getwRoad().populateRoad();
    }
}
