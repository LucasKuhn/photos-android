package com.example.photos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photos.R;
import com.example.photos.model.Photo;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private final List<String> elements;
    Context context;

    public PhotosAdapter(List<String> elements, Context context) {
        this.elements = elements;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.line,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.setData(elements.get(position));
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtName = itemView.findViewById(R.id.txtName);
        }

        private void setData(String name) {
            txtName.setText(name);
        }

        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Você clicou aqui", Toast.LENGTH_LONG).show();
        }

    }
}
