package com.example.java3.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.example.java3.R;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.databinding.ActivityProductDetailBinding;
import com.example.java3.model.Product;
import com.nex3z.notificationbadge.NotificationBadge;

public class ProductDetail extends AppCompatActivity {
    ActivityProductDetailBinding binding;
    private int userId;
    private DatabaseHelper databaseHelper;
    private NotificationBadge menuBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarProduct);
        databaseHelper = new DatabaseHelper(this);

        // Initialize NotificationBadge
        menuBadge = findViewById(R.id.menu_sl); // Ensure this ID exists in your layout

        // Fetch userId from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1); // Ensure key matches

        // Register BroadcastReceiver to update cart badge
        LocalBroadcastManager.getInstance(this).registerReceiver(cartUpdateReceiver, new IntentFilter("UPDATE_CART_BADGE"));

        // Initial update of cart badge
        updateCartBadge();

        Intent intent = getIntent();
        if (intent != null) {
            Product product = intent.getParcelableExtra("Product");

            if (product != null) {
                ImageView productImage = findViewById(R.id.productImage);
                TextView productName = findViewById(R.id.productName);
                TextView productPrice = findViewById(R.id.productPrice);
                TextView productDiscount = findViewById(R.id.txtProductDis);
                TextView productDes = findViewById(R.id.productDes);

                Glide.with(this).load(product.getImg_sp()).into(productImage);
                productName.setText(product.getName_sp());
                productPrice.setText(String.valueOf(product.getPrice_sp()) + " đ");
                productDiscount.setText("(-" + product.getGiam_gia() + "%)");
                productDes.setText(product.getDescription_sp());

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(product.getName_sp());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }

                binding.toolbarProduct.setNavigationOnClickListener(v -> onBackPressed());

                binding.addToCartButton.setOnClickListener(v -> showProductOptionsDialog(userId, product));
                binding.buyNowButton.setOnClickListener(v -> showProductOptionsDialog(userId, product));
            }
        }
        binding.Giohang.setOnClickListener(v -> navigateToCartFragment());
    }

    private void navigateToCartFragment() {
        CartFragment cartFragment = new CartFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, cartFragment); // R.id.fragment_container là ID của container trong MainActivity
        transaction.addToBackStack(null); // Để có thể quay lại màn hình trước đó
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(cartUpdateReceiver);
    }

    private void updateCartBadge() {
        // Fetch the cart count
        int cartCount = getCartItemCount();
        // Update the badge with the cart count
        if (cartCount > 0) {
            menuBadge.setVisibility(View.VISIBLE);
            menuBadge.setNumber(cartCount);
        } else {
            menuBadge.setVisibility(View.GONE);
        }
    }

    private int getCartItemCount() {
        // Get a reference to your SQLite database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Query to get the count of items in the cart
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

    private void showProductOptionsDialog(int userId, Product product) {
        String originalPrice = String.valueOf(product.getPrice_sp()) + " đ";
        int availableQuantity = product.getQuantity_sp();
        String productName = product.getName_sp();
        String productImage = product.getImg_sp();
        int productId = product.getId_sp(); // Get product ID from the product object

        BottomSheetDialogFragment dialog = BottomSheetDialogFragment.newInstance(userId, productId, productName, originalPrice, availableQuantity, productImage);
        dialog.show(getSupportFragmentManager(), "ProductOptionsBottomSheetFragment");
    }

    private final BroadcastReceiver cartUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateCartBadge();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
