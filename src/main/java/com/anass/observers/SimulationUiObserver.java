package com.anass.observers;

/**
 * Cette interface définit les méthodes que les observateurs de l'interface utilisateur de la simulation doivent implémenter
 * pour être notifiés des changements dans la simulation.
 * 
 * Les méthodes permettent de mettre à jour l'interface utilisateur avec les temps de la simulation et de gérer la fin de la simulation.
 * 
 * @author Anass MEHDAOUI
 */
public interface SimulationUiObserver {
    
    /**
     * Met à jour l'interface utilisateur avec les temps de la simulation.
     * 
     * @param temps Un tableau d'entiers représentant les temps de la simulation.
     */
    public void updateUi(Integer[] temps);
    
    /**
     * Gère la fin de la simulation.
     */
    public void endSimulation();
}
