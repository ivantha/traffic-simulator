package main;

import com.ivantha.ts.api.Simulator;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Simulator simulator = new Simulator();
        simulator.startNewInstance(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}