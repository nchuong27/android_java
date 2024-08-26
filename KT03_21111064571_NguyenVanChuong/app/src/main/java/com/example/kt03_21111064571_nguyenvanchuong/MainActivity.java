package com.example.kt03_21111064571_nguyenvanchuong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kt03_21111064571_nguyenvanchuong.DAO.CarDAO;
import com.example.kt03_21111064571_nguyenvanchuong.adapter.CarAdapter;
import com.example.kt03_21111064571_nguyenvanchuong.model.Car;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editTextBienSoXe, editTextTenXe, editTextHangXe, editTextNamSanXuat, editTextMoTaXe, editTextTenLaiXe;
    Button buttonThem, buttonSua, buttonXoa;
    ListView listViewDSCar;
    ArrayList<Car> carArrayList = new ArrayList<>();
    CarDAO carDAO;
    Context context = this;
    CarAdapter carAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextBienSoXe = findViewById(R.id.editTextBienSoXe);
        editTextTenXe = findViewById(R.id.editTextTenXe);
        editTextHangXe = findViewById(R.id.editTextHangXe);
        editTextNamSanXuat = findViewById(R.id.editTextNamSanXuat);
        editTextMoTaXe = findViewById(R.id.editTextMoTaXe);
        editTextTenLaiXe = findViewById(R.id.editTextTenLaiXe);

        buttonThem = findViewById(R.id.buttonThem);
        buttonSua = findViewById(R.id.buttonSua);
        buttonXoa = findViewById(R.id.buttonXoa);

        listViewDSCar = findViewById(R.id.listViewDSCar);

        carDAO = new CarDAO(context);
        carArrayList = carDAO.getListCar();

        carAdapter = new CarAdapter(context, carArrayList);
        listViewDSCar.setAdapter(carAdapter);

        listViewDSCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Car selectedCar = carArrayList.get(position);
                editTextBienSoXe.setText(selectedCar.getBienSoXe());
                editTextTenXe.setText(selectedCar.getTenXe());
                editTextHangXe.setText(selectedCar.getHangXe());
                editTextNamSanXuat.setText(String.valueOf(selectedCar.getNamSanXuat()));
                editTextMoTaXe.setText(selectedCar.getMoTaXe());
                editTextTenLaiXe.setText(selectedCar.getTenLaiXe());
            }
        });

        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car car = new Car();
                car.setBienSoXe(editTextBienSoXe.getText().toString());
                car.setTenXe(editTextTenXe.getText().toString());
                car.setHangXe(editTextHangXe.getText().toString());
                car.setNamSanXuat(Integer.parseInt(editTextNamSanXuat.getText().toString()));
                car.setMoTaXe(editTextMoTaXe.getText().toString());
                car.setTenLaiXe(editTextTenLaiXe.getText().toString());

                long result = carDAO.addCar(car);
                if (result == -1) {
                    // Biển số xe đã tồn tại, hiển thị Toast
                    Toast.makeText(MainActivity.this, "Biển số xe đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm thành công, cập nhật danh sách và thông báo thành công
                    carArrayList.clear();
                    carArrayList.addAll(carDAO.getListCar());
                    carAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Thêm xe thành công!", Toast.LENGTH_SHORT).show();
                    xoa();
                }
            }
        });

        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car car = new Car();
                car.setBienSoXe(editTextBienSoXe.getText().toString());
                car.setTenXe(editTextTenXe.getText().toString());
                car.setHangXe(editTextHangXe.getText().toString());
                car.setNamSanXuat(Integer.parseInt(editTextNamSanXuat.getText().toString()));
                car.setMoTaXe(editTextMoTaXe.getText().toString());
                car.setTenLaiXe(editTextTenLaiXe.getText().toString());

                long result = carDAO.updateCar(car);
                if (result == -1) {
                    // Biển số xe đã tồn tại, hiển thị Toast
                    Toast.makeText(MainActivity.this, "Biển số xe đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    // Sửa thành công, cập nhật danh sách và thông báo thành công
                    carArrayList.clear();
                    carArrayList.addAll(carDAO.getListCar());
                    carAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Sửa xe thành công!", Toast.LENGTH_SHORT).show();
                    xoa();
                }
            }
        });

        buttonXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bienSoXe = editTextBienSoXe.getText().toString();
                carDAO.deleteCar(bienSoXe);

                carArrayList.clear();
                carArrayList.addAll(carDAO.getListCar());
                carAdapter.notifyDataSetChanged();
                xoa();
            }
        });
    }
    public void xoa(){
        editTextBienSoXe.setText("");
        editTextTenXe.setText("");
        editTextHangXe.setText("");
        editTextMoTaXe.setText("");
        editTextNamSanXuat.setText("");
        editTextTenLaiXe.setText("");
    }
}
