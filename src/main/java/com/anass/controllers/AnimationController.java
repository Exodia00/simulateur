package com.anass.controllers;


import com.anass.models.SimulationModel;
import com.anass.observers.SimulationUiObserver;
import com.anass.simulation.Animation;

import javafx.scene.canvas.Canvas;


/**
 * Cette classe gère l'animation du barrage 
 * 
 * @author Anass MEHDAOUI 
 */
public class AnimationController implements SimulationUiObserver{

    /**
     * Représente l'animation
     */
    private Animation animation;
    /**
     * Le modèle de la simulation
     */
    private SimulationModel model;

    /**
     * Constructeur à partir d'un modèle de la simulation
     */
    public AnimationController(SimulationModel model) {
        this.model = model;
        this.animation = new Animation(new Canvas(800, 500), model);
        // drawGrid(20);
    }

    /**
     * Renvoie un objet de type Canvas ou l'animation de déroule
     * 
     * @return canvas Canvas
     */
    public Canvas getView() {
        return animation.getCanvas();
    }

    /**
     * Notifie l'animation pour une mise à jour
     * 
     * @param temps Integer[]
     */
    @Override
    public void updateUi(Integer[] temps) {
        animation.updateUI(model, temps[0]);
    }

    
    @Override
    public void endSimulation() {
    }



   
}