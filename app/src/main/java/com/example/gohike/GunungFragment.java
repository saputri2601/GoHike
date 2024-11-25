package com.example.gohike;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GunungFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gunungfragment, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.rvLokasi);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Mengganti data yang sebelumnya menggunakan API dengan data statis
        List<ProvinsiResponse> provinsiList = getProvinsiList();

        if (!provinsiList.isEmpty()) {
            ProvinsiAdapter provinsiAdapter = new ProvinsiAdapter(provinsiList);
            recyclerView.setAdapter(provinsiAdapter);

            provinsiAdapter.setOnItemClickListener((provinsi) -> {
                Intent intent = new Intent(getContext(), ActivityListGunung.class);
                intent.putExtra("lokasi", provinsi.getNama()); // Mengirimkan nama provinsi yang dipilih
                startActivity(intent);
            });
        } else {
            Toast.makeText(getContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    // Data statis yang akan ditampilkan
    private List<ProvinsiResponse> getProvinsiList() {
        List<ProvinsiResponse> provinsiList = new ArrayList<>();
        provinsiList.add(new ProvinsiResponse("Jawa Barat", R.drawable.ic_jabar));
        provinsiList.add(new ProvinsiResponse("Jawa Tengah", R.drawable.ic_jateng));
        provinsiList.add(new ProvinsiResponse("Jawa Timur", R.drawable.ic_jatim));
        provinsiList.add(new ProvinsiResponse("Luar Jawa", R.drawable.ic_luar_jawa));
        // Tambahkan lebih banyak provinsi sesuai kebutuhan
        return provinsiList;
    }
}
