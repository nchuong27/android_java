package com.example.a21111064571_nguyenvanchuong_lab05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a21111064571_nguyenvanchuong_lab05.Adaper.AdapterList;
import com.example.a21111064571_nguyenvanchuong_lab05.Adaper.PerSonAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = findViewById(R.id.list_Peson);
        List<PerSon> perSonList = new ArrayList<>();
        perSonList.add(new PerSon(R.drawable.daula2, "Tuyệt Thế"));
        perSonList.add(new PerSon(R.drawable.daupha, "Tam Niên"));
        perSonList.add(new PerSon(R.drawable.thanlan, "Thần Lan"));
        perSonList.add(new PerSon(R.drawable.thegioi, "Thế Giới"));
        perSonList.add(new PerSon(R.drawable.thonphe, "Thôn Phệ"));

        AdapterList adapterList = new AdapterList(this, R.layout.list_item, perSonList);
        listView.setAdapter(adapterList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PerSon selectedPerson = adapterList.getItem(position);
                if (selectedPerson != null) {
                    String selectedName = selectedPerson.getNamePerSon();
                    Toast.makeText(MainActivity2.this, "Bạn đã chọn " + selectedName, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}