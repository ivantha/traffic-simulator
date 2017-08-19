package main;

import a.Draw;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Road;
import a.RoadMap;
import a.Vehicle;

import java.net.URL;
import java.util.Iterator;
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
                Platform.runLater(() -> Draw.refreshMap(roadMap, canvasAnchorPane));
            }));
            uiUpdater.setCycleCount(Timeline.INDEFINITE);
            uiUpdater.play();

            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            roadMap.populateRoadMap();

                            Iterator<Vehicle> vehicleIterator = nRoad.getInLane().getVehicles().iterator();
                            while (vehicleIterator.hasNext()){
                                Vehicle v = vehicleIterator.next();
                                v.move();

                                if(v.getTrajectory().getLocation() + v.getLength() >= Global.ROAD_LENGTH){
                                    vehicleIterator.remove();
                                    roadMap.changeRoad(v);
                                }
                            }

                            vehicleIterator = eRoad.getOutLane().getVehicles().iterator();
                            while (vehicleIterator.hasNext()){
                                Vehicle v = vehicleIterator.next();
                                v.getTrajectory().setLocation(v.getTrajectory().getLocation() - v.getDesiredVelocity());
                                if(v.getTrajectory().getLocation() - v.getLength() <= 0){
                                    vehicleIterator.remove();
                                }
                            }

                        }
                    }, 0, 10);
        });
    }
}
