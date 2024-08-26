package com.example.a21111064571_nguyenvanchuong_lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {
    private TextView txtHoten;
    private EditText edtDiem;
    private Button btnThongBao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        txtHoten = findViewById(R.id.txtHoten);
        edtDiem = findViewById(R.id.edtDiem);
        btnThongBao = findViewById(R.id.btnThongBao);

        Intent intent = getIntent();
        txtHoten.setText(intent.getStringExtra("HoTen"));
        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nhapdiemSV = edtDiem.getText().toString();
                Intent intent1 = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("diemGui",nhapdiemSV);
                intent1.putExtras(bundle);
                setResult(RESULT_OK,intent1);
                finish();
            }
        });
    }
}