package model;

import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

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
}
