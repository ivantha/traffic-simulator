package com.ivantha.ts.util;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Common {
    public static Color getRandomVehicleColor() {
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.web("#1976D2"));
        colors.add(Color.web("#C2185B"));
        colors.add(Color.web("#00796B"));
        colors.add(Color.web("#F57C00"));
        colors.add(Color.web("#AFB42B"));
        colors.add(Color.web("#E64A19"));
        colors.add(Color.web("#03A9F4"));
        colors.add(Color.web("#d32f2f"));
        colors.add(Color.web("#512DA8"));
        colors.add(Color.web("#FBC02D"));
        colors.add(Color.web("#5D4037"));
        colors.add(Color.web("#388E3C"));
        colors.add(Color.web("#7986CB"));
        colors.add(Color.web("#4DB6AC"));
        colors.add(Color.web("#2196F3"));
        colors.add(Color.web("#00695C"));
        colors.add(Color.web("#8E24AA"));

        Random randomizer = new Random();
        return colors.get(randomizer.nextInt(colors.size()));
    }
}
