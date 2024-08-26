package com.example.a21111064571_nguyenvanchuong_lab09;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.example.a21111064571_nguyenvanchuong_lab09.DAO.ProductDAO;
import com.example.a21111064571_nguyenvanchuong_lab09.adapter.ProductAdapter;
import com.example.a21111064571_nguyenvanchuong_lab09.model.Product;
import com.example.a21111064571_nguyenvanchuong_lab09.model.ProductDetail;
import com.example.a21111064571_nguyenvanchuong_lab09.service.ProductService;

public class MainActivity extends AppCompatActivity {
    private EditText editTextProductName, editTextProductQuantity, editTextProductDescription, editTextProductPrice, editTextProductColor;

    private Button buttonAddProduct;
    private ListView listViewProducts;
    private ProductDAO productDAO;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private ProductService productService;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductQuantity = findViewById(R.id.editTextProductQuantity);
        editTextProductDescription = findViewById(R.id.editTextProductDescription);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        editTextProductColor = findViewById(R.id.editTextProductColor);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        listViewProducts = findViewById(R.id.listViewProducts);
        productService = new ProductService(this);
        productService.open();
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        listViewProducts.setAdapter(productAdapter);
        registerForContextMenu(listViewProducts);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = productList.get(position);
                showProductDetailDialog(product);
            }
        });
        loadProducts();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        productDAO.close();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewProducts) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Tùy chọn");
            menu.add(Menu.NONE, 1, Menu.NONE, "Sửa");
            menu.add(Menu.NONE, 2, Menu.NONE, "Xóa");
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {
            case 1:
                editProduct(productList.get(position), position);
                return true;
            case 2:
                deleteProduct(productList.get(position));
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }
    private void addProduct() {
        String name = editTextProductName.getText().toString().trim();
        String quantityStr = editTextProductQuantity.getText().toString().trim();
        String description = editTextProductDescription.getText().toString().trim();
        String priceStr = editTextProductPrice.getText().toString().trim();
        String color = editTextProductColor.getText().toString().trim();
        if (name.isEmpty() || quantityStr.isEmpty() || description.isEmpty()

                || priceStr.isEmpty() || color.isEmpty()) {
            Toast.makeText(this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        int quantity = Integer.parseInt(quantityStr);
        double price = Double.parseDouble(priceStr);
        Product product = new Product(0, name, quantity);
        ProductDetail productDetail = new ProductDetail(0, 0, description, price, color);
        long result = productService.addProductWithDetail(product, productDetail);
        if (result != -1) {
            loadProducts();
            clearFields();
            Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadProducts() {
        productList.clear();
        productList.addAll(productService.getAllProducts());
        productAdapter.notifyDataSetChanged();
    }
    private void clearFields() {
        editTextProductName.setText("");
        editTextProductQuantity.setText("");
        editTextProductDescription.setText("");
        editTextProductPrice.setText("");
        editTextProductColor.setText("");
    }
    private void showProductDetailDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chi tiết sản phẩm");
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_product_detail, null);
        builder.setView(dialogView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewProductName = dialogView.findViewById(R.id.textViewProductName);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewProductQuantity = dialogView.findViewById(R.id.textViewProductQuantity);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewProductDescription = dialogView.findViewById(R.id.textViewProductDescription);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewProductPrice = dialogView.findViewById(R.id.textViewProductPrice);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewProductColor = dialogView.findViewById(R.id.textViewProductColor);
        textViewProductName.setText("Tên sản phẩm: " + product.getName());
        textViewProductQuantity.setText("Số lượng: " + String.valueOf(product.getQuantity()));
        if (product.getProductDetail() != null) {
            textViewProductDescription.setText("Mô tả: " + product.getProductDetail().getDescription());
            textViewProductPrice.setText("Giá: " + String.valueOf(product.getProductDetail().getPrice()));
            textViewProductColor.setText("Màu sắc: " + product.getProductDetail().getColor());
        }
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void editProduct(Product productToEdit, int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_product, null);
        dialogBuilder.setView(dialogView);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextProductName = dialogView.findViewById(R.id.editTextProductName);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextProductQuantity = dialogView.findViewById(R.id.editTextProductQuantity);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextProductDescription = dialogView.findViewById(R.id.editTextProductDescription);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextProductPrice = dialogView.findViewById(R.id.editTextProductPrice);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextProductColor = dialogView.findViewById(R.id.editTextProductColor);
        editTextProductName.setText(productToEdit.getName());
        editTextProductQuantity.setText(String.valueOf(productToEdit.getQuantity()));
        editTextProductDescription.setText(productToEdit.getProductDetail().getDescription());
        editTextProductPrice.setText(String.valueOf(productToEdit.getProductDetail().getPrice()));
        editTextProductColor.setText(productToEdit.getProductDetail().getColor());

        dialogBuilder.setTitle("Sửa sản phẩm");
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = editTextProductName.getText().toString();
                int quantity = Integer.parseInt(editTextProductQuantity.getText().toString());
                String description = editTextProductDescription.getText().toString();
                double price = Double.parseDouble(editTextProductPrice.getText().toString());
                String color = editTextProductColor.getText().toString();

                ProductDetail productDetail = new ProductDetail(0, 0, description, price, color);
                Product editedProduct = new Product(productToEdit.getId(), name, quantity);
                editedProduct.setProductDetail(productDetail);

                productService.updateProduct(editedProduct);
                productList.set(position, editedProduct);
                productAdapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Sửa sản phẩm thành công", Toast.LENGTH_LONG).show();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }
    private void deleteProduct(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa sản phẩm từ cơ sở dữ liệu
                productService.deleteProduct(product.getId());
                // Cập nhật lại danh sách sản phẩm
                loadProducts();
                Toast.makeText(MainActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Không thực hiện thao tác xóa
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}