package com.example.zuckcapstonemasterfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        String imagePath = getIntent().getStringExtra("imagePath");

        if (imagePath != null) {
            // Load and display the image using an ImageView
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(Uri.parse(imagePath));
        }
    }
}