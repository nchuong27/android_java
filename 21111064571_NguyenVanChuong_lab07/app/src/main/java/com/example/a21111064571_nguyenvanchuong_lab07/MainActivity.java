package com.example.a21111064571_nguyenvanchuong_lab07;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a21111064571_nguyenvanchuong_lab07.Adapter.CustomAdapter;
import com.example.a21111064571_nguyenvanchuong_lab07.DAO.ProductDAO;
import com.example.a21111064571_nguyenvanchuong_lab07.model.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editTextMaSP, editTextTenSP, editTextSoSP, editTextGiaSP;
    Button buttonThem, buttonSua, buttonXoa;
    ListView listViewDSSP;
    ArrayList<Product> productArrayList = new ArrayList<>();
    ProductDAO produtcDAO;
    Context context = this;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextMaSP = findViewById(R.id.edtMa);
        editTextTenSP = findViewById(R.id.edtTen);
        editTextSoSP = findViewById(R.id.edtSoLuong);
        editTextGiaSP = findViewById(R.id.edtGia);
        buttonThem = findViewById(R.id.btnThem);
        buttonSua = findViewById(R.id.btnSua);
        buttonXoa = findViewById(R.id.btnXoa);
        listViewDSSP = findViewById(R.id.list_ViewSanPham);

        produtcDAO = new ProductDAO(context);
        productArrayList = produtcDAO.getListProduct();

        CustomAdapter customAdapter = new CustomAdapter(context, productArrayList);
        listViewDSSP.setAdapter(customAdapter);

        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                product.setMaSP(Integer.parseInt(editTextMaSP.getText().toString()));
                product.setTensP(editTextTenSP.getText().toString());
                product.setSoSP(Integer.parseInt(editTextSoSP.getText().toString()));
                product.setGia(Double.parseDouble(editTextGiaSP.getText().toString()));
                produtcDAO.addProduct(product);
                Toast.makeText(MainActivity.this,"Thêm sản phẩm thành công",Toast.LENGTH_LONG).show();
                productArrayList.clear();
                productArrayList.addAll(produtcDAO.getListProduct());
                customAdapter.notifyDataSetChanged();
            }
        });
        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                product.setMaSP(Integer.parseInt(editTextMaSP.getText().toString()));
                product.setTensP(editTextTenSP.getText().toString());
                product.setSoSP(Integer.parseInt(editTextSoSP.getText().toString()));
                product.setGia(Double.parseDouble(editTextGiaSP.getText().toString()));
                produtcDAO.updateProduct(product);
                Toast.makeText(MainActivity.this,"Sửa sản phẩm thành công",Toast.LENGTH_LONG).show();
                productArrayList.clear();
                productArrayList.addAll(produtcDAO.getListProduct());
                customAdapter.notifyDataSetChanged();
            }
        });
        buttonXoa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int maSP = Integer.parseInt(editTextMaSP.getText().toString());
                produtcDAO.deleteProduct(maSP);
                Toast.makeText(MainActivity.this,"Xoá sản phẩm thành công",Toast.LENGTH_LONG).show();
                productArrayList.clear();
                productArrayList.addAll(produtcDAO.getListProduct());
                customAdapter.notifyDataSetChanged();
            }
        });
    }
}