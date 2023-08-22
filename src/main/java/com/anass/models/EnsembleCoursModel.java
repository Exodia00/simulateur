package com.anass.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette class représente une collection des modèles des cours d'eau dans la simulation
 * <p>
 * La EnsembleCoursModel permet de gérer l'ensemble des cours d'eau, de récupérer le débit total, et savoir le débit d'un cours d'eau spécifique
 * 
 * @author Anass MEHDAOUI
 */
public class EnsembleCoursModel {
    /**
     * Liste des cours d'eau
     */
    private ArrayList<CoursModel> coursList;
    /**
     * Nombre de cours d'eau
     */
    private int nbCours = 0;

    /**
     * Constructeur par liste des cours d'eau
     * 
     * @param cours : Liste des cours d'eau
     */
    public EnsembleCoursModel(ArrayList<CoursModel> cours){
        this.coursList = cours;
        this.nbCours = coursList.size();
    }

    /**
     * Constructeur par liste des débits
     * 
     * @param debits : Liste des débits des cours d'eau
     */
    public EnsembleCoursModel(List<Integer> debits){
        coursList = new ArrayList<>();
        for (int i=0; i<debits.size(); i++){
            this.coursList.add(new CoursModel(i, debits.get(i)));
            nbCours++;
        }
    }

    /**
     * Renvoie le cours avec l'index spécifié.
     * @param index : Indice du cours d'eau
     * @return  cours 
     */
    public CoursModel getCours(int index){
        return this.coursList.get(index);
    }

    /**
     * Renvoie le débit du cours d'eau ayant l'indice spécifié.
     * @param index : indice du cours d'eau
     * @return  Débit
     */
    public int getDebit(int index){
        return coursList.get(index).getDebit();
    }

    /**
     * Modifie le débit du cours d'eau ayant l'indice spécifié.
     * @param index : Indice
     * @param debit : Nouveau débit
     */
    public void setDebit(int index, int debit){
        coursList.get(index).setDebit(debit);
    }

    /**
     * Renvoie le nombre de cours d'eau
     * @return nbCours
     */
    public int getNbCours(){
        return this.nbCours;
    }

    /**
     * Renvoie le débit totale : La somme des débits des cours d'eau
     * @return debitTotal
     */
    public int getDebitTotal(){
        int sum = 0;
        for (CoursModel cours : this.coursList){
            sum += cours.getDebit();
        }
        return sum;
    }

    /**
     * Renvoie une représentation textuelle de l'ensemble.
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("-------- Ensemble de Cours : \n");
        builder.append("===== [Nombre de Cours] : ").append(nbCours).append("\n");
        for(int i=0; i<nbCours; i++){
            builder.append(String.format("Cours N : %02d : ", nbCours)).append(this.coursList.get(i).getDebit()).append("\n");
        }
        
        return builder.toString();
    }

}
