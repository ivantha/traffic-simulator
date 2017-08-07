package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Road;
import model.RoadMap;
import model.Vehicle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardController implements Initializable {
    @FXML
    private Button startButton;
    @FXML
    private AnchorPane canvasAnchorPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RoadMap roadMap = new RoadMap();

        Road nRoad = roadMap.getJunction().getnRoad();
        Road eRoad = roadMap.getJunction().geteRoad();
        Road sRoad = roadMap.getJunction().getsRoad();
        Road wRoad = roadMap.getJunction().getwRoad();

        Draw.drawMap(roadMap, canvasAnchorPane);

        startButton.setOnAction(event -> {
            Timeline uiUpdater = new Timeline(new KeyFrame(Duration.millis(10), event1 -> {
                Draw.refreshMap(roadMap, canvasAnchorPane);
            }));
            uiUpdater.setCycleCount(Timeline.INDEFINITE);
            uiUpdater.play();

            nRoad.generateInVehicle();
            eRoad.generateInVehicle();
            sRoad.generateInVehicle();
            wRoad.generateInVehicle();

            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            for(Vehicle v: nRoad.getInLane().getVehicles()){
                                v.setLocation(v.getLocation() + v.getBaseSpeed());
                            }

                            for(Vehicle v: eRoad.getInLane().getVehicles()){

                            }

                            for(Vehicle v: sRoad.getInLane().getVehicles()){

                            }

                            for(Vehicle v: wRoad.getInLane().getVehicles()){

                            }
                        }
                    }, 0, 10);
        });
    }
}
