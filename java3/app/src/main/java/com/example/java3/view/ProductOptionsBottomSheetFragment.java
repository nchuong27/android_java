package com.example.java3.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.java3.R;
import com.example.java3.model.CartItem;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductOptionsBottomSheetFragment extends BottomSheetDialogFragment {

    private CartItem cartItem;
    private ProductOptionListener listener;
    private String selectedSize = null; // To store the selected size

    public interface ProductOptionListener {
        void onSizeSelected(String newSize);
    }

    public ProductOptionsBottomSheetFragment(CartItem cartItem, ProductOptionListener listener) {
        this.cartItem = cartItem;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_options, container, false);

        Button buttonSizeS = view.findViewById(R.id.buttonSizeS);
        Button buttonSizeM = view.findViewById(R.id.buttonSizeM);
        Button buttonSizeL = view.findViewById(R.id.buttonSizeL);
        Button buttonSizeXL = view.findViewById(R.id.buttonSizeXL);
        TextView textViewTotalPrice = view.findViewById(R.id.textViewTotalPrice);
        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);

        // Setup size buttons
        String[] sizes = new String[]{"S", "M", "L", "XL"};
        Button[] sizeButtons = new Button[]{buttonSizeS, buttonSizeM, buttonSizeL, buttonSizeXL};

        // Initialize the selected size
        for (int i = 0; i < sizes.length; i++) {
            String size = sizes[i];
            Button button = sizeButtons[i];
            button.setOnClickListener(v -> selectSize(size, button));
        }

        // Convert price from String to double and format it
        String priceString = cartItem.getPrice();
        double price = parsePrice(priceString);
        double totalPrice = price * cartItem.getQuantity();

        DecimalFormat formatter = new DecimalFormat("#,### đ");
        String formattedTotalPrice = formatter.format(totalPrice);

        textViewTotalPrice.setText(formattedTotalPrice);

        buttonConfirm.setOnClickListener(v -> {
            if (selectedSize != null && listener != null) {
                listener.onSizeSelected(selectedSize); // Pass the selected size
            }
            dismiss();
        });

        return view;
    }

    private double parsePrice(String priceString) {
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        try {
            Number number = format.parse(priceString.replace(" đ", "").replace(".", "").trim());
            return number != null ? number.doubleValue() : 0.0;
        } catch (ParseException e) {
            return 0.0;
        }
    }

    private void selectSize(String size, Button button) {
        // Unselect the previously selected size
        View view = getView();
        if (view != null) {
            int[] buttonIds = {R.id.buttonSizeS, R.id.buttonSizeM, R.id.buttonSizeL, R.id.buttonSizeXL};
            for (int id : buttonIds) {
                Button b = view.findViewById(id);
                b.setBackgroundResource(R.color.white); // Reset background
            }
        }
        // Select the new size
        selectedSize = size;
        button.setBackgroundResource(R.drawable.circle_selected); // Set selected background
    }
}
