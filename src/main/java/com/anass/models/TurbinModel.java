package com.anass.models;

import java.util.ArrayList;
import java.util.List;

public class TurbinModel {
    private List<Integer> debits = new ArrayList<Integer>();

    public TurbinModel(List<Integer> debits) {
        this.debits = debits;
    }

    public int getDebit(int index){
        return debits.get(index);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("-------- Turbin :\n");
        for(int i=0; i<23; i++){
            builder.append("====> ").append(String.format("%02d", i)).append(":00h : ").append(debits.get(i)).append("m3/s\n");
        }        
        return builder.append("\n").toString();
    }
    
}
