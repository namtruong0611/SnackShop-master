package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVIenDAO {
    private Context mContext;
    private SQLiteDatabase database;
    private  DataBases sqLite;

    public ThanhVIenDAO(Context mContext){
        this.mContext = mContext;
        sqLite = new DataBases(mContext);
        database = sqLite.getWritableDatabase();
    }
    public int Insert(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put("hoTen",thanhVien.getHoTenTV());
        values.put("namSinh",thanhVien.getNamSinhTV());

        long kq = database.insert("THANHVIEN",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ArrayList<ThanhVien> getAllThanhVien(){
        Cursor cursor = database.rawQuery("SELECT *FROM THANHVIEN",null);
        ArrayList<ThanhVien> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(ThanhVien thanhVien){
        int kq = database.delete("THANHVIEN","maTV=?",new String[]{String.valueOf(thanhVien.getMaTv())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put("hoTen",thanhVien.getHoTenTV());
        values.put("namSinh",thanhVien.getNamSinhTV());

        int kq = database.update("THANHVIEN",values,"maTV=?",new String[]{String.valueOf(thanhVien.getMaTv())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ThanhVien getID(int id){
        Cursor cursor = database.rawQuery("SELECT * FROM THANHVIEN WHERE maTV=?",new String[]{String.valueOf(id)});
        ArrayList<ThanhVien> list = new ArrayList<>();

        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            list.add(new ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2) ));
        }
        return list.get(0);
    }
}
