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
import com.example.java3.model.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }
    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtProductPrice, txtProductDiscount, txtProductRating, txtProductReviews;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            txtProductDiscount = itemView.findViewById(R.id.txtProductDis);
            txtProductRating = itemView.findViewById(R.id.txtProductRating);
            txtProductReviews = itemView.findViewById(R.id.txtProductReviews);
        }

        public void bind(Product product) {
            txtProductName.setText(product.getName_sp());
            txtProductPrice.setText(String.valueOf(product.getPrice_sp()) + " đ");
            txtProductDiscount.setText("(-" + product.getGiam_gia() + "%)");
            txtProductRating.setText(String.valueOf(product.getRating()));
            txtProductReviews.setText("(" + product.getReviewCount() + " reviews)");

            Glide.with(context).load(product.getImg_sp()).into(imgProduct);
        }
    }

    public void updateData(List<Product> newProductList) {
        productList.clear();
        productList.addAll(newProductList);
        notifyDataSetChanged();
    }
}
