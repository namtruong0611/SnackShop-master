package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.LoaiSanPham;

import java.util.ArrayList;

public class LoaiSanPhamDAO {
    private Context context;
    private SQLiteDatabase database;
    private DataBases sqLite;

    public LoaiSanPhamDAO(Context context){
        this.context = context;
        sqLite = new DataBases(context);
        database = sqLite.getWritableDatabase();
    }
    public int Insert(LoaiSanPham loaiSP){
        ContentValues values = new ContentValues();
        values.put("tenLoai",loaiSP.getTenloai());
        long kq = database.insert("LOAISANPHAM",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ArrayList<LoaiSanPham> getAllLoaiSP(){
        Cursor cursor = database.rawQuery("SELECT *FROM LOAISANPHAM",null);
        ArrayList<LoaiSanPham> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new LoaiSanPham(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(LoaiSanPham loaiSP){
        int kq = database.delete("LOAISANPHAM","maLOAISP=?",new String[]{String.valueOf(loaiSP.getMaloai())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(LoaiSanPham loaiSP){
        ContentValues values = new ContentValues();
        values.put("tenLoai",loaiSP.getTenloai());
        int kq = database.update("LOAISANPHAM",values,"maLOAISP=?",new String[]{String.valueOf(loaiSP.getMaloai())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public LoaiSanPham getID(int id){
        Cursor cursor = database.rawQuery("SELECT * FROM LOAISANPHAM WHERE maLOAISP= ?",new String[]{String.valueOf(id)});
        ArrayList<LoaiSanPham> list = new ArrayList<>();
        cursor.moveToFirst();
        list.add(new LoaiSanPham(cursor.getInt(0),cursor.getString(1)));
        return list.get(0);
    }

}
