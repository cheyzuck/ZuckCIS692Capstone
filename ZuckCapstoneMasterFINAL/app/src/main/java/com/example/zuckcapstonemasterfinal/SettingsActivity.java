package com.example.zuckcapstonemasterfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zuckcapstonemasterfinal.data.Database;
import com.example.zuckcapstonemasterfinal.data.Person;

public class SettingsActivity extends AppCompatActivity { /* Checked code against Rima and Shraddha*/
    private EditText editTextName;
    private EditText editTextStartDate;
    private EditText editTextStartWeight;
    private EditText editTextHeight;
    private EditText editTextTargetWeight;
    private EditText editTextTargetDate;
    private RadioGroup genderGroup;
    private Database dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dbManager = new Database(this);

        editTextName = findViewById(R.id.name);
        editTextStartDate = findViewById(R.id.input_settings_start_date);
        editTextStartWeight = findViewById(R.id.input_settings_current_weight);
        editTextHeight = findViewById(R.id.input_settings_height);
        genderGroup = findViewById(R.id.genderGroup);
        editTextTargetWeight = findViewById(R.id.input_settings_target_weight);
        editTextTargetDate = findViewById(R.id.input_settings_target_date);


        loadUser();


        Button btnSaveSettings = findViewById(R.id.button_settings_save_settings);
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserSettings();
            }
        });

        Button btnCancel = findViewById(R.id.button_settings_discard_settings);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loadUser() {

        Person userSettings = dbManager.getUserSettings();
        if (userSettings != null) {
            editTextName.setText(String.valueOf(userSettings.getName()));
            editTextStartWeight.setText(String.valueOf(userSettings.getStartWeight()));
            editTextStartDate.setText(String.valueOf(userSettings.getStartDate()));
            editTextHeight.setText(String.valueOf(userSettings.getHeight()));
            genderGroup.getCheckedRadioButtonId();
            editTextTargetWeight.setText(String.valueOf(userSettings.getTargetWeight()));
            editTextTargetDate.setText(userSettings.getTargetDate());
        }
    }

    private void saveUserSettings() {

        String name = editTextName.getText().toString();
        float initialWeight = Float.parseFloat(editTextStartWeight.getText().toString());
        String initialDate = editTextStartDate.getText().toString();
        String height = editTextHeight.getText().toString();
        String gender = getSelectedGender();
        float goalWeight = Float.parseFloat(editTextTargetWeight.getText().toString());
        String goalDate = editTextTargetDate.getText().toString();

        Person userSettings = dbManager.getUserSettings();

        if (userSettings == null) {
            userSettings = new Person(name, initialWeight, initialDate, goalWeight, goalDate, gender, height);
        } else {
            userSettings.setStartWeight(initialWeight);
            userSettings.setStartDate(initialDate);
            userSettings.setHeight(height);
            userSettings.setGender(gender);
            userSettings.setTargetWeight(goalWeight);
            userSettings.setTargetDate(goalDate);
        }


        long result = dbManager.saveUserSettings(userSettings);

        if (result != -1) {
            Toast.makeText(this, "Settings Saved", Toast.LENGTH_SHORT).show();
            loadUser();
        } else {
            Toast.makeText(this, "Settings NOT Saved", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(SettingsActivity.this, MainActivity.class); /* Originally Written by Shraddha*/
        startActivity(intent);

    }

    // Helper method to retrieve the selected gender from the RadioGroup
    private String getSelectedGender() {
        int selectedRadioButtonId = genderGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == R.id.radio_button_male) {
            return "Male";
        } else if (selectedRadioButtonId == R.id.radio_button_female) {
            return "Female";
        } else if (selectedRadioButtonId == R.id.radio_button_no_answer) {
            return "Prefer Not to Answer";
        }
        return "";
    }

    private void selectGender(String gender) {
        if (gender.equals("Male")) {
            genderGroup.check(R.id.radio_button_male);
        } else if (gender.equals("Female")) {
            genderGroup.check(R.id.radio_button_female);
        } else if (gender.equals("Prefer Not to Answer")){
            genderGroup.check(R.id.radio_button_no_answer);
        }
    }

}