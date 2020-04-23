package com.example.photos.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photos.R;
import com.example.photos.db.BDSQLiteHelper;
import com.example.photos.model.Photo;

import java.io.File;

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
        ImageView imageView = findViewById(R.id.visualizePhotoImageView);
        imageView.setImageBitmap(bitmap);

        txtTitle = findViewById(R.id.txtTitle);
        txtDescription  = findViewById(R.id.txtDescription);

        txtTitle.setText(photo.getTitle());
        txtDescription.setText(photo.getDescription());
    }


    public void handleUpdatePhotoActivity(View view) {
        Intent intent = new Intent(this,UpdatePhotoActivity.class);
        intent.putExtra("photoId", this.photoId);
        startActivity(intent);
    }

    public void handleDeletePhoto(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir");
        builder.setMessage("Você tem certeza que deseja excluir esta foto?");
        builder.setCancelable(false);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int returnedId = database.deletePhoto(photo);
                File file = new File(photo.getUrl());
                boolean deleted = file.delete();
                Toast.makeText(getApplicationContext(), "Foto excluída com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VisualizePhotoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
