package com.example.a21111064571_nguyenvanchuong_lab08.model;

public class Product {
    private int maSP;
    private String tenSP;
    private int soLuong;
    private double giaSP;

    public Product(int maSP, String tenSP, int soLuong, double giaSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
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
