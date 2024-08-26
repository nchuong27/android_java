package com.example.java3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.java3.R;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.databinding.ActivityHomeBinding;
import com.google.android.material.badge.BadgeDrawable;

public class Home extends AppCompatActivity {
    ActivityHomeBinding binding;
    private BadgeDrawable badgeDrawable;
    private DatabaseHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        replaceFragment(new HomeFragment());
        binding.bottomNavigatinView.setBackground(null);
        badgeDrawable = binding.bottomNavigatinView.getOrCreateBadge(R.id.cart);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);
        // Hiển thị số lượng sản phẩm trong giỏ hàng ban đầu
        updateCartBadge();

        // Đăng ký BroadcastReceiver để cập nhật badge khi có thay đổi giỏ hàng
        LocalBroadcastManager.getInstance(this).registerReceiver(cartUpdateReceiver, new IntentFilter("UPDATE_CART_BADGE"));


        binding.bottomNavigatinView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.history) {
                replaceFragment(new HistoryFragment());
            } else if (itemId == R.id.cart) {
                replaceFragment(new CartFragment());
            } else if (itemId == R.id.acount) {
                replaceFragment(new AcountFragment());
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
    private void updateCartBadge() {
        // Lấy số lượng sản phẩm trong giỏ hàng
        int cartCount = getCartItemCount();

        if (cartCount > 0) {
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(cartCount);
        } else {
            badgeDrawable.setVisible(false);
        }
    }

    private int getCartItemCount() {
        // Lấy dữ liệu từ SQLite để đếm số lượng sản phẩm trong giỏ hàng
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String countQuery = "SELECT COUNT(*) FROM Cart WHERE UserID = ?";
        Cursor cursor = db.rawQuery(countQuery, new String[]{String.valueOf(userId)});

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return count;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(cartUpdateReceiver);
    }

    // BroadcastReceiver để cập nhật badge khi có thay đổi giỏ hàng
    private final BroadcastReceiver cartUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Cập nhật badge khi nhận được broadcast
            updateCartBadge();
        }
    };
}