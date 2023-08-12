package com.anass.observers;

import com.anass.models.EtatSimulation;

public interface DashboardObserver {
    
    public abstract void updateDebitCours(int index, int value);
    public abstract void updateEtatsimulation(EtatSimulation etat);

}
