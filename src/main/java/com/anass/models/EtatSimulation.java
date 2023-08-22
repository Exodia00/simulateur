package com.anass.models;
/**
 * Cette classe représente l'état de la simulation : Actif, En Pause ou Terminée.
 * <p>
 * Cette classe encapsule les différents états possibles de la simulation
 * et fournit des méthodes pour accéder et modifier l'état actuel.
 * </p>
 * 
 * @author Anass MEHDAOUI
 */
public class EtatSimulation {
    
    /**
     * Énumération des différents états possibles de la simulation.
     */
    public enum Etat{
        /**
         * État de la simulation : Actif.
         */
        ACTIVE, 
        
        /**
         * État de la simulation : En Pause.
         */
        PAUSE,
        
        /**
         * État de la simulation : Terminée.
         */
        FIN
    }
    
    /**
     * Constante représentant l'état de la simulation : Actif.
     */
    public static Etat ACTIVE = Etat.ACTIVE;
    
    /**
     * Constante représentant l'état de la simulation : En Pause.
     */
    public static Etat PAUSE = Etat.PAUSE;
    
    /**
     * Constante représentant l'état de la simulation : Terminée.
     */
    public static Etat FIN = Etat.FIN;

    /**
     * L'état actuel de la simulation.
     */
    private Etat etat;

    /**
     * Constructeur permettant d'initialiser l'état de la simulation.
     *
     * @param etat L'état initial de la simulation.
     */
    public EtatSimulation(Etat etat){
        this.etat = etat;
    }

    /**
     * Obtient l'état actuel de la simulation.
     *
     * @return L'état actuel de la simulation.
     */
    public Etat getEtat(){
        return this.etat;
    }

    /**
     * Modifie l'état actuel de la simulation.
     *
     * @param etat Le nouvel état de la simulation.
     */
    public void setEtat(Etat etat){
        this.etat = etat;
    }

    /**
     * Retourne une représentation textuelle de l'état de la simulation.
     *
     * @return La chaîne représentant l'état de la simulation.
     */
    public String toString(){
        StringBuilder builder = new StringBuilder();
        String str = "";
        if (etat == Etat.ACTIVE) str = "Actif !";
        else if(etat == Etat.PAUSE) str = "En Pause !";
        else if(etat == Etat.FIN) str = "Terminée";

        return builder.append(str).append("\n").toString();
    }
}