package main;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import model.RoadMap;
import model.Vehicle;

public class Draw {
    private static final double canvasRadius = 350.0;
    private static final double roadRadius = 40.0;

    public static void drawMap(RoadMap roadMap, AnchorPane canvas){
        Group g = new Group();

        Rectangle vRect = new Rectangle(canvasRadius - roadRadius, 0, roadRadius * 2, canvasRadius * 2);
        vRect.getStyleClass().add("roadSurface");
        g.getChildren().add(vRect);

        Rectangle hRect = new Rectangle(0, canvasRadius - roadRadius, canvasRadius * 2, roadRadius * 2);
        hRect.getStyleClass().add("roadSurface");
        g.getChildren().add(hRect);

        Polyline p = new Polyline();
        p.getPoints().addAll(new Double[]{
                canvasRadius + roadRadius, 0.0,
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
                canvasRadius - roadRadius, 0.0
        });
        p.getStyleClass().add("borderLine");
        g.getChildren().add(p);

        Line l1 = new Line(canvasRadius, 0, canvasRadius, 2 * canvasRadius);
        l1.getStyleClass().add("midLine");
        g.getChildren().add(l1);

        Line l2 = new Line(0, canvasRadius, 2 * canvasRadius, canvasRadius);
        l2.getStyleClass().add("midLine");
        g.getChildren().add(l2);

        canvas.getChildren().add(g);
    }

    public static void refreshMap(RoadMap roadMap, AnchorPane canvas){
        canvas.getChildren().clear();

        drawMap(roadMap, canvas);

        for(Vehicle vehicle: roadMap.getJunction().getnRoad().getInLane().getVehicles()){
            Rectangle rect = new Rectangle();
            rect.setHeight(vehicle.getLength());
            rect.setWidth(vehicle.getWidth());
            rect.setX(canvasRadius - roadRadius / 2);
            rect.setY(vehicle.getLocation());

            canvas.getChildren().add(rect);
        }

//        for(Vehicle vehicle: roadMap.getJunction().geteRoad().getInLane().getVehicles()){
//
//        }
//
//        for(Vehicle vehicle: roadMap.getJunction().getsRoad().getInLane().getVehicles()){
//
//        }
//
//        for(Vehicle vehicle: roadMap.getJunction().getwRoad().getInLane().getVehicles()){
//
//        }
    }
}
