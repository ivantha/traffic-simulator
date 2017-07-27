package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable{
    @FXML
    private AnchorPane canvasAnchorPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double d = 350.0;
        double r = 40.0;

        Group g = new Group();

        Polyline p = new Polyline();
        p.getPoints().addAll(new Double[]{
                d + r, 0.0,
                d + r, d - r,
                2 * d, d - r,
                2 * d, d + r,
                d + r, d + r,
                d + r, 2 * d,
                d - r, 2 * d,
                d - r, d + r,
                0.0, d + r,
                0.0, d - r,
                d - r, d - r,
                d - r, 0.0
        });
        p.getStyleClass().add("borderLine");
        g.getChildren().add(p);

        Line l1 = new Line(d, 0, d, 2 * d);
        l1.getStyleClass().add("midLine");
        g.getChildren().add(l1);

        Line l2 = new Line(0, d, 2 * d, d);
        l2.getStyleClass().add("midLine");
        g.getChildren().add(l2);

        canvasAnchorPane.getChildren().add(g);
    }
}
