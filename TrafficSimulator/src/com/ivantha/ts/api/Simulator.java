package com.ivantha.ts.api;

import com.ivantha.ts.common.Session;
import com.ivantha.ts.model.TrafficLight;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class Simulator {
    private Stage stage;

    public void start(Stage stage) {
        try {
            this.stage = stage;

            Parent root = FXMLLoader.load(getClass().getResource("../ui/view/dashboard.fxml"));
            Scene scene = new Scene(root);
            scene.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    System.exit(0);
                }
            });
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Traffic simulator");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        stage.close();
    }

//    public ArrayList<Boolean> getNorthTraffic() {
//
//    }
//
//    public ArrayList<Boolean> getEastTraffic() {
//
//    }
//
//    public ArrayList<Boolean> getSouthTraffic() {
//`
//    }
//
//    public ArrayList<Boolean> getWestTraffic() {
//
//    }

    public void setNorthLane1Traffic(TrafficLight.TrafficLightState state){
        Session.getNorthLane1TrafficLight().setState(state);
    }

    public void setNorthLane2Traffic(TrafficLight.TrafficLightState state){
        Session.getNorthLane2TrafficLight().setState(state);
    }

    public void setNorthLane3Traffic(TrafficLight.TrafficLightState state){
        Session.getNorthLane3TrafficLight().setState(state);
    }

    public void setEastLane1Traffic(TrafficLight.TrafficLightState state){
        Session.getEastLane1TrafficLight().setState(state);
    }

    public void setEastLane2Traffic(TrafficLight.TrafficLightState state){
        Session.getEastLane2TrafficLight().setState(state);
    }

    public void setEastLane3Traffic(TrafficLight.TrafficLightState state){
        Session.getEastLane3TrafficLight().setState(state);
    }

    public void setSouthLane1Traffic(TrafficLight.TrafficLightState state){
        Session.getSouthLane1TrafficLight().setState(state);
    }

    public void setSouthLane2Traffic(TrafficLight.TrafficLightState state){
        Session.getSouthLane2TrafficLight().setState(state);
    }

    public void setSouthLane3Traffic(TrafficLight.TrafficLightState state){
        Session.getSouthLane3TrafficLight().setState(state);
    }

    public void setWestLane1Traffic(TrafficLight.TrafficLightState state){
        Session.getWestLane1TrafficLight().setState(state);
    }

    public void setWestLane2Traffic(TrafficLight.TrafficLightState state){
        Session.getWestLane2TrafficLight().setState(state);
    }

    public void setWestLane3Traffic(TrafficLight.TrafficLightState state){
        Session.getWestLane3TrafficLight().setState(state);
    }
}
