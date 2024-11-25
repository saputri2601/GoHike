package com.example.gohike;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfilFragment extends Fragment {

    private TextView profileName, profileEmail, profileUsername, profilePassword;
    private TextView titleName, titleUsername;
    private Button editProfile;
    private ImageButton backButton; // Tambahkan variabel untuk backButton
    private FirebaseAuth mAuth;

    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        showAllUserData(); // Muat ulang data dari Firebase
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Inisialisasi tampilan
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileUsername = view.findViewById(R.id.profileUsername);
        profilePassword = view.findViewById(R.id.profilePassword);
        titleName = view.findViewById(R.id.titleName);
        titleUsername = view.findViewById(R.id.titleUsername);
        editProfile = view.findViewById(R.id.editButton);
        backButton = view.findViewById(R.id.backButton); // Inisialisasi backButton

        // Tampilkan data pengguna
        showAllUserData();

        // Tombol Edit Profil
        editProfile.setOnClickListener(view1 -> {
            Log.d("ProfilFragment", "Tombol Edit Profil ditekan");
            passUserData();
        });

        // Tombol Back
        backButton.setOnClickListener(v -> {
            Log.d("ProfilFragment", "Tombol Back ditekan");
            Intent intent = new Intent(getActivity(), NavigationActivity.class);
            startActivity(intent);
            requireActivity().finish(); // Opsional: menutup aktivitas fragment saat ini
        });
    }

    // Menampilkan data pengguna dari Firebase
    public void showAllUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            Log.d("ProfilFragment", "UID Pengguna Saat Ini: " + userId);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            Query userQuery = reference.child(userId);

            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String nameFromDB = snapshot.child("name").getValue(String.class);
                        String emailFromDB = snapshot.child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child("username").getValue(String.class);
                        String passwordFromDB = snapshot.child("password").getValue(String.class);

                        titleName.setText(nameFromDB);
                        titleUsername.setText(usernameFromDB);
                        profileName.setText(nameFromDB);
                        profileEmail.setText(emailFromDB);
                        profileUsername.setText(usernameFromDB);
                        profilePassword.setText(passwordFromDB);
                    } else {
                        Log.e("ProfilFragment", "Data pengguna tidak ditemukan di database.");
                        Toast.makeText(getContext(), "Data pengguna tidak ditemukan.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("ProfilFragment", "Kesalahan Database: " + error.getMessage());
                    Toast.makeText(getContext(), "Terjadi kesalahan saat mengakses database.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e("ProfilFragment", "Tidak ada pengguna yang masuk.");
            Toast.makeText(getContext(), "Anda belum masuk. Silakan login terlebih dahulu.", Toast.LENGTH_SHORT).show();
        }
    }

    // Mengirim data pengguna ke EditProfileActivity
    private void passUserData() {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        intent.putExtra("name", profileName.getText().toString());
        intent.putExtra("email", profileEmail.getText().toString());
        intent.putExtra("username", profileUsername.getText().toString());
        intent.putExtra("password", profilePassword.getText().toString());
        startActivityForResult(intent, 1); // Meminta hasil dari EditProfileActivity
    }

    // Menangani hasil dari EditProfileActivity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String email = data.getStringExtra("email");
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");

            // Perbarui tampilan di ProfilFragment
            profileName.setText(name);
            profileEmail.setText(email);
            profileUsername.setText(username);
            profilePassword.setText(password);
        }
    }
}