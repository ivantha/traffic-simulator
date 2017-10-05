package util;

import constant.Direction;
import constant.LaneType;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.util.Duration;
import model.RoadMap;
import model.Vehicle;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Iterator;

import static constant.Direction.*;
import static constant.LaneType.IN_LANE;
import static constant.LaneType.OUT_LANE;
import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static main.Global.*;

public class Draw {

    public static void drawMap(RoadMap roadMap, AnchorPane canvas){
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

    public static void refreshMap(RoadMap roadMap, AnchorPane canvas){
        canvas.getChildren().clear();

        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(1).getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(2).getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(3).getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(1).getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(2).getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(3).getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(1).getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(2).getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(3).getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(1).getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(2).getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(3).getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 1);

        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(4).getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(5).getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(6).getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(4).getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(5).getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(6).getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(4).getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(5).getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(6).getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(4).getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(5).getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(6).getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 5);

        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getIntLane(1, 7).getVehicles(), 100, 100,
                200, 200, 0, 0);
    }

    private static void drawVehiclesOnLane(AnchorPane canvas, Collection<Vehicle> vehicles, Direction direction, LaneType laneType, double offset) {
        Iterator<Vehicle> vehicleIterator = vehicles.iterator();
        switch (direction) {
            case NORTH:
                while (vehicleIterator.hasNext()) {
                    Vehicle vehicle = vehicleIterator.next();

                    Rectangle rect = new Rectangle();
                    rect.setFill(vehicle.getColor());

                    double adjustedOffset = offset - vehicle.width / 2;

                    rect.setHeight(vehicle.length);
                    rect.setWidth(vehicle.width);

                    rect.setX(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setY(vehicle.trajectory.getLocation() - vehicle.length);
                    }else {
                        rect.setY(ROAD_LENGTH - vehicle.trajectory.getLocation());
                    }

                    canvas.getChildren().add(rect);
                }
                break;
            case EAST:
                while (vehicleIterator.hasNext()) {
                    Vehicle vehicle = vehicleIterator.next();

                    Rectangle rect = new Rectangle();
                    rect.setFill(vehicle.getColor());

                    double adjustedOffset = offset - vehicle.width / 2;

                    rect.setHeight(vehicle.width);
                    rect.setWidth(vehicle.length);

                    rect.setY(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setX((2 * CANVAS_RADIUS) - vehicle.trajectory.getLocation());
                    }else {
                        rect.setX(CANVAS_RADIUS + ROAD_RADIUS + vehicle.trajectory.getLocation() - vehicle.length);
                    }

                    canvas.getChildren().add(rect);
                }
                break;
            case SOUTH:
                while (vehicleIterator.hasNext()) {
                    Vehicle vehicle = vehicleIterator.next();

                    Rectangle rect = new Rectangle();
                    rect.setFill(vehicle.getColor());

                    double adjustedOffset = offset - vehicle.width / 2;

                    rect.setHeight(vehicle.length);
                    rect.setWidth(vehicle.width);

                    rect.setX(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setY((2 * CANVAS_RADIUS) - vehicle.trajectory.getLocation());
                    }else {
                        rect.setY(CANVAS_RADIUS + ROAD_RADIUS + vehicle.trajectory.getLocation() - vehicle.length);
                    }

                    canvas.getChildren().add(rect);
                }
                break;
            case WEST:
                while (vehicleIterator.hasNext()) {
                    Vehicle vehicle = vehicleIterator.next();

                    Rectangle rect = new Rectangle();
                    rect.setFill(vehicle.getColor());

                    double adjustedOffset = offset - vehicle.width / 2;

                    rect.setHeight(vehicle.width);
                    rect.setWidth(vehicle.length);

                    rect.setY(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setX(vehicle.trajectory.getLocation() - vehicle.length);
                    }else {
                        rect.setX(ROAD_LENGTH - vehicle.trajectory.getLocation());
                    }

                    canvas.getChildren().add(rect);
                }
                break;
        }
    }

    public static void drawVehiclesOnIntersection(AnchorPane canvas, Collection<Vehicle> vehicles, double startX, double startY, double endX, double endY, double offset, double angleOffset){
        Iterator<Vehicle> vehicleIterator = vehicles.iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();

            Rectangle rect = new Rectangle();
            rect.setFill(vehicle.getColor());

            double adjustedOffset = offset - vehicle.width / 2;

            rect.setHeight(vehicle.length);
            rect.setWidth(vehicle.width);

            double xDiff = endX - startX;
            double yDiff = endY - startY;

            double r = sqrt(pow(xDiff, 2) + pow(yDiff, 2));

            double arcLength = PI * r / 2;
            double theta = vehicle.trajectory.getLocation() / r;
            
            double y = r * Math.sin(theta);
            double x = r * Math.cos(theta);

            rect.setX(x);
            rect.setY(y);

            GraphicsContext gc;

            canvas.getChildren().add(rect);
        }
    }

    public static void animateIntersection(AnchorPane canvas, Rectangle rect, Vehicle vehicle, int originRoad, int originLane,
                                           int destinationRoad, int destinationLane, EventHandler<ActionEvent> afterAction){
        rect.setFill(vehicle.getColor());
        canvas.getChildren().add(rect);

        RotateTransition rotateTransition = new RotateTransition();

        MoveTo moveToStart = new MoveTo();
        QuadCurveTo quadCurveTo = new QuadCurveTo();
        LineTo lineTo = new LineTo();
        MoveTo moveToEnd = new MoveTo();

        double inLaneOffset = 0.0;
        double outLaneOffset = 0.0;
        double startX = 0.0;
        double startY = 0.0;
        double endX = 0.0;
        double endY = 0.0;

        if(originLane == 1){
            inLaneOffset = (ROAD_RADIUS / 6 * 5);
        }else if(originLane == 2){
            inLaneOffset = (ROAD_RADIUS / 6 * 3);
        }else if(originLane == 3){
            inLaneOffset = (ROAD_RADIUS / 6 * 1);
        }

        if(destinationLane == 4){
            outLaneOffset = ROAD_RADIUS / 6 * 1;
        }else if(destinationLane == 5){
            outLaneOffset = ROAD_RADIUS / 6 * 3;
        }else if(destinationLane == 6){
            outLaneOffset = ROAD_RADIUS / 6 * 5;
        }

        Path path = new Path();

        switch (originRoad){
            case 1:
                startX = CANVAS_RADIUS + inLaneOffset;
                startY = ROAD_LENGTH;

                switch (destinationRoad){
                    case 2:
                        endX = CANVAS_RADIUS + ROAD_RADIUS + vehicle.length / 2;
                        endY = CANVAS_RADIUS - outLaneOffset;

                        rect.setHeight(vehicle.width);
                        rect.setWidth(vehicle.length);

                        quadCurveTo.setControlX(startX);
                        quadCurveTo.setControlY(endY);

                        rotateTransition.setByAngle(-90);

                        path.getElements().addAll(moveToStart, quadCurveTo);
                        break;
                    case 3:
//                        endX = CANVAS_RADIUS + outLaneOffset;
//                        endY = CANVAS_RADIUS + ROAD_RADIUS;
//
//                        rect.setHeight(vehicle.width);
//                        rect.setWidth(vehicle.length);
//
//                        lineTo.setX(endX);
//                        lineTo.setY(endY);
//
//                        rotateTransition.setByAngle(0);
//
//                        path.getElements().addAll(moveToStart, lineTo);
                        break;
                    case 4:
                        endX = ROAD_LENGTH - vehicle.length / 2;
                        endY = CANVAS_RADIUS + outLaneOffset;

                        rect.setHeight(vehicle.width);
                        rect.setWidth(vehicle.length);

                        quadCurveTo.setControlX(startX);
                        quadCurveTo.setControlY(endY);

                        rotateTransition.setByAngle(90);

                        path.getElements().addAll(moveToStart, quadCurveTo);
                        break;
                }
                break;
            case 2:
                startX = CANVAS_RADIUS + ROAD_RADIUS;
                startY = CANVAS_RADIUS + inLaneOffset;

                switch (destinationRoad){
                    case 3:
                        endX = CANVAS_RADIUS - outLaneOffset;
                        endY = CANVAS_RADIUS + ROAD_RADIUS + vehicle.length / 2;

                        rect.setHeight(vehicle.length);
                        rect.setWidth(vehicle.width);

                        quadCurveTo.setControlX(endX);
                        quadCurveTo.setControlY(startY);

                        rotateTransition.setFromAngle(90);
                        rotateTransition.setToAngle(0);
                        break;
                    case 4:
                        break;
                    case 1:
                        break;
                }
                break;
            case 3:
                startX = CANVAS_RADIUS - inLaneOffset;
                startY = (2 * CANVAS_RADIUS) - ROAD_LENGTH;
                switch (destinationRoad){
                    case 4:
                        endX = ROAD_LENGTH - vehicle.length;
                        endY = CANVAS_RADIUS - outLaneOffset;

                        rect.setHeight(vehicle.width);
                        rect.setWidth(vehicle.length);

                        quadCurveTo.setControlX(startX);
                        quadCurveTo.setControlY(endY);

                        rotateTransition.setByAngle(-90);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                break;
            case 4:
                startX = ROAD_LENGTH - vehicle.length;
                startY = CANVAS_RADIUS - inLaneOffset;
                switch (destinationRoad){
                    case 1:
                        endX = CANVAS_RADIUS + outLaneOffset;
                        endY = ROAD_LENGTH - vehicle.length;

                        rect.setHeight(vehicle.length);
                        rect.setWidth(vehicle.width);

                        quadCurveTo.setControlX(endX);
                        quadCurveTo.setControlY(startY);

                        rotateTransition.setFromAngle(90);
                        rotateTransition.setToAngle(0);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                break;
        }

        moveToStart.setX(startX);
        moveToStart.setY(startY);
        quadCurveTo.setX(endX);
        quadCurveTo.setY(endY);
        moveToEnd.setX(endX);
        moveToEnd.setY(endY);

        LocalTime currentTime = LocalTime.now();
        double milliDiff = ChronoUnit.MILLIS.between(vehicle.birthTime, currentTime);
        double tempSpeed = ROAD_LENGTH / milliDiff;
        double crossingTime = ROAD_RADIUS * 2 / tempSpeed;

        if(vehicle.trajectory.destinationDiff == 1){
            crossingTime = ROAD_RADIUS / tempSpeed;
        }

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(crossingTime));
        pathTransition.setPath(path);
        pathTransition.setNode(rect);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        rotateTransition.setDuration(Duration.millis(crossingTime));
        rotateTransition.setNode(rect);

        ParallelTransition parallelTransition = new ParallelTransition(pathTransition, rotateTransition);
        parallelTransition.setOnFinished(afterAction);
        parallelTransition.play();

    }

}