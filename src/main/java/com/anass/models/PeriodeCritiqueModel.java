package com.anass.models;

/**
 * Cette classe représente un modèle de période critique.
 * <p>
 * Un modèle de période critique contient les informations
 * sur le début et la fin d'une période critique en termes
 * d'heures.
 * </p>
 * 
 * @author Anass MEHDAOUI
 */
public class PeriodeCritiqueModel {
    
    /** L'heure de début de la période critique. */
    private int heureDebut;
    
    /** L'heure de fin de la période critique. */
    private int heureFin;

    /**
     * Constructeur pour initialiser l'heure de début de la période critique.
     *
     * @param debut L'heure de début de la période critique.
     */
    public PeriodeCritiqueModel(int debut){
        this.heureDebut = debut;
    }

    /**
     * Obtient l'heure de début de la période critique.
     *
     * @return L'heure de début de la période critique.
     */
    public int getHeureDebut(){
        return this.heureDebut;
    }

    /**
     * Obtient l'heure de fin de la période critique.
     *
     * @return L'heure de fin de la période critique.
     */
    public int getHeureFin(){
        return this.heureFin;
    }

    /**
     * Modifie l'heure de fin de la période critique.
     *
     * @param fin L'heure de fin de la période critique.
     */
    public void setHeureFin(int fin){
        this.heureFin = fin;
    }
}