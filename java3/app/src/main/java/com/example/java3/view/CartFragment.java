package com.example.java3.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java3.R;
import com.example.java3.SwipeToDeleteCallback;
import com.example.java3.adapter.CartAdapter;
import com.example.java3.model.CartItem;
import com.example.java3.Dao.CartDao;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerViewCart;
    private CartAdapter cartItemAdapter;
    private List<CartItem> cartItemList;
    private CartDao cartDao;
    private CheckBox checkBoxSelectAll;
    private TextView textViewTotalPrice;
    private TextView buttonBuyNow;
    private Toolbar toolbarCart;
    private int currentUserId; // Di chuyển khai báo biến ra ngoài hàm

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Khởi tạo Toolbar
        toolbarCart = view.findViewById(R.id.toolbarCart);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarCart);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Giỏ Hàng");
            }
        }

        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);
        checkBoxSelectAll = view.findViewById(R.id.checkBoxSelectAll);
        textViewTotalPrice = view.findViewById(R.id.textViewTotalPrice);
        buttonBuyNow = view.findViewById(R.id.buttonBuyNow);

        // Khởi tạo CartDao và lấy ID người dùng sau khi Fragment đã được gắn vào Activity
        cartDao = new CartDao(requireContext());
        currentUserId = getCurrentUserId();
        cartItemList = cartDao.getCartItemsForUser(currentUserId);

        cartItemAdapter = new CartAdapter(requireContext(), cartItemList, this::updateTotalPrice);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewCart.setAdapter(cartItemAdapter);

        // Áp dụng ItemTouchHelper
        Drawable deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_delete_outline_24);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(cartItemAdapter, deleteIcon));
        itemTouchHelper.attachToRecyclerView(recyclerViewCart);

        // Cập nhật tổng giá tiền ban đầu
        updateTotalPrice();

        // Lắng nghe sự thay đổi trạng thái của CheckBox "Select All"
        checkBoxSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (CartItem item : cartItemList) {
                item.setSelected(isChecked);
            }
            cartItemAdapter.notifyDataSetChanged();
            updateTotalPrice();
        });

        // Lắng nghe sự kiện bấm nút Checkout
        buttonBuyNow.setOnClickListener(v -> handleCheckout());

        return view;
    }

    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : cartItemList) {
            if (item.isSelected()) {
                double itemPrice = item.getPriceAsDouble();
                int itemQuantity = item.getQuantity();
                totalPrice += itemPrice * itemQuantity;
            }
        }
        textViewTotalPrice.setText(String.format("%,.0f đ", totalPrice));
    }

    private int getCurrentUserId() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }

    // Hàm xử lý hành động thanh toán
    private void handleCheckout() {
        ArrayList<CartItem> selectedItems = new ArrayList<>();
        for (CartItem item : cartItemList) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }

        if (!selectedItems.isEmpty()) {
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            intent.putParcelableArrayListExtra("selectedItems", selectedItems);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Vui lòng chọn sản phẩm để mua.", Toast.LENGTH_SHORT).show();
        }
    }
}

