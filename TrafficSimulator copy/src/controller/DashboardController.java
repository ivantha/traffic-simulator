package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import constant.LaneType;
import javafx.scene.shape.Rectangle;
import model.Intersection;
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

import static constant.LaneType.INTERSECTION_LANE;
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
    private AnchorPane backgroundCanvasAnchorPane;

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
    private Intersection intersection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Draw.drawMap(roadMap, backgroundCanvasAnchorPane);

        vehicleDensitySlider.valueProperty().bindBidirectional(Global.VEHICLE_DENSITY);
        averageGapSlider.valueProperty().bindBidirectional(Global.AVERAGE_GAP);
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

        intersection = roadMap.getJunction().getIntersection();
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

            moveVehicles(intersection.getIntLane(1,7).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(1,8).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(1,9).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(2,7).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(2,8).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(2,9).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(3,7).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(3,8).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(3,9).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(4,7).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(4,8).getVehicles(), INTERSECTION_LANE);
//            moveVehicles(intersection.getIntLane(4,9).getVehicles(), INTERSECTION_LANE);
        }

        public void moveVehicles(ArrayList<Vehicle> vehicles, LaneType laneType) {
            Iterator<Vehicle> vehicleIterator = vehicles.iterator();

            while (vehicleIterator.hasNext()) {
                Vehicle v = vehicleIterator.next();
                v.move();

                if (v.trajectory.getLocation() >= Global.ROAD_LENGTH) {
                    vehicleIterator.remove();

                    if (laneType == IN_LANE) {
                        intersection.appendVehicleToIntLane(v, v.trajectory.origin, 6 + v.trajectory.destinationDiff);

//                        Road destinationRoad = roadMap.getJunction().getRoad(v.trajectory.destination);
//
//                        Rectangle rect = new Rectangle();
//
//                        if(v.trajectory.destinationDiff == 1){
//                            Platform.runLater(() -> Draw.animateIntersection(backgroundCanvasAnchorPane, rect, v, v.trajectory.origin,
//                                    v.trajectory.getLane().getLaneId(), v.trajectory.destination, 6, event -> {
//                                        backgroundCanvasAnchorPane.getChildren().remove(rect);
//                                        destinationRoad.appendVehicleToOutLane(v, 6);
//                                    }));
//                        }else if(v.trajectory.destinationDiff == 2){
//                            Platform.runLater(() -> Draw.animateIntersection(backgroundCanvasAnchorPane, rect, v, v.trajectory.origin,
//                                    v.trajectory.getLane().getLaneId(), v.trajectory.destination, 5, event -> {
//                                        backgroundCanvasAnchorPane.getChildren().remove(rect);
//                                        destinationRoad.appendVehicleToOutLane(v, 5);
//                                    }));
//                        }else{
//                            Platform.runLater(() -> Draw.animateIntersection(backgroundCanvasAnchorPane, rect, v, v.trajectory.origin,
//                                    v.trajectory.getLane().getLaneId(), v.trajectory.destination, 4, event -> {
//                                        backgroundCanvasAnchorPane.getChildren().remove(rect);
//                                        destinationRoad.appendVehicleToOutLane(v, 4);
//                                    }));
//                        }
                    }

                    for (int i = 0; i < vehicles.size(); i++) {
                        vehicles.get(i).trajectory.setLaneIndex(i);
                    }
                }
            }
        }
    }
}
