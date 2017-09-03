package util;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import constant.Direction;
import constant.LaneType;
import javafx.util.Duration;
import model.RoadMap;
import model.Vehicle;

import java.util.Collection;
import java.util.Iterator;

import static main.Global.CANVAS_RADIUS;
import static main.Global.ROAD_LENGTH;
import static main.Global.ROAD_RADIUS;
import static constant.Direction.*;
import static constant.LaneType.IN_LANE;
import static constant.LaneType.OUT_LANE;

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

        drawMap(roadMap, canvas);

        drawVehicles(canvas, roadMap.getJunction().getRoad(1).getLane(1).getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getRoad(1).getLane(2).getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getRoad(1).getLane(3).getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getRoad(2).getLane(1).getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getRoad(2).getLane(2).getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getRoad(2).getLane(3).getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getRoad(3).getLane(1).getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getRoad(3).getLane(2).getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getRoad(3).getLane(3).getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getRoad(4).getLane(1).getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getRoad(4).getLane(2).getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getRoad(4).getLane(3).getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 1);

        drawVehicles(canvas, roadMap.getJunction().getRoad(1).getLane(4).getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getRoad(1).getLane(5).getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getRoad(1).getLane(6).getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getRoad(2).getLane(4).getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getRoad(2).getLane(5).getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getRoad(2).getLane(6).getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getRoad(3).getLane(4).getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getRoad(3).getLane(5).getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getRoad(3).getLane(6).getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getRoad(4).getLane(4).getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getRoad(4).getLane(5).getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getRoad(4).getLane(6).getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 5);
    }

    private static void drawVehicles(AnchorPane canvas, Collection<Vehicle> vehicles, Direction direction, LaneType laneType, double offset){
        Iterator<Vehicle> vehicleIterator = vehicles.iterator();
        while (vehicleIterator.hasNext()){
            Vehicle vehicle = vehicleIterator.next();

            Rectangle rect = new Rectangle();
            rect.setFill(vehicle.getColor());

            //Debug condition for vehicle overlapping
            if(vehicle.isOverlapping()){
                rect.setFill(Color.WHITE);
            }

            double adjustedOffset = offset - vehicle.getWidth() / 2;

            switch (direction){
                case NORTH:
                    rect.setHeight(vehicle.getLength());
                    rect.setWidth(vehicle.getWidth());

                    rect.setX(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setY(vehicle.getTrajectory().getLocation() - vehicle.getLength());
                    }else {
                        rect.setY(ROAD_LENGTH - vehicle.getTrajectory().getLocation());
                    }
                    break;
                case EAST:
                    rect.setHeight(vehicle.getWidth());
                    rect.setWidth(vehicle.getLength());

                    rect.setY(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setX((2 * CANVAS_RADIUS) - vehicle.getTrajectory().getLocation());
                    }else {
                        rect.setX(CANVAS_RADIUS + ROAD_RADIUS + vehicle.getTrajectory().getLocation() - vehicle.getLength());
                    }
                    break;
                case SOUTH:
                    rect.setHeight(vehicle.getLength());
                    rect.setWidth(vehicle.getWidth());

                    rect.setX(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setY((2 * CANVAS_RADIUS) - vehicle.getTrajectory().getLocation());
                    }else {
                        rect.setY(CANVAS_RADIUS + ROAD_RADIUS + vehicle.getTrajectory().getLocation() - vehicle.getLength());
                    }

                    break;
                case WEST:
                    rect.setHeight(vehicle.getWidth());
                    rect.setWidth(vehicle.getLength());

                    rect.setY(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setX(vehicle.getTrajectory().getLocation() - vehicle.getLength());
                    }else {
                        rect.setX(ROAD_LENGTH - vehicle.getTrajectory().getLocation());
                    }
                    break;
            }

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
        MoveTo moveToEnd = new MoveTo();

        double adjustedOffset1 = 0.0;
        double adjustedOffset2 = 0.0;
        double startX = 0.0;
        double startY = 0.0;
        double endX = 0.0;
        double endY = 0.0;

        if(originLane == 1){
            adjustedOffset1 = (ROAD_RADIUS / 6 * 5);
        }else if(originLane == 2){
            adjustedOffset1 = (ROAD_RADIUS / 6 * 3);
        }else if(originLane == 3){
            adjustedOffset1 = (ROAD_RADIUS / 6 * 1);
        }

        if(destinationLane == 4){
            adjustedOffset2 = ((-1) * ROAD_RADIUS / 6 * 1);
        }else if(destinationLane == 5){
            adjustedOffset2 = ((-1) * ROAD_RADIUS / 6 * 3);
        }else if(destinationLane == 6){
            adjustedOffset2 = ((-1) * ROAD_RADIUS / 6 * 5);
        }

        switch (originRoad){
            case 1:
                startX = CANVAS_RADIUS + adjustedOffset1;
                startY = ROAD_LENGTH;

                switch (destinationRoad){
                    case 2:
                        endX = CANVAS_RADIUS + ROAD_RADIUS + vehicle.getLength() / 2;
                        endY = CANVAS_RADIUS + adjustedOffset2;

                        rect.setHeight(vehicle.getWidth());
                        rect.setWidth(vehicle.getLength());

                        quadCurveTo.setControlX(startX);
                        quadCurveTo.setControlY(endY);

                        rotateTransition.setByAngle(-90);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
                break;
            case 2:
                startX = CANVAS_RADIUS + ROAD_RADIUS;
                startY = CANVAS_RADIUS + adjustedOffset1;

                switch (destinationRoad){
                    case 3:
                        endX = CANVAS_RADIUS - adjustedOffset2;
                        endY = CANVAS_RADIUS + ROAD_RADIUS + vehicle.getLength() / 2;

                        rect.setHeight(vehicle.getLength());
                        rect.setWidth(vehicle.getWidth());

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
                startX = CANVAS_RADIUS - adjustedOffset1;
                startY = (2 * CANVAS_RADIUS) - ROAD_LENGTH;
                switch (destinationRoad){
                    case 4:
                        endX = ROAD_LENGTH - vehicle.getLength();
                        endY = CANVAS_RADIUS - adjustedOffset2;

                        rect.setHeight(vehicle.getWidth());
                        rect.setWidth(vehicle.getLength());

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
                startX = ROAD_LENGTH - vehicle.getLength();
                startY = CANVAS_RADIUS - adjustedOffset1;
                switch (destinationRoad){
                    case 1:
                        endX = CANVAS_RADIUS + adjustedOffset2;
                        endY = ROAD_LENGTH - vehicle.getLength();

                        rect.setHeight(vehicle.getLength());
                        rect.setWidth(vehicle.getWidth());

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

        Path path = new Path();
        path.getElements().addAll(moveToStart, quadCurveTo, moveToEnd);

        final double CROSSING_TIME = 450;

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(CROSSING_TIME));
        pathTransition.setPath(path);
        pathTransition.setNode(rect);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        rotateTransition.setDuration(Duration.millis(CROSSING_TIME));
        rotateTransition.setNode(rect);

        ParallelTransition parallelTransition = new ParallelTransition(pathTransition, rotateTransition);
        parallelTransition.setOnFinished(afterAction);
        parallelTransition.play();
    }

}
