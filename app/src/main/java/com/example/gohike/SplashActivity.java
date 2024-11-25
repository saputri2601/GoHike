package com.example.gohike;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menampilkan layout splash screen
        setContentView(R.layout.activity_splash);

        // Menemukan tombol "Lanjut ke Sign Up"
        Button exploreButton = findViewById(R.id.exploreButton);

        // Set listener untuk tombol yang akan membawa ke SignUpActivity
        exploreButton.setOnClickListener(v -> {
            // Pindah ke SignUpActivity setelah tombol ditekan
            Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish(); // Menutup SplashActivity agar tidak kembali ke splash screen
        });
    }
}
