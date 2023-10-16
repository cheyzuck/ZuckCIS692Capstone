package com.example.zuckcapstonemaster;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.example.zuckcapstonemaster.data.Person;

import java.util.Calendar;


public class SettingsActivity extends AppCompatActivity {
    DatePickerDialog dateSelector;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText startingDateInput = (EditText) findViewById(R.id.input_settings_starting_date);
        startingDateInput.setInputType(InputType.TYPE_NULL);

        startingDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dateSelector = new DatePickerDialog(SettingsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        startingDateInput.setText(month + "/" + day + "/" + year);
                    }
                }, year, month, day);
                dateSelector.show();
            }
        });

        EditText targetDateInput = (EditText) findViewById(R.id.input_settings_target_date);
        targetDateInput.setInputType(InputType.TYPE_NULL);

        targetDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dateSelector = new DatePickerDialog(SettingsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        targetDateInput.setText(month + "/" + day + "/" + year);
                    }
                }, year, month, day);
                dateSelector.show();
            }
        });
    }

    /**
     * Method to navigate to the Main (Summary) Screen
     * <p>
     * TODO - connect with Discard Setting button
     */
    public void goToMain(View v) {
        this.finish();
    }

    /**
     * Method to navigate to the New Entry Screen
     */
    public void enterData(View v) {
        Intent myIntent = new Intent(this, NewEntryActivity.class);
        this.startActivity(myIntent);
    }

    /**
     * Method to navigate to the History Screen
     */
    public void viewHistory(View v) {
        Intent myIntent = new Intent(this, HistoryActivity.class);
        this.startActivity(myIntent);
    }

    public void addSettings(View v) {
        //retrieve input data from screen
        EditText startingWeightInputEditText = (EditText) findViewById(R.id.input_settings_current_weight);
        EditText startingDateInputEditText = (EditText) findViewById(R.id.input_settings_starting_date);
        EditText targetWeightInputEditText = (EditText) findViewById(R.id.input_settings_target_weight);
        EditText targetDateInputEditText = (EditText) findViewById(R.id.input_settings_target_date);
//        EditText genderSelectionEditText = (EditText) findViewById(R.id.myRadioGroup); // todo - determine how to get this value
        EditText heightInputEditText = (EditText) findViewById(R.id.input_settings_height);

        String startingWeightInputString = startingWeightInputEditText.getText().toString();
        String startingDateInputString = startingDateInputEditText.getText().toString();
        String targetWeightInputString = targetWeightInputEditText.getText().toString();
        String targetDateInputString = targetDateInputEditText.getText().toString();
//        String genderSelectionString //  todo - determine how to get this value
        String heightInputString = heightInputEditText.getText().toString();

        //add data to db
        try {
            float startingWeight = Float.parseFloat(startingWeightInputString);
            float targetWeight = Float.parseFloat(targetWeightInputString);

            Person person = new Person(0, startingWeight, startingDateInputString, targetWeight,
                    targetDateInputString, "NA", heightInputString);

            Toast.makeText(this, "Settings Saved", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Settings NOT Saved", Toast.LENGTH_LONG).show();
        }
    }
}