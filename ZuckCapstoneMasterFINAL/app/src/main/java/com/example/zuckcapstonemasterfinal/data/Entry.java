package com.example.zuckcapstonemasterfinal.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Entry implements Parcelable{
    private long id;
    private float inputWeight;
    private String inputDate;
    private String progressPhotoPath;

    public Entry (long id, float inputWeight, String inputDate, String progressPhotoPath){
        this.id = id;
        this.inputWeight = inputWeight;
        this.inputDate = inputDate;
        this.progressPhotoPath = progressPhotoPath;
    }

    public long getId(){
        return id;
    }

    public float getWeight(){
        return inputWeight;
    }

    public String getDate(){
        return inputDate;
    }

    public String getProgressPhotoPath(){
        return progressPhotoPath;
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

    public void setProgressPhotoPath() {
        this.progressPhotoPath = progressPhotoPath;
    }


    /*Parcing Implementation by Rima D., 2023*/

    protected Entry(Parcel in) {
        inputDate = in.readString();
        inputWeight = Float.parseFloat(in.readString());
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(inputDate);
        dest.writeString(String.valueOf(inputWeight));
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };
}
