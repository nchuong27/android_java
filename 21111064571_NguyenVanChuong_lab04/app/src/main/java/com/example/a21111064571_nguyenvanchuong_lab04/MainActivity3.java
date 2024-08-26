package com.example.a21111064571_nguyenvanchuong_lab04;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    private EditText edthovaten;
    private Button btnGui;
    private TextView txtDiem;

    private ActivityResultLauncher<Intent> nhapDiemSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        edthovaten = findViewById(R.id.edthovaten);
        btnGui = findViewById(R.id.btnGui);
        txtDiem = findViewById(R.id.txtDiem);

        ActivityResultLauncher<Intent> nhapDiemSV = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult ketQuaDiem) {
                        if (ketQuaDiem.getResultCode() == RESULT_OK){
                            Intent intent = ketQuaDiem.getData();
                            if (intent != null){
                                Bundle bundle = intent.getExtras();
                                String diemNhan = bundle.getString("diemGui");
                                txtDiem.setText(diemNhan);
                            }
                        }
                    }
                }
        );

        btnGui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                intent.putExtra("HoTen", edthovaten.getText().toString());
                nhapDiemSV.launch(intent);
            }
        });
    }
}