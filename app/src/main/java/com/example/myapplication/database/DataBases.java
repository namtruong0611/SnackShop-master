package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBases extends SQLiteOpenHelper {


    public  static final  String HoaDon = "create table HOADON ("+
            "maHD INTEGER primary key AUTOINCREMENT," +
            "maNV INTEGER REFERENCES NHANVIEN(maNV)," +
            "maTV INTEGER REFERENCES THANHVIEN(maTV)," +
            "maSP INTEGER REFERENCES SANPHAM(maSP)," +
            "TongTien INTEGER not null ," +
            "Ngay DATE not null );";
    public  static final String LoaiSanPham = "create table LOAISANPHAM(" +
            "maLOAISP INTEGER primary key AUTOINCREMENT," +
            "tenLoai TEXT not null );";
    public  static final String SanPham = "create table SANPHAM(" +
            "maSP INTEGER primary key AUTOINCREMENT," +
            "tenSP TEXT not null ," +
            "Gia INTEGER not null ," +
            "maLOAISP INTEGER REFERENCES LOAISAMPHAM(maLOAISP));";


    public  static final String ThanhVien = "create table THANHVIEN(" +
            "maTV INTEGER primary key AUTOINCREMENT," +
            "hoTen TEXT not null ," +
            "namSinh TEXT not null );";
    public  static  final  String NhanVien = "create table NHANVIEN (" +
            "maNV TEXT primary key," +
            "tenNV TEXT not null," +
            "pass TEXT not null )";
    public  static  final  String NHANVIEN_INSERT = "INSERT INTO NHANVIEN(maNV,tenNV,pass)VALUES\n"+
            "('admin','admin','123')";
    public DataBases(@Nullable Context context ) {
        super(context, "snackshop.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HoaDon);
        db.execSQL(LoaiSanPham);
        db.execSQL(SanPham);
        db.execSQL(ThanhVien);
        db.execSQL(NhanVien);
        db.execSQL(NHANVIEN_INSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS HOADON");
        db.execSQL("DROP TABLE IF EXISTS LOAISANPHAM");
        db.execSQL("DROP TABLE IF EXISTS NHANVIEN");
        db.execSQL("DROP TABLE IF EXISTS SANPHAM");
        db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
        onCreate(db);
    }
}
