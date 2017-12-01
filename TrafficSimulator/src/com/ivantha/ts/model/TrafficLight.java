package com.ivantha.ts.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TrafficLight {
    private State state = State.RED;

    private BooleanProperty red = new SimpleBooleanProperty(true);
    private BooleanProperty orange = new SimpleBooleanProperty(false);
    private BooleanProperty green = new SimpleBooleanProperty(false);

    public TrafficLight() {
        red.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                state = State.RED;
            }
        });

        orange.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                state = State.ORANGE;
            }
        });

        green.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                state = State.GREEN;
            }
        });
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        switch (state){
            case RED:
                red.setValue(true);
                orange.setValue(false);
                green.setValue(false);
                break;
            case ORANGE:
                red.setValue(false);
                orange.setValue(true);
                green.setValue(false);
                break;
            case GREEN:
                red.setValue(false);
                orange.setValue(false);
                green.setValue(true);
                break;
        }
    }

    public BooleanProperty redProperty() {
        return red;
    }

    public BooleanProperty orangeProperty() {
        return orange;
    }

    public BooleanProperty greenProperty() {
        return green;
    }

    public enum State {
        RED,
        ORANGE,
        GREEN
    }
}