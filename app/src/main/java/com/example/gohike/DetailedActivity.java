package com.example.gohike;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gohike.databinding.ActivityDetailBinding;

public class DetailedActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menggunakan View Binding
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mendapatkan data dari Intent
        Intent intent = this.getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String pembawaan = intent.getStringExtra("pembawaan");
            String pengertian = intent.getStringExtra("pengertian");
            int image = intent.getIntExtra("image", R.drawable.carier); // Default image jika tidak ada

            // Menampilkan data ke UI
            binding.detailName.setText(name);
            binding.detailPembawaan.setText(pembawaan);
            binding.detailPengertian.setText(pengertian);
            binding.detailImage.setImageResource(image);
        }
    }
}
