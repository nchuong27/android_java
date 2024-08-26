package com.example.kt4_21111064571_nguyenvanchuong;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kt4_21111064571_nguyenvanchuong.DAO.ProductDAO;
import com.example.kt4_21111064571_nguyenvanchuong.adapter.ProductAdapter;
import com.example.kt4_21111064571_nguyenvanchuong.model.Product;
import com.example.kt4_21111064571_nguyenvanchuong.model.ProductDetail;
import com.example.kt4_21111064571_nguyenvanchuong.service.ProductService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTextProductName, editTextProductDescription, editTextProductPrice, editTextProductColor;

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
        editTextProductName = findViewById(R.id.edtHT);
        editTextProductDescription = findViewById(R.id.edtBD);
        editTextProductPrice = findViewById(R.id.edtNgay);
        editTextProductColor = findViewById(R.id.editGio);
        buttonAddProduct = findViewById(R.id.btnThem);
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
        String bietdanh = editTextProductDescription.getText().toString().trim();
        String ngay = editTextProductPrice.getText().toString().trim();
        String gio = editTextProductColor.getText().toString().trim();

        if (name.isEmpty() || bietdanh.isEmpty() || ngay.isEmpty() || gio.isEmpty()) {
            Toast.makeText(this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra trùng lặp ngày và giờ
        for (Product product : productList) {
            if (product.getProductDetail() != null && product.getProductDetail().getNgay().equals(ngay)
                    && product.getProductDetail().getGio().equals(gio)) {
                Toast.makeText(this, "Ngày và giờ hẹn đã tồn tại", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Product product = new Product(0, name);
        ProductDetail productDetail = new ProductDetail(0, 0, bietdanh, ngay, gio);
        long result = productService.addProductWithDetail(product, productDetail);

        if (result != -1) {
            loadProducts();
            clearFields();
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show();

            // Lập lịch thông báo nhắc nhở
            String[] timeParts = gio.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            Intent reminderIntent = new Intent(this, ProductReminderReceiver.class);
            reminderIntent.putExtra("product_name", name);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadProducts() {
        productList.clear();
        productList.addAll(productService.getAllProducts());
        productAdapter.notifyDataSetChanged();
    }

    private void clearFields() {
        editTextProductName.setText("");
        editTextProductDescription.setText("");
        editTextProductPrice.setText("");
        editTextProductColor.setText("");
    }
    private void showProductDetailDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chi tiết nguoi iu");
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_product_detail, null);
        builder.setView(dialogView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewProductName = dialogView.findViewById(R.id.textViewProductName);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewProductDescription = dialogView.findViewById(R.id.textViewProductDescription);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewProductPrice = dialogView.findViewById(R.id.textViewProductPrice);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewProductColor = dialogView.findViewById(R.id.textViewProductColor);
        textViewProductName.setText("Tên sản phẩm: " + product.getName());
        if (product.getProductDetail() != null) {
            textViewProductDescription.setText("Biệt danh: " + product.getProductDetail().getBietDanh());
            textViewProductPrice.setText("Ngày: " + product.getProductDetail().getNgay());
            textViewProductColor.setText("Giờ: " + product.getProductDetail().getGio());
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
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextProductDescription = dialogView.findViewById(R.id.editTextProductBietDanh);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextProductPrice = dialogView.findViewById(R.id.editTextProductNgay);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextProductColor = dialogView.findViewById(R.id.editTextProductGio);
        editTextProductName.setText(productToEdit.getName());
        editTextProductDescription.setText(productToEdit.getProductDetail().getBietDanh());
        editTextProductPrice.setText(productToEdit.getProductDetail().getNgay());
        editTextProductColor.setText(productToEdit.getProductDetail().getGio());

        dialogBuilder.setTitle("Sửa người hẹn");
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = editTextProductName.getText().toString();
                String bietdanh = editTextProductDescription.getText().toString();
                String ngay = editTextProductPrice.getText().toString();
                String gio = editTextProductColor.getText().toString();

                ProductDetail productDetail = new ProductDetail(0, 0, bietdanh, ngay, gio);
                Product editedProduct = new Product(productToEdit.getId(), name);
                editedProduct.setProductDetail(productDetail);

                productService.updateProduct(editedProduct);
                productList.set(position, editedProduct);
                productAdapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_LONG).show();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }
    private void deleteProduct(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa sản phẩm từ cơ sở dữ liệu
                productService.deleteProduct(product.getId());
                // Cập nhật lại danh sách sản phẩm
                loadProducts();
                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_LONG).show();
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