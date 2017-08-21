package model;

import javafx.scene.paint.Color;
import main.Global;

import java.util.ArrayList;
import java.util.Random;

import static main.Global.VEHICLE_HASH_MAP;

public class Vehicle {
    private int length = (int) (15 + (Math.random() * 15));
    private int width = 10;
    private Color color;

    private double velocity = 0;
    private double desiredVelocity = Math.random();
    private double maxAcceleration = 0.05;
    private double breakingDeceleration = 0.15;

    private double timeHeadway = 0.1;
    private double minimumSpacing = 3.0;

    private Trajectory trajectory;

    private boolean isDebug = true;

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

    private Color getRandomColor(){
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.web("#1976D2"));
        colors.add(Color.web("#C2185B"));
        colors.add(Color.web("#00796B"));
        colors.add(Color.web("#F57C00"));
        colors.add(Color.web("#AFB42B"));

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
        double delta = 1;
        double acceleration = getAcceleration();

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
//                System.exit(0);
            }

            if (!trajectory.isAtFront() &&
                    step > getTrajectory().getFrontVehicle().getTrajectory().getLocation()
                            - getTrajectory().getLocation() - getTrajectory().getFrontVehicle().getLength()) {
                System.out.println(">>>> Overlapping cars");
//                System.exit(0);
            }
        }
    }
}