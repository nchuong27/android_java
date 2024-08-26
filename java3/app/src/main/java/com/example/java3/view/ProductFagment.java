package com.example.java3.view;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java3.Dao.Product_Dao;
import com.example.java3.adapter.Product_Adapter;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.databinding.FragmentProductFagmentBinding;
import com.example.java3.model.Category;
import com.example.java3.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductFagment extends Fragment {
    private FragmentProductFagmentBinding binding;
    public static final int ADD_PRODUCT_REQUEST_CODE = 1;
    public static final int UPDATE_PRODUCT_REQUEST_CODE = 2;
    private List<Product> productList;
    private Product_Dao productDao;
    private Product_Adapter productAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductFagmentBinding.inflate(inflater, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        productDao = new Product_Dao(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recycleviewSP.setLayoutManager(layoutManager);
        productList = getProductList("");
        productAdapter = new Product_Adapter(productList, productDao, this);
        binding.recycleviewSP.setAdapter(productAdapter);
        binding.searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần sử dụng
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Tìm kiếm tài khoản khi văn bản thay đổi
                productList = getProductList(s.toString());
                productAdapter.setProductList(productList);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần sử dụng
            }
        });
        binding.addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Addproduct.class);
                startActivityForResult(intent, ADD_PRODUCT_REQUEST_CODE);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PRODUCT_REQUEST_CODE && resultCode == RESULT_OK) {
            productList = getProductList("");
            productAdapter.setProductList(productList);
        } else if (requestCode == UPDATE_PRODUCT_REQUEST_CODE && resultCode == RESULT_OK) {
            productList = getProductList("");
            productAdapter.setProductList(productList);
            int id = data.getIntExtra("id", -1);
            String name = data.getStringExtra("name");
            String description = data.getStringExtra("description");
            String price = data.getStringExtra("price");
            String original_price = data.getStringExtra("original_price");
            String img = data.getStringExtra("img");
            String IMG = data.getStringExtra("IMG");
            int quantity = data.getIntExtra("quantity", 0);
            String outstanding = data.getStringExtra("outstanding");
            int cate_product = data.getIntExtra("cate_product", 1);
            int discount = data.getIntExtra("giam_gia", -1); // Đã thay đổi thành giam_gia

            if (id != -1 && name != null && description != null && price != null && original_price!= null && img != null && IMG != null && quantity != 0 && cate_product != 0 && discount != -1) {
                productDao.updateProduct(id, name, description, price,original_price, quantity, img, IMG, outstanding, String.valueOf(cate_product), discount);
            }
        }
    }

    private List<Product> getProductList(String keyword) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Thêm câu lệnh ORDER BY để sắp xếp sản phẩm theo id giảm dần
        String query = "SELECT id, img, IMG_NB, name, description, price, original_price, quantity, outstanding, cate_product, discount " +
                "FROM product " +
                "WHERE name LIKE ? " +
                "ORDER BY id DESC"; // Sắp xếp theo ID giảm dần

        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%"});
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String img = cursor.getString(cursor.getColumnIndexOrThrow("img"));
                String IMG = cursor.getString(cursor.getColumnIndexOrThrow("IMG_NB"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String outstanding = cursor.getString(cursor.getColumnIndexOrThrow("outstanding"));
                String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                String original_price = cursor.getString(cursor.getColumnIndexOrThrow("original_price"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                String cate_product = cursor.getString(cursor.getColumnIndexOrThrow("cate_product"));
                int discount = cursor.getInt(cursor.getColumnIndexOrThrow("discount"));

                Product product = new Product(id, img, IMG, name, description, price, quantity, outstanding, cate_product, discount, original_price);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return productList;
    }
}
