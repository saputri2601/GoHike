package com.example.gohike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class lihatAnggota extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter_Opentrip anggotaAdapter;
    private ArrayList<data_class_uploaddata> anggotaList;
    private DatabaseReference anggotaRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihat_anggota);  // XML for View Members layout


        recyclerView = findViewById(R.id.recyclerView);

        // Setup Firebase reference to "Anggota" node
        anggotaRef = FirebaseDatabase.getInstance().getReference("Anggota");

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        anggotaList = new ArrayList<>();
        anggotaAdapter = new MyAdapter_Opentrip(anggotaList);
        recyclerView.setAdapter(anggotaAdapter);

        // Load members from Firebase
        anggotaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                anggotaList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    data_class_uploaddata anggota = snapshot.getValue(data_class_uploaddata.class);
                    anggotaList.add(anggota);
                }
                anggotaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

    }
}
