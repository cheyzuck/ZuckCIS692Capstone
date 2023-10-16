package com.example.zuckcapstonemaster;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    /**
     * Method to navigate to the Main (Summary) Screen
     */
    public void goToMain (View v) {
        this.finish();
    }

    /**
     * Method to navigate to the Settings Screen
     */
    public void updateSettings (View v) {
        Intent myIntent = new Intent( this, SettingsActivity.class);
        this.startActivity(myIntent);
    }

    /**
     * Method to navigate to the New Entry Screen
     */
    public void enterData (View v) {
        Intent myIntent = new Intent(this, NewEntryActivity.class);
        this.startActivity(myIntent);
    }
}