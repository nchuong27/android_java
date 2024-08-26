package com.example.a21111064571_nguyenvanchuong_kt2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a21111064571_nguyenvanchuong_kt2.R;
import com.example.a21111064571_nguyenvanchuong_kt2.model.Book;


import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private List<Book> bookList;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }
    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_item_view,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewItemID,textViewItemProductName,textViewItemQuantityProduct,textViewItemPriceProduct,textViewNXB;

        private Button buttonEdit, buttonDelete;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemID = itemView.findViewById(R.id.txtMa);
            textViewItemProductName=itemView.findViewById(R.id.txtTen);
            textViewNXB=itemView.findViewById(R.id.txtNXB);
            textViewItemQuantityProduct=itemView.findViewById(R.id.txtSL);
            textViewItemPriceProduct=itemView.findViewById(R.id.txtGia);
            buttonEdit = itemView.findViewById(R.id.btnSua);
            buttonDelete = itemView.findViewById(R.id.btnXoa);

            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
        public void bind(Book book) {
            textViewItemID.setText(String.valueOf(book.getMaSP()));
            textViewItemProductName.setText(book.getTenSP());
            textViewNXB.setText(book.getNhaXB());
            textViewItemQuantityProduct.setText(String.valueOf(book.getSoLuong()));
            textViewItemPriceProduct.setText(String.valueOf(book.getGiaSP()));
        }


    }
}
