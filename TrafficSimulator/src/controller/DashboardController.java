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

            moveVehicles(nRoad.getOutLane6().getVehicles(), OUT_LANE);
            moveVehicles(nRoad.getOutLane5().getVehicles(), OUT_LANE);
            moveVehicles(nRoad.getOutLane4().getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getOutLane6().getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getOutLane5().getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getOutLane4().getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getOutLane6().getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getOutLane5().getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getOutLane4().getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getOutLane6().getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getOutLane5().getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getOutLane4().getVehicles(), OUT_LANE);
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

            Rectangle rect = new Rectangle();

            if (destinationRoad.isOutLaneFree(6, vehicle)) {
                Platform.runLater(() -> Draw.animateIntersection(topCanvasAnchorPane, rect, vehicle, vehicle.getTrajectory().getOrigin(),
                        vehicle.getTrajectory().getLane().getLaneId(), vehicle.getTrajectory().getDestination(), 6, event -> {
                            topCanvasAnchorPane.getChildren().remove(rect);
                            destinationRoad.appendVehicleToOutLane(vehicle, 6);
                        }));
            } else if (destinationRoad.isOutLaneFree(5, vehicle)) {
                Platform.runLater(() -> Draw.animateIntersection(topCanvasAnchorPane, rect, vehicle, vehicle.getTrajectory().getOrigin(),
                        vehicle.getTrajectory().getLane().getLaneId(), vehicle.getTrajectory().getDestination(), 5, event -> {
                            topCanvasAnchorPane.getChildren().remove(rect);
                            destinationRoad.appendVehicleToOutLane(vehicle, 5);
                        }));
            } else if (destinationRoad.isOutLaneFree(4, vehicle)) {
                Platform.runLater(() -> Draw.animateIntersection(topCanvasAnchorPane, rect, vehicle, vehicle.getTrajectory().getOrigin(),
                        vehicle.getTrajectory().getLane().getLaneId(), vehicle.getTrajectory().getDestination(), 4, event -> {
                            topCanvasAnchorPane.getChildren().remove(rect);
                            destinationRoad.appendVehicleToOutLane(vehicle, 4);
                        }));
            }
        }
    }
}
