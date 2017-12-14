package com.ivantha.ts.util;

import com.ivantha.ts.common.Global;
import com.ivantha.ts.common.Session;
import com.ivantha.ts.model.Lane;
import com.ivantha.ts.model.Vehicle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

import static com.ivantha.ts.common.Global.ROAD_RADIUS;
import static com.ivantha.ts.model.TrafficLight.State.GREEN;
import static java.lang.Math.PI;

public class CustomTimerTask extends TimerTask{
    // Default run task
    @Override
    public void run() {
        Session.getRoadMap().populateRoadMap();

        moveInLaneVehicles(Session.getnRoad().getLane(1));
        moveInLaneVehicles(Session.getnRoad().getLane(2));
        moveInLaneVehicles(Session.getnRoad().getLane(3));
        moveInLaneVehicles(Session.geteRoad().getLane(1));
        moveInLaneVehicles(Session.geteRoad().getLane(2));
        moveInLaneVehicles(Session.geteRoad().getLane(3));
        moveInLaneVehicles(Session.getsRoad().getLane(1));
        moveInLaneVehicles(Session.getsRoad().getLane(2));
        moveInLaneVehicles(Session.getsRoad().getLane(3));
        moveInLaneVehicles(Session.getwRoad().getLane(1));
        moveInLaneVehicles(Session.getwRoad().getLane(2));
        moveInLaneVehicles(Session.getwRoad().getLane(3));

        moveOutLaneVehicles(Session.getnRoad().getLane(6));
        moveOutLaneVehicles(Session.getnRoad().getLane(5));
        moveOutLaneVehicles(Session.getnRoad().getLane(4));
        moveOutLaneVehicles(Session.geteRoad().getLane(6));
        moveOutLaneVehicles(Session.geteRoad().getLane(5));
        moveOutLaneVehicles(Session.geteRoad().getLane(4));
        moveOutLaneVehicles(Session.getsRoad().getLane(6));
        moveOutLaneVehicles(Session.getsRoad().getLane(5));
        moveOutLaneVehicles(Session.getsRoad().getLane(4));
        moveOutLaneVehicles(Session.getwRoad().getLane(6));
        moveOutLaneVehicles(Session.getwRoad().getLane(5));
        moveOutLaneVehicles(Session.getwRoad().getLane(4));

        moveIntersectionVehiclesSmallArc(Session.getnIntRoad().get(7));
        moveIntersectionVehiclesStraight(Session.getnIntRoad().get(8));
        moveIntersectionVehiclesLargeArc(Session.getnIntRoad().get(9));
        moveIntersectionVehiclesSmallArc(Session.geteIntRoad().get(7));
        moveIntersectionVehiclesStraight(Session.geteIntRoad().get(8));
        moveIntersectionVehiclesLargeArc(Session.geteIntRoad().get(9));
        moveIntersectionVehiclesSmallArc(Session.getsIntRoad().get(7));
        moveIntersectionVehiclesStraight(Session.getsIntRoad().get(8));
        moveIntersectionVehiclesLargeArc(Session.getsIntRoad().get(9));
        moveIntersectionVehiclesSmallArc(Session.getwIntRoad().get(7));
        moveIntersectionVehiclesStraight(Session.getwIntRoad().get(8));
        moveIntersectionVehiclesLargeArc(Session.getwIntRoad().get(9));
    }

    // Move vehicles in lane
    public void moveInLaneVehicles(Lane lane) {
        lane.resetSensorArray();
        ArrayList<Vehicle> vehicles = lane.getVehicleArrayList();
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

                        // Reset lane positions
                        for (int i = 0; i < vehicles.size(); i++) {
                            vehicles.get(i).getTrajectory().setLaneIndex(i);
                        }
                    }
                } else {
                    v.move();

                    // Check sensor positions
                    if(v.getTrajectory().getLocation() % Global.DPS <= v.getLength()){
                        lane.getSensorArray()[9 - ((int) (v.getTrajectory().getLocation() / Global.DPS) % 10)] = true;
                    }
                }
            }
        }
    }

    // Move out lane vehicles
    public void moveOutLaneVehicles(Lane lane) {
        lane.resetSensorArray();
        ArrayList<Vehicle> vehicles = lane.getVehicleArrayList();
        synchronized (vehicles){
            Iterator<Vehicle> vehicleIterator = vehicles.iterator();

            while (vehicleIterator.hasNext()) {
                Vehicle v = vehicleIterator.next();

                if (v.getTrajectory().getLocation() >= Global.ROAD_LENGTH) {
                    vehicleIterator.remove();

                    // Reset lane positions
                    for (int i = 0; i < vehicles.size(); i++) {
                        vehicles.get(i).getTrajectory().setLaneIndex(i);
                    }
                } else {
                    v.move();

                    // Check sensor positions
                    if(v.getTrajectory().getLocation() % Global.DPS <= v.getLength()){
                        lane.getSensorArray()[(int) (v.getTrajectory().getLocation() / Global.DPS) % 10] = true;
                    }
                }
            }
        }
    }

    // Move intersection vehicles - Small curve
    public void moveIntersectionVehiclesSmallArc(Lane lane) {
        ArrayList<Vehicle> vehicles = lane.getVehicleArrayList();
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

                        // Reset lane positions
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

    // Move intersection vehicles straight
    public void moveIntersectionVehiclesStraight(Lane lane) {
        ArrayList<Vehicle> vehicles = lane.getVehicleArrayList();
        synchronized (vehicles){
            Iterator<Vehicle> vehicleIterator = vehicles.iterator();

            while (vehicleIterator.hasNext()) {
                Vehicle v = vehicleIterator.next();

                if (v.getTrajectory().getLocation() >= ROAD_RADIUS * 2 + v.getLength()) {
                    if (Session.getJunction().getRoad(v.getTrajectory().destination).getLane(3 + v.getTrajectory().destinationDiff).isSapceAvailable(v)) {
                        vehicleIterator.remove();
                        Session.getJunction().getRoad(v.getTrajectory().destination).appendVehicleToOutLane(v, 5);

                        // Reset lane positions
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

    // Move intersection vehicles - Large curve
    public void moveIntersectionVehiclesLargeArc(Lane lane) {
        ArrayList<Vehicle> vehicles = lane.getVehicleArrayList();
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

                        // Reset lane positions
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
