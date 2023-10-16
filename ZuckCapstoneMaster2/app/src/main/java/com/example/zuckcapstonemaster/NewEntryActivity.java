package com.example.zuckcapstonemaster;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zuckcapstonemaster.data.DatabaseManager;
import com.example.zuckcapstonemaster.data.NewEntry;

public class NewEntryActivity extends AppCompatActivity {

    private DatabaseManager dbManager;

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
    }

    /**
     * Method to navigate to the Main (Summary) Screen
     */
    public void goBack (View v) {
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
     * Method to navigate to the History Screen
     */
    public void viewHistory (View v) {
        Intent myIntent = new Intent( this, HistoryActivity.class);
        this.startActivity(myIntent);
    }

    /**
     * Method for adding entry to sqlite db
     */
    public void addEntry( View v) {
        //Retrieves input data
        EditText weightEntryNumberEditText = (EditText) findViewById(R.id.input_new_entry_weight);
        EditText weightEntryDateEditText = (EditText) findViewById(R.id.input_new_entry_date);
        String weightEntryNumber = weightEntryNumberEditText.getText().toString();
        String weightEntryDate = weightEntryDateEditText.getText().toString();

        //add data to db
        try {
            float weightEntry = Float.parseFloat(weightEntryNumber);
            NewEntry entry = new NewEntry(0, weightEntry, weightEntryDate);
            Toast.makeText(this, "Entry Added", Toast.LENGTH_SHORT).show();

            /**
             * updates cancel button text to reflect better option for user -- entry is added at this point,
             * so no "Cancel Entry" option is needed. Instead user can navigate back to summary
             */
            Button cancelButtonText = findViewById(R.id.button_new_entry_cancel);
            cancelButtonText.setText("View Summary Page");
            cancelButtonText.setTextSize(12);
        } catch (Exception e) {
            Toast.makeText(this, "Entry NOT Added", Toast.LENGTH_LONG).show();
            System.out.println(e);
        }

        //clears input data
        weightEntryNumberEditText.setText("");
        weightEntryDateEditText.setText("");

    }
}