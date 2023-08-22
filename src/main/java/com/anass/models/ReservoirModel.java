package com.anass.models;

/**
 * Cette classe représente un modèle de réservoir.
 * <p>
 * Un modèle de réservoir contient les informations sur le volume
 * actuel du réservoir ainsi que ses capacités minimales et maximales.
 * </p>
 * 
 * @author Anass MEHDAOUI
 */
public class ReservoirModel {

    /** Le volume actuel du réservoir. */
    private int volume;
    
    /** Le volume maximum du réservoir. */
    private int maxVolume = 50000;
    
    /** Le volume minimum du réservoir. */
    private int minVolume = 100;

    /**
     * Constructeur pour initialiser le volume du réservoir.
     *
     * @param volume Le volume initial du réservoir.
     */
    public ReservoirModel(int volume) {
        this.volume = volume;
    }

    /**
     * Obtient le volume actuel du réservoir.
     *
     * @return Le volume actuel du réservoir.
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Modifie le volume actuel du réservoir.
     *
     * @param volume Le nouveau volume du réservoir.
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }

    /**
     * Obtient le volume maximum du réservoir.
     *
     * @return Le volume maximum du réservoir.
     */
    public int getMaxVolume(){
        return this.maxVolume;
    }

    /**
     * Ajoute un certain volume au réservoir.
     *
     * @param volume Le volume à ajouter.
     */
    public void addVolume(int volume){
        int vol = this.volume + volume;
        if (vol < minVolume) return;
        this.volume = vol < maxVolume ? vol : maxVolume;
    }

    /**
     * Génère une représentation textuelle du réservoir.
     *
     * @return Une chaîne de caractères décrivant le réservoir.
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("-------- Reservoir :");
        builder.append(String.format("Le volume : %dm3/%dm3" , this.volume, this.maxVolume));
        return builder.append("\n").toString();
    }
}
