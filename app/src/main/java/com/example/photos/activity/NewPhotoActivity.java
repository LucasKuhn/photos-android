package com.example.photos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photos.R;
import com.example.photos.db.BDSQLiteHelper;
import com.example.photos.model.Photo;

public class NewPhotoActivity extends AppCompatActivity {

    private BDSQLiteHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);
        database = new BDSQLiteHelper(this);
        Intent intent = getIntent();
        final String photoURL = intent.getStringExtra("photoURL");
        Bitmap bitmap = BitmapFactory.decodeFile(photoURL);
        ImageView imageView = findViewById(R.id.newPhotoImageView);
        imageView.setImageBitmap(bitmap);
        final EditText title = findViewById(R.id.inputTitle);
        final EditText description = findViewById(R.id.inputDescription);
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo photo = new Photo(title.getText().toString(),description.getText().toString(), photoURL);
                int imageId = database.addPhoto(photo);
                Toast.makeText(getApplicationContext() , "Foto salva com sucesso!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NewPhotoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
