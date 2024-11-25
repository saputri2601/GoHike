package com.example.gohike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupName, signupUsername, signupEmail, signupPassword, signupConfirmPassword;
    private Button signupButton;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        // Inisialisasi komponen tampilan
        signupName = findViewById(R.id.signup_name);
        signupUsername = findViewById(R.id.signup_username);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupConfirmPassword = findViewById(R.id.signup_confirm_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupName.getText().toString().trim();
                String username = signupUsername.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String confirmPassword = signupConfirmPassword.getText().toString().trim();

                // Validasi untuk field yang kosong
                if (name.isEmpty()) {
                    signupName.setError("Nama tidak boleh kosong");
                } else if (username.isEmpty()) {
                    signupUsername.setError("Username tidak boleh kosong");
                } else if (email.isEmpty()) {
                    signupEmail.setError("Email tidak boleh kosong");
                } else if (password.isEmpty()) {
                    signupPassword.setError("Kata sandi tidak boleh kosong");
                } else if (password.length() < 6) {
                    signupPassword.setError("Kata sandi harus terdiri dari minimal 6 karakter");
                } else if (confirmPassword.isEmpty()) {
                    signupConfirmPassword.setError("Konfirmasi kata sandi diperlukan");
                } else if (!password.equals(confirmPassword)) {
                    signupConfirmPassword.setError("Kata sandi tidak cocok");
                } else {
                    // Registrasi pengguna menggunakan Firebase Auth
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Dapatkan UID pengguna saat ini
                                String userId = auth.getCurrentUser().getUid();

                                // Simpan data pengguna tambahan di Realtime Database
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                                HelperClass user = new HelperClass(name, email, username, password); // Anggap HelperClass adalah model

                                // Simpan data pengguna dengan UID sebagai kunci
                                reference.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUpActivity.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
                                            // Alihkan ke LoginActivity setelah pendaftaran berhasil
                                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                            finish();  // Tutup SignUpActivity
                                        } else {
                                            Toast.makeText(SignUpActivity.this, "Gagal menyimpan data pengguna: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(SignUpActivity.this, "Pendaftaran gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // Tangani pengalihan ke layar login
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Alihkan ke LoginActivity
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
