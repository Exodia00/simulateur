package com.anass.models;

import java.util.ArrayList;
import java.util.List;

public class SimulationRslt {
    private int volUtilise = 0;
    private List<PeriodeCritiqueModel> periodesCritiques = new ArrayList<>();

    public int getVolUtilise(){
        return this.volUtilise;
    }

    public List<PeriodeCritiqueModel> getPeriodesCritiques(){
        return this.periodesCritiques;
    }
    
    public void incVolum(int vol){
        this.volUtilise += vol;
    }

    public void ajouterPeriodeCritique(PeriodeCritiqueModel periodeCritique){
        this.periodesCritiques.add(periodeCritique);
    }

}
