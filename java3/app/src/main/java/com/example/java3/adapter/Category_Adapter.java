package com.example.java3.adapter;

import android.app.Fragment;
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
import com.example.java3.Dao.Category_Dao;
import com.example.java3.R;
import com.example.java3.model.Category;
import com.example.java3.view.CatagoryFagment;
import com.example.java3.view.Updatecate;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.CategoryViewHolder> {
    private List<Category> categoryList;
    private Category_Dao categoryDao;
    private CatagoryFagment fragment;

    public Category_Adapter(List<Category> categoryList, Category_Dao categoryDao, CatagoryFagment fragment) {
        this.categoryList = categoryList;
        this.categoryDao = categoryDao;
        this.fragment = fragment;
    }

    public void setCategory(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.nameCateTextview.setText(category.getName());
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
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imgDM);

        holder.deleCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryDao.deleteCategory(category.getId());
                categoryList.remove(category);
                notifyDataSetChanged();
            }
        });
        holder.editCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment.getActivity(), Updatecate.class);
                intent.putExtra("id", category.getId());
                intent.putExtra("name", category.getName());
                intent.putExtra("imgDM", category.getImageUrl());
                fragment.startActivityForResult(intent, CatagoryFagment.UPDATE_CATEGORY_REQUEST_CODE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView nameCateTextview;
        public ImageView editCate,deleCate,imgDM;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            nameCateTextview = itemView.findViewById(R.id.nameCateTextview);
            editCate= itemView.findViewById(R.id.editCate);
            deleCate = itemView.findViewById(R.id.deleCate);
            imgDM = itemView.findViewById(R.id.imgDM);
        }
    }
}
