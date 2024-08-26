package com.example.java3.adapter;

import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.java3.R;
import com.example.java3.model.Photo;
import com.example.java3.view.HomeFragment;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {
    private HomeFragment mcontext;
    private List<Photo> mlistphoto;

    public PhotoAdapter(HomeFragment mcontext, List<Photo> mlistphoto) {
        this.mcontext = mcontext;
        this.mlistphoto = mlistphoto;
    }
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate the item layout
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.photo_item, container, false);

        // Tìm ImageView trong ConstraintLayout
        ImageView imgPhoto = view.findViewById(R.id.img_photo);

        // Lấy đối tượng photo từ danh sách
        Photo photo = mlistphoto.get(position);
        if (photo != null) {
            // Tải ảnh sử dụng Glide và thiết lập vào ImageView với RoundedCornersTransformation
            RequestOptions requestOptions = new RequestOptions()
                    .transform(new RoundedCorners(20)) // Radius 8dp
                    .override(Target.SIZE_ORIGINAL); // Bỏ qua kích thước để Glide tự điều chỉnh

            Glide.with(mcontext)
                    .load(photo.getResourceId())
                    .apply(requestOptions)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            // Thiết lập ảnh vào ImageView
                            imgPhoto.setImageDrawable(resource);

                            // Cập nhật lại chiều cao của ImageView
                            ViewGroup.LayoutParams layoutParams = imgPhoto.getLayoutParams();
                            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, mcontext.getResources().getDisplayMetrics());
                            imgPhoto.setLayoutParams(layoutParams);

                            // Yêu cầu layout cho ImageView sau khi đã cập nhật
                            imgPhoto.requestLayout();
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            // Placeholder handling if needed
                        }
                    });
        }

        // Thêm view vào container
        container.addView(view);
        return view;
    }



    @Override
    public int getCount() {
        if (mlistphoto != null){
            return mlistphoto.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
