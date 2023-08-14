package com.anass.simulation;

import com.anass.barrage.TempsOperations;
import com.anass.models.EtatSimulation;
import com.anass.models.EtatSimulation.Etat;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class TempsSimulation extends ScheduledService<Void>{

    private EtatSimulation etat;
    private Integer[] temps = new Integer[] {0, 0}; 
    private Integer[] dureeCumul = new Integer[] {0, 0}; 
    private int dureeH;

    public TempsSimulation(EtatSimulation etat, int duree){
        this.etat = etat;
        this.dureeH = duree;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (etat.getEtat() == Etat.PAUSE) return null;
                if (etat.getEtat() == Etat.FIN) cancel();
                addTemps(0, 15);
                return null;
            }
            
        };
    }

    public void addTemps(int h, int m){
        this.temps = TempsOperations.add(temps, h, m);
        this.dureeCumul = TempsOperations.addCumul(dureeCumul, h, m);
    }

    public boolean estTerminee(){
        return dureeCumul[0] >= dureeH;
    }

    public Integer[] getTemps(){
        return this.temps;
    }
    
}
