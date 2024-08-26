package com.example.a21111064571_nguyenvanchuong_lab05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a21111064571_nguyenvanchuong_lab05.Adaper.PerSonAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        private Spinner spnPerSon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnPerSon = findViewById(R.id.spnPerSon);
        List<PerSon> perSonList = new ArrayList<>();
        perSonList.add(new PerSon(R.drawable.daula2,"Tuyệt Thế"));
        perSonList.add(new PerSon(R.drawable.daupha,"Tam Niên"));
        perSonList.add(new PerSon(R.drawable.thanlan,"Thần Lan"));
        perSonList.add(new PerSon(R.drawable.thegioi,"Thế Giới"));
        perSonList.add(new PerSon(R.drawable.thonphe,"Thôn Phệ"));

        PerSonAdapter perSonAdapter = new PerSonAdapter(this,R.layout.spinner_item,perSonList);
        spnPerSon.setAdapter(perSonAdapter);
        spnPerSon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PerSon selectperson = perSonAdapter.getItem(position);
                    if (selectperson != null){
                        String selectName = selectperson.getNamePerSon();
                        Toast.makeText(MainActivity.this,"Bạn đã chọn " +selectName,Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}