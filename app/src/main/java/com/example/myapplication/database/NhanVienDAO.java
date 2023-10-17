package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.NhanVien;

import java.util.ArrayList;

public class NhanVienDAO {
    private Context context;
    private SQLiteDatabase database;
    private DataBases sqLite;

    public NhanVienDAO(Context context) {
        this.context = context;
        sqLite = new DataBases(context);
        database = sqLite.getWritableDatabase();
    }
    public int Insert(NhanVien NV){
        ContentValues values = new ContentValues();
        values.put("maNV",NV.getMaNV());
        values.put("tenNV",NV.getTenNV());
        values.put("pass",NV.getPass());
        long kq = database.insert("NHANVIEN",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;

    }
    public   ArrayList<NhanVien> getAllNhanVien(){
        Cursor cursor = database.rawQuery("SELECT *FROM NHANVIEN",null);
        ArrayList<NhanVien> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new NhanVien(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(NhanVien NV){
        int kq = database.delete("NHANVIEN","maNV=?",new String[]{String.valueOf(NV.getMaNV())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(NhanVien NV){
        ContentValues values = new ContentValues();

        values.put("tenNV",NV.getTenNV());
        values.put("pass",NV.getPass());
        int kq = database.update("NHANVIEN",values,"tenNV=?",new String[]{String.valueOf(NV.getMaNV())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public NhanVien getId(String id) {

        Cursor cursor = database.rawQuery("SELECT * FROM NHANVIEN WHERE maNV=?", new String[]{id});
        ArrayList<NhanVien> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            list.add(new NhanVien(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }

        if (!list.isEmpty()) {
            return list.get(0);
        } else {

            return null;
        }
    }
}
