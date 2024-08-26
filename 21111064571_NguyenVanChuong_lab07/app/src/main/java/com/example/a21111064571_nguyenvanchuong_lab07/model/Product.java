package com.example.a21111064571_nguyenvanchuong_lab07.model;

public class Product {
    private int maSP;
    private String tensP;
    private int soSP;
    private double Gia;

    public Product(int maSP, String tensP, int soSP, double gia) {
        this.maSP = maSP;
        this.tensP = tensP;
        this.soSP = soSP;
        this.Gia = gia;
    }

    public Product() {
        this.maSP = 0;
        this.tensP = "";
        this.soSP = 0;
        this.Gia = 0.0f;
    }


    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTensP() {
        return tensP;
    }

    public void setTensP(String tensP) {
        this.tensP = tensP;
    }

    public int getSoSP() {
        return soSP;
    }

    public void setSoSP(int soSP) {
        this.soSP = soSP;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double gia) {
        Gia = gia;
    }
}
