package model;

import main.Global;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Double.isInfinite;
import static java.lang.Double.isNaN;
import static main.Global.*;

public class Vehicle {
    private int length = (int) (15 + (Math.random() * 10));
    private int width = 8;
    private Color color;

    private double velocity = 0;
    private double desiredVelocity = Math.random() * 3.5 + (AVERAGE_SPEED.get() * 0.5);
    private double maxAcceleration = 0.4;
    private double breakingDeceleration = 1.2;

    private double timeHeadway = 0.1;
    private double minimumSpacing = 3.0;

    private Trajectory trajectory;

    private boolean isDebug = false;
    private boolean isOverlapping = false;
    private boolean hasInfiniteSpeed = false;

    public Vehicle(int origin, int destination) {
        if(!VEHICLE_HASH_MAP.containsKey(this.toString())){
            VEHICLE_HASH_MAP.put(this.toString(), VEHICLE_HASH_MAP.size() + 1);
        }

        this.trajectory = new Trajectory(origin, destination, length);
        this.color = getRandomColor();
    }

    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public double getTimeHeadway() {
        return timeHeadway;
    }
    public void setTimeHeadway(double timeHeadway) {
        this.timeHeadway = timeHeadway;
    }
    public Trajectory getTrajectory() {
        return trajectory;
    }
    public void setTrajectory(Trajectory trajectory) {
        this.trajectory = trajectory;
    }
    public double getDesiredVelocity() {
        return desiredVelocity;
    }
    public void setDesiredVelocity(double desiredVelocity) {
        this.desiredVelocity = desiredVelocity;
    }
    public double getMinimumSpacing() {
        return minimumSpacing;
    }
    public void setMinimumSpacing(double minimumSpacing) {
        this.minimumSpacing = minimumSpacing;
    }
    public double getBreakingDeceleration() {
        return breakingDeceleration;
    }
    public void setBreakingDeceleration(double breakingDeceleration) {
        this.breakingDeceleration = breakingDeceleration;
    }
    public double getVelocity() {
        return velocity;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
    public double getMaxAcceleration() {
        return maxAcceleration;
    }
    public void setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
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

    private Color getRandomColor(){
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.web("#1976D2"));
        colors.add(Color.web("#C2185B"));
        colors.add(Color.web("#00796B"));
        colors.add(Color.web("#F57C00"));
        colors.add(Color.web("#AFB42B"));
        colors.add(Color.web("#E64A19"));
        colors.add(Color.web("#03A9F4"));
        colors.add(Color.web("#d32f2f"));
        colors.add(Color.web("#512DA8"));
        colors.add(Color.web("#FBC02D"));
        colors.add(Color.web("#5D4037"));
        colors.add(Color.web("#388E3C"));
        colors.add(Color.web("#7986CB"));
        colors.add(Color.web("#4DB6AC"));
        colors.add(Color.web("#2196F3"));
        colors.add(Color.web("#00695C"));
        colors.add(Color.web("#8E24AA"));

        Random randomizer = new Random();
        return colors.get(randomizer.nextInt(colors.size()));
    }

    public double getAcceleration() {
        double distanceToNextCar = Global.CANVAS_RADIUS;
        if (!getTrajectory().isAtFront()) {
            Vehicle frontVehicle = trajectory.getFrontVehicle();
            distanceToNextCar = frontVehicle.getTrajectory().getLocation() - getTrajectory().getLocation() - frontVehicle.getLength();
        }

        double a = getMaxAcceleration();
        double b = getBreakingDeceleration();

        double deltaSpeed = getDeltaSpeed();

        double speed = getVelocity();
        double maxSpeed = getDesiredVelocity();
        double freeRoadCoeff = Math.pow(speed / maxSpeed, 4);

        double distanceGap = minimumSpacing;
        double timeGap = speed * timeHeadway;
        double breakGap = (speed * deltaSpeed) / (2 * Math.sqrt(a * b));
        double safeDistance = distanceGap + timeGap + breakGap;

        double busyRoadCoeff = Math.pow(safeDistance / distanceToNextCar, 2);
        double safeIntersectionDistance = 1 + timeGap + (Math.pow(speed, 2) / (2 * b));
        double intersectionCoeff = Math.pow(safeIntersectionDistance / getTrajectory().getDistanceToStopLine(), 2);
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
        if (getTrajectory().isAtFront()) {
            return getVelocity();
        } else {
            return getVelocity() - getTrajectory().getFrontVehicle().getVelocity();
        }
    }

    public void move() {
        double delta = 0.5;
        double acceleration = getAcceleration();

        double temp_velocity = velocity;
        temp_velocity += acceleration * delta;
        double temp_step = (temp_velocity * delta) + (0.5 * acceleration * Math.pow(delta, 2));

        // I am not sure if we need this condition. >> Just a way to stop negative steps and over steps
        if(temp_step >= 0
                && (getTrajectory().getFrontVehicle() == null
                    || getTrajectory().getLocation() + AVERAGE_SPACING.get() + temp_step < getTrajectory().getFrontVehicle().getTrajectory().getLocation())) {
            velocity += acceleration * delta;
            double step = (velocity * delta) + (0.5 * acceleration * Math.pow(delta, 2));

            getTrajectory().setLocation(getTrajectory().getLocation() + step);

            if(isDebug){
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
                        step > getTrajectory().getFrontVehicle().getTrajectory().getLocation()
                                - getTrajectory().getLocation() - getTrajectory().getFrontVehicle().getLength()) {
                    System.out.println(">>>> Overlapping cars");
                    isOverlapping = true;
//                    System.exit(0);
                }
            }
        }
    }
}