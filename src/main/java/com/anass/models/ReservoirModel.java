package com.anass.models;

public class ReservoirModel {
    private int volume;

    public ReservoirModel(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("-------- Reservoir :");
        builder.append("niveau :").append(this.volume);
        return builder.append("\n").toString();
    }

    
}
