package com.ivantha.ts.controller;

import com.ivantha.ts.common.Global;
import com.ivantha.ts.common.Session;
import com.ivantha.ts.model.RoadMap;
import com.ivantha.ts.model.Vehicle;
import com.ivantha.ts.util.Draw;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import static com.ivantha.ts.model.TrafficLight.TrafficLightState.*;
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

    private boolean isStarted = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Draw.drawMap(Session.getRoadMap(), backgroundCanvasAnchorPane);

        vehicleDensitySlider.valueProperty().bindBidirectional(Session.vehicleDensityProperty());
        averageGapSlider.valueProperty().bindBidirectional(Session.averageGapProperty());
        averageSpeedSlider.valueProperty().bindBidirectional(Session.averageSpeedProperty());

        reset();

        uiUpdater = new Timeline(new KeyFrame(Duration.millis(REFRESH_INTERVAL), event1 -> {
            Platform.runLater(() -> Draw.refreshMap(Session.getRoadMap(), canvasAnchorPane));
        }));
        uiUpdater.setCycleCount(Timeline.INDEFINITE);

        startStopButton.setOnAction(event -> {
            if (!isStarted) {
                stop();

                mainTimer = new Timer();
                mainTimerTask = new CustomerTimerTask();
                mainTimer.schedule(mainTimerTask, 0, REFRESH_INTERVAL);
                uiUpdater.play();

                isStarted = true;
                startStopButton.setText("Stop");
            } else {
                stop();

                isStarted = false;
                startStopButton.setText("Start");
            }
        });

        resetButton.setOnAction(event -> reset());

        northToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().northEnabledProperty());
        eastToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().eastEnabledProperty());
        southToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().southEnabledProperty());
        westToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().westEnabledProperty());

        n1RedRadioButton.setOnAction(event -> Session.getNorthLane1TrafficLight().setState(RED));
        n1OrangeRadioButton.setOnAction(event -> Session.getNorthLane1TrafficLight().setState(ORANGE));
        n1GreenRadioButton.setOnAction(event -> Session.getNorthLane1TrafficLight().setState(GREEN));

        n2RedRadioButton.setOnAction(event -> Session.getNorthLane2TrafficLight().setState(RED));
        n2OrangeRadioButton.setOnAction(event -> Session.getNorthLane2TrafficLight().setState(ORANGE));
        n2GreenRadioButton.setOnAction(event -> Session.getNorthLane2TrafficLight().setState(GREEN));

        n3RedRadioButton.setOnAction(event -> Session.getNorthLane3TrafficLight().setState(RED));
        n3OrangeRadioButton.setOnAction(event -> Session.getNorthLane3TrafficLight().setState(ORANGE));
        n3GreenRadioButton.setOnAction(event -> Session.getNorthLane3TrafficLight().setState(GREEN));

        e1RedRadioButton.setOnAction(event -> Session.getEastLane1TrafficLight().setState(RED));
        e1OrangeRadioButton.setOnAction(event -> Session.getEastLane1TrafficLight().setState(ORANGE));
        e1GreenRadioButton.setOnAction(event -> Session.getEastLane1TrafficLight().setState(GREEN));

        e2RedRadioButton.setOnAction(event -> Session.getEastLane2TrafficLight().setState(RED));
        e2OrangeRadioButton.setOnAction(event -> Session.getEastLane2TrafficLight().setState(ORANGE));
        e2GreenRadioButton.setOnAction(event -> Session.getEastLane2TrafficLight().setState(GREEN));

        e3RedRadioButton.setOnAction(event -> Session.getEastLane3TrafficLight().setState(RED));
        e3OrangeRadioButton.setOnAction(event -> Session.getEastLane3TrafficLight().setState(ORANGE));
        e3GreenRadioButton.setOnAction(event -> Session.getEastLane3TrafficLight().setState(GREEN));

        s1RedRadioButton.setOnAction(event -> Session.getSouthLane1TrafficLight().setState(RED));
        s1OrangeRadioButton.setOnAction(event -> Session.getSouthLane1TrafficLight().setState(ORANGE));
        s1GreenRadioButton.setOnAction(event -> Session.getSouthLane1TrafficLight().setState(GREEN));

        s2RedRadioButton.setOnAction(event -> Session.getSouthLane2TrafficLight().setState(RED));
        s2OrangeRadioButton.setOnAction(event -> Session.getSouthLane2TrafficLight().setState(ORANGE));
        s2GreenRadioButton.setOnAction(event -> Session.getSouthLane2TrafficLight().setState(GREEN));

        s3RedRadioButton.setOnAction(event -> Session.getSouthLane3TrafficLight().setState(RED));
        s3OrangeRadioButton.setOnAction(event -> Session.getSouthLane3TrafficLight().setState(ORANGE));
        s3GreenRadioButton.setOnAction(event -> Session.getSouthLane3TrafficLight().setState(GREEN));

        w1RedRadioButton.setOnAction(event -> Session.getWestLane1TrafficLight().setState(RED));
        w1OrangeRadioButton.setOnAction(event -> Session.getWestLane1TrafficLight().setState(ORANGE));
        w1GreenRadioButton.setOnAction(event -> Session.getWestLane1TrafficLight().setState(GREEN));

        w2RedRadioButton.setOnAction(event -> Session.getWestLane2TrafficLight().setState(RED));
        w2OrangeRadioButton.setOnAction(event -> Session.getWestLane2TrafficLight().setState(ORANGE));
        w2GreenRadioButton.setOnAction(event -> Session.getWestLane2TrafficLight().setState(GREEN));

        w3RedRadioButton.setOnAction(event -> Session.getWestLane3TrafficLight().setState(RED));
        w3OrangeRadioButton.setOnAction(event -> Session.getWestLane3TrafficLight().setState(ORANGE));
        w3GreenRadioButton.setOnAction(event -> Session.getWestLane3TrafficLight().setState(GREEN));
    }

    private void stop() {
        if (null != mainTimer) {
            mainTimer.cancel();
            mainTimer.purge();
        }
        uiUpdater.stop();
    }

    private void reset() {
        Session.setRoadMap(new RoadMap());

        northToggleButton.setSelected(false);
        eastToggleButton.setSelected(false);
        southToggleButton.setSelected(false);
        westToggleButton.setSelected(false);
    }

    class CustomerTimerTask extends TimerTask {
        @Override
        public void run() {
            Session.getRoadMap().populateRoadMap();

            moveInLaneVehicles(Session.getnRoad().getLane(1).getVehicleArrayList());
            moveInLaneVehicles(Session.getnRoad().getLane(2).getVehicleArrayList());
            moveInLaneVehicles(Session.getnRoad().getLane(3).getVehicleArrayList());
            moveInLaneVehicles(Session.geteRoad().getLane(1).getVehicleArrayList());
            moveInLaneVehicles(Session.geteRoad().getLane(2).getVehicleArrayList());
            moveInLaneVehicles(Session.geteRoad().getLane(3).getVehicleArrayList());
            moveInLaneVehicles(Session.getsRoad().getLane(1).getVehicleArrayList());
            moveInLaneVehicles(Session.getsRoad().getLane(2).getVehicleArrayList());
            moveInLaneVehicles(Session.getsRoad().getLane(3).getVehicleArrayList());
            moveInLaneVehicles(Session.getwRoad().getLane(1).getVehicleArrayList());
            moveInLaneVehicles(Session.getwRoad().getLane(2).getVehicleArrayList());
            moveInLaneVehicles(Session.getwRoad().getLane(3).getVehicleArrayList());

            moveOutLaneVehicles(Session.getnRoad().getLane(6).getVehicleArrayList());
            moveOutLaneVehicles(Session.getnRoad().getLane(5).getVehicleArrayList());
            moveOutLaneVehicles(Session.getnRoad().getLane(4).getVehicleArrayList());
            moveOutLaneVehicles(Session.geteRoad().getLane(6).getVehicleArrayList());
            moveOutLaneVehicles(Session.geteRoad().getLane(5).getVehicleArrayList());
            moveOutLaneVehicles(Session.geteRoad().getLane(4).getVehicleArrayList());
            moveOutLaneVehicles(Session.getsRoad().getLane(6).getVehicleArrayList());
            moveOutLaneVehicles(Session.getsRoad().getLane(5).getVehicleArrayList());
            moveOutLaneVehicles(Session.getsRoad().getLane(4).getVehicleArrayList());
            moveOutLaneVehicles(Session.getwRoad().getLane(6).getVehicleArrayList());
            moveOutLaneVehicles(Session.getwRoad().getLane(5).getVehicleArrayList());
            moveOutLaneVehicles(Session.getwRoad().getLane(4).getVehicleArrayList());

            moveIntersectionVehiclesSmallArc(Session.getnIntRoad().get(7).getVehicleArrayList());
            moveIntersectionVehiclesStraight(Session.getnIntRoad().get(8).getVehicleArrayList());
            moveIntersectionVehiclesLargeArc(Session.getnIntRoad().get(9).getVehicleArrayList());
            moveIntersectionVehiclesSmallArc(Session.geteIntRoad().get(7).getVehicleArrayList());
            moveIntersectionVehiclesStraight(Session.geteIntRoad().get(8).getVehicleArrayList());
            moveIntersectionVehiclesLargeArc(Session.geteIntRoad().get(9).getVehicleArrayList());
            moveIntersectionVehiclesSmallArc(Session.getsIntRoad().get(7).getVehicleArrayList());
            moveIntersectionVehiclesStraight(Session.getsIntRoad().get(8).getVehicleArrayList());
            moveIntersectionVehiclesLargeArc(Session.getsIntRoad().get(9).getVehicleArrayList());
            moveIntersectionVehiclesSmallArc(Session.getwIntRoad().get(7).getVehicleArrayList());
            moveIntersectionVehiclesStraight(Session.getwIntRoad().get(8).getVehicleArrayList());
            moveIntersectionVehiclesLargeArc(Session.getwIntRoad().get(9).getVehicleArrayList());
        }

        public void moveInLaneVehicles(ArrayList<Vehicle> vehicles) {
            synchronized (vehicles){
                Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                while (vehicleIterator.hasNext()) {
                    Vehicle v = vehicleIterator.next();

                    if (v.getTrajectory().getLocation() >= Global.ROAD_LENGTH) {
                        boolean trafficLightGreen = Session.getJunction().getRoad(v.getTrajectory().origin).getLane(v.getTrajectory().startLaneId).getTrafficLight().getState() == GREEN;
                        boolean spaceAvalable = Session.getIntersection().getIntLane(v.getTrajectory().origin, 6 + v.getTrajectory().destinationDiff).isSapceAvailable(v);
                        if (trafficLightGreen && spaceAvalable) {
                            vehicleIterator.remove();
                            Session.getIntersection().appendVehicleToIntLane(v, v.getTrajectory().origin, 6 + v.getTrajectory().destinationDiff);

                            for (int i = 0; i < vehicles.size(); i++) {
                                vehicles.get(i).getTrajectory().setLaneIndex(i);
                            }
                        }
                    } else {
                        v.move();
                    }
                }
            }
        }

        public void moveOutLaneVehicles(ArrayList<Vehicle> vehicles) {
            synchronized (vehicles){
                Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                while (vehicleIterator.hasNext()) {
                    Vehicle v = vehicleIterator.next();

                    if (v.getTrajectory().getLocation() >= Global.ROAD_LENGTH) {
                        vehicleIterator.remove();

                        for (int i = 0; i < vehicles.size(); i++) {
                            vehicles.get(i).getTrajectory().setLaneIndex(i);
                        }
                    } else {
                        v.move();
                    }
                }
            }
        }

        public void moveIntersectionVehiclesSmallArc(ArrayList<Vehicle> vehicles) {
            synchronized (vehicles){
                Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                while (vehicleIterator.hasNext()) {
                    Vehicle v = vehicleIterator.next();

                    double laneWidth = ROAD_RADIUS / 6;
                    double thetaRadSmall = v.getTrajectory().getLocation() / (laneWidth + v.getLength() / 2);
                    if (thetaRadSmall >= PI / 2) {
                        if (Session.getJunction().getRoad(v.getTrajectory().destination).getLane(3 + v.getTrajectory().destinationDiff).isSapceAvailable(v)) {
                            vehicleIterator.remove();
                            Session.getJunction().getRoad(v.getTrajectory().destination).appendVehicleToOutLane(v, 6);

                            for (int i = 0; i < vehicles.size(); i++) {
                                vehicles.get(i).getTrajectory().setLaneIndex(i);
                            }
                        }
                    } else {
                        v.move();
                    }
                }
            }
        }

        public void moveIntersectionVehiclesStraight(ArrayList<Vehicle> vehicles) {
            synchronized (vehicles){
                Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                while (vehicleIterator.hasNext()) {
                    Vehicle v = vehicleIterator.next();

                    if (v.getTrajectory().getLocation() >= ROAD_RADIUS * 2 + v.getLength()) {
                        if (Session.getJunction().getRoad(v.getTrajectory().destination).getLane(3 + v.getTrajectory().destinationDiff).isSapceAvailable(v)) {
                            vehicleIterator.remove();
                            Session.getJunction().getRoad(v.getTrajectory().destination).appendVehicleToOutLane(v, 5);

                            for (int i = 0; i < vehicles.size(); i++) {
                                vehicles.get(i).getTrajectory().setLaneIndex(i);
                            }
                        }
                    } else {
                        v.move();
                    }
                }
            }
        }

        public void moveIntersectionVehiclesLargeArc(ArrayList<Vehicle> vehicles) {
            synchronized (vehicles){
                Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                while (vehicleIterator.hasNext()) {
                    Vehicle v = vehicleIterator.next();

                    double laneWidth = ROAD_RADIUS / 6;
                    double thetaRadLarge = v.getTrajectory().getLocation() / (ROAD_RADIUS + laneWidth + v.getLength() / 2);
                    if (thetaRadLarge >= PI / 2) {
                        if (Session.getJunction().getRoad(v.getTrajectory().destination).getLane(3 + v.getTrajectory().destinationDiff).isSapceAvailable(v)) {
                            vehicleIterator.remove();
                            Session.getJunction().getRoad(v.getTrajectory().destination).appendVehicleToOutLane(v, 4);

                            for (int i = 0; i < vehicles.size(); i++) {
                                vehicles.get(i).getTrajectory().setLaneIndex(i);
                            }
                        }
                    } else {
                        v.move();
                    }
                }
            }
        }
    }
}
