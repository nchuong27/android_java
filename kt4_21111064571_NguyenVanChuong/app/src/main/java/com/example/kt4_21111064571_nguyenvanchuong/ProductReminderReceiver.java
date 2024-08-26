package com.example.kt4_21111064571_nguyenvanchuong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ProductReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String productName = intent.getStringExtra("product_name");
        Toast.makeText(context, "Nhắc nhở: Đã đến giờ hẹn cho sản phẩm " + productName, Toast.LENGTH_LONG).show();
    }
}