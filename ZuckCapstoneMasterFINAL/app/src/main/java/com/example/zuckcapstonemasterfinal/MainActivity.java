package com.example.zuckcapstonemasterfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zuckcapstonemasterfinal.data.Database;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        updateView();
    }

    public void updateView() {
        Database dbManager = new Database(this);
        try {
            System.out.println(dbManager.getAllWeightEntries());

            TextView currentWeight = (TextView) findViewById(R.id.label_current_weight); /* Originally written by Rima D.*/
            currentWeight.setText(String.valueOf(dbManager.getAllWeightEntries().get(dbManager.getAllWeightEntries().size() - 1).getWeight()).trim() + " lbs");


            TextView totalWeightLostText = (TextView) findViewById(R.id.label_total_weight_loss_number);
            totalWeightLostText.setText((( dbManager.getAllWeightEntries().get(dbManager.getAllWeightEntries().size() - 1).getWeight() - (float) dbManager.getAllWeightEntries().get(0).getWeight()) * -1) + " lbs");

        } catch (Exception e) {
            System.out.println("No Data Found");
        }
    }

    public void enterData (View v){
        Intent dataIntent = new Intent (this, EntryActivity.class);
        this.startActivity(dataIntent);
    }

    public void updateSettings(View v){
        Intent settingsIntent = new Intent (this, SettingsActivity.class);
        this.startActivity(settingsIntent);
    }

    public void viewHistory(View v){
        Intent historyIntent = new Intent (this, HistoryActivity.class);
        this.startActivity(historyIntent);
    }
}