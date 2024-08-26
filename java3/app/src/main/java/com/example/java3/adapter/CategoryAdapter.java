package com.example.java3.adapter;

import android.content.Context;
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
import com.example.java3.R;
import com.example.java3.model.Category;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;
    private Context context;
    private OnCategoryClickListener onCategoryClickListener;

    public CategoryAdapter(Context context, List<Category> categoryList, OnCategoryClickListener onCategoryClickListener) {
        this.categoryList = categoryList;
        this.context = context;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.txtDM.setText(category.getName());
        int radius = 10;
        int width = 80;
        int height = 80;
        Glide.with(holder.itemView.getContext())
                .load(category.getImageUrl())
                .placeholder(R.drawable.uploadimg)
                .error(R.drawable.uploadimg)
                .apply(new RequestOptions()
                        .override(width, height) // Thiết lập kích thước
                        .transform(new RoundedCorners(radius))) // Bo tròn các góc
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide", "Failed to load image: " + e != null ? e.getMessage() : "Unknown error");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("Glide", "Image loaded successfully");
                        return false;
                    }
                })
                .into(holder.imgDM);
        holder.bind(category, onCategoryClickListener);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtDM;
        ImageView imgDM;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDM = itemView.findViewById(R.id.txtDM);
            imgDM = itemView.findViewById(R.id.imgDM);
        }

        public void bind(final Category category, final OnCategoryClickListener listener) {
            txtDM.setText(category.getName());
            Glide.with(itemView.getContext()).load(category.getImageUrl()).into(imgDM);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCategoryClick(category);
                }
            });
        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }
}
