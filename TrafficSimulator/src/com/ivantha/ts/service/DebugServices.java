package com.ivantha.ts.service;

import com.ivantha.ts.model.Vehicle;

import static com.ivantha.ts.common.Global.VEHICLE_HASH_MAP;
import static java.lang.Double.isInfinite;
import static java.lang.Double.isNaN;

public class DebugServices {
    public static void showVehicleDebug(Vehicle vehicle, double speed, double freeRoadCoeff, double timeGap, double breakGap, double safeDistance, double busyRoadCoeff,
                                        double safeIntersectionDistance, double intersectionCoeff, double coeff, double maxSpeed) {
        System.out.println("________________________________________");
        System.out.println("vehicle                 :" + VEHICLE_HASH_MAP.get(vehicle.toString()));
        System.out.println("speed                   :" + speed);
        System.out.println("freeRoadCoeff           :" + freeRoadCoeff);
        System.out.println("timeGap                 :" + timeGap);
        System.out.println("breakGap                :" + breakGap);
        System.out.println("safeDistance            :" + safeDistance);
        System.out.println("busyRoadCoeff           :" + busyRoadCoeff);
        System.out.println("safeIntersectionDistance:" + safeIntersectionDistance);
        System.out.println("intersectionCoeff       :" + intersectionCoeff);
        System.out.println("coeff                   :" + coeff);

        if (vehicle.getMaxAcceleration() * coeff > 1000 || vehicle.getMaxAcceleration() * coeff < -1000) {
            System.out.println("Incorrect acceleration");
            System.exit(0);
        }

        if (speed > 1000 || speed < -1000) {
            System.out.println("Incorrect speed");
            System.exit(0);
        }

        if (isInfinite(speed) || isInfinite(freeRoadCoeff) || isInfinite(timeGap) || isInfinite(breakGap) || isInfinite(safeDistance) || isInfinite(busyRoadCoeff)
                || isInfinite(safeIntersectionDistance) || isInfinite(intersectionCoeff) || isInfinite(coeff)) {
            System.out.println(">>>> Infinite value");

            System.out.println(speed);
            System.out.println(maxSpeed);
            System.out.println(speed / maxSpeed);
            System.out.println(Math.pow(speed / maxSpeed, 4));
            System.exit(0);
        }
    }

    public static void showVehicleNaNDebud(double speed, double freeRoadCoeff, double timeGap, double breakGap, double safeDistance, double busyRoadCoeff,
                                           double safeIntersectionDistance, double intersectionCoeff, double coeff) {
        if (isNaN(speed) || isNaN(freeRoadCoeff) || isNaN(timeGap) || isNaN(breakGap) || isNaN(safeDistance) || isNaN(busyRoadCoeff)
                || isNaN(safeIntersectionDistance) || isNaN(intersectionCoeff) || isNaN(coeff)) {
            System.out.println(">>>> NaN value");
        }
    }

    public static void showVehicleStepDebug(Vehicle vehicle, double velocity, double step, double acceleration, double delta) {
        System.out.println("");
        System.out.println("velocity                :" + velocity);
        System.out.println("step                    :" + step);

        for (int i = 0; i < vehicle.getTrajectory().getLocation() / 2; i++) {
            System.out.print(".");
        }
        System.out.println(" : " + vehicle.getTrajectory().getLocation());

        if (step < 0) {
            System.out.println(">>>> step is less than 0");
            System.out.println("v-delta             :" + velocity * delta);
            System.out.println("1/2a(t^2)           :" + 0.5 * acceleration * Math.pow(delta, 2));
        }
    }

    public static void showVehicleOverlappingDebug(Vehicle vehicle, double step) {
        if (!vehicle.getTrajectory().isAtFront() &&
                step > vehicle.getTrajectory().getFrontVehicle().getTrajectory().getLocation()
                        - vehicle.getTrajectory().getLocation() - vehicle.getTrajectory().getFrontVehicle().getLength()) {
            System.out.println(">>>> Overlapping cars");
            System.exit(0);
        }
    }
}
