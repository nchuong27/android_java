package com.example.java1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtmalop, edttenlop, edtsiso;
    Button btninsert, btndelete, btnupdate, btnquery;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtmalop = findViewById(R.id.edtmalop);
        edttenlop = findViewById(R.id.edttenlop);
        edtsiso = findViewById(R.id.edtsiso);
        btninsert = findViewById(R.id.btninsert);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnuodate);
        btnquery = findViewById(R.id.btnquery);
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);
        mydatabase = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE IF NOT EXISTS tbllop(malop TEXT primary key,tenlop TEXT,siso INTEGER)";
            mydatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã tồn tại");
        }

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String malop = edtmalop.getText().toString();
                String tenlop = edttenlop.getText().toString();
                int siso = Integer.parseInt(edtsiso.getText().toString());
                ContentValues myvalues = new ContentValues();
                myvalues.put("malop", malop);
                myvalues.put("tenlop", tenlop);
                myvalues.put("siso", siso);
                String msq = "";
                if (mydatabase.insert("tbllop", null, myvalues) == -1) {
                    msq = "Fail to Insert Record";
                } else {
                    msq = "Insert record Successfully";
                    queryData(); // Gọi phương thức queryData để hiển thị thông tin ngay sau khi chèn dữ liệu
                }
                Toast.makeText(MainActivity.this, msq, Toast.LENGTH_SHORT).show();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String malop = edtmalop.getText().toString();
                int n = mydatabase.delete("tbllop", "malop = ?", new String[]{malop});
                String msq = "";
                if (n == 0) {
                    msq = "No record Delete";
                } else {
                    msq = n + " record is deleted";
                    queryData(); // Gọi phương thức queryData để hiển thị thông tin ngay sau khi xóa dữ liệu
                }
                Toast.makeText(MainActivity.this, msq, Toast.LENGTH_SHORT).show();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int siso = Integer.parseInt(edtsiso.getText().toString());
                String malop = edtmalop.getText().toString();
                ContentValues myvalues = new ContentValues();
                myvalues.put("siso", siso);
                int n = mydatabase.update("tbllop", myvalues, "malop = ?", new String[]{malop});
                String msq = "";
                if (n == 0) {
                    msq = "No record Update";
                } else {
                    msq = n + " record is updated";
                    queryData(); // Gọi phương thức queryData để hiển thị thông tin ngay sau khi cập nhật dữ liệu
                }
                Toast.makeText(MainActivity.this, msq, Toast.LENGTH_SHORT).show();
            }
        });

        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryData(); // Gọi phương thức queryData để hiển thị thông tin khi nhấn nút "Query"
            }
        });
    }

    private void queryData() {
        mylist.clear();
        Cursor c = mydatabase.query("tbllop", null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String data = c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2);
            mylist.add(data);
            c.moveToNext();
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }
}