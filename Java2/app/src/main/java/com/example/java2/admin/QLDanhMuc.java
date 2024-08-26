package com.example.java2.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.java2.Adapter.DMAdapter;
import com.example.java2.KhoiTao.DanhMuc;
import com.example.java2.Dao.DaoDanhMuc;
import com.example.java2.R;
import com.example.java2.model.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class QLDanhMuc extends AppCompatActivity {
    public static final int REQUEST_CODE_SUA_DM = 2;
    private static final int REQUEST_CODE_THEM_DM = 1;

    private RecyclerView recyclerViewDM;
    private DatabaseHelper dbHelper;
    private DMAdapter dmAdapter;
    private List<DanhMuc> dmList;
    private DaoDanhMuc daoDanhMuc;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_danhmuc);
        dbHelper = new DatabaseHelper(this);
        daoDanhMuc = new DaoDanhMuc(this);

        recyclerViewDM = findViewById(R.id.recycler_dm);
        buttonAdd = findViewById(R.id.buttonAddCategory);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewDM.setLayoutManager(layoutManager);

        dmList = getDMList();
        dmAdapter = new DMAdapter(dmList, this);
        recyclerViewDM.setAdapter(dmAdapter);
        dmAdapter.notifyDataSetChanged();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QLDanhMuc.this, themDm.class);
                startActivityForResult(i, REQUEST_CODE_THEM_DM);
            }
        });
    }

    public List<DanhMuc> getDMList() {
        List<DanhMuc> dmList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, ten FROM DanhMuc", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex("ten"));
                DanhMuc danhMuc = new DanhMuc(id, ten);
                dmList.add(danhMuc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dmList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEM_DM && resultCode == RESULT_OK) {
            dmList = getDMList();
            dmAdapter.setDMList(dmList);
        } else if (requestCode == REQUEST_CODE_SUA_DM && resultCode == RESULT_OK) {
            String tenDanhMuc = data.getStringExtra("ten");
            int idDanhMuc = data.getIntExtra("id", -1);
            if (idDanhMuc != -1 && tenDanhMuc != null) {
                daoDanhMuc.updateDanhMuc(idDanhMuc, tenDanhMuc);
                dmList = getDMList();
                dmAdapter.setDMList(dmList);
            }
        }
    }


}
