package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Lane;
import model.Road;
import model.RoadMap;
import model.Vehicle;

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

            nRoad.generateInVehicle();
            eRoad.generateInVehicle();
            sRoad.generateInVehicle();
            wRoad.generateInVehicle();

            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            if(nRoad.getInLane().getVehicles().size() < 10){
                                nRoad.generateInVehicle();
                            }

                            Iterator<Vehicle> vehicleIterator = nRoad.getInLane().getVehicles().iterator();
                            while (vehicleIterator.hasNext()){
                                Vehicle v = vehicleIterator.next();

                                adjustSpeed(v, nRoad.getInLane().getFrontVehicle(v));

                                if(v.getLocation() + v.getLength() >= Global.roadLength){
                                    vehicleIterator.remove();
                                    if(v.getDestination() == 1){
                                        nRoad.getOutLane().addVehicleToQueue(v);
                                    }else if(v.getDestination() == 2){
                                        eRoad.getOutLane().addVehicleToQueue(v);
                                    }else if(v.getDestination() == 3){
                                        sRoad.getOutLane().addVehicleToQueue(v);
                                    }else if(v.getDestination() == 4){
                                        wRoad.getOutLane().addVehicleToQueue(v);
                                    }
                                }
                            }

                            vehicleIterator = eRoad.getOutLane().getVehicles().iterator();
                            while (vehicleIterator.hasNext()){
                                Vehicle v = vehicleIterator.next();
                                v.setLocation(v.getLocation() - v.getDesiredVelocity());
                                if(v.getLocation() - v.getLength() <= 0){
                                    vehicleIterator.remove();
                                }
                            }

                        }

                        public void adjustSpeed(Vehicle vehicle, Vehicle frontVehicle){
                            double v = vehicle.getVelocity();
                            double v0 = vehicle.getDesiredVelocity();
                            double s0 = Global.minimumSpacing;
                            double a = vehicle.getMaxAcceleration();
                            double b = vehicle.getBreakingDecleration();
                            double delta = 4;
                            double aFree = a * (1 - Math.pow(v / v0, delta));

                            //////////
                            if(aFree == Double.POSITIVE_INFINITY){
                                aFree = a;
                            }else if(aFree == Double.NEGATIVE_INFINITY){
                                aFree = (-1) * a;
                            }

                            double currentAcceleration = aFree;

                            if(frontVehicle != null){
                                double deltaS = frontVehicle.getLocation() - vehicle.getLocation() - frontVehicle.getLength();     // delta S
                                double deltaV = vehicle.getVelocity() - frontVehicle.getVelocity();                            // delta V

                                double sKleene = s0 + (v * 1.0) + ((v * deltaV) / (2 * Math.sqrt(a * b)));

                                double aInteraction = (-1) * a * Math.pow(sKleene / deltaS, 2);

                                ///////
                                if(aInteraction == Double.POSITIVE_INFINITY){
                                    aInteraction = a;
                                }else if(aInteraction == Double.NEGATIVE_INFINITY){
                                    aInteraction = (-1) * a;
                                }

                                currentAcceleration = aFree + aInteraction;
                            }

                            vehicle.setVelocity(currentAcceleration);
                            vehicle.setLocation(vehicle.getLocation() + currentAcceleration);

                            System.out.println(vehicle.getLocation() + "             " + vehicle.getVelocity());
                        }
                    }, 0, 10);
        });
    }
}
