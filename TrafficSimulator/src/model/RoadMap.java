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
        junction.getRoad(1).populateRoad();
        junction.getRoad(2).populateRoad();
        junction.getRoad(3).populateRoad();
        junction.getRoad(4).populateRoad();
    }
}
