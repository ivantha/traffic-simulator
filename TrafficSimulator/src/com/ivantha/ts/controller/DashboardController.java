package com.ivantha.ts.controller;

import com.ivantha.ts.model.*;
import com.ivantha.ts.ui.components.TileToggle;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import com.ivantha.ts.constant.LaneType;
import com.ivantha.ts.util.Draw;
import com.ivantha.ts.common.Global;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

import static com.ivantha.ts.constant.LaneType.INTERSECTION_LANE;
import static com.ivantha.ts.model.TrafficLight.TrafficLightState.GREEN;
import static java.lang.Math.PI;
import static com.ivantha.ts.common.Global.REFRESH_INTERVAL;
import static com.ivantha.ts.constant.LaneType.IN_LANE;
import static com.ivantha.ts.constant.LaneType.OUT_LANE;
import static com.ivantha.ts.common.Global.ROAD_RADIUS;

public class DashboardController implements Initializable {
    @FXML
    private JFXToggleButton northToggleButton;
    @FXML
    private JFXToggleButton eastToggleButton;
    @FXML
    private JFXToggleButton southToggleButton;
    @FXML
    private JFXToggleButton westToggleButton;

    @FXML
    private JFXSlider vehicleDensitySlider;
    @FXML
    private JFXSlider averageSpeedSlider;
    @FXML
    private JFXSlider averageGapSlider;

    @FXML
    private AnchorPane canvasAnchorPane;
    @FXML
    private AnchorPane backgroundCanvasAnchorPane;

    @FXML
    private Button startStopButton;
    @FXML
    private Button resetButton;

    @FXML
    private TilePane nLane1IntervalTile;
    @FXML
    private TilePane nLane2IntervalTile;
    @FXML
    private TilePane nLane3IntervalTile;
    @FXML
    private TilePane eLane1IntervalTile;
    @FXML
    private TilePane eLane2IntervalTile;
    @FXML
    private TilePane eLane3IntervalTile;
    @FXML
    private TilePane sLane1IntervalTile;
    @FXML
    private TilePane sLane2IntervalTile;
    @FXML
    private TilePane sLane3IntervalTile;
    @FXML
    private TilePane wLane1IntervalTile;
    @FXML
    private TilePane wLane2IntervalTile;
    @FXML
    private TilePane wLane3IntervalTile;

    @FXML
    private HBox nLane1ManualTile;
    @FXML
    private HBox nLane2ManualTile;
    @FXML
    private HBox nLane3ManualTile;
    @FXML
    private HBox eLane1ManualTile;
    @FXML
    private HBox eLane2ManualTile;
    @FXML
    private HBox eLane3ManualTile;
    @FXML
    private HBox sLane1ManualTile;
    @FXML
    private HBox sLane2ManualTile;
    @FXML
    private HBox sLane3ManualTile;
    @FXML
    private HBox wLane1ManualTile;
    @FXML
    private HBox wLane2ManualTile;
    @FXML
    private HBox wLane3ManualTile;




    private Timeline uiUpdater;
    private Timer mainTimer;
    private TimerTask mainTimerTask;

    private RoadMap roadMap;
    private Road nRoad;
    private Road eRoad;
    private Road sRoad;
    private Road wRoad;
    private Intersection intersection;
    private HashMap<Integer, Lane> nIntRoad;
    private HashMap<Integer, Lane> eIntRoad;
    private HashMap<Integer, Lane> sIntRoad;
    private HashMap<Integer, Lane> wIntRoad;

    private boolean isStarted = false;

    private TileToggle<TilePane, HBox> nLane1TileToggle;
    private TileToggle<TilePane, HBox> nLane2TileToggle;
    private TileToggle<TilePane, HBox> nLane3TileToggle;
    private TileToggle<TilePane, HBox> eLane1TileToggle;
    private TileToggle<TilePane, HBox> eLane2TileToggle;
    private TileToggle<TilePane, HBox> eLane3TileToggle;
    private TileToggle<TilePane, HBox> sLane1TileToggle;
    private TileToggle<TilePane, HBox> sLane2TileToggle;
    private TileToggle<TilePane, HBox> sLane3TileToggle;
    private TileToggle<TilePane, HBox> wLane1TileToggle;
    private TileToggle<TilePane, HBox> wLane2TileToggle;
    private TileToggle<TilePane, HBox> wLane3TileToggle;

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

        startStopButton.setOnAction(event -> {
            if(!isStarted){
                stop();

                mainTimer = new Timer();
                mainTimerTask = new CustomerTimerTask();
                mainTimer.schedule(mainTimerTask, 0, REFRESH_INTERVAL);
                uiUpdater.play();

                isStarted = true;
                startStopButton.setText("Stop");
            }else{
                stop();

                isStarted = false;
                startStopButton.setText("Start");
            }
        });

        resetButton.setOnAction(event -> reset());

        northToggleButton.setOnAction(event -> roadMap.setNorthEnabled(northToggleButton.isSelected()));
        eastToggleButton.setOnAction(event -> roadMap.setEastEnabled(eastToggleButton.isSelected()));
        southToggleButton.setOnAction(event -> roadMap.setSouthEnabled(southToggleButton.isSelected()));
        westToggleButton.setOnAction(event -> roadMap.setWestEnabled(westToggleButton.isSelected()));

        nLane1TileToggle = new TileToggle<>(nLane1IntervalTile, nLane1ManualTile);
        nLane2TileToggle = new TileToggle<>(nLane2IntervalTile, nLane2ManualTile);
        nLane3TileToggle = new TileToggle<>(nLane3IntervalTile, nLane3ManualTile);
        eLane1TileToggle = new TileToggle<>(eLane1IntervalTile, eLane1ManualTile);
        eLane2TileToggle = new TileToggle<>(eLane2IntervalTile, eLane2ManualTile);
        eLane3TileToggle = new TileToggle<>(eLane3IntervalTile, eLane3ManualTile);
        sLane1TileToggle = new TileToggle<>(sLane1IntervalTile, sLane1ManualTile);
        sLane2TileToggle = new TileToggle<>(sLane2IntervalTile, sLane2ManualTile);
        sLane3TileToggle = new TileToggle<>(sLane3IntervalTile, sLane3ManualTile);
        wLane1TileToggle = new TileToggle<>(wLane1IntervalTile, wLane1ManualTile);
        wLane2TileToggle = new TileToggle<>(wLane2IntervalTile, wLane2ManualTile);
        wLane3TileToggle = new TileToggle<>(wLane3IntervalTile, wLane3ManualTile);
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

        nIntRoad = intersection.getNorthIntRoad();
        eIntRoad = intersection.getEastIntRoad();
        sIntRoad = intersection.getSouthIntRoad();
        wIntRoad = intersection.getWestIntRoad();

        northToggleButton.setSelected(false);
        eastToggleButton.setSelected(false);
        southToggleButton.setSelected(false);
        westToggleButton.setSelected(false);
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

            moveVehicles(nIntRoad.get(7).getVehicles(), INTERSECTION_LANE);
            moveVehicles(nIntRoad.get(8).getVehicles(), INTERSECTION_LANE);
            moveVehicles(nIntRoad.get(9).getVehicles(), INTERSECTION_LANE);
            moveVehicles(eIntRoad.get(7).getVehicles(), INTERSECTION_LANE);
            moveVehicles(eIntRoad.get(8).getVehicles(), INTERSECTION_LANE);
            moveVehicles(eIntRoad.get(9).getVehicles(), INTERSECTION_LANE);
            moveVehicles(sIntRoad.get(7).getVehicles(), INTERSECTION_LANE);
            moveVehicles(sIntRoad.get(8).getVehicles(), INTERSECTION_LANE);
            moveVehicles(sIntRoad.get(9).getVehicles(), INTERSECTION_LANE);
            moveVehicles(wIntRoad.get(7).getVehicles(), INTERSECTION_LANE);
            moveVehicles(wIntRoad.get(8).getVehicles(), INTERSECTION_LANE);
            moveVehicles(wIntRoad.get(9).getVehicles(), INTERSECTION_LANE);
        }

        public void moveVehicles(ArrayList<Vehicle> vehicles, LaneType laneType) {
            Iterator<Vehicle> vehicleIterator = vehicles.iterator();

            while (vehicleIterator.hasNext()) {
                Vehicle v = vehicleIterator.next();

                switch(laneType){
                    case IN_LANE:
                        if(v.trajectory.getLocation() >= Global.ROAD_LENGTH){
                            boolean trafficLightGreen = roadMap.getJunction().getRoad(v.trajectory.origin).getLane(v.trajectory.startLaneId).getTrafficLight().getState() == GREEN;
                            boolean spaceAvalable = intersection.getIntLane(v.trajectory.origin, 6 + v.trajectory.destinationDiff).isSapceAvailable(v);
                            if(trafficLightGreen && spaceAvalable){
                                vehicleIterator.remove();
                                intersection.appendVehicleToIntLane(v, v.trajectory.origin, 6 + v.trajectory.destinationDiff);

                                for (int i = 0; i < vehicles.size(); i++) {
                                    vehicles.get(i).trajectory.setLaneIndex(i);
                                }
                            }
                        }else{
                            v.move();
                        }
                        break;
                    case OUT_LANE:
                        if(v.trajectory.getLocation() >= Global.ROAD_LENGTH){
                            vehicleIterator.remove();

                            for (int i = 0; i < vehicles.size(); i++) {
                                vehicles.get(i).trajectory.setLaneIndex(i);
                            }
                        }else{
                            v.move();
                        }
                        break;
                    case INTERSECTION_LANE:
                        double laneWidth = ROAD_RADIUS / 6;
                        switch (v.trajectory.destinationDiff) {
                            case 1:
                                double thetaRadSmall = v.trajectory.getLocation() / (laneWidth + v.length / 2);
                                if (thetaRadSmall >= PI / 2) {
                                    if(roadMap.getJunction().getRoad(v.trajectory.destination).getLane(6).isSapceAvailable(v)){
                                        vehicleIterator.remove();
                                        roadMap.getJunction().getRoad(v.trajectory.destination).appendVehicleToOutLane(v, 6);

                                        for (int i = 0; i < vehicles.size(); i++) {
                                            vehicles.get(i).trajectory.setLaneIndex(i);
                                        }
                                    }
                                } else {
                                    v.move();
                                }
                                break;
                            case 2:
                                if (v.trajectory.getLocation() >= ROAD_RADIUS * 2 + v.length) {
                                    if(roadMap.getJunction().getRoad(v.trajectory.destination).getLane(5).isSapceAvailable(v)){
                                        vehicleIterator.remove();
                                        roadMap.getJunction().getRoad(v.trajectory.destination).appendVehicleToOutLane(v, 5);

                                        for (int i = 0; i < vehicles.size(); i++) {
                                            vehicles.get(i).trajectory.setLaneIndex(i);
                                        }
                                    }
                                } else {
                                    v.move();
                                }
                                break;
                            case 3:
                                double thetaRadLarge = v.trajectory.getLocation() / (ROAD_RADIUS + laneWidth + v.length / 2);
                                if (thetaRadLarge >= PI / 2) {
                                    if(roadMap.getJunction().getRoad(v.trajectory.destination).getLane(4).isSapceAvailable(v)){
                                        vehicleIterator.remove();
                                        roadMap.getJunction().getRoad(v.trajectory.destination).appendVehicleToOutLane(v, 4);

                                        for (int i = 0; i < vehicles.size(); i++) {
                                            vehicles.get(i).trajectory.setLaneIndex(i);
                                        }
                                    }
                                } else {
                                    v.move();
                                }
                                break;
                        }
                        break;
                }
            }
        }
    }
}
