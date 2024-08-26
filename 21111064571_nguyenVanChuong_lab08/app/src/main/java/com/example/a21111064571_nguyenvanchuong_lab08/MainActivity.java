package com.example.a21111064571_nguyenvanchuong_lab08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a21111064571_nguyenvanchuong_lab08.DAO.ProductDao;
import com.example.a21111064571_nguyenvanchuong_lab08.adapter.ProductAdapter;
import com.example.a21111064571_nguyenvanchuong_lab08.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTextMaSP, editTextTenSP, editTextSoSP, editTextGiaSP;
    private Button buttonThem;
    private RecyclerView recyclerViewListProduct;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private ProductDao productDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMaSP = findViewById(R.id.edtMa);
        editTextTenSP = findViewById(R.id.edtTen);
        editTextSoSP = findViewById(R.id.edtSL);
        editTextGiaSP = findViewById(R.id.edtGia);
        buttonThem = findViewById(R.id.btnThem);
        recyclerViewListProduct = findViewById(R.id.rcproduct);

        productDao = new ProductDao(this);
        productList = new ArrayList<>();

        productAdapter = new ProductAdapter(this, productList);
        recyclerViewListProduct.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListProduct.setAdapter(productAdapter);

        productDao.open();

        loadProductList();

        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int maSP = Integer.parseInt(editTextMaSP.getText().toString());
                String tenSP = editTextTenSP.getText().toString();
                int soSP = Integer.parseInt(editTextSoSP.getText().toString());
                double giaSP = Double.parseDouble(editTextGiaSP.getText().toString());

                Product product = new Product(maSP, tenSP, soSP, giaSP);

                productDao.open();


                long result = productDao.addProduct(product);
                if (result != -1) {
                    productList.add(product);
                    productAdapter.notifyDataSetChanged();
                }

                productDao.close();

                editTextMaSP.setText("");
                editTextTenSP.setText("");
                editTextSoSP.setText("");
                editTextGiaSP.setText("");
            }
        });
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                Product productToEdit = productList.get(position);
                showEditProductDialog(productToEdit, position);
            }
            @Override
            public void onDeleteClick(int position) {
                showDeleteConfirmationDialog(position);
            }
        });
    }

    private void loadProductList() {
        productList.clear();
        productList.addAll(productDao.getListProduct());
        productAdapter.notifyDataSetChanged();
        productDao.close();
    }

    private void showEditProductDialog(Product productToEdit, int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_product, null);
        dialogBuilder.setView(dialogView);
        final TextView textViewMaSP = dialogView.findViewById(R.id.textViewMaSP);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextTenSP = dialogView.findViewById(R.id.editTextTenSP);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextSoSP = dialogView.findViewById(R.id.editTextSoSP);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextGiaSP = dialogView.findViewById(R.id.editTextGiaSP);

        textViewMaSP.setText(String.valueOf(productToEdit.getMaSP()));
        editTextTenSP.setText(productToEdit.getTenSP());
        editTextSoSP.setText(String.valueOf(productToEdit.getSoLuong()));
        editTextGiaSP.setText(String.valueOf(productToEdit.getGiaSP()));

        dialogBuilder.setTitle("Sửa sản phẩm");

        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                int maSP = Integer.parseInt(textViewMaSP.getText().toString());
                String tenSP = editTextTenSP.getText().toString();
                int soSP = Integer.parseInt(editTextSoSP.getText().toString());

                double giaSP = Double.parseDouble(editTextGiaSP.getText().toString());

                Product editedProduct = new Product(maSP, tenSP, soSP, giaSP);

                productDao.open();
                boolean updated = productDao.updateProduct(editedProduct);
                productDao.close();

                if (updated) {
                    productList.set(position, editedProduct);
                    productAdapter.notifyItemChanged(position);
                    Toast.makeText(MainActivity.this, "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Không thể sửa sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa sản phẩm");
        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                productDao.open();
                boolean deleted = productDao.deleteProduct(productList.get(position).getMaSP());
                productDao.close();

                if (deleted) {
                    productList.remove(position);
                    productAdapter.notifyItemRemoved(position);
                    Toast.makeText(MainActivity.this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Không thể xóa sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }
}