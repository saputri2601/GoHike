package com.example.gohike;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class OpentripFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_opentrip, container, false);

        // Initialize RecyclerView and data list
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Prepare some sample data (this could be from a database or API)
        List<data_class.OpenTrip> openTrips = new ArrayList<>();
        openTrips.add(new data_class.OpenTrip("Gunung Semeru", "2 Hari 1 Malam", "Tersedia"));
        openTrips.add(new data_class.OpenTrip("Gunung Rinjani", "3 Hari 2 Malam", "Tidak Tersedia"));
        openTrips.add(new data_class.OpenTrip("Gunung Merapi", "1 Hari", "Tersedia"));

        // Set the adapter to RecyclerView
        OpenTripAdapter openTripAdapter = new OpenTripAdapter(getContext(), openTrips);
        recyclerView.setAdapter(openTripAdapter);

        return rootView;
    }
}
