package com.example.java2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.java2.KhoiTao.SanPham;
import com.example.java2.R;

import java.util.List;

public class SPuser extends RecyclerView.Adapter<SPuser.SanPhamViewHolder>{
    private Context context;
    private List<SanPham> spList;

    public SPuser(Context context, List<SanPham> spList) {
        this.context = context;
        this.spList = spList;
    }
    // Khai báo một interface để xử lý sự kiện khi người dùng nhấn vào một sản phẩm
    public interface OnItemClickListener {
        void onItemClick(SanPham sanPham);
    }

    // Biến để lưu trữ listener
    private OnItemClickListener mListener;

    // Phương thức để thiết lập listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    @NonNull
    @Override
    public SPuser.SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_listsp_item,parent,false);
        return new SPuser.SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham  = spList.get(position);
        holder.bind(sanPham);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(sanPham);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return spList.size();
    }
    public class SanPhamViewHolder extends RecyclerView.ViewHolder {
        private TextView txtSP,txtGia;
        private ImageView imgSP;


        public SanPhamViewHolder(View itemView) {
            super(itemView);
            imgSP = itemView.findViewById(R.id.imgAnh);
            txtSP = itemView.findViewById(R.id.txtSP);
            txtGia = itemView.findViewById(R.id.txtGia);


        }

        public void bind(SanPham sanPham) {
            txtSP.setText(sanPham.getTen());
            txtGia.setText(sanPham.getGia());

            Glide.with(context)
                    .load(sanPham.getAnh())
                    .into(imgSP);
        }
    }
}
