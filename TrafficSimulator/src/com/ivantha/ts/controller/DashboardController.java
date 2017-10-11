package com.ivantha.ts.controller;

import com.ivantha.ts.common.Global;
import com.ivantha.ts.constant.LaneType;
import com.ivantha.ts.model.*;
import com.ivantha.ts.util.Draw;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

import static com.ivantha.ts.common.Global.REFRESH_INTERVAL;
import static com.ivantha.ts.common.Global.ROAD_RADIUS;
import static com.ivantha.ts.constant.LaneType.*;
import static com.ivantha.ts.model.TrafficLight.TrafficLightState.GREEN;
import static com.ivantha.ts.model.TrafficLight.TrafficLightState.ORANGE;
import static com.ivantha.ts.model.TrafficLight.TrafficLightState.RED;
import static java.lang.Math.PI;

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
    private RadioButton n1RedRadioButton;
    @FXML
    private RadioButton n1OrangeRadioButton;
    @FXML
    private RadioButton n1GreenRadioButton;
    @FXML
    private RadioButton n2RedRadioButton;
    @FXML
    private RadioButton n2OrangeRadioButton;
    @FXML
    private RadioButton n2GreenRadioButton;
    @FXML
    private RadioButton n3RedRadioButton;
    @FXML
    private RadioButton n3OrangeRadioButton;
    @FXML
    private RadioButton n3GreenRadioButton;
    @FXML
    private RadioButton e1RedRadioButton;
    @FXML
    private RadioButton e1OrangeRadioButton;
    @FXML
    private RadioButton e1GreenRadioButton;
    @FXML
    private RadioButton e2RedRadioButton;
    @FXML
    private RadioButton e2OrangeRadioButton;
    @FXML
    private RadioButton e2GreenRadioButton;
    @FXML
    private RadioButton e3RedRadioButton;
    @FXML
    private RadioButton e3OrangeRadioButton;
    @FXML
    private RadioButton e3GreenRadioButton;
    @FXML
    private RadioButton s1RedRadioButton;
    @FXML
    private RadioButton s1OrangeRadioButton;
    @FXML
    private RadioButton s1GreenRadioButton;
    @FXML
    private RadioButton s2RedRadioButton;
    @FXML
    private RadioButton s2OrangeRadioButton;
    @FXML
    private RadioButton s2GreenRadioButton;
    @FXML
    private RadioButton s3RedRadioButton;
    @FXML
    private RadioButton s3OrangeRadioButton;
    @FXML
    private RadioButton s3GreenRadioButton;
    @FXML
    private RadioButton w1RedRadioButton;
    @FXML
    private RadioButton w1OrangeRadioButton;
    @FXML
    private RadioButton w1GreenRadioButton;
    @FXML
    private RadioButton w2RedRadioButton;
    @FXML
    private RadioButton w2OrangeRadioButton;
    @FXML
    private RadioButton w2GreenRadioButton;
    @FXML
    private RadioButton w3RedRadioButton;
    @FXML
    private RadioButton w3OrangeRadioButton;
    @FXML
    private RadioButton w3GreenRadioButton;

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

        n1RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(1).getLane(1).getTrafficLight().setState(RED));
        n1OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(1).getLane(1).getTrafficLight().setState(ORANGE));
        n1GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(1).getLane(1).getTrafficLight().setState(GREEN));
        n2RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(1).getLane(2).getTrafficLight().setState(RED));
        n2OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(1).getLane(2).getTrafficLight().setState(ORANGE));
        n2GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(1).getLane(2).getTrafficLight().setState(GREEN));
        n3RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(1).getLane(3).getTrafficLight().setState(RED));
        n3OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(1).getLane(3).getTrafficLight().setState(ORANGE));
        n3GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(1).getLane(3).getTrafficLight().setState(GREEN));
        e1RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(2).getLane(1).getTrafficLight().setState(RED));
        e1OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(2).getLane(1).getTrafficLight().setState(ORANGE));
        e1GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(2).getLane(1).getTrafficLight().setState(GREEN));
        e2RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(2).getLane(2).getTrafficLight().setState(RED));
        e2OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(2).getLane(2).getTrafficLight().setState(ORANGE));
        e2GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(2).getLane(2).getTrafficLight().setState(GREEN));
        e3RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(2).getLane(3).getTrafficLight().setState(RED));
        e3OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(2).getLane(3).getTrafficLight().setState(ORANGE));
        e3GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(2).getLane(3).getTrafficLight().setState(GREEN));
        s1RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(3).getLane(1).getTrafficLight().setState(RED));
        s1OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(3).getLane(1).getTrafficLight().setState(ORANGE));
        s1GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(3).getLane(1).getTrafficLight().setState(GREEN));
        s2RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(3).getLane(2).getTrafficLight().setState(RED));
        s2OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(3).getLane(2).getTrafficLight().setState(ORANGE));
        s2GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(3).getLane(2).getTrafficLight().setState(GREEN));
        s3RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(3).getLane(3).getTrafficLight().setState(RED));
        s3OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(3).getLane(3).getTrafficLight().setState(ORANGE));
        s3GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(3).getLane(3).getTrafficLight().setState(GREEN));
        w1RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(4).getLane(1).getTrafficLight().setState(RED));
        w1OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(4).getLane(1).getTrafficLight().setState(ORANGE));
        w1GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(4).getLane(1).getTrafficLight().setState(GREEN));
        w2RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(4).getLane(2).getTrafficLight().setState(RED));
        w2OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(4).getLane(2).getTrafficLight().setState(ORANGE));
        w2GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(4).getLane(2).getTrafficLight().setState(GREEN));
        w3RedRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(4).getLane(3).getTrafficLight().setState(RED));
        w3OrangeRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(4).getLane(3).getTrafficLight().setState(ORANGE));
        w3GreenRadioButton.setOnAction(event -> roadMap.getJunction().getRoad(4).getLane(3).getTrafficLight().setState(GREEN));
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