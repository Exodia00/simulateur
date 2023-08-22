package com.anass.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente le modèle de la turbine qui est alimentée par des débits.
 * Elle contient les débits d'eau correspondant à chaque heure de la journée.
 * 
 * @author Anass MEHDAOUI
 */
public class TurbinModel {
    private List<Integer> debits = new ArrayList<Integer>();

    /**
     * Constructeur de la classe TurbinModel.
     * 
     * @param debits La liste des débits d'eau pour chaque heure de la journée.
     */
    public TurbinModel(List<Integer> debits) {
        this.debits = debits;
    }

    /**
     * Renvoie le débit d'eau pour une heure spécifique.
     * 
     * @param index L'index correspondant à l'heure.
     * @return Le débit d'eau pour l'heure donnée.
     */
    public int getDebit(int index){
        return debits.get(index);
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères des débits de la turbine.
     * 
     * @return La chaîne de caractères représentant les débits de la turbine.
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("-------- Turbine :\n");
        for(int i=0; i<23; i++){
            builder.append("====> ").append(String.format("%02d", i)).append(":00h : ").append(debits.get(i)).append("m3/s\n");
        }        
        return builder.append("\n").toString();
    }
}
