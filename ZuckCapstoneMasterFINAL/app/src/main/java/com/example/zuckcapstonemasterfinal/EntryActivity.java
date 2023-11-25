package com.example.zuckcapstonemasterfinal;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.zuckcapstonemasterfinal.data.Database;
import com.example.zuckcapstonemasterfinal.data.Repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class EntryActivity extends AppCompatActivity {
    Repository entryRepository = new Repository();
    final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    final int REQUEST_IMAGE_CAPTURE = 1;
    private String imagePath;
    public ImageView imageView;
    private ActivityResultLauncher<Intent> cameraLauncher;
    DatePickerDialog datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ImageView imageView = findViewById(R.id.imageView);
        Button captureImage = findViewById(R.id.buttonCapturePicture);
        captureImage.setOnClickListener(view -> checkCameraPermissionAndTakePicture()); /* Originally Written by Rima Deshpande */

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null && data.getExtras() != null) {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    if (imageBitmap != null) {
                        imagePath = saveImageToStorage(imageBitmap);
                        if (imagePath != null) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                            imageView.setImageBitmap(bitmap);
                        } else {
                            Log.e("Camera", "Failed to load saved image");
                        }
                    } else {
                        Log.e("Camera", "Failed to capture image");
                    }
                }
            } else {
                Log.e("Camera", "Error capturing picture");
            }
        });

        entryRepository = Repository.getRepository();

        EditText weight = findViewById(R.id.weight);
        Button saveButton = findViewById(R.id.buttonSave);
        Button cancelButton = findViewById(R.id.buttonCancel);

        EditText selectedDate = findViewById(R.id.date);
        selectedDate.setInputType(InputType.TYPE_NULL);
        selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(EntryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selectedDate.setText(month+1  + "/" + day + "/" + year);
                    }
                }, year, month, day);
                datePicker.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userWeight = weight.getText().toString();
                String userDate = selectedDate.getText().toString();
                String progressPhotoPath = imagePath.toString();

                if (!userWeight.isEmpty() && imagePath != null && !imagePath.isEmpty()) {
                    Database dbManager = new Database(EntryActivity.this);
                    SQLiteDatabase db = dbManager.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(Database.WEIGHT_ENTRY_INPUT_DATE, userDate);
                    values.put(Database.WEIGHT_ENTRY_INPUT, userWeight);
                    values.put(Database.PROGRESS_PHOTO_PATH, progressPhotoPath);
                    long newRowId = db.insert(Database.TABLE_WEIGHT_ENTRIES, null, values);

                    db.close();
                    dbManager.close();

                    if (newRowId != -1) {
                        Toast.makeText(EntryActivity.this, "Weight entry saved!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EntryActivity.this, "Failed to save entry.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EntryActivity.this, "Please enter weight and capture image.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK && data != null) {
            }
            if (data != null && data.getData() !=null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(imageBitmap);
                imagePath = data.getData().toString();
                Log.d("Camera", "Image Path: " + imagePath);
            }
        }
    }

    public void checkCameraPermissionAndTakePicture() { /* Method originally written by Rima Deshpande, 2023 */
        if (ContextCompat.checkSelfPermission(EntryActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EntryActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, dispatch take picture intent
                dispatchTakePictureIntent();
            } else {
                // Camera permission denied
                Toast.makeText(this, "Camera permission denied. Cannot capture picture.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void returnHome (View v) {
        this.finish();
    }

    public void updateSettings (View v) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        this.startActivity(settingsIntent);
    }

    public void viewHistory (View v) {
        Intent historyIntent = new Intent (this, HistoryActivity.class);
        this.startActivity(historyIntent);
    }

    public void dispatchTakePictureIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
        PackageManager manager = getPackageManager();
        if (manager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
            try {

                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                System.out.println(e);
            }
        else
            Toast.makeText(this, "Camera Not Found", Toast.LENGTH_LONG).show();
    }

    public String saveImageToStorage (Bitmap bitmap) {
        String fileName = "captured_image.jpg";
        try (FileOutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            return getFilesDir() + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}