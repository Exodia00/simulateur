package com.anass.observers;

/**
 * Cette interface définit la méthode que les observateurs du volume de la simulation doivent implémenter
 * pour être notifiés des changements dans le volume.
 * 
 * La méthode permet de mettre à jour les observateurs avec le volume actuel de la simulation.
 * 
 * @author Anass MEHDAOUI
 */
public interface VolumeObserver {
    
    /**
     * Met à jour les observateurs avec le volume actuel de la simulation.
     * 
     * @param volume Le volume actuel de la simulation.
     */
    public void updateVolume(int volume);

}
