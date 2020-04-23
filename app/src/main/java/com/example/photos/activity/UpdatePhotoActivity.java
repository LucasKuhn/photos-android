package com.example.photos.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photos.R;
import com.example.photos.db.BDSQLiteHelper;
import com.example.photos.model.Photo;

public class UpdatePhotoActivity extends AppCompatActivity {

    private BDSQLiteHelper database;
    private int photoId;
    private Photo photo;
    private TextView txtTitle;
    private TextView txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new BDSQLiteHelper(this);
        Intent intent = getIntent();
        this.photoId = intent.getIntExtra("photoId", 0);

        photo = database.getPhoto(this.photoId);
        Bitmap bitmap = BitmapFactory.decodeFile(photo.getUrl());
        ImageView imageView = findViewById(R.id.updatePhotoImageView);
        imageView.setImageBitmap(bitmap);

        this.txtTitle = findViewById(R.id.inputTitle);
        this.txtDescription = findViewById(R.id.inputDescription);

        txtTitle.setText(photo.getTitle());
        txtDescription.setText(photo.getDescription());
    }

    public void handleSavePhoto(View view){
        Photo updatedPhoto = new Photo(this.txtTitle.getText().toString(),this.txtDescription.getText().toString(), this.photo.getUrl());
        updatedPhoto.setId(this.photo.getId());
        int photoId = database.updatePhoto(updatedPhoto);

        Toast.makeText(getApplicationContext() , "Foto atualizada com sucesso!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
