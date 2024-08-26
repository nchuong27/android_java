package com.example.java2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.java2.R;
import com.example.java2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private Button btnbatdau;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        btnbatdau = findViewById(R.id.btnbatdau);
        btnbatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToDangNhapActivity();
            }
        });
    }
    private void navigateToDangNhapActivity() {
        Intent intent = new Intent(MainActivity.this, DangNhap.class);
        startActivity(intent);
    }
}