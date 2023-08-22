package com.anass.simulation;

import com.anass.barrage.BarrageOperations;
import com.anass.models.EtatSimulation;
import com.anass.models.SimulationModel;
import com.anass.models.TurbinModel;
import com.anass.models.EtatSimulation.Etat;
import com.anass.observers.VolumeObserver;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

/**
 * Cette classe gère la simulation du fonctionnement de la turbine dans la simulation globale.
 * Elle est responsable de la mise à jour du volume d'eau utilisé par la turbine.
 * Elle agit en tant que service planifié pour mettre à jour le volume utilisé par la turbine.
 * 
 * @author Anass
 */
public class TurbinSimulation extends ScheduledService<Void>{

    private TurbinModel turbin;
    private EtatSimulation etat;
    private VolumeObserver observer;
    private int heure = 0;

    /**
     * Constructeur de la classe TurbinSimulation.
     * 
     * @param model Le modèle de la turbine.
     * @param etat L'état de la simulation.
     */
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

    /**
     * Définit l'observateur du volume.
     * 
     * @param observer L'observateur du volume.
     */
    public void setVolumeObserver(VolumeObserver observer){
        this.observer = observer;
    }

    /**
     * Définit l'heure actuelle pour la simulation de la turbine.
     * 
     * @param h L'heure à définir.
     */
    public void setHeure(int h){
        this.heure = h;
    }

}
