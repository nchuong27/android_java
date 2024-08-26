package com.example.java3.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.java3.MainActivity;
import com.example.java3.R;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.databinding.FragmentSettingBinding;
import com.example.java3.model.Account;

public class Setting extends Fragment {
    private FragmentSettingBinding binding;
    private DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        int radius = 120; // bán kính bo tròn tính theo px

        Glide.with(this)
                .load(R.drawable.avata) // hoặc URL hình ảnh
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                .into(binding.imageView3);
        // Lấy tên người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("username", "name"); // Giá trị mặc định là "UserName" nếu không tìm thấy
        binding.txtnameUser.setText(name);

        binding.textVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),VoucherActivity.class);
                startActivity(intent);
            }
        });
        binding.textAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AcountActivity.class);
                startActivity(intent);
            }
        });
        binding.textLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform logout
                databaseHelper.logout();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("userRole");
                editor.remove("isLoggedIn");
                editor.apply();

                Intent loginIntent = new Intent(getContext(), MainActivity.class);
                startActivity(loginIntent);
                getActivity().finish();
            }
        });

        return binding.getRoot();
    }
}