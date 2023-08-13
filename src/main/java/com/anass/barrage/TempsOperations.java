package com.anass.barrage;

public class TempsOperations {

    public static String toString(Integer[] temps){
        return String.format("%02d H : %02d m", temps[0], temps[1]);
    }

    public static Integer[] add(Integer[] temps, int h, int m){
        Integer[] result = temps;
        int sumMin = m + temps[1];
        result[1] = sumMin%60;
        result[0] = (result[0] + (int)(sumMin/60))%24;
        return result;
    }

}
