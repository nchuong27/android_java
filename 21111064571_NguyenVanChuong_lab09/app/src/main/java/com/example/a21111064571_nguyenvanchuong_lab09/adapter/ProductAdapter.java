package com.example.a21111064571_nguyenvanchuong_lab09.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a21111064571_nguyenvanchuong_lab09.model.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> products;
    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
//simple_list_item_2 là layout sẵn có, chỉ cần gọi ra để sử dụng
        }
        Product currentProduct = products.get(position);
        TextView productName = convertView.findViewById(android.R.id.text1);
        productName.setText(currentProduct.getName());
        TextView productQuantity = convertView.findViewById(android.R.id.text2);
        productQuantity.setText("Quantity: " + currentProduct.getQuantity());
        return convertView;
    }

}
