package com.example.a21111064571nguyenvanchuonglab06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a21111064571nguyenvanchuonglab06.Adapter.PerSonAdapter;
import com.example.a21111064571nguyenvanchuonglab06.model.PerSon;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<PerSon> perSonList;
    private PerSonAdapter perSonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        perSonList = new ArrayList<>();
        perSonList.add(new PerSon(R.drawable.phamnhan, "Người thứ 1"));
        perSonList.add(new PerSon(R.drawable.phamnhan, "Người thứ 1"));
        perSonList.add(new PerSon(R.drawable.phamnhan, "Người thứ 1"));
        perSonList.add(new PerSon(R.drawable.phamnhan, "Người thứ 1"));


        perSonAdapter = new PerSonAdapter(this, R.layout.list_item_person, perSonList);
        ListView listViewPerSon = findViewById(R.id.listViewPerSon);
        listViewPerSon.setAdapter(perSonAdapter);

        registerForContextMenu(listViewPerSon);
        listViewPerSon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PerSon selectPerSon = perSonAdapter.getItem(position);
                if (selectPerSon != null) {
                    Toast.makeText(MainActivity.this, "Bạn vừa chọn" + selectPerSon.getNamePerSon(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewPerSon) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            getMenuInflater().inflate(R.menu.context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        if (item.getItemId() == R.id.menu_view) {
            showPerSonInfo(position);

            return true;
        } else if (item.getItemId() == R.id.menu_delete) {
            deletePerSonInfo(position);
            return true;


        }else {
            return super.onContextItemSelected(item);
        }
    }




    private void showPerSonInfo(int position) {
        PerSon selectedPerSon = perSonList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(selectedPerSon.getNamePerSon())
                .setMessage("Bạn vừa chọn hiển thị thông tin"+selectedPerSon.getNamePerSon())
                .setPositiveButton("Ok",(dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void deletePerSonInfo(int position) {
        perSonList.remove(position);
        perSonAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Bạn vừa xoá khỏi ListView",Toast.LENGTH_LONG).show();
    }
}