package com.example.java3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.java3.R;
import com.example.java3.databinding.ActivityAdminBinding;

public class Admin extends AppCompatActivity {
    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new CatagoryFagment());
        binding.bottomNavigatinView.setBackground(null);

        binding.bottomNavigatinView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.setting) {
                replaceFragment(new Setting());
            } else if (itemId == R.id.category) {
                replaceFragment(new CatagoryFagment());
            } else if (itemId == R.id.product) {
                replaceFragment(new ProductFagment());
            } else if (itemId == R.id.order) {
                replaceFragment(new OrderFagment());
            } else {
                return false;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
