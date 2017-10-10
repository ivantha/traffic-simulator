package com.ivantha.ts.model;

public class RoadMap {
    private final Junction junction;
    private boolean northEnabled = false;
    private boolean eastEnabled = false;
    private boolean southEnabled = false;
    private boolean westEnabled = false;

    public RoadMap() {
        junction = new Junction();
    }

    public Junction getJunction() {
        return junction;
    }
    public boolean isNorthEnabled() {
        return northEnabled;
    }
    public void setNorthEnabled(boolean northEnabled) {
        this.northEnabled = northEnabled;
    }
    public boolean isEastEnabled() {
        return eastEnabled;
    }
    public void setEastEnabled(boolean eastEnabled) {
        this.eastEnabled = eastEnabled;
    }
    public boolean isSouthEnabled() {
        return southEnabled;
    }
    public void setSouthEnabled(boolean southEnabled) {
        this.southEnabled = southEnabled;
    }
    public boolean isWestEnabled() {
        return westEnabled;
    }
    public void setWestEnabled(boolean westEnabled) {
        this.westEnabled = westEnabled;
    }

    public void populateRoadMap(){
        if(northEnabled){
            junction.getRoad(1).populateRoad();
        }

        if(eastEnabled){
            junction.getRoad(2).populateRoad();
        }

        if(southEnabled){
            junction.getRoad(3).populateRoad();
        }

        if(westEnabled){
            junction.getRoad(4).populateRoad();
        }
    }
}
