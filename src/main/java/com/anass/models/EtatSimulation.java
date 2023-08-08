package com.anass.models;

public class EtatSimulation {
    

    
    public enum Etat{
        ACTIVE, 
        PAUSE,
        FIN
    }
    
    public static Etat ACTIVE = Etat.ACTIVE;
    public static Etat PAUSE = Etat.PAUSE;
    public static Etat FIN = Etat.FIN;


    private Etat etat;

    public EtatSimulation(Etat etat){
        this.etat = etat;
    }


    public String toString(){
        StringBuilder builder = new StringBuilder("------ Etat de la simulation : ");
        String str = "";
        if (etat == Etat.ACTIVE) str = "Actve !";
        else if(etat == Etat.PAUSE) str = "En Pause !";
        else if(etat == Etat.FIN) str = "Terminee";

        return builder.append(str).append("\n").toString();
    }

}
