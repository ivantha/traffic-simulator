package com.ivantha.ts.ui.components;

import com.sun.tools.hat.internal.model.Root;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

public class TileToggle<T extends Node, S extends  Node> {
    private T component1;
    private S component2;

    private boolean component1On;
    private boolean component2On;

    public TileToggle(T component1, S component2) {
        this.component1 = component1;
        this.component2 = component2;

        component1On = false;
        component2On = false;

        component1.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if(component1On){
                component1On = false;
                component1.setStyle(null);
            }else{
                component1On = true;
                component1.setStyle("-fx-background-color: #42A5F5;");
                component2On = false;
                component2.setStyle(null);
            }
        });
        component2.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if(component2On){
                component2On = false;
                component2.setStyle(null);
            }else{
                component2On = true;
                component2.setStyle("-fx-background-color: #42A5F5;");
                component1On = false;
                component1.setStyle(null);
            }
        });
    }
}
