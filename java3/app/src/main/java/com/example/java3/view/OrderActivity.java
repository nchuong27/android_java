package com.example.java3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.java3.R;
import com.example.java3.adapter.OrderAdapter;
import com.example.java3.model.CartItem;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Lấy RecyclerView và TextView từ layout
        RecyclerView recyclerViewOrderItems = findViewById(R.id.recyclerViewOrderItems);
        TextView textViewProductCount = findViewById(R.id.textViewProduct);
        TextView textViewTotalPrice = findViewById(R.id.textViewPrice);
        TextView textViewGia = findViewById(R.id.textViewGia);
        TextView textViewTotalPriceBeforeDiscount = findViewById(R.id.textViewTotalPriceBeforeDiscount);

        // Nhận dữ liệu từ Intent
        ArrayList<CartItem> selectedItems = getIntent().getParcelableArrayListExtra("selectedItems");

        // Thiết lập RecyclerView với dữ liệu
        OrderAdapter orderAdapter = new OrderAdapter(this, selectedItems);
        recyclerViewOrderItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrderItems.setAdapter(orderAdapter);

        // Cập nhật thông tin tổng số lượng sản phẩm và giá tiền
        updatePriceSummary(selectedItems, textViewProductCount, textViewTotalPrice,textViewGia,textViewTotalPriceBeforeDiscount);
    }

    private void updatePriceSummary(ArrayList<CartItem> selectedItems, TextView textViewProductCount, TextView textViewTotalPrice,TextView textViewGia,TextView textViewTotalPriceBeforeDiscount) {
        int totalCount = 0;
        double totalPrice = 0.0;

        for (CartItem item : selectedItems) {
            totalCount += item.getQuantity(); // Cộng dồn số lượng sản phẩm
            totalPrice += item.getPriceAsDouble() * item.getQuantity(); // Tính tổng giá tiền
        }

        // Cập nhật số lượng sản phẩm và giá tiền vào các TextView
        textViewProductCount.setText(String.format("%d sản phẩm", totalCount));
        textViewTotalPrice.setText(String.format("%,.0f đ", totalPrice));
        textViewGia.setText(String.format("%,.0f đ", totalPrice));
        textViewTotalPriceBeforeDiscount.setText(String.format("%,.0f đ", totalPrice));
    }
}
