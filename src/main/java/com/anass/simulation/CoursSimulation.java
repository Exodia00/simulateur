package com.anass.simulation;

import com.anass.barrage.BarrageOperations;
import com.anass.models.CoursModel;
import com.anass.models.EtatSimulation;
import com.anass.models.EtatSimulation.Etat;
import com.anass.observers.VolumeObserver;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

/**
 * Cette classe représente la simulation d'un cours d'eau dans le système.
 * Elle hérite de la classe ScheduledService<Void>, ce qui permet de planifier l'exécution
 * périodique de la simulation.
 * Elle met à jour le volume en cours d'eau en fonction de l'état de la simulation.
 * Elle utilise un observateur pour notifier les changements de volume.
 * 
 * @author Anass MEHDAOUI
 */
public class CoursSimulation extends ScheduledService<Void> {

    private CoursModel cours;
    private EtatSimulation etat;
    private VolumeObserver observer;

    /**
     * Constructeur de la classe CoursSimulation.
     * 
     * @param cours Le modèle du cours d'eau à simuler.
     * @param etat L'état de la simulation.
     */
    public CoursSimulation(CoursModel cours, EtatSimulation etat) {
        this.cours = cours;
        this.etat = etat;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                if (etat.getEtat() == Etat.PAUSE) return null;
                if (etat.getEtat() == Etat.FIN) cancel();
                int volume = BarrageOperations.calculerVolume(cours);
                observer.updateVolume(volume);
                return null;
            }

        };
    }

    /**
     * Définit l'observateur de volume pour cette simulation.
     * 
     * @param observer L'observateur de volume à définir.
     */
    public void setVolumeObserver(VolumeObserver observer) {
        this.observer = observer;
    }

}
