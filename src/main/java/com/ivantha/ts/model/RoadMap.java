package com.ivantha.ts.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class RoadMap {
    private final Junction junction;

    private BooleanProperty northEnabled = new SimpleBooleanProperty(false);
    private BooleanProperty eastEnabled = new SimpleBooleanProperty(false);
    private BooleanProperty southEnabled = new SimpleBooleanProperty(false);
    private BooleanProperty westEnabled = new SimpleBooleanProperty(false);

    public RoadMap() {
        junction = new Junction();
    }

    public Junction getJunction() {
        return junction;
    }

    public boolean isNorthEnabled() {
        return northEnabled.get();
    }

    public BooleanProperty northEnabledProperty() {
        return northEnabled;
    }

    public void setNorthEnabled(boolean northEnabled) {
        this.northEnabled.set(northEnabled);
    }

    public boolean isEastEnabled() {
        return eastEnabled.get();
    }

    public BooleanProperty eastEnabledProperty() {
        return eastEnabled;
    }

    public void setEastEnabled(boolean eastEnabled) {
        this.eastEnabled.set(eastEnabled);
    }

    public boolean isSouthEnabled() {
        return southEnabled.get();
    }

    public BooleanProperty southEnabledProperty() {
        return southEnabled;
    }

    public void setSouthEnabled(boolean southEnabled) {
        this.southEnabled.set(southEnabled);
    }

    public boolean isWestEnabled() {
        return westEnabled.get();
    }

    public BooleanProperty westEnabledProperty() {
        return westEnabled;
    }

    public void setWestEnabled(boolean westEnabled) {
        this.westEnabled.set(westEnabled);
    }

    public void populateRoadMap() {
        if (isNorthEnabled()) {
            junction.getRoad(1).populateRoad();
        }

        if (isEastEnabled()) {
            junction.getRoad(2).populateRoad();
        }

        if (isSouthEnabled()) {
            junction.getRoad(3).populateRoad();
        }

        if (isWestEnabled()) {
            junction.getRoad(4).populateRoad();
        }
    }
}
