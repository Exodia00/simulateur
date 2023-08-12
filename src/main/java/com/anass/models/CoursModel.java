package com.anass.models;

public class CoursModel {
    private int id;
    private int debit;
    
    public CoursModel(int id, int debit) {
        this.id = id;
        this.debit = debit;
    }

    public int getId(){
        return this.id;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }


    

}
