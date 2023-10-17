package com.example.myapplication.model;

public class LoaiSanPham {
    private  int maloai ;
    private String tenloai;

    public  LoaiSanPham (){

    }
    public LoaiSanPham(int maloai, String tenloai) {
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
}
