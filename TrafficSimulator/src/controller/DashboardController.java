package controller;

import main.Global;
import ui.Draw;
import model.Vehicle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.LaneType;
import model.Road;
import model.RoadMap;

import java.net.URL;
import java.util.*;

import static main.Global.NORMAL_VEHICLE_SPACING;
import static main.Global.REFRESH_INTERVAL;
import static model.LaneType.IN_LANE;
import static model.LaneType.OUT_LANE;

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
            Timeline uiUpdater = new Timeline(new KeyFrame(Duration.millis(REFRESH_INTERVAL), event1 -> {
                Platform.runLater(() -> Draw.refreshMap(roadMap, canvasAnchorPane));
            }));
            uiUpdater.setCycleCount(Timeline.INDEFINITE);
            uiUpdater.play();

            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            roadMap.populateRoadMap();

                            moveVehicles(nRoad.getInLane().getVehicles(), IN_LANE);
                            moveVehicles(eRoad.getInLane().getVehicles(), IN_LANE);
                            moveVehicles(sRoad.getInLane().getVehicles(), IN_LANE);
                            moveVehicles(wRoad.getInLane().getVehicles(), IN_LANE);

                            moveVehicles(nRoad.getOutLane().getVehicles(), OUT_LANE);
                            moveVehicles(eRoad.getOutLane().getVehicles(), OUT_LANE);
                            moveVehicles(sRoad.getOutLane().getVehicles(), OUT_LANE);
                            moveVehicles(wRoad.getOutLane().getVehicles(), OUT_LANE);
                        }

                        public void moveVehicles(ArrayList<Vehicle> vehicles, LaneType laneType){
                            Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                            boolean isRemoved = false;
                            while (vehicleIterator.hasNext()){
                                Vehicle v = vehicleIterator.next();
                                v.move();

                                if(v.getTrajectory().getLocation() + v.getLength() >= Global.ROAD_LENGTH){
                                    vehicleIterator.remove();
                                    isRemoved = true;
                                    if(laneType == IN_LANE) {
                                        changeRoad(v);
                                    }
                                }
                            }

                            if(isRemoved){
                                for(int i = 0; i < vehicles.size(); i++){
                                    vehicles.get(i).getTrajectory().setLaneIndex(i);
                                }
                            }
                        }

                        public void changeRoad(Vehicle vehicle){
                            switch (vehicle.getTrajectory().getDestination()){
                                case 1:
//                                    nRoad.getOutLane().addVehicleToQueue(vehicle);
                                    nRoad.appendVehicleToOutLane(vehicle);
                                    break;
                                case 2:
//                                    eRoad.getOutLane().addVehicleToQueue(vehicle);
                                    eRoad.appendVehicleToOutLane(vehicle);
                                    break;
                                case 3:
//                                    sRoad.getOutLane().addVehicleToQueue(vehicle);
                                    sRoad.appendVehicleToOutLane(vehicle);
                                    break;
                                case 4:
//                                    wRoad.getOutLane().addVehicleToQueue(vehicle);
                                    wRoad.appendVehicleToOutLane(vehicle);
                                    break;
                            }
                        }
                    }, 0, REFRESH_INTERVAL);
        });
    }
}
