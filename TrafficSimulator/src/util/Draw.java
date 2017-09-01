package util;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import model.Direction;
import model.LaneType;
import model.RoadMap;
import model.Vehicle;

import java.util.Collection;
import java.util.Iterator;

import static main.Global.CANVAS_RADIUS;
import static main.Global.ROAD_LENGTH;
import static main.Global.ROAD_RADIUS;
import static model.Direction.*;
import static model.LaneType.IN_LANE;
import static model.LaneType.OUT_LANE;

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

        drawVehicles(canvas, roadMap.getJunction().getnRoad().getInLane1().getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getnRoad().getInLane2().getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getnRoad().getInLane3().getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().geteRoad().getInLane1().getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().geteRoad().getInLane2().getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().geteRoad().getInLane3().getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getsRoad().getInLane1().getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getsRoad().getInLane2().getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getsRoad().getInLane3().getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getwRoad().getInLane1().getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getwRoad().getInLane2().getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getwRoad().getInLane3().getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 1);
//
        drawVehicles(canvas, roadMap.getJunction().getnRoad().getOutLane1().getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getnRoad().getOutLane2().getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getnRoad().getOutLane2().getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().geteRoad().getOutLane1().getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().geteRoad().getOutLane2().getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().geteRoad().getOutLane2().getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getsRoad().getOutLane1().getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getsRoad().getOutLane2().getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getsRoad().getOutLane2().getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 1);
        drawVehicles(canvas, roadMap.getJunction().getwRoad().getOutLane1().getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 5);
        drawVehicles(canvas, roadMap.getJunction().getwRoad().getOutLane2().getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 3);
        drawVehicles(canvas, roadMap.getJunction().getwRoad().getOutLane2().getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 1);
    }

    private static void drawVehicles(AnchorPane canvas, Collection<Vehicle> vehicles, Direction direction, LaneType laneType, double offset){
        Iterator<Vehicle> vehicleIterator = vehicles.iterator();
        while (vehicleIterator.hasNext()){
            Vehicle vehicle = vehicleIterator.next();

            Rectangle rect = new Rectangle();
            rect.setFill(vehicle.getColor());


            ///////////////////////////////////////////
            if(vehicle.isOverlapping()){
                rect.setFill(Color.WHITE);
            }

            double adjustedOffset = offset - vehicle.getWidth() / 2;

            switch (direction){
                case NORTH:
                    if(laneType == IN_LANE){
                        rect.setHeight(vehicle.getLength());
                        rect.setWidth(vehicle.getWidth());
                        rect.setX(CANVAS_RADIUS + adjustedOffset);
                        rect.setY(vehicle.getTrajectory().getLocation());
                    }else {
                        rect.setHeight(vehicle.getLength());
                        rect.setWidth(vehicle.getWidth());
                        rect.setX(CANVAS_RADIUS + adjustedOffset);
                        rect.setY(ROAD_LENGTH - vehicle.getTrajectory().getLocation());
                    }
                    break;
                case EAST:
                    if(laneType == IN_LANE){
                        rect.setHeight(vehicle.getWidth());
                        rect.setWidth(vehicle.getLength());
                        rect.setX((2 * CANVAS_RADIUS) - vehicle.getTrajectory().getLocation());
                        rect.setY(CANVAS_RADIUS + adjustedOffset);
                    }else {
                        rect.setHeight(vehicle.getWidth());
                        rect.setWidth(vehicle.getLength());
                        rect.setX(CANVAS_RADIUS + ROAD_RADIUS + vehicle.getTrajectory().getLocation());
                        rect.setY(CANVAS_RADIUS + adjustedOffset);
                    }
                    break;
                case SOUTH:
                    if(laneType == IN_LANE){
                        rect.setHeight(vehicle.getLength());
                        rect.setWidth(vehicle.getWidth());
                        rect.setX(CANVAS_RADIUS + adjustedOffset);
                        rect.setY((2 * CANVAS_RADIUS) - vehicle.getTrajectory().getLocation());
                    }else {
                        rect.setHeight(vehicle.getLength());
                        rect.setWidth(vehicle.getWidth());
                        rect.setX(CANVAS_RADIUS + adjustedOffset);
                        rect.setY(CANVAS_RADIUS + ROAD_RADIUS + vehicle.getTrajectory().getLocation());
                    }

                    break;
                case WEST:
                    if(laneType == IN_LANE){
                        rect.setHeight(vehicle.getWidth());
                        rect.setWidth(vehicle.getLength());
                        rect.setX(vehicle.getTrajectory().getLocation());
                        rect.setY(CANVAS_RADIUS + adjustedOffset);
                    }else {
                        rect.setHeight(vehicle.getWidth());
                        rect.setWidth(vehicle.getLength());
                        rect.setX(ROAD_LENGTH - vehicle.getTrajectory().getLocation());
                        rect.setY(CANVAS_RADIUS + adjustedOffset);
                    }
                    break;
            }

            canvas.getChildren().add(rect);
        }
    }
}
