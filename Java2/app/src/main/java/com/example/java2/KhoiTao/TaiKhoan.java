package com.example.java2.KhoiTao;

public class TaiKhoan {
    private int id;
    private String email;
    private String password;

    public TaiKhoan(int id, String email,String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
