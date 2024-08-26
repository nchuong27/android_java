package com.example.kt4_21111064571_nguyenvanchuong.model;

public class ProductDetail {
    private int id;
    private int productId;
    private String bietDanh;
    private String Ngay;
    private String Gio;

    public ProductDetail(int id, int productId,String bietDanh, String Ngay,String Gio) {
        this.id = id;
        this.productId = productId;
        this.bietDanh = bietDanh;
        this.Ngay = Ngay;
        this.Gio = Gio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getBietDanh() {
        return bietDanh;
    }

    public void setBietDanh(String bietDanh) {
        this.bietDanh = bietDanh;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getGio() {
        return Gio;
    }

    public void setGio(String gio) {
        Gio = gio;
    }
}
