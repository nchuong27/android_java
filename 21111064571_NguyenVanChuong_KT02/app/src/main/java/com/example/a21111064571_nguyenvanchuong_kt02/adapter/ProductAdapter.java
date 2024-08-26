package com.example.a21111064571_nguyenvanchuong_kt02.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a21111064571_nguyenvanchuong_kt02.R;
import com.example.a21111064571_nguyenvanchuong_kt02.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }
    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_item_view,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewItemID,textViewItemProductName,textViewNha,textViewItemQuantityProduct,textViewItemPriceProduct;

        private Button buttonEdit, buttonDelete;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemID = itemView.findViewById(R.id.txtMa);
            textViewItemProductName=itemView.findViewById(R.id.txtTen);
            textViewNha=itemView.findViewById(R.id.txtNha);
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
        public void bind(Product product) {
            textViewItemID.setText(String.valueOf(product.getMaSP()));
            textViewItemProductName.setText(product.getTenSP());
            textViewNha.setText(product.getNhaXB());
            textViewItemQuantityProduct.setText(String.valueOf(product.getSoLuong()));
            textViewItemPriceProduct.setText(String.valueOf(product.getGiaSP()));
        }


    }
}
