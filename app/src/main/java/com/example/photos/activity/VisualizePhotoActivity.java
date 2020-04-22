package com.example.photos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photos.R;
import com.example.photos.db.BDSQLiteHelper;
import com.example.photos.model.Photo;

public class VisualizePhotoActivity extends AppCompatActivity {

    private BDSQLiteHelper database;
    private int photoId;
    private Photo photo;
    private TextView txtTitle;
    private TextView txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new BDSQLiteHelper(this);
        Intent intent = getIntent();
        this.photoId = intent.getIntExtra("photoId", 0);

        photo = database.getPhoto(this.photoId);
        Bitmap bitmap = BitmapFactory.decodeFile(photo.getUrl());
        ImageView imageView = findViewById(R.id.updatePhotoImageView);
        imageView.setImageBitmap(bitmap);

        txtTitle = findViewById(R.id.txtTitle);
        txtDescription  = findViewById(R.id.txtDescription);

        txtTitle.setText(photo.getTitle());
        txtDescription.setText(photo.getDescription());


    }
}
