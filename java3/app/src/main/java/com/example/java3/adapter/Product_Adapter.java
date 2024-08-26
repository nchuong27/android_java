package com.example.java3.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.java3.Dao.Product_Dao;
import com.example.java3.R;
import com.example.java3.model.Product;
import com.example.java3.view.ProductFagment;
import com.example.java3.view.UpdateProduct;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.ProductViewHolder> {
    private List<Product> productList;
    private Product_Dao productDao;
    private ProductFagment fragment;

    public Product_Adapter(List<Product> productList, Product_Dao productDao, ProductFagment fragment) {
        this.productList = productList;
        this.productDao = productDao;
        this.fragment = fragment;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameProduct.setText(product.getName_sp());
        holder.priceProduct.setText(String.format("%s VND", product.getPrice_sp()));
        holder.cateTextView.setText(String.valueOf(product.getId_cate()));
        holder.outstanding.setText(product.getOutstanding());
        holder.quantity.setText(String.valueOf(product.getQuantity_sp()));

        // Load image using Glide
        int radius = 10;
        int width = 120;
        int height = 100;
        Glide.with(holder.itemView.getContext())
                .load(product.getImg_sp())
                .placeholder(R.drawable.uploadimg)
                .error(R.drawable.uploadimg)
                .apply(new RequestOptions()
                        .override(width, height) // Thiết lập kích thước
                        .transform(new RoundedCorners(radius))) // Bo tròn các góc
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imageProduct);

        holder.deleProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productDao.deleteProduct(product.getId_sp());
                productList.remove(product);
                notifyDataSetChanged();
            }
        });

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment.getActivity(), UpdateProduct.class);
                intent.putExtra("id", product.getId_sp());
                intent.putExtra("name", product.getName_sp());
                intent.putExtra("description", product.getDescription_sp());
                intent.putExtra("img", product.getImg_sp());
                intent.putExtra("IMG", product.getIMG_nb());
                intent.putExtra("price", product.getPrice_sp());
                intent.putExtra("quantity", product.getQuantity_sp());
                intent.putExtra("outstanding", product.getOutstanding());
                intent.putExtra("cate_product", product.getId_cate());
                intent.putExtra("giam_gia", product.getGiam_gia());
                fragment.startActivityForResult(intent, ProductFagment.UPDATE_PRODUCT_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView nameProduct, priceProduct, cateTextView, outstanding,quantity;
        public ImageView editProduct, deleProduct, imageProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.nameProduct);
            priceProduct = itemView.findViewById(R.id.priceProduct);
            cateTextView = itemView.findViewById(R.id.cateTextView);
            outstanding = itemView.findViewById(R.id.outstanding);
            quantity= itemView.findViewById(R.id.quantity);
            imageProduct = itemView.findViewById(R.id.imgProduct);
            editProduct = itemView.findViewById(R.id.editProduct);
            deleProduct = itemView.findViewById(R.id.deleProduct);
        }
    }
}
