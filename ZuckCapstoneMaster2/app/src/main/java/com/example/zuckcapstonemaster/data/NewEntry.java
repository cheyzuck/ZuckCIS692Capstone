package com.example.zuckcapstonemaster.data;

public class NewEntry {
    private int id;
    private float inputWeight;
    private String inputDate;

    public NewEntry(int id, float inputWeight, String inputDate){
        this.id = id;
        this.inputWeight = inputWeight;
        this.inputDate = inputDate;
    }

    public int getId(){
        return id;
    }

    public float getWeight(){
        return inputWeight;
    }

    public String getDate(){
        return inputDate;
    }

    public void setId(){
        this.id = id;
    }

    public void setInputWeight(){
        this.inputWeight = inputWeight;
    }

    public void setInputDate(){
        this.inputDate = inputDate;
    }

    @Override
    public String toString(){
        return "New Entry{" + "id = " + id + "Input Weight = "+ inputWeight + "Input Date = " + inputDate;
    }
}
