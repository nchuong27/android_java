package com.example.java3.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.java3.Dao.Category_Dao;
import com.example.java3.Dao.Product_Dao;
import com.example.java3.adapter.CategoryAdapter;
import com.example.java3.adapter.PhotoAdapter;
import com.example.java3.adapter.ProductAdapter;
import com.example.java3.databinding.FragmentHomeBinding;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.model.Category;
import com.example.java3.model.Photo;
import com.example.java3.model.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment implements FilterDialogFragment .FilterListener {
    FragmentHomeBinding binding;
    private PhotoAdapter photoAdapter;
    private Handler handler;
    private Runnable runnable;
    private DatabaseHelper databaseHelper;
    private Category_Dao categoryDao;
    private Product_Dao productDao;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private List<Category> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        productDao = new Product_Dao(getContext());

        // Setup ViewPager and CircleIndicator
        photoAdapter = new PhotoAdapter(this, getHighlightedPhotos());
        binding.viewPager.setAdapter(photoAdapter);
        binding.circleindicator.setViewPager(binding.viewPager);
        photoAdapter.registerDataSetObserver(binding.circleindicator.getDataSetObserver());

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                int currentItem = binding.viewPager.getCurrentItem();
                int totalItems = photoAdapter.getCount();

                if (totalItems > 0) {
                    int nextItem = (currentItem + 1) % totalItems;
                    binding.viewPager.setCurrentItem(nextItem, true);
                }

                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);

        // Setup CategoryAdapter
        categoryList = new ArrayList<>();
        categoryDao = new Category_Dao(getContext());
        categoryList = categoryDao.getAllDanhMuc();

        categoryAdapter = new CategoryAdapter(getContext(), categoryList, new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                List<Product> filteredProducts = productDao.getProductsByCategory(category.getName());
                productAdapter.updateData(filteredProducts);
            }
        });
        GridLayoutManager categoryLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        binding.recyViewHT1.setLayoutManager(categoryLayoutManager);
        binding.recyViewHT1.setAdapter(categoryAdapter);

        // Setup ProductAdapter
        productList = new ArrayList<>();
        productList = productDao.getAllSanPham();
        productAdapter = new ProductAdapter(productList, getContext());
        GridLayoutManager productLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyViewHT2.setLayoutManager(productLayoutManager);
        binding.recyViewHT2.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                showProductDetail(product);
            }
        });

        // Search functionality
        binding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Product> filteredProducts = getProductList(s.toString());
                productAdapter.updateData(filteredProducts);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Show filter BottomSheet
        binding.ImgBoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialogFragment filterDialog = new FilterDialogFragment();
                filterDialog.setTargetFragment(HomeFragment.this, 0);
                filterDialog.show(getParentFragmentManager(), filterDialog.getTag());
            }
        });

        return binding.getRoot();
    }

    private void showProductDetail(Product product) {
        Intent intent = new Intent(getContext(), ProductDetail.class);
        intent.putExtra("Product", product);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onFilterApplied(String priceFilter, boolean isDiscounted) {
        List<Product> filteredList = filterProducts(priceFilter, isDiscounted);
        productAdapter.updateData(filteredList);
    }

    private List<Product> filterProducts(String priceFilter, boolean isDiscounted) {
        List<Product> filteredList = new ArrayList<>(productList);

        if (isDiscounted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                filteredList.removeIf(product -> product.getGiam_gia() <= 0);
            }
        }

        // Sort by price
        if (priceFilter.equals("lowToHigh")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                filteredList.sort(Comparator.comparingDouble(product -> parsePrice(product.getPrice_sp())));
            }
        } else if (priceFilter.equals("highToLow")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                filteredList.sort((p1, p2) -> Double.compare(parsePrice(p2.getPrice_sp()), parsePrice(p1.getPrice_sp())));
            }
        }

        return filteredList;
    }

    // Phương thức để chuyển chuỗi giá thành double
    private double parsePrice(String price) {
        String numericPrice = price.replaceAll("[^\\d]", ""); // Loại bỏ các ký tự không phải số
        return Double.parseDouble(numericPrice);
    }

    private List<Photo> getHighlightedPhotos() {
        List<Photo> photoList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.ANH_NB + " FROM " + DatabaseHelper.TABLE_SP + " WHERE " + DatabaseHelper.NOI_BAT + " = 'Có'", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String photoPath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ANH_NB));
                if (photoPath != null && !photoPath.isEmpty()) {
                    photoList.add(new Photo(photoPath));
                    Log.d("HomeFragment", "Photo path: " + photoPath);
                }
            } while (cursor.moveToNext());
        } else {
            Log.d("HomeFragment", "No highlighted photos found");
        }
        cursor.close();
        return photoList;
    }

    private List<Product> getProductList(String keyword) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT id, img, IMG_NB, name, description, price, original_price, quantity, outstanding, cate_product, discount FROM product WHERE name LIKE ?";
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
