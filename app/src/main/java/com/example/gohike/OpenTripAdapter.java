package com.example.gohike;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.content.ContextCompat;

import java.util.List;

public class OpenTripAdapter extends RecyclerView.Adapter<OpenTripAdapter.ViewHolder> {

    private Context context;
    private final List<data_class.OpenTrip> openTrips; // Use data_class.OpenTrip as the data model

    // Constructor with context for passing to intent
    public OpenTripAdapter(Context context, List<data_class.OpenTrip> openTrips) {
        this.context = context;
        this.openTrips = openTrips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating layout item untuk RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        data_class.OpenTrip openTrip = openTrips.get(position);

        // Mengisi data ke dalam elemen UI
        holder.mountainTitle.setText(openTrip.getTitle());
        holder.mountainDetails.setText(openTrip.getDuration());
        holder.mountainStatus.setText(openTrip.getStatus());

        // Ubah warna status berdasarkan nilai status
        if (openTrip.getStatus().equalsIgnoreCase("Tersedia")) {
            holder.mountainStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
            // Enable the "Create Team" button and restore its color
            holder.createTeamButton.setEnabled(true);
            holder.createTeamButton.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.brown));
        } else {
            holder.mountainStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
            // Disable the "Create Team" button and apply a faded gray color
            holder.createTeamButton.setEnabled(false);
            holder.createTeamButton.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.lavender_brown));
        }

        // Set actions for "Create Team" and "Join Team" buttons
        holder.createTeamButton.setOnClickListener(v -> {
            // Sending data to next activity with Intent
            Intent intent = new Intent(context, BuatTim.class);
            context.startActivity(intent);
        });

        holder.joinTeamButton.setOnClickListener(v -> {
            // Sending data to next activity with Intent
            Intent intent = new Intent(context, GabungTim.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return openTrips.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mountainTitle, mountainDetails, mountainStatus;
        Button createTeamButton, joinTeamButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mountainTitle = itemView.findViewById(R.id.mountainTitle);
            mountainDetails = itemView.findViewById(R.id.mountainDetails);
            mountainStatus = itemView.findViewById(R.id.mountainStatus);
            createTeamButton = itemView.findViewById(R.id.createTeamButton);
            joinTeamButton = itemView.findViewById(R.id.joinTeamButton);
        }
    }
}
