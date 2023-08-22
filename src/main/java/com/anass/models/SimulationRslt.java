package com.anass.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente les résultats de la simulation du barrage hydroélectrique.
 * Elle contient le volume d'eau utilisé et les périodes critiques.
 * 
 * @author Anass MEHDAOUI
 */
public class SimulationRslt {
    private int volUtilise = 0;
    private List<PeriodeCritiqueModel> periodesCritiques = new ArrayList<>();

    /**
     * Renvoie le volume d'eau utilisé lors de la simulation.
     * 
     * @return Le volume d'eau utilisé.
     */
    public int getVolUtilise(){
        return this.volUtilise;
    }

    /**
     * Renvoie la liste des périodes critiques enregistrées lors de la simulation.
     * 
     * @return La liste des périodes critiques.
     */
    public List<PeriodeCritiqueModel> getPeriodesCritiques(){
        return this.periodesCritiques;
    }
    
    /**
     * Incrémente le volume d'eau utilisé.
     * 
     * @param vol Le volume d'eau à ajouter.
     */
    public void incVolum(int vol){
        this.volUtilise += vol;
    }

    /**
     * Ajoute une période critique à la liste des périodes critiques.
     * 
     * @param periodeCritique La période critique à ajouter.
     */
    public void ajouterPeriodeCritique(PeriodeCritiqueModel periodeCritique){
        this.periodesCritiques.add(periodeCritique);
    }
}
