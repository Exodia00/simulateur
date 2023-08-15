package com.anass.models;

public class ReservoirModel {
    private int volume;
    private int maxVolume = 5000000;
    private int minVolume = 1000;

    public ReservoirModel(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getMaxVolume(){
        return this.maxVolume;
    }

    public void addVolume(int volume){
        int vol = this.volume + volume;
        if (vol<minVolume) return;
        this.volume = vol < maxVolume ? vol : maxVolume;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("-------- Reservoir :");
        builder.append("niveau :").append(this.volume);
        return builder.append("\n").toString();
    }

    
}
