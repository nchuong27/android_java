package com.example.java3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.java3.R;
import com.example.java3.model.CartItem;

import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<CartItem> orderItems;

    public OrderAdapter(Context context, List<CartItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        CartItem orderItem = orderItems.get(position);
        holder.bind(orderItem);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewProductName;
        private TextView textViewProductSize;
        private TextView textViewProductQuantity;
        private TextView textViewProductPrice;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewName);
            textViewProductSize = itemView.findViewById(R.id.textViewSize);
            textViewProductQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewProductPrice = itemView.findViewById(R.id.textViewPrice);
        }

        public void bind(CartItem orderItem) {
            textViewProductName.setText(orderItem.getProductName());
            textViewProductSize.setText(orderItem.getSize());
            textViewProductQuantity.setText("x" + orderItem.getQuantity());

            // Định dạng giá và hiển thị
            double itemPrice = orderItem.getPriceAsDouble();
            int itemQuantity = orderItem.getQuantity();
            double totalPrice = itemPrice * itemQuantity;
            textViewProductPrice.setText(String.format("%,.0f đ", totalPrice));

            // Sử dụng Glide để tải ảnh sản phẩm
            Glide.with(context)
                    .load(orderItem.getProductImage())
                    .placeholder(R.drawable.uploadimg) // Hình ảnh thay thế khi đang tải
                    .into(imageViewProduct);
        }
    }
}
