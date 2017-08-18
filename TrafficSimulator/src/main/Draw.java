package main;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import model.RoadMap;
import model.Vehicle;

import java.util.Iterator;

import static main.Global.canvasRadius;
import static main.Global.roadRadius;

public class Draw {

    public static void drawMap(RoadMap roadMap, AnchorPane canvas){
        Group g = new Group();

        Rectangle vRect = new Rectangle(canvasRadius - roadRadius, 0, roadRadius * 2, canvasRadius * 2);
        vRect.getStyleClass().add("roadSurface");
        g.getChildren().add(vRect);

        Rectangle hRect = new Rectangle(0, canvasRadius - roadRadius, canvasRadius * 2, roadRadius * 2);
        hRect.getStyleClass().add("roadSurface");
        g.getChildren().add(hRect);

        Polyline p = new Polyline();
        p.getPoints().addAll(canvasRadius + roadRadius, 0.0,
                canvasRadius + roadRadius, canvasRadius - roadRadius,
                2 * canvasRadius, canvasRadius - roadRadius,
                2 * canvasRadius, canvasRadius + roadRadius,
                canvasRadius + roadRadius, canvasRadius + roadRadius,
                canvasRadius + roadRadius, 2 * canvasRadius,
                canvasRadius - roadRadius, 2 * canvasRadius,
                canvasRadius - roadRadius, canvasRadius + roadRadius,
                0.0, canvasRadius + roadRadius,
                0.0, canvasRadius - roadRadius,
                canvasRadius - roadRadius, canvasRadius - roadRadius,
                canvasRadius - roadRadius, 0.0);
        g.getChildren().add(p);

        Line lVerticle = new Line(canvasRadius, 0, canvasRadius, 2 * canvasRadius);
        lVerticle.getStyleClass().add("midLine");
        g.getChildren().add(lVerticle);

        Line lHorizontal = new Line(0, canvasRadius, 2 * canvasRadius, canvasRadius);
        lHorizontal.getStyleClass().add("midLine");
        g.getChildren().add(lHorizontal);

        Line markLeft = new Line(canvasRadius - roadRadius, canvasRadius - roadRadius, canvasRadius - roadRadius, canvasRadius + roadRadius);
        markLeft.getStyleClass().add("markLine");
        g.getChildren().add(markLeft);

        Line markRight = new Line(canvasRadius + roadRadius, canvasRadius - roadRadius, canvasRadius + roadRadius, canvasRadius + roadRadius);
        markRight.getStyleClass().add("markLine");
        g.getChildren().add(markRight);

        Line markTop = new Line(canvasRadius - roadRadius, canvasRadius - roadRadius, canvasRadius + roadRadius, canvasRadius - roadRadius);
        markTop.getStyleClass().add("markLine");
        g.getChildren().add(markTop);

        Line markBottom = new Line(canvasRadius - roadRadius, canvasRadius + roadRadius, canvasRadius + roadRadius, canvasRadius + roadRadius);
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
            if(vehicle.getLocation() >= 0) {
                Rectangle rect = new Rectangle();
                rect.setHeight(vehicle.getLength());
                rect.setWidth(vehicle.getWidth());
                rect.setX(canvasRadius - roadRadius / 2);
                rect.setY(vehicle.getLocation());

                canvas.getChildren().add(rect);
            }
        }

        vehicleIterator = roadMap.getJunction().geteRoad().getOutLane().getVehicles().iterator();
        while (vehicleIterator.hasNext()){
            Vehicle vehicle = vehicleIterator.next();
            Rectangle rect = new Rectangle();
            rect.setHeight(vehicle.getWidth());
            rect.setWidth(vehicle.getLength());
            rect.setX(2 * canvasRadius - vehicle.getLocation());
            rect.setY(canvasRadius);

            canvas.getChildren().add(rect);
        }
    }
}
