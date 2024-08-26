package com.example.a21111064571_nguyenvanchuong_lab63;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tìm toolbar từ layout
        Toolbar toolbarTop = findViewById(R.id.toolbarTop);
        //đặt toolbar là action bar
        setSupportActionBar(toolbarTop);
        //hiển thị nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //đặt tiêu đề
        getSupportActionBar().setTitle("Hay Quá");
        getSupportActionBar().setSubtitle(" Quá Tuyệt Vời ");
        //xử lý sự kiện khi nút báck đc ấn
        toolbarTop.setNavigationOnClickListener(view->getOnBackPressedDispatcher().onBackPressed());
    }
}