package com.anass.simulation;

import com.anass.barrage.TempsOperations;
import com.anass.models.EtatSimulation;
import com.anass.models.EtatSimulation.Etat;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

/**
 * Cette classe gère la simulation du temps pendant la simulation globale. Elle est responsable de la gestion
 * du temps écoulé et de la mise à jour des temps simulés.
 * Elle agit en tant que service planifié pour mettre à jour le temps.
 * 
 * @author Anass MEHDAOUI
 */
public class TempsSimulation extends ScheduledService<Void>{

    private EtatSimulation etat;
    private Integer[] temps = new Integer[] {0, 0}; 
    private Integer[] dureeCumul = new Integer[] {0, 0}; 
    private int dureeH;

    /**
     * Constructeur de la classe TempsSimulation.
     * 
     * @param etat L'état de la simulation.
     * @param duree La durée totale de la simulation en heures.
     */
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

    /**
     * Ajoute un temps spécifié à l'heure actuelle.
     * 
     * @param h Les heures à ajouter.
     * @param m Les minutes à ajouter.
     */
    public void addTemps(int h, int m){
        this.temps = TempsOperations.add(temps, h, m);
        this.dureeCumul = TempsOperations.addCumul(dureeCumul, h, m);
    }

    /**
     * Vérifie si la durée totale spécifiée a été atteinte.
     * 
     * @return Vrai si la durée totale a été atteinte, sinon faux.
     */
    public boolean estTerminee(){
        return dureeCumul[0] >= dureeH;
    }

    /**
     * Obtient le temps simulé actuel.
     * 
     * @return Le tableau contenant les heures et les minutes du temps simulé.
     */
    public Integer[] getTemps(){
        return this.temps;
    }
    
}