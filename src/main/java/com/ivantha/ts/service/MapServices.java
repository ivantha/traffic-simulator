package com.ivantha.ts.service;

import com.ivantha.ts.model.RoadMap;
import com.ivantha.ts.model.Vehicle;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.Iterator;

import static com.ivantha.ts.common.Global.*;
import static java.lang.Math.PI;

public class MapServices {

    public static void drawMap(RoadMap roadMap, AnchorPane canvas) {
        Group g = new Group();

        Rectangle vRect = new Rectangle(ROAD_LENGTH, 0, ROAD_RADIUS * 2, CANVAS_RADIUS * 2);
        vRect.getStyleClass().add("roadSurface");
        g.getChildren().add(vRect);

        Rectangle hRect = new Rectangle(0, ROAD_LENGTH, CANVAS_RADIUS * 2, ROAD_RADIUS * 2);
        hRect.getStyleClass().add("roadSurface");
        g.getChildren().add(hRect);

        Polyline p = new Polyline();
        p.getPoints().addAll(CANVAS_RADIUS + ROAD_RADIUS, 0.0,
                CANVAS_RADIUS + ROAD_RADIUS, ROAD_LENGTH,
                2 * CANVAS_RADIUS, ROAD_LENGTH,
                2 * CANVAS_RADIUS, CANVAS_RADIUS + ROAD_RADIUS,
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS,
                CANVAS_RADIUS + ROAD_RADIUS, 2 * CANVAS_RADIUS,
                ROAD_LENGTH, 2 * CANVAS_RADIUS,
                ROAD_LENGTH, CANVAS_RADIUS + ROAD_RADIUS,
                0.0, CANVAS_RADIUS + ROAD_RADIUS,
                0.0, ROAD_LENGTH,
                ROAD_LENGTH, ROAD_LENGTH,
                ROAD_LENGTH, 0.0);
        g.getChildren().add(p);

        Line lVerticle = new Line(CANVAS_RADIUS, 0, CANVAS_RADIUS, 2 * CANVAS_RADIUS);
        lVerticle.getStyleClass().add("midLine");
        g.getChildren().add(lVerticle);

        Line lHorizontal = new Line(0, CANVAS_RADIUS, 2 * CANVAS_RADIUS, CANVAS_RADIUS);
        lHorizontal.getStyleClass().add("midLine");
        g.getChildren().add(lHorizontal);

        Line markLeft = new Line(ROAD_LENGTH, ROAD_LENGTH,
                ROAD_LENGTH, CANVAS_RADIUS + ROAD_RADIUS);
        markLeft.getStyleClass().add("markLine");
        g.getChildren().add(markLeft);

        Line markRight = new Line(CANVAS_RADIUS + ROAD_RADIUS, ROAD_LENGTH,
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS);
        markRight.getStyleClass().add("markLine");
        g.getChildren().add(markRight);

        Line markTop = new Line(ROAD_LENGTH, ROAD_LENGTH,
                CANVAS_RADIUS + ROAD_RADIUS, ROAD_LENGTH);
        markTop.getStyleClass().add("markLine");
        g.getChildren().add(markTop);

        Line markBottom = new Line(ROAD_LENGTH, CANVAS_RADIUS + ROAD_RADIUS,
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS);
        markBottom.getStyleClass().add("markLine");
        g.getChildren().add(markBottom);

        Line lMidVerticle1 = new Line(CANVAS_RADIUS - ROAD_RADIUS / 3, 0, CANVAS_RADIUS - ROAD_RADIUS / 3, 2 * CANVAS_RADIUS);
        lMidVerticle1.getStyleClass().add("markLine");
        g.getChildren().add(lMidVerticle1);

        Line lMidVerticle2 = new Line(CANVAS_RADIUS - ROAD_RADIUS / 3 * 2, 0, CANVAS_RADIUS - ROAD_RADIUS / 3 * 2, 2 * CANVAS_RADIUS);
        lMidVerticle2.getStyleClass().add("markLine");
        g.getChildren().add(lMidVerticle2);

        Line rMidVerticle1 = new Line(CANVAS_RADIUS + ROAD_RADIUS / 3, 0, CANVAS_RADIUS + ROAD_RADIUS / 3, 2 * CANVAS_RADIUS);
        rMidVerticle1.getStyleClass().add("markLine");
        g.getChildren().add(rMidVerticle1);

        Line rMidVerticle2 = new Line(CANVAS_RADIUS + ROAD_RADIUS / 3 * 2, 0, CANVAS_RADIUS + ROAD_RADIUS / 3 * 2, 2 * CANVAS_RADIUS);
        rMidVerticle2.getStyleClass().add("markLine");
        g.getChildren().add(rMidVerticle2);

        Line topMidHorizontal1 = new Line(0, CANVAS_RADIUS - ROAD_RADIUS / 3, 2 * CANVAS_RADIUS, CANVAS_RADIUS - ROAD_RADIUS / 3);
        topMidHorizontal1.getStyleClass().add("markLine");
        g.getChildren().add(topMidHorizontal1);

        Line topMidHorizontal2 = new Line(0, CANVAS_RADIUS - ROAD_RADIUS / 3 * 2, 2 * CANVAS_RADIUS, CANVAS_RADIUS - ROAD_RADIUS / 3 * 2);
        topMidHorizontal2.getStyleClass().add("markLine");
        g.getChildren().add(topMidHorizontal2);

        Line downMidHorizontal1 = new Line(0, CANVAS_RADIUS + ROAD_RADIUS / 3, 2 * CANVAS_RADIUS, CANVAS_RADIUS + ROAD_RADIUS / 3);
        downMidHorizontal1.getStyleClass().add("markLine");
        g.getChildren().add(downMidHorizontal1);

        Line downMidHorizontal2 = new Line(0, CANVAS_RADIUS + ROAD_RADIUS / 3 * 2, 2 * CANVAS_RADIUS, CANVAS_RADIUS + ROAD_RADIUS / 3 * 2);
        downMidHorizontal2.getStyleClass().add("markLine");
        g.getChildren().add(downMidHorizontal2);

        canvas.getChildren().add(g);
    }

    public synchronized static void refreshMap(RoadMap roadMap, AnchorPane canvas) {
        canvas.getChildren().clear();

        Iterator<Vehicle> vehicleIterator;

        // North - In lane 1
        vehicleIterator = roadMap.getJunction().getRoad(1).getLane(1).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 5) - vehicle.getWidth() / 2),
                    vehicle.getTrajectory().getLocation() - vehicle.getLength());
        }

        // North - In lane 2
        vehicleIterator = roadMap.getJunction().getRoad(1).getLane(2).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 3) - vehicle.getWidth() / 2),
                    vehicle.getTrajectory().getLocation() - vehicle.getLength());
        }

        // North - In lane 3
        vehicleIterator = roadMap.getJunction().getRoad(1).getLane(3).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 1) - vehicle.getWidth() / 2),
                    vehicle.getTrajectory().getLocation() - vehicle.getLength());
        }

        // North - Out lane 1
        vehicleIterator = roadMap.getJunction().getRoad(1).getLane(4).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 1) - vehicle.getWidth() / 2),
                    ROAD_LENGTH - vehicle.getTrajectory().getLocation());
        }

        // North - Out lane 2
        vehicleIterator = roadMap.getJunction().getRoad(1).getLane(5).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 3) - vehicle.getWidth() / 2),
                    ROAD_LENGTH - vehicle.getTrajectory().getLocation());
        }

        // North - Out lane 3
        vehicleIterator = roadMap.getJunction().getRoad(1).getLane(6).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 5) - vehicle.getWidth() / 2),
                    ROAD_LENGTH - vehicle.getTrajectory().getLocation());
        }

        // East - In lane 1
        vehicleIterator = roadMap.getJunction().getRoad(2).getLane(1).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    (2 * CANVAS_RADIUS) - vehicle.getTrajectory().getLocation(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 5) - vehicle.getWidth() / 2));
        }

        // East - In lane 2
        vehicleIterator = roadMap.getJunction().getRoad(2).getLane(2).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    (2 * CANVAS_RADIUS) - vehicle.getTrajectory().getLocation(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 3) - vehicle.getWidth() / 2));
        }

        // East - In lane 3
        vehicleIterator = roadMap.getJunction().getRoad(2).getLane(3).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    (2 * CANVAS_RADIUS) - vehicle.getTrajectory().getLocation(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 1) - vehicle.getWidth() / 2));
        }

        // East - Out lane 1
        vehicleIterator = roadMap.getJunction().getRoad(2).getLane(4).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    CANVAS_RADIUS + ROAD_RADIUS + vehicle.getTrajectory().getLocation() - vehicle.getLength(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 1) - vehicle.getWidth() / 2));
        }

        // East - Out lane 2
        vehicleIterator = roadMap.getJunction().getRoad(2).getLane(5).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    CANVAS_RADIUS + ROAD_RADIUS + vehicle.getTrajectory().getLocation() - vehicle.getLength(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 3) - vehicle.getWidth() / 2));
        }

        // East - Out lane 3
        vehicleIterator = roadMap.getJunction().getRoad(2).getLane(6).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    CANVAS_RADIUS + ROAD_RADIUS + vehicle.getTrajectory().getLocation() - vehicle.getLength(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 5) - vehicle.getWidth() / 2));
        }

        // South - In lane 1
        vehicleIterator = roadMap.getJunction().getRoad(3).getLane(1).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 5) - vehicle.getWidth() / 2),
                    (2 * CANVAS_RADIUS) - vehicle.getTrajectory().getLocation());
        }

        // South - In lane 2
        vehicleIterator = roadMap.getJunction().getRoad(3).getLane(2).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 3) - vehicle.getWidth() / 2),
                    (2 * CANVAS_RADIUS) - vehicle.getTrajectory().getLocation());
        }

        // South - In lane 3
        vehicleIterator = roadMap.getJunction().getRoad(3).getLane(3).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 1) - vehicle.getWidth() / 2),
                    (2 * CANVAS_RADIUS) - vehicle.getTrajectory().getLocation());
        }

        // South - Out lane 1
        vehicleIterator = roadMap.getJunction().getRoad(3).getLane(4).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 1) - vehicle.getWidth() / 2),
                    CANVAS_RADIUS + ROAD_RADIUS + vehicle.getTrajectory().getLocation() - vehicle.getLength());
        }

        // South - Out lane 2
        vehicleIterator = roadMap.getJunction().getRoad(3).getLane(5).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 3) - vehicle.getWidth() / 2),
                    CANVAS_RADIUS + ROAD_RADIUS + vehicle.getTrajectory().getLocation() - vehicle.getLength());
        }

        // South - Out lane 3
        vehicleIterator = roadMap.getJunction().getRoad(3).getLane(6).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 5) - vehicle.getWidth() / 2),
                    CANVAS_RADIUS + ROAD_RADIUS + vehicle.getTrajectory().getLocation() - vehicle.getLength());
        }

        // West - In lane 1
        vehicleIterator = roadMap.getJunction().getRoad(4).getLane(1).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    vehicle.getTrajectory().getLocation() - vehicle.getLength(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 5) - vehicle.getWidth() / 2));
        }

        // West - In lane 2
        vehicleIterator = roadMap.getJunction().getRoad(4).getLane(2).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    vehicle.getTrajectory().getLocation() - vehicle.getLength(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 3) - vehicle.getWidth() / 2));
        }

        // West - In lane 3
        vehicleIterator = roadMap.getJunction().getRoad(4).getLane(3).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    vehicle.getTrajectory().getLocation() - vehicle.getLength(),
                    CANVAS_RADIUS + (((-1) * ROAD_RADIUS / 6 * 1) - vehicle.getWidth() / 2));
        }

        // West - Out lane 1
        vehicleIterator = roadMap.getJunction().getRoad(4).getLane(4).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    ROAD_LENGTH - vehicle.getTrajectory().getLocation(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 1) - vehicle.getWidth() / 2));
        }

        // West - Out lane 2
        vehicleIterator = roadMap.getJunction().getRoad(4).getLane(5).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    ROAD_LENGTH - vehicle.getTrajectory().getLocation(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 3) - vehicle.getWidth() / 2));
        }

        // West - Out lane 3
        vehicleIterator = roadMap.getJunction().getRoad(4).getLane(6).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnLane(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    ROAD_LENGTH - vehicle.getTrajectory().getLocation(),
                    CANVAS_RADIUS + ((ROAD_RADIUS / 6 * 5) - vehicle.getWidth() / 2));
        }


        // North - Int lane 1
        vehicleIterator = roadMap.getJunction().getIntersection().getNorthIntRoad().get(7).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            double radius = ROAD_RADIUS / 6;
            double thetaRad = vehicle.getTrajectory().getLocation() / (radius + vehicle.getLength() / 2);
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    (CANVAS_RADIUS + (ROAD_RADIUS / 6 * 5)) - vehicle.getWidth() / 2, (CANVAS_RADIUS - ROAD_RADIUS) - vehicle.getLength(),
                    (-1) * thetaRad * 180 / PI,
                    CANVAS_RADIUS + ROAD_RADIUS + vehicle.getLength() / 2, ROAD_LENGTH - vehicle.getLength() / 2);
        }

        // North - Int lane 2
        vehicleIterator = roadMap.getJunction().getIntersection().getNorthIntRoad().get(8).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    (CANVAS_RADIUS + (ROAD_RADIUS / 6 * 3)) - vehicle.getWidth() / 2, ROAD_LENGTH + vehicle.getTrajectory().getLocation() - vehicle.getLength());
        }

        // North - Int lane 3
        vehicleIterator = roadMap.getJunction().getIntersection().getNorthIntRoad().get(9).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            double radius = ROAD_RADIUS + ROAD_RADIUS / 6;
            double thetaRad = vehicle.getTrajectory().getLocation() / (radius + vehicle.getLength() / 2);
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    (CANVAS_RADIUS + (ROAD_RADIUS / 6 * 1)) - vehicle.getWidth() / 2, (CANVAS_RADIUS - ROAD_RADIUS) - vehicle.getLength(),
                    thetaRad * 180 / PI,
                    ROAD_LENGTH - vehicle.getLength() / 2, ROAD_LENGTH - vehicle.getLength() / 2);
        }

        // East - Int lane 1
        vehicleIterator = roadMap.getJunction().getIntersection().getEastIntRoad().get(7).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            double radius = ROAD_RADIUS / 6;
            double thetaRad = vehicle.getTrajectory().getLocation() / (radius + vehicle.getLength() / 2);
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    (CANVAS_RADIUS + ROAD_RADIUS), (CANVAS_RADIUS + (ROAD_RADIUS / 6 * 5)) - vehicle.getWidth() / 2,
                    (-1) * thetaRad * 180 / PI,
                    CANVAS_RADIUS + ROAD_RADIUS + vehicle.getLength() / 2, CANVAS_RADIUS + ROAD_RADIUS + vehicle.getLength() / 2);
        }

        // East - Int lane 2
        vehicleIterator = roadMap.getJunction().getIntersection().getEastIntRoad().get(8).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    CANVAS_RADIUS + ROAD_RADIUS - vehicle.getTrajectory().getLocation(), (CANVAS_RADIUS + (ROAD_RADIUS / 6 * 3)) - vehicle.getWidth() / 2);
        }

        // East - Int lane 3
        vehicleIterator = roadMap.getJunction().getIntersection().getEastIntRoad().get(9).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            double radius = ROAD_RADIUS + ROAD_RADIUS / 6;
            double thetaRad = vehicle.getTrajectory().getLocation() / (radius + vehicle.getLength() / 2);
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    (CANVAS_RADIUS + ROAD_RADIUS), (CANVAS_RADIUS + (ROAD_RADIUS / 6 * 1)) - vehicle.getWidth() / 2,
                    thetaRad * 180 / PI,
                    CANVAS_RADIUS + ROAD_RADIUS + vehicle.getLength() / 2, ROAD_LENGTH - vehicle.getLength() / 2);
        }

        // South - Int lane 1
        vehicleIterator = roadMap.getJunction().getIntersection().getSouthIntRoad().get(7).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            double radius = ROAD_RADIUS / 6;
            double thetaRad = vehicle.getTrajectory().getLocation() / (radius + vehicle.getLength() / 2);
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    (CANVAS_RADIUS - (ROAD_RADIUS / 6 * 5)) - vehicle.getWidth() / 2, (CANVAS_RADIUS + ROAD_RADIUS),
                    (-1) * thetaRad * 180 / PI,
                    ROAD_LENGTH - vehicle.getLength() / 2, CANVAS_RADIUS + ROAD_RADIUS + vehicle.getLength() / 2);
        }

        // South - Int lane 2
        vehicleIterator = roadMap.getJunction().getIntersection().getSouthIntRoad().get(8).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    (CANVAS_RADIUS - (ROAD_RADIUS / 6 * 3)) - vehicle.getWidth() / 2, CANVAS_RADIUS + ROAD_RADIUS - vehicle.getTrajectory().getLocation());
        }

        // South - Int lane 3
        vehicleIterator = roadMap.getJunction().getIntersection().getSouthIntRoad().get(9).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            double radius = ROAD_RADIUS + ROAD_RADIUS / 6;
            double thetaRad = vehicle.getTrajectory().getLocation() / (radius + vehicle.getLength() / 2);
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getLength(), vehicle.getWidth(),
                    (CANVAS_RADIUS - (ROAD_RADIUS / 6 * 1)) - vehicle.getWidth() / 2, (CANVAS_RADIUS + ROAD_RADIUS),
                    thetaRad * 180 / PI,
                    CANVAS_RADIUS + ROAD_RADIUS + vehicle.getLength() / 2, CANVAS_RADIUS + ROAD_RADIUS + vehicle.getLength() / 2);
        }

        // West - Int lane 1
        vehicleIterator = roadMap.getJunction().getIntersection().getWestIntRoad().get(7).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            double radius = ROAD_RADIUS / 6;
            double thetaRad = vehicle.getTrajectory().getLocation() / (radius + vehicle.getLength() / 2);
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    (ROAD_LENGTH) - vehicle.getLength(), (CANVAS_RADIUS - (ROAD_RADIUS / 6 * 5)) - vehicle.getWidth() / 2,
                    (-1) * thetaRad * 180 / PI,
                    ROAD_LENGTH - vehicle.getLength() / 2, ROAD_LENGTH - vehicle.getLength() / 2);
        }

        // West - Int lane 2
        vehicleIterator = roadMap.getJunction().getIntersection().getWestIntRoad().get(8).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    ROAD_LENGTH + vehicle.getTrajectory().getLocation() - vehicle.getLength(), (CANVAS_RADIUS - (ROAD_RADIUS / 6 * 3)) - vehicle.getWidth() / 2);
        }

        // West - Int lane 3
        vehicleIterator = roadMap.getJunction().getIntersection().getWestIntRoad().get(9).getVehicleArrayList().iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            double radius = ROAD_RADIUS + ROAD_RADIUS / 6;
            double thetaRad = vehicle.getTrajectory().getLocation() / (radius + vehicle.getLength() / 2);
            drawVehicleOnIntersection(canvas, vehicle, vehicle.getWidth(), vehicle.getLength(),
                    (ROAD_LENGTH) - vehicle.getLength(), (CANVAS_RADIUS - (ROAD_RADIUS / 6 * 1)) - vehicle.getWidth() / 2,
                    thetaRad * 180 / PI,
                    ROAD_LENGTH - vehicle.getLength() / 2, CANVAS_RADIUS + ROAD_RADIUS + vehicle.getLength() / 2);
        }
    }

    private static void drawVehicleOnLane(AnchorPane canvas, Vehicle vehicle, double rectHeight, double rectWidth, double rectX, double rectY) {
        Rectangle rect = new Rectangle();
        rect.setFill(vehicle.getColor());

        rect.setHeight(rectHeight);
        rect.setWidth(rectWidth);

        rect.setX(rectX);
        rect.setY(rectY);

        canvas.getChildren().add(rect);
    }

    public static void drawVehicleOnIntersection(AnchorPane canvas, Vehicle vehicle, double rectHeight, double rectWidth, double startX, double startY, double rotateAngle, double pivotX, double pivotY) {
        Rectangle rect = new Rectangle();
        rect.setFill(vehicle.getColor());

        rect.setHeight(rectHeight);
        rect.setWidth(rectWidth);

        rect.setX(startX);
        rect.setY(startY);

        rect.getTransforms().add(new Rotate(rotateAngle, pivotX, pivotY));

        canvas.getChildren().add(rect);
    }

    public static void drawVehicleOnIntersection(AnchorPane canvas, Vehicle vehicle, double rectHeight, double rectWidth, double startX, double startY) {
        Rectangle rect = new Rectangle();
        rect.setFill(vehicle.getColor());

        rect.setHeight(rectHeight);
        rect.setWidth(rectWidth);

        rect.setX(startX);
        rect.setY(startY);

        canvas.getChildren().add(rect);
    }
}