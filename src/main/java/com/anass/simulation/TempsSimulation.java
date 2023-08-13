package com.anass.simulation;

import com.anass.models.EtatSimulation;
import com.anass.models.EtatSimulation.Etat;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class TempsSimulation extends ScheduledService<Void>{

    EtatSimulation etat;

    public TempsSimulation(EtatSimulation etat){
        this.etat = etat;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (etat.getEtat() == Etat.PAUSE) return null;
                Simulation.addTemps(0, 15);
                return null;
            }
            
        };
    }
    
}
