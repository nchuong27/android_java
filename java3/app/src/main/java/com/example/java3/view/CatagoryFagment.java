package com.example.java3.view;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.java3.Dao.Category_Dao;
import com.example.java3.R;
import com.example.java3.adapter.Category_Adapter;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class CatagoryFagment extends Fragment {
    public static final int UPDATE_CATEGORY_REQUEST_CODE = 2 ;
    private RecyclerView recyclerViewdm;
    private List<Category> categoryList;
    private DatabaseHelper databaseHelper;
    private Category_Dao categoryDao;
    private Category_Adapter categoryAdapter;
    private EditText searchCategory;
    private FloatingActionButton addcate;
    private static final int ADD_CATEGORY_REQUEST_CODE = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catagory_fagment, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        categoryDao = new Category_Dao(getContext());
        recyclerViewdm = view.findViewById(R.id.recycleviewDM);
        searchCategory = view.findViewById(R.id.searchCategory);
        addcate = view.findViewById(R.id.addcate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewdm.setLayoutManager(layoutManager);
        categoryList = getCategoryList("");
        categoryAdapter = new Category_Adapter(categoryList, categoryDao, this);
        recyclerViewdm.setAdapter(categoryAdapter);
        searchCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần sử dụng
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Tìm kiếm tài khoản khi văn bản thay đổi
                categoryList = getCategoryList(s.toString());
                categoryAdapter.setCategory(categoryList);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần sử dụng
            }
        });
        addcate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Addcate.class);
                startActivityForResult(intent, ADD_CATEGORY_REQUEST_CODE);
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CATEGORY_REQUEST_CODE && resultCode == RESULT_OK) {
            categoryList = getCategoryList("");
            categoryAdapter.setCategory(categoryList);
        } else if (requestCode == UPDATE_CATEGORY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String img = data.getStringExtra("imgDM");
            int id = data.getIntExtra("id", -1);
            if (id != -1 && name != null && img != null) {
                categoryDao.updateDanhMuc(id, name,img);
                categoryList = getCategoryList("");
                categoryAdapter.setCategory(categoryList);
            }
        }
    }

    private List<Category> getCategoryList(String keyword){
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT id, name,imgDM FROM category WHERE name LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%"});
        if (cursor.moveToNext()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String imgDM = cursor.getString(cursor.getColumnIndex("imgDM"));
                Category category = new Category(id, name,imgDM);
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoryList;
    }
}