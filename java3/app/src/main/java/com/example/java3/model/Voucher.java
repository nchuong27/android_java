package com.example.java3.model;

public class Voucher {
    private int id_vc;
    private String name_vc;
    private String des_vc;
    private int giam_gia;
    private long toi_thieu;

    public Voucher(int id_vc, String name_vc, String des_vc, int giam_gia, long toi_thieu) {
        this.id_vc = id_vc;
        this.name_vc = name_vc;
        this.des_vc = des_vc;
        this.giam_gia = giam_gia;
        this.toi_thieu = toi_thieu;
    }

    public int getId_vc() {
        return id_vc;
    }

    public void setId_vc(int id_vc) {
        this.id_vc = id_vc;
    }

    public String getName_vc() {
        return name_vc;
    }

    public void setName_vc(String name_vc) {
        this.name_vc = name_vc;
    }

    public String getDes_vc() {
        return des_vc;
    }

    public void setDes_vc(String des_vc) {
        this.des_vc = des_vc;
    }

    public int getGiam_gia() {
        return giam_gia;
    }

    public void setGiam_gia(int giam_gia) {
        this.giam_gia = giam_gia;
    }

    public long getToi_thieu() {
        return toi_thieu;
    }

    public void setToi_thieu(long toi_thieu) {
        this.toi_thieu = toi_thieu;
    }
}
