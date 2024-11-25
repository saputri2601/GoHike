package com.example.gohike;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuatTim extends AppCompatActivity {

    // Declare views
    private ImageButton backButton;
    private TextView titleCreateTeam;
    private EditText inputTeamName;
    private Button addMemberButton, viewMembersButton, saveButton;

    private DatabaseReference teamRef;  // Firebase reference to store the team data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buat_tim);  // This should be the XML layout provided above

        // Initialize views
        backButton = findViewById(R.id.backButton);
        titleCreateTeam = findViewById(R.id.titleCreateTeam);
        inputTeamName = findViewById(R.id.inputTeamName);
        addMemberButton = findViewById(R.id.addMemberButton);
        viewMembersButton = findViewById(R.id.viewMembersButton);
        saveButton = findViewById(R.id.saveButton);

        // Firebase reference to "Tim" node
        teamRef = FirebaseDatabase.getInstance().getReference("Tim");

        // Set back button click listener
        backButton.setOnClickListener(v -> {
            // Go back to previous activity
            onBackPressed();
        });

        // Add member button logic
        addMemberButton.setOnClickListener(v -> {
            // Open Add Member Activity
            Intent addMemberIntent = new Intent(BuatTim.this, TambahAnggota.class);
            startActivity(addMemberIntent);
        });

        if (viewMembersButton != null) {
            viewMembersButton.setOnClickListener(v -> {
                Intent viewMembersIntent = new Intent(BuatTim.this, lihatAnggota.class);
                startActivity(viewMembersIntent);
            });
        } else {
            Log.e("BuatTim", "viewMembersButton is null");

        }
    }
}
