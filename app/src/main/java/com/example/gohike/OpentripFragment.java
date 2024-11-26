package com.example.gohike;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OpentripFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_opentrip, container, false);

        // Inisialisasi tombol Back
        ImageButton backButton = rootView.findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                Log.d("OpenTripFragment", "Tombol Back ditekan");
                // Navigasi kembali ke NavigationActivity
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
                requireActivity().finish(); // Menutup aktivitas saat ini
            });
        } else {
            Log.e("OpenTripFragment", "backButton tidak ditemukan di layout.");
        }

        // Inisialisasi RecyclerView dan daftar data
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Menyiapkan data contoh (ini bisa diganti dengan data dari database atau API)
        List<data_class.OpenTrip> openTrips = new ArrayList<>();
        openTrips.add(new data_class.OpenTrip("Gunung Semeru", "2 Hari 1 Malam", "Tersedia"));
        openTrips.add(new data_class.OpenTrip("Gunung Rinjani", "3 Hari 2 Malam", "Tidak Tersedia"));
        openTrips.add(new data_class.OpenTrip("Gunung Merapi", "1 Hari", "Tersedia"));

        // Mengatur adapter untuk RecyclerView
        OpenTripAdapter openTripAdapter = new OpenTripAdapter(getContext(), openTrips);
        recyclerView.setAdapter(openTripAdapter);

        return rootView;
    }
}
