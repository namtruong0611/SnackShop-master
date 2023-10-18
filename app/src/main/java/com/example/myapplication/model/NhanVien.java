package com.example.myapplication.model;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private String Pass;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String pass) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.Pass = pass;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        this.Pass = pass;
    }
}

