package com.example.myapplication.model;

import java.util.Date;

public class HoaDon {

    private  int maHD,maNV,maTV,maSP,TongTien;
    private Date NgayMua;
    public HoaDon (){}

    public HoaDon(int maHD, int maNV, int maTV, int maSP, int tongTien, Date ngayMua) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.maTV = maTV;
        this.maSP = maSP;
        TongTien = tongTien;
        NgayMua = ngayMua;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getTongTien() {
        return TongTien;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }

    public Date getNgayMua() {
        return NgayMua;
    }

    public void setNgayMua(Date ngayMua) {
        NgayMua = ngayMua;
    }
}
