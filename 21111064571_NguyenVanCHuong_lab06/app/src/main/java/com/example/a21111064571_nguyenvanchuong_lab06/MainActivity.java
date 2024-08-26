package com.example.a21111064571_nguyenvanchuong_lab06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        drawer = findViewById(R.id.layout_drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_chucnag1) {
                    Toast.makeText(MainActivity.this, "Bạn chọn chức năng 1", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (itemId == R.id.nav_chucnag2) {
                    Toast.makeText(MainActivity.this, "Bạn chọn chức năng 2", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (itemId == R.id.nav_chucnag3) {
                    Toast.makeText(MainActivity.this, "Bạn chọn chức năng 3", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

    }
}