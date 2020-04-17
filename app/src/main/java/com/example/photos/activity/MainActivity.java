package com.example.photos.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.photos.R;
import com.example.photos.adapter.PhotosAdapter;
import com.example.photos.model.Photo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
}
