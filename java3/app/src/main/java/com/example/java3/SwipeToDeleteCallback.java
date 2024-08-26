package com.example.java3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java3.adapter.CartAdapter;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback{
    private final CartAdapter mAdapter;
    private final Drawable icon;
    private final Paint paint;

    public SwipeToDeleteCallback(CartAdapter adapter, Drawable icon) {
        super(0, ItemTouchHelper.LEFT); // Enable swipe left
        this.mAdapter = adapter;
        this.icon = icon;
        this.paint = new Paint();
        this.paint.setColor(Color.GRAY);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false; // We don't need move functionality
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT) {
            mAdapter.showDeleteAction(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        if (dX < 0) { // Swipe left
            View itemView = viewHolder.itemView;

            // Vẽ nền màu đỏ
            c.drawRect(itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom(), paint);

            // Điều chỉnh kích thước biểu tượng thùng rác lớn hơn
            float scaleFactor = 1.5f; // Tăng kích thước biểu tượng lên 1.5 lần
            int iconHeight = (int) (icon.getIntrinsicHeight() * scaleFactor);
            int iconWidth = (int) (icon.getIntrinsicWidth() * scaleFactor);

            // Vị trí biểu tượng
            int iconMargin = (itemView.getHeight() - iconHeight) / 2;
            int iconTop = itemView.getTop() + iconMargin;
            int iconBottom = iconTop + iconHeight;
            int iconLeft = itemView.getRight() - iconMargin - iconWidth;
            int iconRight = itemView.getRight() - iconMargin;

            // Thiết lập kích thước mới cho biểu tượng
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            icon.draw(c);
        }
    }
}
