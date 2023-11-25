package com.example.zuckcapstonemasterfinal.data;

public class Person {
    private static Person person;
    private String name;
    private float startWeight;
    private String startDate;
    private float targetWeight;
    private String targetDate;
    private String gender;
    private String height;

    public Person getPerson(){
        if (person == null){
            person = new Person(name, startWeight, startDate, targetWeight, targetDate, gender, height);
        }
        return person;
    }

    public Person(String name, float startWeight, String startDate, float targetWeight, String targetDate, String gender, String height){
    }

    public String getName(){ return name;}

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

    public void setName(String name){ this.name = name;}

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
        return "Person{ name" + name + "start weight= "+startWeight+"start date= "+startDate+"target weight= "+targetWeight+"target date= "+targetDate+"gender= "+gender+"height= "+height+"}";
    }
}
