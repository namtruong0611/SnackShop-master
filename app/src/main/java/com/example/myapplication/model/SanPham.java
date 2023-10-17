package com.example.myapplication.model;

import java.util.Date;

public class SanPham {
    private int maSp;
    private String tenSP;
    private int giaSp;
    private int maLoaiSp;
    private int soLuong;


    public SanPham() {
    }

    public SanPham(int maSp, String tenSP, int giaSp, int maLoaiSp ) {
        this.maSp = maSp;
        this.tenSP = tenSP;
        this.giaSp = giaSp;
        this.maLoaiSp = maLoaiSp;


    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(int giaSp) {
        this.giaSp = giaSp;
    }

    public int getMaLoaiSp() {
        return maLoaiSp;
    }

    public void setMaLoaiSp(int maLoaiSp) {
        this.maLoaiSp = maLoaiSp;
    }



}
