package com.example.a21111064571_nguyenvanchuong_kt02.model;

public class Product {
    private int maSP;
    private String tenSP;
    private String nhaXB;
    private int soLuong;
    private double giaSP;

    public Product(int maSP, String tenSP,String nhaXB, int soLuong, double giaSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.nhaXB = nhaXB;
        this.soLuong = soLuong;
        this.giaSP = giaSP;
    }

    public Product() {}

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }


    public String getNhaXB() {
        return nhaXB;
    }

    public void setNhaXB(String nhaXB) {
        this.nhaXB = nhaXB;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(double giaSP) {
        this.giaSP = giaSP;
    }
}
