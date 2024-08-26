package com.example.java3.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.java3.Dao.CartDao;
import com.example.java3.R;
import com.example.java3.model.CartItem;

public class BottomSheetDialogFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    private int userId;
    private int productId;
    private String productName;
    private int availableQuantity;
    private String productImage;
    private String selectedSize = "S";
    private View selectedSizeView;
    private TextView textViewQuantityLeft;
    private TextView textViewPrice;

    public static BottomSheetDialogFragment newInstance(int userId, int productId, String productName, String price, int availableQuantity, String productImage) {
        BottomSheetDialogFragment fragment = new BottomSheetDialogFragment();
        Bundle args = new Bundle();
        String cleanedPrice = price.replaceAll("[^\\d]", "");
        args.putInt("userId", userId);
        args.putInt("productId", productId);
        args.putDouble("price", Double.parseDouble(cleanedPrice));
        args.putString("priceString", price);
        args.putString("productName", productName);
        args.putInt("availableQuantity", availableQuantity);
        args.putString("productImage", productImage);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_options_bottom_sheet, container, false);

        textViewQuantityLeft = view.findViewById(R.id.textViewQuantityLeft);
        textViewPrice = view.findViewById(R.id.textViewTotalPrice);
        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);

        if (getArguments() != null) {
            userId = getArguments().getInt("userId");
            productId = getArguments().getInt("productId");
            double price = getArguments().getDouble("price");
            String priceString = getArguments().getString("priceString");
            productName = getArguments().getString("productName");
            availableQuantity = getArguments().getInt("availableQuantity");
            productImage = getArguments().getString("productImage");

            textViewQuantityLeft.setText(String.valueOf(availableQuantity));
            textViewPrice.setText(priceString);
        }

        buttonConfirm.setOnClickListener(v -> addToCart());

        view.findViewById(R.id.buttonSizeS).setOnClickListener(v -> selectSize("S", v));
        view.findViewById(R.id.buttonSizeM).setOnClickListener(v -> selectSize("M", v));
        view.findViewById(R.id.buttonSizeL).setOnClickListener(v -> selectSize("L", v));
        view.findViewById(R.id.buttonSizeXL).setOnClickListener(v -> selectSize("XL", v));

        return view;
    }

    private void addToCart() {
        CartDao cartDao = new CartDao(getContext());
        CartItem cartItem = new CartItem(0, productId, userId, 1, textViewPrice.getText().toString(), selectedSize, productName, productImage);
        cartDao.addCartItem(cartItem);
        Toast.makeText(getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();

        // Broadcast to update cart badge
        Intent intent = new Intent("UPDATE_CART_BADGE");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

        dismiss();
    }

    private void selectSize(String size, View view) {
        if (selectedSizeView != null) {
            selectedSizeView.setBackgroundResource(0);
        }
        selectedSize = size;
        selectedSizeView = view;
        view.setBackgroundResource(R.drawable.circle_selected);
    }
}