package com.example.java3.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.java3.Dao.CartDao;
import com.example.java3.R;
import com.example.java3.model.CartItem;
import com.example.java3.view.ProductOptionsBottomSheetFragment;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {

    private Context context;
    private List<CartItem> cartItemList;
    private Runnable updateTotalPriceCallback;
    private CartDao cartDao;

    public CartAdapter(Context context, List<CartItem> cartItemList, Runnable updateTotalPriceCallback) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.cartDao = new CartDao(context);
        this.updateTotalPriceCallback = updateTotalPriceCallback;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);
        holder.textViewName.setText(item.getProductName());
        holder.textViewPrice.setText(formatPrice(item.getPrice(), item.getQuantity()));
        holder.textViewQuantity.setText(String.valueOf(item.getQuantity()));
        holder.textViewSize.setText(item.getSize());
        holder.checkBoxSelect.setChecked(item.isSelected());
        Glide.with(context).load(item.getProductImage()).into(holder.imageViewProduct);

        holder.checkBoxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setSelected(isChecked);
            if (updateTotalPriceCallback != null) {
                updateTotalPriceCallback.run();
            }
        });

        holder.buttonPlus.setOnClickListener(v -> {
            int currentQuantity = item.getQuantity();
            item.setQuantity(currentQuantity + 1);
            holder.textViewQuantity.setText(String.valueOf(item.getQuantity()));
            holder.textViewPrice.setText(formatPrice(item.getPrice(), item.getQuantity()));
            if (updateTotalPriceCallback != null) {
                updateTotalPriceCallback.run();
            }
        });

        holder.buttonMinus.setOnClickListener(v -> {
            int currentQuantity = item.getQuantity();
            if (currentQuantity > 1) {
                item.setQuantity(currentQuantity - 1);
                holder.textViewQuantity.setText(String.valueOf(item.getQuantity()));
                holder.textViewPrice.setText(formatPrice(item.getPrice(), item.getQuantity()));
                if (updateTotalPriceCallback != null) {
                    updateTotalPriceCallback.run();
                }
            }
        });

        holder.itemView.setOnClickListener(v -> {
            ProductOptionsBottomSheetFragment fragment = new ProductOptionsBottomSheetFragment(item, newSize -> {
                item.setSize(newSize);
                cartDao.updateCartItem(item); // Update size in database
                holder.textViewSize.setText(newSize); // Update UI
                if (updateTotalPriceCallback != null) {
                    updateTotalPriceCallback.run(); // Update total price
                }
            });
            fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "ProductOptionsBottomSheet");
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public void showDeleteAction(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận xoá sản phẩm")
                .setMessage("Bạn có chắc muốn xoá sản phẩm khỏi giỏ")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    removeItem(position);
                })
                .setNegativeButton("Quay lại", (dialog, which) -> notifyItemChanged(position))
                .show();
    }

    public void removeItem(int position) {
        CartItem item = cartItemList.get(position);
        cartDao.removeCartItem(item.getId()); // Remove from database using ID
        cartItemList.remove(position); // Remove from list
        notifyItemRemoved(position); // Notify adapter
        Toast.makeText(context, "Đã xoá sản phẩm", Toast.LENGTH_SHORT).show();
        if (updateTotalPriceCallback != null) {
            updateTotalPriceCallback.run(); // Update total price
        }
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxSelect;
        ImageView imageViewProduct;
        TextView textViewName;
        TextView textViewPrice;
        TextView textViewQuantity;
        TextView textViewSize;
        View buttonPlus;
        View buttonMinus;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxSelect = itemView.findViewById(R.id.checkBoxSelect);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewSize = itemView.findViewById(R.id.textViewSize);
            buttonPlus = itemView.findViewById(R.id.buttonPlus);
            buttonMinus = itemView.findViewById(R.id.buttonMinus);
        }
    }

    private String formatPrice(String price, int quantity) {
        String cleanedPrice = price.replaceAll("[^\\d]", "");
        double itemPrice;
        try {
            itemPrice = Double.parseDouble(cleanedPrice);
        } catch (NumberFormatException e) {
            itemPrice = 0.0;
            e.printStackTrace();
        }
        double totalPrice = itemPrice * quantity;
        DecimalFormat formatter = new DecimalFormat("#,### đ");
        return formatter.format(totalPrice);
    }
}
