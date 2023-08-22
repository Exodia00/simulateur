package com.anass.models;

/**
 * Cette class représente le modèle d'un cours d'eau
 */
public class CoursModel {
    /**
     * Identifiant utilisé pour identifier le cours d'eau dans la simulation
     */
    private int id;
    /**
     * Débit par m3/h
     */
    private int debit;
    
    /**
     * Constructeur qui prend en parametres l'identifiant et le débit
     * @param id
     * @param debit
     */
    public CoursModel(int id, int debit) {
        this.id = id;
        this.debit = debit;
    }

    /**
     * Renvoie l'identifiant 
     * @return id
     */
    public int getId(){
        return this.id;
    }

    /**
     * Renvoie le débit 
     * @return debit
     */
    public int getDebit() {
        return debit;
    }

    /**
     * Modifie la valeur du débit
     * @param debit
     */
    public void setDebit(int debit) {
        this.debit = debit;
    }


    

}
