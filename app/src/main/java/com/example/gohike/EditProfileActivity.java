package com.example.gohike;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editEmail, editUsername, editPassword;
    Button saveButton;
    String nameUser, emailUser, usernameUser, passwordUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        // Firebase Realtime Database reference
        reference = FirebaseDatabase.getInstance().getReference("users");

        // Bind XML elements
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);

        // Show existing data
        showData();

        // Save button listener
        saveButton.setOnClickListener(view -> {
            if (validateInputs()) {
                if (isNameChanged() || isPasswordChanged() || isEmailChanged()) {
                    // After validation and change checks, update Firebase
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String newName = editName.getText().toString();
                        String newEmail = editEmail.getText().toString();
                        String newUsername = editUsername.getText().toString();
                        String newPassword = editPassword.getText().toString();

                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
                        userRef.child("name").setValue(newName);
                        userRef.child("email").setValue(newEmail);
                        userRef.child("username").setValue(newUsername);
                        userRef.child("password").setValue(newPassword);

                        Toast.makeText(EditProfileActivity.this, "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show();
                        navigateBackToProfile(); // Navigasi kembali ke ProfilFragment
                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "Tidak ada perubahan yang ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Validate inputs before checking for changes
    private boolean validateInputs() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Nama tidak boleh kosong");
            return false;
        }

        if (email.isEmpty()) {
            editEmail.setError("Email tidak boleh kosong");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Email tidak valid");
            return false;
        }

        if (password.isEmpty()) {
            editPassword.setError("Kata sandi tidak boleh kosong");
            return false;
        }

        if (password.length() < 6) {
            editPassword.setError("Kata sandi harus minimal 6 karakter");
            return false;
        }

        return true;
    }

    // Check if the name has changed and update if necessary
    private boolean isNameChanged() {
        if (!nameUser.equals(editName.getText().toString())) {
            reference.child(usernameUser).child("name").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    // Check if the email has changed and update if necessary
    private boolean isEmailChanged() {
        if (!emailUser.equals(editEmail.getText().toString())) {
            reference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    // Check if the password has changed and update if necessary
    private boolean isPasswordChanged() {
        if (!passwordUser.equals(editPassword.getText().toString())) {
            reference.child(usernameUser).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    // Show user data passed from previous activity
    public void showData() {
        Intent intent = getIntent();

        // Get data passed from ProfileActivity
        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        usernameUser = intent.getStringExtra("username");
        passwordUser = intent.getStringExtra("password");

        // Set the fields with the existing data
        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editUsername.setText(usernameUser);
        editPassword.setText(passwordUser);
    }

    // Navigate back to profile with updated data
    private void navigateBackToProfile() {
        // Send updated data back to ProfilFragment
        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", editName.getText().toString());
        resultIntent.putExtra("email", editEmail.getText().toString());
        resultIntent.putExtra("username", editUsername.getText().toString());
        resultIntent.putExtra("password", editPassword.getText().toString());

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
