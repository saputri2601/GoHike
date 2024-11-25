package com.example.gohike;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {

    // Declare views
    TextView detailTitle, detailNameLabel, detailName, detailAddressLabel, detailAddress, detailEmailLabel, detailEmail, detailAgeLabel, detailAge;
    Button deleteButton;

    String key = "";  // Firebase key to identify the data to be deleted

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail1);

        // Initialize views
        detailTitle = findViewById(R.id.detailTitle);
        detailNameLabel = findViewById(R.id.detailNameLabel);
        detailName = findViewById(R.id.detailName);
        detailAddressLabel = findViewById(R.id.detailAddressLabel);
        detailAddress = findViewById(R.id.detailAddress);
        detailEmailLabel = findViewById(R.id.detailEmailLabel);
        detailEmail = findViewById(R.id.detailEmail);
        detailAgeLabel = findViewById(R.id.detailAgeLabel);
        detailAge = findViewById(R.id.detailAge);
        deleteButton = findViewById(R.id.deleteButton);

        // Get data from the Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Set the data to the views
            detailTitle.setText("Detail Information");
            detailName.setText(bundle.getString("Name"));
            detailAddress.setText(bundle.getString("Address"));
            detailEmail.setText(bundle.getString("Email"));
            detailAge.setText(bundle.getString("Age"));
            key = bundle.getString("Key");  // Get the unique key for the item from Firebase
        }

        // Set up the delete button functionality
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get reference to Firebase Realtime Database
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Anggota");

                // Remove the data using the key
                reference.child(key).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(DetailActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DetailActivity.this, lihatAnggota.class));
                        finish();
                    } else {
                        Toast.makeText(DetailActivity.this, "Failed to Delete Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
