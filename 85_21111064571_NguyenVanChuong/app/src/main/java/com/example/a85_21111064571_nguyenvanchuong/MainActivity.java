package com.example.a85_21111064571_nguyenvanchuong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a85_21111064571_nguyenvanchuong.DAO.GVDAO;
import com.example.a85_21111064571_nguyenvanchuong.adapter.GVAdapter;
import com.example.a85_21111064571_nguyenvanchuong.model.GV;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editTextMa, editTextTen, editTextNgay, editTextsdt, editTextKhoa, editTextMon,editTextTrinh;
    Button buttonThem, buttonSua, buttonXoa;
    ListView listViewDSGV;
    ArrayList<GV> gvArrayList = new ArrayList<>();
    GVDAO gvdao;
    Context context = this;
    GVAdapter gvAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMa = findViewById(R.id.editTextMa);
        editTextTen = findViewById(R.id.editTextTen);
        editTextNgay = findViewById(R.id.editTextNgay);
        editTextsdt = findViewById(R.id.editTextsdt);
        editTextKhoa = findViewById(R.id.editTextKhoa);
        editTextMon = findViewById(R.id.editTextMon);
        editTextTrinh = findViewById(R.id.editTextTrinhdo);

        buttonThem = findViewById(R.id.buttonThem);
        buttonSua = findViewById(R.id.buttonSua);
        buttonXoa = findViewById(R.id.buttonXoa);

        listViewDSGV = findViewById(R.id.listViewDSGV);

        gvdao = new GVDAO(context);
        gvArrayList = GVDAO.getListGV();

        gvAdapter = new GVAdapter(context, gvArrayList);
        listViewDSGV.setAdapter(gvAdapter);

        listViewDSGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GV selectedgv = gvArrayList.get(position);
                editTextMa.setText(selectedgv.getMa());
                editTextTen.setText(selectedgv.getTen());
                editTextNgay.setText(selectedgv.getNgaysinh());
                editTextsdt.setText(selectedgv.getSdt());
                editTextKhoa.setText(selectedgv.getKhoa());
                editTextMon.setText(selectedgv.getMon());
                editTextTrinh.setText(selectedgv.getTrinhdo());
            }
        });

        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GV gv = new GV();
                gv.setMa(editTextMa.getText().toString());
                gv.setTen(editTextTen.getText().toString());
                gv.setNgaysinh(editTextNgay.getText().toString());
                gv.setSdt(editTextsdt.getText().toString());
                gv.setKhoa(editTextKhoa.getText().toString());
                gv.setMon(editTextMon.getText().toString());
                gv.setTrinhdo(editTextTrinh.getText().toString());

                long result = gvdao.addGv(gv);
                if (result == -1) {
                    // Biển số xe đã tồn tại, hiển thị Toast
                    Toast.makeText(MainActivity.this, "Mã đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm thành công, cập nhật danh sách và thông báo thành công
                    gvArrayList.clear();
                    gvArrayList.addAll(gvdao.getListGV());
                    gvAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Thêm giảng viên thành công!", Toast.LENGTH_SHORT).show();
                    xoa();
                }
            }
        });

        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GV gv = new GV();
                gv.setMa(editTextMa.getText().toString());
                gv.setTen(editTextTen.getText().toString());
                gv.setNgaysinh(editTextNgay.getText().toString());
                gv.setSdt(editTextsdt.getText().toString());
                gv.setKhoa(editTextKhoa.getText().toString());
                gv.setMon(editTextMon.getText().toString());
                gv.setTrinhdo(editTextTrinh.getText().toString());
                long result = gvdao.updateGv(gv);
                if (result == -1) {
                    // Biển số xe đã tồn tại, hiển thị Toast
                    Toast.makeText(MainActivity.this, "Mã đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    // Sửa thành công, cập nhật danh sách và thông báo thành công
                    gvArrayList.clear();
                    gvArrayList.addAll(gvdao.getListGV());
                    gvAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Sửa giảng viên thành công!", Toast.LENGTH_SHORT).show();
                    xoa();
                }
            }
        });

        buttonXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = editTextMa.getText().toString();
                gvdao.deleteGv(ma);


                gvArrayList.clear();
                gvArrayList.addAll(gvdao.getListGV());
                gvAdapter.notifyDataSetChanged();
                xoa();
            }
        });
    }
    public void xoa(){
        editTextMa.setText("");
        editTextTen.setText("");
        editTextNgay.setText("");
        editTextsdt.setText("");
        editTextKhoa.setText("");
        editTextMon.setText("");
        editTextTrinh.setText("");
    }
}
