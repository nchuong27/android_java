package com.example.a85_21111064571_nguyenvanchuong.model;

public class GV {
    private String ma;
    private String ten;
    private String ngaysinh;
    private String sdt;
    private String khoa;
    private String mon;
    private String trinhdo;

    public GV() {
    }

    public GV(String ma, String ten, String ngaysinh, String sdt, String khoa, String mon, String trinhdo) {
        this.ma = ma;
        this.ten = ten;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.khoa = khoa;
        this.mon = mon;
        this.trinhdo = trinhdo;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTrinhdo() {
        return trinhdo;
    }

    public void setTrinhdo(String trinhdo) {
        this.trinhdo = trinhdo;
    }
}
