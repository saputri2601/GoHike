package com.example.gohike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahAnggota extends AppCompatActivity {

    private EditText inputName, inputAddress, inputEmail, inputAge;
    private DatabaseReference anggotaRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_anggota);  // XML for Add Member layout

        // Initialize views
        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputEmail = findViewById(R.id.inputEmail);
        inputAge = findViewById(R.id.inputAge);
        Button saveButton = findViewById(R.id.saveButton);

        // Firebase reference to "Anggota" node
        anggotaRef = FirebaseDatabase.getInstance().getReference("Anggota");

        // Save button logic
        saveButton.setOnClickListener(v -> {
            // Get member data from input fields
            String name = inputName.getText().toString().trim();
            String address = inputAddress.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String age = inputAge.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || email.isEmpty() || age.isEmpty()) {
                Toast.makeText(TambahAnggota.this, "Semua data harus diisi", Toast.LENGTH_SHORT).show();
            } else {
                // Create a new Anggota object and save it to Firebase
                String key = anggotaRef.push().getKey();  // Generate unique key for the member
                if (key != null) {
                    data_class_uploaddata anggota = new data_class_uploaddata(name, address, email, age);
                    anggotaRef.child(key).setValue(anggota)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(TambahAnggota.this, "Anggota berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(TambahAnggota.this, lihatAnggota.class));
                                    finish();
                                } else {
                                    Toast.makeText(TambahAnggota.this, "Gagal menambahkan anggota", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
