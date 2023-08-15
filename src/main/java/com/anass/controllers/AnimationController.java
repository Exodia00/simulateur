package com.anass.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.anass.models.SimulationModel;

import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AnimationController {

    private SimulationModel model;
    private Canvas canvas;

    private double reservoirWidth = 300; // Width of the reservoir drawing
    private double reservoirX = 50;      // X-coordinate of the reservoir drawing
    private double reservoirMaxY = 400;   // Maximum Y-coordinate of the reservoir drawing

    public AnimationController(SimulationModel model) {
        this.model = model;
        this.canvas = new Canvas(800, 500);
        initAnimation();
    }

    public Canvas getView() {
        return canvas;
    }

    private void initAnimation() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(gc);
            }
        }.start();
    }

    private void draw(GraphicsContext gc) {
        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw the barrage
        drawBarrage(gc);

        // Draw the reservoir with dynamic water level
        double reservoirHeight = calculateReservoirHeight();
        drawReservoir(gc, reservoirHeight);
    }

    private double calculateReservoirHeight() {
        double maxVolume = model.getReservoirModel().getMaxVolume();
        double currentVolume = model.getReservoirModel().getVolume();
        return (currentVolume / maxVolume) * reservoirMaxY;
    }

    private void drawBarrage(GraphicsContext gc) {
        // Implement your barrage drawing logic here
    }

    private void drawReservoir(GraphicsContext gc, double waterHeight) {
        gc.setFill(Color.BLUE);
        gc.fillRect(reservoirX, reservoirMaxY - waterHeight, reservoirWidth, waterHeight);
    }
}