package com.ivantha.ts.model;

public class TrafficLight {
    private TrafficLightState state = TrafficLightState.GREEN;


    public TrafficLight() {
    }

    public TrafficLightState getState() {
        return state;
    }

    public void setState(TrafficLightState state) {
        this.state = state;
    }


    public enum TrafficLightState {
        RED,
        ORANGE,
        GREEN
    }
}