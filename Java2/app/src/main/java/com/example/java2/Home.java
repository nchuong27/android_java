package com.example.java2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.java2.model.DatabaseHelper;
import com.example.java2.view.CartFragment;
import com.example.java2.view.DangNhap;
import com.example.java2.view.HistoryFragment;
import com.example.java2.view.HomeFragment;
import com.example.java2.view.NotFragment;
import com.example.java2.view.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    FloatingActionButton fag;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.databaseHelper = new DatabaseHelper(this);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_home) {
                    openFragment(new HomeFragment());
                    return true;
                } else if (itemId == R.id.bottom_favorite) {
                    openFragment(new HistoryFragment());
                    return true;

                } else if (itemId == R.id.bottom_notification) {
                    openFragment(new NotFragment());
                    return true;

                } else if (itemId == R.id.bottom_cart) {
                    openFragment(new CartFragment());
                    return true;
                }
                return false;
            }
        });
        fragmentManager = getSupportFragmentManager();
        openFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            openFragment(new HomeFragment());
        } else if (itemId == R.id.nav_profile) {
            openFragment(new ProfileFragment());
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_logout) {
            logout();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void logout() {
        if (this != null && databaseHelper != null) {
            databaseHelper.logout();
            Toast.makeText(getApplicationContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Lỗi: databaseHelper chưa được thiết lập", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, DangNhap.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
