package com.anass.observers;

import com.anass.models.EtatSimulation;

/**
 * Cette interface définit les méthodes que les observateurs du tableau de bord doivent implémenter
 * pour être notifiés des changements dans la simulation.
 * 
 * Les méthodes permettent de mettre à jour les débits de cours, l'état de la simulation et de réinitialiser la simulation.
 * 
 * @author Anass MEHDAOUI
 */
public interface DashboardObserver {
    
    /**
     * Met à jour le débit d'un cours spécifique.
     * 
     * @param index L'index du cours.
     * @param value La nouvelle valeur du débit.
     */
    public abstract void updateDebitCours(int index, int value);
    
    /**
     * Met à jour l'état de la simulation.
     * 
     * @param etat Le nouvel état de la simulation.
     */
    public abstract void updateEtatsimulation(EtatSimulation etat);
    
    /**
     * Réinitialise la simulation.
     */
    public abstract void reinitialiserSimulation();
}
