package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.SanPham;

import java.util.ArrayList;

public class SanPhamDAO {
    private Context mContext;
    private SQLiteDatabase database;
    private  DataBases sqLite;
    public SanPhamDAO(Context context){
        this.mContext = context;
        sqLite = new DataBases(mContext);
        database =  sqLite.getWritableDatabase();
    }
    public int Insert(SanPham sp){
        ContentValues values = new ContentValues();
        values.put("tenSP",sp.getTenSP());
        values.put("Gia",sp.getGiaSp());
        values.put("maLoaiSP",sp.getMaLoaiSp());


        long kq = database.insert("SANPHAM",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ArrayList<SanPham> getAllSP(){
        Cursor cursor = database.rawQuery("SELECT *FROM SANPHAM",null);
        ArrayList<SanPham> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new SanPham(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)  ));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(SanPham sp){
        int kq = database.delete("SANPHAM","maSP=?",new String[]{String.valueOf(sp.getMaSp())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(SanPham sp){
        ContentValues values = new ContentValues();
        values.put("tenSP",sp.getTenSP());
        values.put("Gia",sp.getGiaSp());
        values.put("maLoaiSP",sp.getMaLoaiSp());


        int kq = database.update("SANPHAM",values,"maSP=?",new String[]{String.valueOf(sp.getMaSp())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public SanPham getID(int id){
        Cursor cursor = database.rawQuery("SELECT * FROM SANPHAM WHERE maSP=?",new String[]{String.valueOf(id)});
        ArrayList<SanPham> list = new ArrayList<>();
        cursor.moveToFirst();
        list.add(new SanPham(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)  ));
        SanPham sp = list.get(0);
        return sp;

    }
}
