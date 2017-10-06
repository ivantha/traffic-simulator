package model;

import javafx.scene.paint.Color;
import main.Global;
import util.Common;

import java.time.LocalTime;

import static java.lang.Double.isInfinite;
import static java.lang.Double.isNaN;
import static main.Global.*;

public class Vehicle {
    public final LocalTime birthTime;
    public final int length = (int) (15 + (Math.random() * 10));
    public final int width = 8;

    public final double desiredVelocity = Math.random() * 3.5 + (AVERAGE_SPEED.get() * 0.5);
    public final double maxAcceleration = 1;
    public final double breakingDeceleration = 1.2;
    public final double timeHeadway = 0.1;
    public final double minimumGap = 3.0;

    public final Trajectory trajectory;

    private Color color = Common.getRandomVehicleColor();

    private double velocity = 0;

    private boolean isDebug = false;
    private boolean isOverlapping = false;
    private boolean hasInfiniteSpeed = false;

    public Vehicle(int origin, int destination, int preDestination, int startLaneId) {
        birthTime = LocalTime.now();

        if(!VEHICLE_HASH_MAP.containsKey(this.toString())){
            VEHICLE_HASH_MAP.put(this.toString(), VEHICLE_HASH_MAP.size() + 1);
        }

        this.trajectory = new Trajectory(origin, destination, preDestination, startLaneId);
    }

    public double getVelocity() {
        return velocity;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public boolean isDebug() {
        return isDebug;
    }
    public void setDebug(boolean debug) {
        isDebug = debug;
    }
    public boolean isOverlapping() {
        return isOverlapping;
    }
    public void setOverlapping(boolean overlapping) {
        isOverlapping = overlapping;
    }
    public boolean isHasInfiniteSpeed() {
        return hasInfiniteSpeed;
    }
    public void setHasInfiniteSpeed(boolean hasInfiniteSpeed) {
        this.hasInfiniteSpeed = hasInfiniteSpeed;
    }

    public double getAcceleration() {
        double distanceToNextCar = Global.CANVAS_RADIUS;
        if (!trajectory.isAtFront()) {
            Vehicle frontVehicle = trajectory.getFrontVehicle();
            distanceToNextCar = frontVehicle.trajectory.getLocation() - trajectory.getLocation() - frontVehicle.length;
        }

        double a = maxAcceleration;
        double b = breakingDeceleration;

        double deltaSpeed = getDeltaSpeed();

        double speed = getVelocity();
        double maxSpeed = desiredVelocity;
        double freeRoadCoeff = Math.pow(speed / maxSpeed, 4);

        double distanceGap = minimumGap;
        double timeGap = speed * timeHeadway;
        double breakGap = (speed * deltaSpeed) / (2 * Math.sqrt(a * b));
        double safeDistance = distanceGap + timeGap + breakGap;

        double busyRoadCoeff = Math.pow(safeDistance / distanceToNextCar, 2);
        double safeIntersectionDistance = 1 + timeGap + (Math.pow(speed, 2) / (2 * b));
        double intersectionCoeff = Math.pow(safeIntersectionDistance / trajectory.getDistanceToStopLine(), 2);
        double coeff = 1 - freeRoadCoeff - busyRoadCoeff - intersectionCoeff;

        if(isDebug){
            System.out.println("________________________________________");
            System.out.println("vehicle                 :" + VEHICLE_HASH_MAP.get(this.toString()));
            System.out.println("laneIndex               :" + trajectory.getLaneIndex());
            System.out.println("speed                   :" + speed);
            System.out.println("freeRoadCoeff           :" + freeRoadCoeff);
            System.out.println("timeGap                 :" + timeGap);
            System.out.println("breakGap                :" + breakGap);
            System.out.println("safeDistance            :" + safeDistance);
            System.out.println("busyRoadCoeff           :" + busyRoadCoeff);
            System.out.println("safeIntersectionDistance:" + safeIntersectionDistance);
            System.out.println("intersectionCoeff       :" + intersectionCoeff);
            System.out.println("coeff                   :" + coeff);

            if(maxAcceleration * coeff > 1000 || maxAcceleration * coeff < -1000){
                System.out.println("Incorrect acceleration");
//                System.exit(0);
            }

            if(speed > 1000 || speed < -1000){
                System.out.println("Incorrect speed");
//                System.exit(0);
            }

            if(isInfinite(speed) || isInfinite(freeRoadCoeff) || isInfinite(timeGap) || isInfinite(breakGap) || isInfinite(safeDistance) || isInfinite(busyRoadCoeff)
                    || isInfinite(safeIntersectionDistance) || isInfinite(intersectionCoeff) || isInfinite(coeff)){
                System.out.println(">>>> Infinite value");
                hasInfiniteSpeed = true;

                System.out.println(speed);
                System.out.println(maxSpeed);
                System.out.println(speed / maxSpeed);
                System.out.println(Math.pow(speed/ maxSpeed, 4));
//                System.exit(0);
            }

            if(isNaN(speed) || isNaN(freeRoadCoeff) || isNaN(timeGap) || isNaN(breakGap) || isNaN(safeDistance) || isNaN(busyRoadCoeff)
                    || isNaN(safeIntersectionDistance) || isNaN(intersectionCoeff) || isNaN(coeff)){
                System.out.println(">>>> NaN value");
            }
        }

        return maxAcceleration * coeff;
    }

    public double getDeltaSpeed() {
        if (trajectory.isAtFront()) {
            return getVelocity();
        } else {
            return getVelocity() - trajectory.getFrontVehicle().getVelocity();
        }
    }

    public void move() {
        double delta = 0.5;
        double acceleration = getAcceleration();

        double temp_velocity = velocity;
        temp_velocity += acceleration * delta;
        double temp_step = (temp_velocity * delta) + (0.5 * acceleration * Math.pow(delta, 2));

        // I am not sure if we need this condition. >> Just a way to stop negative steps and over steps
        if (temp_step >= 0
                && (trajectory.getFrontVehicle() == null
                        || trajectory.getLocation() + AVERAGE_GAP.get() + temp_step < trajectory.getFrontVehicle().trajectory.getLocation())) {
            velocity += acceleration * delta;
            double step = (velocity * delta) + (0.5 * acceleration * Math.pow(delta, 2));

            trajectory.setLocation(trajectory.getLocation() + step);

            if (isDebug) {
                System.out.println("");
                System.out.println("velocity                :" + velocity);
                System.out.println("step                    :" + step);

                for (int i = 0; i < trajectory.getLocation() / 2; i++) {
                    System.out.print(".");
                }
                System.out.println(" : " + trajectory.getLocation());

                if (step < 0) {
                    System.out.println(">>>> step is less than 0");
                    System.out.println("v-delta             :" + velocity * delta);
                    System.out.println("1/2a(t^2)           :" + 0.5 * acceleration * Math.pow(delta, 2));
                }

                if (!trajectory.isAtFront() &&
                        step > trajectory.getFrontVehicle().trajectory.getLocation()
                                - trajectory.getLocation() - trajectory.getFrontVehicle().length) {
                    System.out.println(">>>> Overlapping cars");
                    isOverlapping = true;
//                    System.exit(0);
                }
            }
        }
    }

    public class Trajectory{
        public final int origin;
        public final int destination;
        public final int destinationDiff;
        public final int startLaneId;

        private double location;

        private Lane lane;
        private int laneIndex;

        public Trajectory(int origin, int destination, int destinationDiff, int startLaneId) {
            this.origin = origin;
            this.destination = destination;
            this.destinationDiff = destinationDiff;
            this.startLaneId = startLaneId;

            this.location = length;
        }

        public double getLocation() {
            return location;
        }
        public void setLocation(double location) {
            this.location = location;
        }
        public int getLaneIndex() {
            return laneIndex;
        }
        public void setLaneIndex(int laneIndex) {
            this.laneIndex = laneIndex;
        }
        public Lane getLane() {
            return lane;
        }
        public void setLane(Lane lane) {
            this.lane = lane;
        }

        public boolean isAtFront() {
            return laneIndex == 0;
        }

        public Vehicle getFrontVehicle() {
            if (isAtFront()) {
                return null;
            } else {
                return getLane().getVehicles().get(getLaneIndex() - 1);
            }
        }

        public double getDistanceToStopLine(){
            return Math.abs(lane.length - location);
        }
    }
}
