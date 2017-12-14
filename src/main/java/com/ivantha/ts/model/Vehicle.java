package com.ivantha.ts.model;

import com.ivantha.ts.common.Global;
import com.ivantha.ts.common.Session;
import com.ivantha.ts.service.VehicleServices;
import javafx.scene.paint.Color;

import java.time.LocalTime;

public class Vehicle {
    private final LocalTime birthTime;
    private final int length = (int) (15 + (Math.random() * 10));
    private final int width = 8;

    private final double desiredVelocity = Math.random() * 3.5 + (Session.getAverageSpeed() * 0.5);
    private final double maxAcceleration = 1;
    private final double breakingDeceleration = 1.2;
    private final double timeHeadway = 0.1;
    private final double minimumGap = 3.0;

    private final Trajectory trajectory;

    private Color color = VehicleServices.getRandomVehicleColor();

    private double velocity = 0;

    public Vehicle(int origin, int destination, int preDestination, int startLaneId) {
        birthTime = LocalTime.now();

        if (!Session.VEHICLE_HASH_MAP.containsKey(this.toString())) {
            Session.VEHICLE_HASH_MAP.put(this.toString(), Session.VEHICLE_HASH_MAP.size() + 1);
        }

        this.trajectory = new Trajectory(origin, destination, preDestination, startLaneId);
    }

    public LocalTime getBirthTime() {
        return birthTime;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public double getDesiredVelocity() {
        return desiredVelocity;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    public double getBreakingDeceleration() {
        return breakingDeceleration;
    }

    public double getTimeHeadway() {
        return timeHeadway;
    }

    public double getMinimumGap() {
        return minimumGap;
    }

    public Trajectory getTrajectory() {
        return trajectory;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    // Calcualte acceleration
    public double getAcceleration() {
        double distanceToNextCar;
        if (trajectory.isAtFront()) {
            distanceToNextCar = Global.CANVAS_RADIUS - trajectory.getLocation();
        } else {
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

//        DebugServices.showVehicleDebug(this, speed, freeRoadCoeff, timeGap, breakGap, safeDistance, busyRoadCoeff, safeIntersectionDistance, intersectionCoeff, coeff, maxSpeed);
//        DebugServices.showVehicleNaNDebud(speed, freeRoadCoeff, timeGap, breakGap, safeDistance, busyRoadCoeff, safeIntersectionDistance, intersectionCoeff, coeff);

        return maxAcceleration * coeff;
    }

    // Speed difference
    public double getDeltaSpeed() {
        if (trajectory.isAtFront()) {
            return getVelocity();
        } else {
            return getVelocity() - trajectory.getFrontVehicle().getVelocity();
        }
    }

    // Move the vehicle in oen time unit
    public void move() {
        double delta = 0.5;
        double acceleration = getAcceleration();

        double temp_velocity = velocity;
        temp_velocity += acceleration * delta;
        double temp_step = (temp_velocity * delta) + (0.5 * acceleration * Math.pow(delta, 2));

        // I am not sure if we need this condition. >> Just a way to stop negative steps and over steps
        if (temp_step >= 0 && (trajectory.getFrontVehicle() == null
                || trajectory.getLocation() + Session.getAverageGap() + Math.abs(temp_step) < trajectory.getFrontVehicle().trajectory.getLocation() - trajectory.getFrontVehicle().length)) {
            velocity += acceleration * delta;
            double step = (velocity * delta) + (0.5 * acceleration * Math.pow(delta, 2));

            trajectory.setLocation(trajectory.getLocation() + step);

//            DebugServices.showVehicleStepDebug(this, velocity, step, acceleration, delta);
//            DebugServices.showVehicleOverlappingDebug(this, step);
        }
    }

    // Path of the vehicle
    public class Trajectory {
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
                return getLane().getVehicleArrayList().get(getLaneIndex() - 1);
            }
        }

        public double getDistanceToStopLine() {
            return Math.abs(lane.getLength() - location);
        }
    }
}
