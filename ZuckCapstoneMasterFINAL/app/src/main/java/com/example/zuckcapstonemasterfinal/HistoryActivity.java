package com.example.zuckcapstonemasterfinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zuckcapstonemasterfinal.data.Database;
import com.example.zuckcapstonemasterfinal.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private Database dbManager;
    private Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbManager = new Database(this);

        ListView listViewHistory = findViewById(R.id.listViewHistory);
        ArrayList<Entry> entries = dbManager.getAllWeightEntries();
        adapter = new Adapter(this, entries);
        listViewHistory.setAdapter(adapter);

        listViewHistory.setOnItemClickListener((parent, view, position, id) -> {
            Entry selectedEntry = (Entry) parent.getItemAtPosition(position);

            Intent intent = new Intent(HistoryActivity.this, PhotoViewActivity.class);
            intent.putExtra("imagePath", selectedEntry.getProgressPhotoPath());
            startActivity(intent);
        });
    }


    protected void onResume() {
        super.onResume();
        ArrayList<Entry> entries = dbManager.getAllWeightEntries();
        adapter.setEntries(entries);
        adapter.notifyDataSetChanged();
    }

    protected static class Adapter extends ArrayAdapter<Entry> { /* Originally written by Rima D.*/
        private List<Entry> entries;

        public Adapter(Context context, ArrayList<Entry> entries) {
            super(context, 0, entries);
            this.entries = entries;
        }

        public void setEntries(List<Entry> updatedList) {
            super.clear();
            entries.clear();
            entries.addAll(updatedList);
            notifyDataSetChanged();
        }

        public int getEntryCount() {
            return entries.size();
        }

        public void updateData(ArrayList<Entry> updatedList) {
            super.clear();
            entries.clear();
            entries.addAll(updatedList);
            notifyDataSetChanged();
        }

        static class ViewHolder {
            TextView dateTextView;
            TextView weightTextView;
            Button deleteButton;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_entry, parent, false);
            }

            Entry entry = getItem(position);

            TextView dateTextView = convertView.findViewById(R.id.textViewDate);
            TextView weightTextView = convertView.findViewById(R.id.textViewWeight);

            if (dateTextView != null && weightTextView != null && entry != null) {
                dateTextView.setText(entry.getDate());
                weightTextView.setText((int) entry.getWeight());
            }

            Button deleteButton = convertView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long entryId = entries.get(position).getId();

                    Database dbHelper = new Database(getContext());
                    dbHelper.deleteWeightEntry(entryId);

                    entries.remove(position);
                }
            });
            return convertView;
        }
    }
}