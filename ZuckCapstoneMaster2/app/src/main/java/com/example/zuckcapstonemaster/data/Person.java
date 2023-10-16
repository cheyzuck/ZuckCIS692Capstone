package com.example.zuckcapstonemaster.data;

public class Person {
    private int id;
    private float startWeight;
    private String startDate;
    private float targetWeight;
    private String targetDate;
    private String gender;
    private String height;

    public Person(int id, float startWeight, String startDate, float targetWeight, String targetDate, String gender, String height){
        this.id = id;
        this.startWeight = startWeight;
        this.startDate = startDate;
        this.targetWeight = targetWeight;
        this.targetDate = targetDate;
        this.gender = gender;
        this.height = height;
    }

    public int getId(){
        return id;
    }

    public float getStartWeight() {
        return startWeight;
    }

    public String getStartDate() {
        return startDate;
    }

    public float getTargetWeight() {
        return targetWeight;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartWeight(float startWeight) {
        this.startWeight = startWeight;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setTargetWeight(float targetWeight) {
        this.targetWeight = targetWeight;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString(){
        return "Person{" + "id= " +id+ "start weight= "+startWeight+"start date= "+startDate+"target weight= "+targetWeight+"target date= "+targetDate+"gender= "+gender+"height= "+height+"}";
    }
}
