package com.example.java2.KhoiTao;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SanPham implements Parcelable {
        private int id;
        private String ten;
        private String Gia;
        private String mo_ta;
        private String anh;
        private int id_dm;

        public SanPham(int id,String ten, String gia, String mo_ta, String anh,int id_dm) {
            this.id = id;
            this.ten = ten;
            this.Gia = gia;
            this.mo_ta = mo_ta;
            this.anh = anh;
            this.id_dm = id_dm;
        }
    public SanPham(String ten, String gia, String anh,String mo_ta) {
        this.ten = ten;
        Gia = gia;
        this.anh = anh;
        this.mo_ta = mo_ta;
    }
    // Getters and setters for imagePath

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    public int getId_dm() {
        return id_dm;
    }

    public void setId_dm(int id_dm) {
        this.id_dm = id_dm;
    }

    public String getTen() {
            return ten;
        }

        public void setTen(String ten) {
            this.ten = ten;
        }

        public String getGia() {
            return Gia;
        }

        public void setGia(String gia) {
            this.Gia = gia;
        }

        public String getMo_ta() {
            return mo_ta;
        }

        public void setMo_ta(String mo_ta) {
            this.mo_ta = mo_ta;
        }

        public String getAnh() {
            return anh;
        }

        public void setAnh(String anh) {
            this.anh = anh;
        }
    protected SanPham(Parcel in) {
        id = in.readInt();
        ten = in.readString();
        Gia = in.readString();
        mo_ta = in.readString();
        anh = in.readString();
        id_dm = in.readInt();
    }

    // Phương thức tạo ra đối tượng SanPham từ Parcel
    public static final Creator<SanPham> CREATOR = new Creator<SanPham>() {
        @Override
        public SanPham createFromParcel(Parcel in) {
            return new SanPham(in);
        }

        @Override
        public SanPham[] newArray(int size) {
            return new SanPham[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(ten);
        parcel.writeString(Gia);
        parcel.writeString(mo_ta);
        parcel.writeString(anh);
        parcel.writeInt(id_dm);
    }
}
