package com.example.gohike;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import category.category;
import category.CategoryAdapter;
import gunung.gunung;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private GoogleSignInClient gClient;

    private Map<Integer, Runnable> navigationMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Toolbar dan Navigation Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inisialisasi avatar_icon
        ImageView avatarIcon = findViewById(R.id.avatar_icon);
        avatarIcon.setOnClickListener(v -> replaceFragment(new ProfilFragment()));



        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Setup Google Sign-In
        GoogleSignInOptions gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gClient = GoogleSignIn.getClient(this, gOptions);

        // Menginisialisasi map untuk navigasi
        navigationMap = new HashMap<>();
        navigationMap.put(R.id.info_gunung, () -> replaceFragment(new GunungFragment()));
        navigationMap.put(R.id.info_opentrip, () -> replaceFragment(new OpentripFragment()));
        navigationMap.put(R.id.Peralatan, () -> replaceFragment(new PeralatanFragment()));
        navigationMap.put(R.id.profil, () -> replaceFragment(new ProfilFragment()));
        navigationMap.put(R.id.logout, this::handleLogout);

        // RecyclerView untuk kategori
        RecyclerView rcvCategory = findViewById(R.id.rcvcategory);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryName -> {
            // Listener klik kategori
            if ("Gunung".equalsIgnoreCase(categoryName)) {
                replaceFragment(new GunungFragment());
            } else if ("Peralatan".equalsIgnoreCase(categoryName)) {
                replaceFragment(new PeralatanFragment());
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter.setData(getListCategory());
        rcvCategory.setAdapter(categoryAdapter);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter.setData(getListCategory());
        rcvCategory.setAdapter(categoryAdapter);


    }

    private List<category> getListCategory() {
        List<category> list = new ArrayList<>();

        List<gunung> gunungList = new ArrayList<>();
        gunungList.add(new gunung(R.drawable.bromo, "Gunung Bromo"));
        gunungList.add(new gunung(R.drawable.semeru, "Gunung Semeru"));
        gunungList.add(new gunung(R.drawable.rinjani, "Gunung Rinjani"));
        gunungList.add(new gunung(R.drawable.slamet, "Gunung Slamet"));
        gunungList.add(new gunung(R.drawable.merbabu, "Gunung Merapi"));
        gunungList.add(new gunung(R.drawable.dua, "Gunung Arjuno"));

        List<gunung> peralatanList = new ArrayList<>();
        peralatanList.add(new gunung(R.drawable.tenda, "Tenda"));
        peralatanList.add(new gunung(R.drawable.carier, "Tas Carier"));
        peralatanList.add(new gunung(R.drawable.kit, "First Aid Kit"));
        peralatanList.add(new gunung(R.drawable.senter, "Senter"));
        peralatanList.add(new gunung(R.drawable.nesting, "Nesting"));
        peralatanList.add(new gunung(R.drawable.propane, "Propane Stove"));

        list.add(new category("Gunung", gunungList));
        list.add(new category("Peralatan", peralatanList));

        return list;
    }

    private void handleLogout() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                    if (account != null) {
                        gClient.signOut().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(NavigationActivity.this, "Berhasil logout", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(NavigationActivity.this, "Gagal logout", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Runnable action = navigationMap.get(item.getItemId());
        if (action != null) {
            action.run();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}