package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import constant.LaneType;
import javafx.scene.shape.Rectangle;
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
import model.Road;
import model.RoadMap;
import model.Vehicle;

import java.net.URL;
import java.util.*;

import static main.Global.REFRESH_INTERVAL;
import static constant.LaneType.IN_LANE;
import static constant.LaneType.OUT_LANE;

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
    private AnchorPane topCanvasAnchorPane;

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
        if (null != mainTimer) {
            mainTimer.cancel();
            mainTimer.purge();
        }
        uiUpdater.stop();
    }

    private void reset() {
        roadMap = new RoadMap();

        nRoad = roadMap.getJunction().getRoad(1);
        eRoad = roadMap.getJunction().getRoad(2);
        sRoad = roadMap.getJunction().getRoad(3);
        wRoad = roadMap.getJunction().getRoad(4);
    }

    class CustomerTimerTask extends TimerTask {
        @Override
        public void run() {
            roadMap.populateRoadMap();

            moveVehicles(nRoad.getLane(1).getVehicles(), IN_LANE);
            moveVehicles(nRoad.getLane(2).getVehicles(), IN_LANE);
            moveVehicles(nRoad.getLane(3).getVehicles(), IN_LANE);
            moveVehicles(eRoad.getLane(1).getVehicles(), IN_LANE);
            moveVehicles(eRoad.getLane(2).getVehicles(), IN_LANE);
            moveVehicles(eRoad.getLane(3).getVehicles(), IN_LANE);
            moveVehicles(sRoad.getLane(1).getVehicles(), IN_LANE);
            moveVehicles(sRoad.getLane(2).getVehicles(), IN_LANE);
            moveVehicles(sRoad.getLane(3).getVehicles(), IN_LANE);
            moveVehicles(wRoad.getLane(1).getVehicles(), IN_LANE);
            moveVehicles(wRoad.getLane(2).getVehicles(), IN_LANE);
            moveVehicles(wRoad.getLane(3).getVehicles(), IN_LANE);

            moveVehicles(nRoad.getLane(6).getVehicles(), OUT_LANE);
            moveVehicles(nRoad.getLane(5).getVehicles(), OUT_LANE);
            moveVehicles(nRoad.getLane(4).getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getLane(6).getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getLane(5).getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getLane(4).getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getLane(6).getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getLane(5).getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getLane(4).getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getLane(6).getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getLane(5).getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getLane(4).getVehicles(), OUT_LANE);
        }

        public void moveVehicles(ArrayList<Vehicle> vehicles, LaneType laneType) {
            Iterator<Vehicle> vehicleIterator = vehicles.iterator();

            while (vehicleIterator.hasNext()) {
                Vehicle v = vehicleIterator.next();
                v.move();

                if (v.getTrajectory().getLocation() >= Global.ROAD_LENGTH) {
                    vehicleIterator.remove();
                    if (laneType == IN_LANE) {
                        insertIntoIntersection(v);
                    }
                    for (int i = 0; i < vehicles.size(); i++) {
                        vehicles.get(i).getTrajectory().setLaneIndex(i);
                    }
                }
            }
        }

        public void insertIntoIntersection(Vehicle vehicle) {
            int destination = vehicle.getTrajectory().getDestination();
            Road destinationRoad = roadMap.getJunction().getRoad(destination);

            if (destinationRoad.isOutLaneFree(6, vehicle)) {
                destinationRoad.appendVehicleToOutIntLane(vehicle, 6);
            } else if (destinationRoad.isOutLaneFree(5, vehicle)) {
                destinationRoad.appendVehicleToOutIntLane(vehicle, 5);
            } else if (destinationRoad.isOutLaneFree(4, vehicle)) {
                destinationRoad.appendVehicleToOutIntLane(vehicle, 4);
            }
        }
    }
}
