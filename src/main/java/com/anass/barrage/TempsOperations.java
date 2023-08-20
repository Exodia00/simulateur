package com.anass.barrage;

/**
 * Gestion de la logique temporelle dans l'application
 * <p>
 * Permet de calculer la somme des temps et la durée totale de la simulation, ainsi que d'obtenir 
 * une représentation textuelle conviviale d'un instant de la simulation.
 * 
 * @author : Anass MEHDAOUI
 */
public class TempsOperations {

    /**
     * Permet d'obtenir une représentation textuelle convivale d'un instant `temps`
     * @param temps Integer[] : List representant l'instant à convertir
     * @return text String : Représentation textuelle de l'instant sous la forme XX:YY 
     */
    public static String toString(Integer[] temps){
        return String.format("%02d:%02d", temps[0], temps[1]);
    }

    /**
     * Permet de calculer la somme d'un instant et d'une durée repérée par le nombre d'heures et le nembre de minutes
     * <p>
     *  Le resultat obtenu représant une periode dans la journée, alors le résultat de l'heure 
     * est toujour inférieur à 24:00
     * 
     * @param temps Integer[] : List representant l'instant actuelle [h, m]
     * @param h Integer : Heures à ajouter
     * @param m Integer : Minutes à ajouter
     * @return temps Integer[] La somme sous forme d'une list [h, m]
     */
    public static Integer[] add(Integer[] temps, int h, int m){
        Integer[] result = temps;
        int sumMin = m + temps[1];
        result[1] = sumMin%60;
        result[0] = (result[0] + (int)(sumMin/60))%24;
        return result;
    }

    /**
     * Permet d'obtenir la somme de deux periodes, le résultat peut étre supérieur à 24h
     * @param tempsCumul Integer[]
     * @param h Integer : heure
     * @param m Integer : minutes
     * @return la somme des deuc périodes 
     */
    public static Integer[] addCumul(Integer[] tempsCumul, int h, int m){
        Integer[] result = tempsCumul;
        int sumMin = m + tempsCumul[1];
        result[1] = sumMin%60;
        result[0] = result[0] + (int)(sumMin/60);
        return result;
    }

}
