package com.anass.simulation;

import com.anass.barrage.BarrageOperations;
import com.anass.models.CoursModel;
import com.anass.models.EtatSimulation;
import com.anass.models.SimulationModel;
import com.anass.models.EtatSimulation.Etat;
import com.anass.observers.VolumeObserver;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class CoursSimulation extends ScheduledService<Void>{

    private CoursModel cours;
    private EtatSimulation etat;
    private VolumeObserver observer;

    public CoursSimulation(CoursModel cours, EtatSimulation etat){
        this.cours = cours;
        this.etat = etat;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                if (etat.getEtat() == Etat.PAUSE) return null;
                int volume = BarrageOperations.calculerVolume(cours);
                observer.updateVolume(volume);
                return null;    
            }
            
        };
    }

    public void setVolumeObserver(VolumeObserver observer){
        this.observer = observer;
    }

}
