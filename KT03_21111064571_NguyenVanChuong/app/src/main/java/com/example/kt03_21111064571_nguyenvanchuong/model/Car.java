package com.example.kt03_21111064571_nguyenvanchuong.model;

public class Car {
    private String bienSoXe;
    private String tenXe;
    private String hangXe;
    private int namSanXuat;
    private String moTaXe;
    private String tenLaiXe;

    public Car() {
    }

    public Car(String bienSoXe, String tenXe, String hangXe, int namSanXuat, String moTaXe, String tenLaiXe) {
        this.bienSoXe = bienSoXe;
        this.tenXe = tenXe;
        this.hangXe = hangXe;
        this.namSanXuat = namSanXuat;
        this.moTaXe = moTaXe;
        this.tenLaiXe = tenLaiXe;
    }

    public String getBienSoXe() {
        return bienSoXe;
    }

    public void setBienSoXe(String bienSoXe) {
        this.bienSoXe = bienSoXe;
    }

    public String getTenXe() {
        return tenXe;
    }

    public void setTenXe(String tenXe) {
        this.tenXe = tenXe;
    }

    public String getHangXe() {
        return hangXe;
    }

    public void setHangXe(String hangXe) {
        this.hangXe = hangXe;
    }

    public int getNamSanXuat() {
        return namSanXuat;
    }

    public void setNamSanXuat(int namSanXuat) {
        this.namSanXuat = namSanXuat;
    }

    public String getMoTaXe() {
        return moTaXe;
    }

    public void setMoTaXe(String moTaXe) {
        this.moTaXe = moTaXe;
    }

    public String getTenLaiXe() {
        return tenLaiXe;
    }

    public void setTenLaiXe(String tenLaiXe) {
        this.tenLaiXe = tenLaiXe;
    }
}
