package com.example.gohike;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityListGunung extends AppCompatActivity {

    private RecyclerView recyclerView;

    private GunungAdapter gunungAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gunung);

        // Inisialisasi TextView untuk menampilkan lokasi
        TextView lokasiPilihanTextView = findViewById(R.id.lokasiPilihan);


        recyclerView = findViewById(R.id.recyclerView);
        // Mengatur GridLayoutManager dengan dua kolom
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);


        // Mendapatkan lokasi dari Intent
        String lokasi = getIntent().getStringExtra("lokasi");

        // Menampilkan lokasi yang dipilih
        if (lokasi != null) {
            lokasiPilihanTextView.setText(getString(R.string.lokasi, lokasi));
        } else {
            lokasiPilihanTextView.setText(R.string.lokasi_tidak_ditemukan);
        }

        // Inisialisasi ApiService untuk mengambil data
        ApiService apiService = new ApiService();

        // Menjalankan pengambilan data di thread terpisah
        new Thread(() -> {
            List<GunungResponse.NamaGunung> gunungList = apiService.fetchGunungByLocation(lokasi);
            runOnUiThread(() -> {
                if (gunungList != null && !gunungList.isEmpty()) {
                    gunungAdapter = new GunungAdapter(gunungList, this);
                    recyclerView.setAdapter(gunungAdapter);
                } else {
                    Toast.makeText(ActivityListGunung.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
