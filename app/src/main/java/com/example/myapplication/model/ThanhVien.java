package com.example.myapplication.model;

public class ThanhVien {

    private int maTv;
    private String hoTenTV;
    private String namSinhTV;

    public ThanhVien() {
    }

    public ThanhVien(int maTv, String hoTenTV, String namSinhTV) {
        this.maTv = maTv;
        this.hoTenTV = hoTenTV;
        this.namSinhTV = namSinhTV;
    }

    public int getMaTv() {
        return maTv;
    }

    public void setMaTv(int maTv) {
        this.maTv = maTv;
    }

    public String getHoTenTV() {
        return hoTenTV;
    }

    public void setHoTenTV(String hoTenTV) {
        this.hoTenTV = hoTenTV;
    }

    public String getNamSinhTV() {
        return namSinhTV;
    }

    public void setNamSinhTV(String namSinhTV) {
        this.namSinhTV = namSinhTV;
    }
}
