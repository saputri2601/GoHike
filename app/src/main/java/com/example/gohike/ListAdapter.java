package com.example.gohike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListData> {

    public ListAdapter(@NonNull Context context, ArrayList<ListData> dataArrayList) {
        super(context, R.layout.list_item, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the current item from the data list
        ListData listData = getItem(position);

        // Inflate the view if not already created
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Bind the view components to variables
        ImageView listImage = convertView.findViewById(R.id.listImage);
        TextView listName = convertView.findViewById(R.id.listName);
        TextView listPembawaan = convertView.findViewById(R.id.listPembawaan);

        // Set the data from the ListData object to the corresponding views
        if (listData != null) {
            listImage.setImageResource(listData.image);
            listName.setText(listData.name);
            listPembawaan.setText(listData.pembawaan);
        }

        // Return the completed view for rendering
        return convertView;
    }
}
