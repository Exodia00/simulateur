package com.anass.models;

public class PeriodeCritiqueModel {
    private int heureDebut, heureFin;

    public PeriodeCritiqueModel(int debut){
        this.heureDebut = debut;
    }

    public int getheureDebut(){
        return this.heureDebut;
    }

    public int getheurefin(){
        return this.heureFin;
    }

    public void setHeureFin(int fin){
        this.heureFin = fin;
    }
}
