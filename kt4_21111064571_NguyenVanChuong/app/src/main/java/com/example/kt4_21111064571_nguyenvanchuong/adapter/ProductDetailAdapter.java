package com.example.kt4_21111064571_nguyenvanchuong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kt4_21111064571_nguyenvanchuong.model.ProductDetail;

import java.util.List;

public class ProductDetailAdapter extends ArrayAdapter<ProductDetail> {
    private Context context;
    private List<ProductDetail> productDetails;
    public ProductDetailAdapter(Context context, List<ProductDetail> productDetails) {
        super(context, 0, productDetails);
        this.context = context;
        this.productDetails = productDetails;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        ProductDetail currentDetail = productDetails.get(position);
        TextView description = convertView.findViewById(android.R.id.text1);
        description.setText(currentDetail.getBietDanh());
        TextView priceAndColor = convertView.findViewById(android.R.id.text2);
        priceAndColor.setText("Price: $" + currentDetail.getNgay() + " | Color: " + currentDetail.getGio());
        return convertView;
    }

}
