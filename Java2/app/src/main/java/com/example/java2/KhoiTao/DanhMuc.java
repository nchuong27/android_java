package com.example.java2.KhoiTao;

public class DanhMuc {
    private int id;
    private String ten;

    public DanhMuc(int id, String ten) {
        this.id = id;
        this.ten = ten;
    }
    public DanhMuc(String ten){
        this.ten = ten;
    }
    @Override
    public String toString() {
        return this.ten; // Trả về tên của đối tượng DanhMuc
    }


    public int getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }
}
