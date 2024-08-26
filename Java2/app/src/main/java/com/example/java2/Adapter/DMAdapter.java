package com.example.java2.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.java2.KhoiTao.DanhMuc;
import com.example.java2.Dao.DaoDanhMuc;
import com.example.java2.R;
import com.example.java2.admin.QLDanhMuc;
import com.example.java2.admin.suaDM;

import java.util.List;

public class DMAdapter extends RecyclerView.Adapter<DMAdapter.DMViewHolder> {
    private List<DanhMuc> dmList;
    private Context context;
    private DaoDanhMuc daoDanhMuc;

    public DMAdapter(List<DanhMuc> dmList, Context context) {
        this.dmList = dmList;
        this.context = context;
        this.daoDanhMuc = new DaoDanhMuc(context);
    }

    @NonNull
    @Override
    public DMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ql_dm, parent, false);
        return new DMViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DMViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DanhMuc danhMuc = dmList.get(position);
        holder.textID.setText(String.valueOf(danhMuc.getId()));
        holder.textDM.setText(danhMuc.getTen());
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện xóa danh mục ở đây
                // Ví dụ: Gọi phương thức deleteDanhMuc(id) để xóa danh mục
                daoDanhMuc.deleteDanhMuc(danhMuc.getId());
                // Cập nhật lại danh sách danh mục và cập nhật RecyclerView
                dmList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Đã xóa danh mục", Toast.LENGTH_SHORT).show();
            }
        });

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, suaDM.class);
                i.putExtra("id", danhMuc.getId());
                i.putExtra("ten", danhMuc.getTen());
                ((QLDanhMuc)context).startActivityForResult(i, ((QLDanhMuc)context).REQUEST_CODE_SUA_DM);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dmList.size();
    }

    public void setDMList(List<DanhMuc> dmList) {
        this.dmList = dmList;
        notifyDataSetChanged();

    }

    public class DMViewHolder extends RecyclerView.ViewHolder {
        public TextView textID;
        public TextView textDM;
        public Button buttonDelete, buttonEdit;

        public DMViewHolder(View itemView) {
            super(itemView);
            textID = itemView.findViewById(R.id.text_id);
            textDM = itemView.findViewById(R.id.text_danhmuc);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteCategory);
            buttonEdit = itemView.findViewById(R.id.buttonEditCategory);
        }
    }
}
