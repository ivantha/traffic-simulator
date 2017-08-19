package a;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;

import static main.Global.CANVAS_RADIUS;
import static main.Global.ROAD_RADIUS;

public class Draw {

    public static void drawMap(RoadMap roadMap, AnchorPane canvas){
        Group g = new Group();

        Rectangle vRect = new Rectangle(CANVAS_RADIUS - ROAD_RADIUS, 0, ROAD_RADIUS * 2, CANVAS_RADIUS * 2);
        vRect.getStyleClass().add("roadSurface");
        g.getChildren().add(vRect);

        Rectangle hRect = new Rectangle(0, CANVAS_RADIUS - ROAD_RADIUS, CANVAS_RADIUS * 2, ROAD_RADIUS * 2);
        hRect.getStyleClass().add("roadSurface");
        g.getChildren().add(hRect);

        Polyline p = new Polyline();
        p.getPoints().addAll(CANVAS_RADIUS + ROAD_RADIUS, 0.0,
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS - ROAD_RADIUS,
                2 * CANVAS_RADIUS, CANVAS_RADIUS - ROAD_RADIUS,
                2 * CANVAS_RADIUS, CANVAS_RADIUS + ROAD_RADIUS,
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS,
                CANVAS_RADIUS + ROAD_RADIUS, 2 * CANVAS_RADIUS,
                CANVAS_RADIUS - ROAD_RADIUS, 2 * CANVAS_RADIUS,
                CANVAS_RADIUS - ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS,
                0.0, CANVAS_RADIUS + ROAD_RADIUS,
                0.0, CANVAS_RADIUS - ROAD_RADIUS,
                CANVAS_RADIUS - ROAD_RADIUS, CANVAS_RADIUS - ROAD_RADIUS,
                CANVAS_RADIUS - ROAD_RADIUS, 0.0);
        g.getChildren().add(p);

        Line lVerticle = new Line(CANVAS_RADIUS, 0, CANVAS_RADIUS, 2 * CANVAS_RADIUS);
        lVerticle.getStyleClass().add("midLine");
        g.getChildren().add(lVerticle);

        Line lHorizontal = new Line(0, CANVAS_RADIUS, 2 * CANVAS_RADIUS, CANVAS_RADIUS);
        lHorizontal.getStyleClass().add("midLine");
        g.getChildren().add(lHorizontal);

        Line markLeft = new Line(CANVAS_RADIUS - ROAD_RADIUS, CANVAS_RADIUS - ROAD_RADIUS, CANVAS_RADIUS - ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS);
        markLeft.getStyleClass().add("markLine");
        g.getChildren().add(markLeft);

        Line markRight = new Line(CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS - ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS);
        markRight.getStyleClass().add("markLine");
        g.getChildren().add(markRight);

        Line markTop = new Line(CANVAS_RADIUS - ROAD_RADIUS, CANVAS_RADIUS - ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS - ROAD_RADIUS);
        markTop.getStyleClass().add("markLine");
        g.getChildren().add(markTop);

        Line markBottom = new Line(CANVAS_RADIUS - ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS);
        markBottom.getStyleClass().add("markLine");
        g.getChildren().add(markBottom);

        canvas.getChildren().add(g);
    }

    public static void refreshMap(RoadMap roadMap, AnchorPane canvas){
        canvas.getChildren().clear();

        drawMap(roadMap, canvas);

        Iterator<Vehicle> vehicleIterator = roadMap.getJunction().getnRoad().getInLane().getVehicles().iterator();
        while (vehicleIterator.hasNext()){
            Vehicle vehicle = vehicleIterator.next();
            if(vehicle.getTrajectory().getLocation() >= 0) {
                Rectangle rect = new Rectangle();
                rect.setHeight(vehicle.getLength());
                rect.setWidth(vehicle.getWidth());
                rect.setX(CANVAS_RADIUS - ROAD_RADIUS / 2);
                rect.setY(vehicle.getTrajectory().getLocation());

                canvas.getChildren().add(rect);
            }
        }

        vehicleIterator = roadMap.getJunction().geteRoad().getOutLane().getVehicles().iterator();
        while (vehicleIterator.hasNext()){
            Vehicle vehicle = vehicleIterator.next();
            Rectangle rect = new Rectangle();
            rect.setHeight(vehicle.getWidth());
            rect.setWidth(vehicle.getLength());
            rect.setX(2 * CANVAS_RADIUS - vehicle.getTrajectory().getLocation());
            rect.setY(CANVAS_RADIUS);

            canvas.getChildren().add(rect);
        }
    }
}
