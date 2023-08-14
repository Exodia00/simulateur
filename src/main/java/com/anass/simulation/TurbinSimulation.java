package com.anass.simulation;

import com.anass.barrage.BarrageOperations;
import com.anass.models.EtatSimulation;
import com.anass.models.SimulationModel;
import com.anass.models.TurbinModel;
import com.anass.models.EtatSimulation.Etat;
import com.anass.observers.VolumeObserver;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class TurbinSimulation extends ScheduledService<Void>{

    private TurbinModel turbin;
    private EtatSimulation etat;
    private VolumeObserver observer;
    private int heure = 0;

    public TurbinSimulation(TurbinModel model, EtatSimulation etat){
        this.turbin = model;
        this.etat = etat;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (etat.getEtat() == Etat.PAUSE) return null;
                if (etat.getEtat() == Etat.FIN) cancel();
                int volume = -1 * BarrageOperations.calculerVolume(turbin, heure);
                observer.updateVolume(volume);
                return null;
            }
        };
    }

    public void setVolumeObserver(VolumeObserver observer){
        this.observer = observer;
    }

    public void setHeure(int h){
        this.heure = h;
    }

}
