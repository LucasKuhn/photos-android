package com.example.photos.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.example.photos.R;
import com.example.photos.adapter.PhotosAdapter;
import com.example.photos.model.Photo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST = 2;
    private final int CAMERA = 3;

    private File filePhoto = null;
    ArrayList<String> photoList;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvPhotos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Asks permission to write files on the device
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission. WRITE_EXTERNAL_STORAGE )
                != PackageManager. PERMISSION_GRANTED ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission. WRITE_EXTERNAL_STORAGE )) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission. WRITE_EXTERNAL_STORAGE },
                        PERMISSION_REQUEST);
            }
        }
    }

    protected void onStart() {
        super.onStart();
        photoList = new ArrayList<String>();
        photoList.add("Foto 1");
        photoList.add("Foto 2");

        PhotosAdapter adapter = new PhotosAdapter(photoList, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA) {
            sendBroadcast(new Intent(
                    Intent. ACTION_MEDIA_SCANNER_SCAN_FILE ,
                    Uri.fromFile(filePhoto))
            );
        }
    }

    public void takePhoto(View view) {
        Intent takePictureIntent = new
                Intent(MediaStore. ACTION_IMAGE_CAPTURE );
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                filePhoto = createFile();
            } catch (IOException ex) {
                showAlert(getString(R.string.error ), getString(
                        R.string.error_saving_photo ));
            }
            if (filePhoto != null) {
                Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                        getBaseContext().getApplicationContext().getPackageName() +
                                ".provider", filePhoto);
                takePictureIntent.putExtra(MediaStore. EXTRA_OUTPUT , photoURI);
                startActivityForResult(takePictureIntent, CAMERA);
            }
        }
    }

    private File createFile() throws IOException {
        String timeStamp = new
                SimpleDateFormat("yyyyMMdd_Hhmmss").format(
                new Date());
        File pasta = Environment. getExternalStoragePublicDirectory (
                Environment.DIRECTORY_PICTURES);
        File image = new File(pasta.getPath() + File. separator
                + "JPG_" + timeStamp + ".jpg");
        return image;
    }

    private void showAlert(String titulo, String mensagem) {
        AlertDialog alertDialog = new
                AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(AlertDialog. BUTTON_NEUTRAL ,
                getString(R.string.ok ),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
