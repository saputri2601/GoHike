package com.example.gohike;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityDetailGunung extends AppCompatActivity {

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gunung);

        // Initialize ExecutorService for background tasks
        executorService = Executors.newSingleThreadExecutor();

        // Get the mountain name from the intent
        String namaGunung = getIntent().getStringExtra("NAMA_GUNUNG");

        // Run the background task to fetch the mountain details
        fetchGunungDetail(namaGunung);
    }

    private void fetchGunungDetail(String namaGunung) {
        executorService.execute(() -> {
            // Initialize ApiService
            ApiService apiService = new ApiService();

            // Get mountain details in the background thread
            GunungResponse.NamaGunung gunungDetail = apiService.getGunungByNama(namaGunung);

            // Update UI after background task completion
            runOnUiThread(() -> {
                if (gunungDetail != null) {
                    // Display data in UI
                    TextView namaTextView = findViewById(R.id.detailGunungName);
                    TextView lokasiTextView = findViewById(R.id.detailLokasi);
                    TextView deskripsiTextView = findViewById(R.id.detailDeskripsi);
                    TextView jalurPendakianTextView = findViewById(R.id.tvJalurGunung);
                    TextView infoGunungTextView = findViewById(R.id.tvInfoGunung);
                    ImageView imageView = findViewById(R.id.detailImage);

                    // Set data to UI elements
                    namaTextView.setText(gunungDetail.getNama());
                    lokasiTextView.setText(gunungDetail.getLokasi());
                    deskripsiTextView.setText(gunungDetail.getDeskripsi());
                    jalurPendakianTextView.setText(gunungDetail.getJalurPendakian());
                    infoGunungTextView.setText(gunungDetail.getInfoGunung());

                    // Load image with Glide
                    String imageUrl = gunungDetail.getImageGunung();
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Glide.with(ActivityDetailGunung.this)
                                .load(imageUrl)
                                .into(imageView);
                    }
                } else {
                    // If mountain data is not found
                    Toast.makeText(ActivityDetailGunung.this, "Detail gunung tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up the executor service
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
