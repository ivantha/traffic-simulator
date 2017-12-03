package com.ivantha.ts.api;

import com.ivantha.ts.service.AppServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AppFunction {
    private Stage stage;

    public void start(Stage stage) {
        try {
            this.stage = stage;

            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
            Scene scene = new Scene(root);
            scene.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    System.exit(0);
                }
            });
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Traffic simulator");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        stage.close();
    }

    public static void startTraffic(){
        AppServices.startTraffic();
    }

    public static void stopTraffic(){
        AppServices.stopTraffic();
    }

    public static void resetTraffic(){
        AppServices.resetTraffic();
    }
}
