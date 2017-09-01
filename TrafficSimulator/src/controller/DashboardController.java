package controller;

import javafx.scene.control.Slider;
import util.Draw;
import main.Global;
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
import model.Vehicle;

import java.net.URL;
import java.util.*;

import static main.Global.REFRESH_INTERVAL;
import static model.LaneType.IN_LANE;
import static model.LaneType.OUT_LANE;

public class DashboardController implements Initializable {
    @FXML
    private Slider vehicleDensitySlider;
    @FXML
    private Slider averageSpeedSlider;
    @FXML
    private Slider averageGapSlider;

    @FXML
    private AnchorPane canvasAnchorPane;

    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button resetButton;

    private Timeline uiUpdater;
    private Timer mainTimer;
    private TimerTask mainTimerTask;

    private RoadMap roadMap;
    private Road nRoad;
    private Road eRoad;
    private Road sRoad;
    private Road wRoad;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Draw.drawMap(roadMap, canvasAnchorPane);

        vehicleDensitySlider.valueProperty().bindBidirectional(Global.LANE_POPULATION);
        averageGapSlider.valueProperty().bindBidirectional(Global.AVERAGE_SPACING);
        averageSpeedSlider.valueProperty().bindBidirectional(Global.AVERAGE_SPEED);

        reset();

        uiUpdater = new Timeline(new KeyFrame(Duration.millis(REFRESH_INTERVAL), event1 -> {
            Platform.runLater(() -> Draw.refreshMap(roadMap, canvasAnchorPane));
        }));
        uiUpdater.setCycleCount(Timeline.INDEFINITE);

        startButton.setOnAction(event -> {
            stop();

            mainTimer = new Timer();
            mainTimerTask = new CustomerTimerTask();
            mainTimer.schedule(mainTimerTask, 0, REFRESH_INTERVAL);
            uiUpdater.play();
        });

        stopButton.setOnAction(event -> stop());

        resetButton.setOnAction(event -> reset());
    }

    private void stop() {
        if(null != mainTimer) {
            mainTimer.cancel();
            mainTimer.purge();
        }
        uiUpdater.stop();
    }

    private void reset() {
        roadMap = new RoadMap();

        nRoad = roadMap.getJunction().getnRoad();
        eRoad = roadMap.getJunction().geteRoad();
        sRoad = roadMap.getJunction().getsRoad();
        wRoad = roadMap.getJunction().getwRoad();
    }

    class CustomerTimerTask extends TimerTask {
        @Override
        public void run() {
            roadMap.populateRoadMap();

            moveVehicles(nRoad.getInLane1().getVehicles(), IN_LANE);
            moveVehicles(nRoad.getInLane2().getVehicles(), IN_LANE);
            moveVehicles(nRoad.getInLane3().getVehicles(), IN_LANE);
            moveVehicles(eRoad.getInLane1().getVehicles(), IN_LANE);
            moveVehicles(eRoad.getInLane2().getVehicles(), IN_LANE);
            moveVehicles(eRoad.getInLane3().getVehicles(), IN_LANE);
            moveVehicles(sRoad.getInLane1().getVehicles(), IN_LANE);
            moveVehicles(sRoad.getInLane2().getVehicles(), IN_LANE);
            moveVehicles(sRoad.getInLane3().getVehicles(), IN_LANE);
            moveVehicles(wRoad.getInLane1().getVehicles(), IN_LANE);
            moveVehicles(wRoad.getInLane2().getVehicles(), IN_LANE);
            moveVehicles(wRoad.getInLane3().getVehicles(), IN_LANE);

            moveVehicles(nRoad.getOutLane1().getVehicles(), OUT_LANE);
            moveVehicles(nRoad.getOutLane2().getVehicles(), OUT_LANE);
            moveVehicles(nRoad.getOutLane3().getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getOutLane1().getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getOutLane2().getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getOutLane3().getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getOutLane1().getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getOutLane2().getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getOutLane3().getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getOutLane1().getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getOutLane2().getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getOutLane3().getVehicles(), OUT_LANE);

            nRoad.refreshOutLaneQueue();
            eRoad.refreshOutLaneQueue();
            sRoad.refreshOutLaneQueue();
            wRoad.refreshOutLaneQueue();
        }

        public void moveVehicles(ArrayList<Vehicle> vehicles, LaneType laneType) {
            Iterator<Vehicle> vehicleIterator = vehicles.iterator();

            while (vehicleIterator.hasNext()) {
                Vehicle v = vehicleIterator.next();
                v.move();

                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Fix vehicle removal condition. Some vehicles get removed prematurely
                if (v.getTrajectory().getLocation() + v.getLength() >= Global.ROAD_LENGTH) {
                    vehicleIterator.remove();
                    if (laneType == IN_LANE) {
                        changeRoad(v);
                    }
                    for (int i = 0; i < vehicles.size(); i++) {
                        vehicles.get(i).getTrajectory().setLaneIndex(i);
                    }
                }
            }
        }

        public void changeRoad(Vehicle vehicle) {
            switch (vehicle.getTrajectory().getDestination()) {
                case 1:
                    nRoad.appendVehicleToRandomOutLane(vehicle);
                    break;
                case 2:
                    eRoad.appendVehicleToRandomOutLane(vehicle);
                    break;
                case 3:
                    sRoad.appendVehicleToRandomOutLane(vehicle);
                    break;
                case 4:
                    wRoad.appendVehicleToRandomOutLane(vehicle);
                    break;
                default:
                    System.out.println(vehicle.getTrajectory().getDestination());
            }
        }
    }
}
