package com.example.java2.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.java2.Adapter.DMuser;
import com.example.java2.Adapter.SPuser;
import com.example.java2.Dao.DaoDanhMuc;
import com.example.java2.Dao.DaoSanPham;
import com.example.java2.KhoiTao.DanhMuc;
import com.example.java2.KhoiTao.SanPham;
import com.example.java2.banner.Photo;
import com.example.java2.banner.PhotoAdapter;
import com.example.java2.R;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private Handler handler;
    private Runnable runnable;
    private RecyclerView recyclerViewDanhMuc,recyclerViewSanPham;
    private DMuser dMuser;
    private List<DanhMuc> dmList;
    private DaoDanhMuc daoDanhMuc;
    private SPuser sPuser;
    private List<SanPham> spList;
    private DaoSanPham daoSanPham;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        viewPager = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circleindicator);
        photoAdapter = new PhotoAdapter(this,getListPhoto());

        recyclerViewDanhMuc = view.findViewById(R.id.recyclerViewDanhMuc);
        recyclerViewSanPham = view.findViewById(R.id.recyclerViewSanPham);
        dmList = new ArrayList<>();
        daoDanhMuc = new DaoDanhMuc(getContext());
        dmList = daoDanhMuc.getAllDanhMuc();
        dMuser = new DMuser(getContext(), dmList);
        GridLayoutManager gridLayoutManager =new GridLayoutManager(getContext(),3);
        recyclerViewDanhMuc.setLayoutManager(gridLayoutManager);
        recyclerViewDanhMuc.setAdapter(dMuser);
        dMuser.notifyDataSetChanged();

        spList = new ArrayList<>();
        daoSanPham = new DaoSanPham(getContext());
        spList = daoSanPham.getAllSanPham();
        sPuser = new SPuser(getContext(), spList);
        GridLayoutManager gridLayoutManager1 =new GridLayoutManager(getContext(),2);
        recyclerViewSanPham.setLayoutManager(gridLayoutManager1);
        recyclerViewSanPham.setAdapter(sPuser);
        sPuser.notifyDataSetChanged();
        sPuser.setOnItemClickListener(new SPuser.OnItemClickListener() {
            @Override
            public void onItemClick(SanPham sanPham) {
                // Hiển thị chi tiết sản phẩm khi người dùng nhấn vào
                showProductDetailActivity(sanPham);
            }
        });

        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        // Tạo Handler và Runnable
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = photoAdapter.getCount();
                int nextItem = (currentItem + 1) % totalItems;
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 3000); // Chạy lại Runnable sau 3 giây
            }
        };
        // Bắt đầu chuyển đổi ảnh tự động
        handler.postDelayed(runnable, 3000);
        return view;
    }

    private void showProductDetailActivity(SanPham sanPham) {
        Intent intent = new Intent(getContext(), ChiTietSanPham.class);
        // Đính kèm dữ liệu (nếu cần)
        intent.putExtra("SanPham",sanPham);
        // Chuyển đến ChiTietSanPhamActivity
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
    }
    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.slide1));
        list.add(new Photo(R.drawable.slide2));
        list.add(new Photo(R.drawable.slide3));
        return list;
    }
}