package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.HoaDon;
import com.example.myapplication.model.SanPham;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HoaDonDAO {
    private Context context1;
    private SQLiteDatabase database;
    private DataBases sqLite;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public HoaDonDAO(Context context) {
        this.context1 = context;
        sqLite = new DataBases(context);
        database = sqLite.getWritableDatabase();

    }

    public int Insert(HoaDon HD) {
        ContentValues values = new ContentValues();
        values.put("maNV", HD.getMaNV());
        values.put("maTV", HD.getMaTV());
        values.put("maSP", HD.getMaSP());
        values.put("TongTien", HD.getTongTien());
        values.put("Ngay", format.format(HD.getNgayMua()));

        long kq = database.insert("HOADON", null, values);
        if (kq <= 0) {
            return -1;
        }
        return 1;
    }

    public ArrayList<HoaDon> getAllHoaDon() {
        Cursor cursor = database.rawQuery("SELECT *FROM HOADON", null);
        ArrayList<HoaDon> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    list.add(new HoaDon(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            format.parse(cursor.getString(5))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int delete(HoaDon HD) {
        int kq = database.delete("HOADON", "maHD=?", new String[]{String.valueOf(HD.getMaHD())});
        if (kq <= 0) {
            return -1;
        }
        return 1;
    }

    public int update(HoaDon HD) {
        ContentValues values = new ContentValues();
        values.put("maNV", HD.getMaNV());
        values.put("maTV", HD.getMaTV());
        values.put("maSP", HD.getMaSP());
        values.put("TongTien", HD.getTongTien());
        values.put("Ngay", format.format(HD.getNgayMua()));

        int kq = database.update("HOADON", values, "maHD=?", new String[]{String.valueOf(HD.getMaHD())});
        if (kq <= 0) {
            return -1;
        }
        return 1;
    }

    public ArrayList<SanPham> top10(){
        Cursor cursor = database.rawQuery("SELECT SANPHAM.maSP,SANPHAM.tenSP,SANPHAM.Gia,SANPHAM.maLoaiSP,COUNT(SANPHAM.maSP) FROM SANPHAM JOIN HOADON ON SANPHAM.maSP=HOADON.maSP GROUP BY HOADON.maSP ORDER BY COUNT(SANPHAM.maSP) DESC LIMIT 10", null);
        ArrayList<SanPham> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new SanPham(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int Doanhthu(String date1, String date2) {
        Cursor cursor = database.rawQuery("SELECT SUM(TongTien) FROM HOADON WHERE Ngay BETWEEN ? AND ?", new String[]{date1, date2});
        int dt = 0;
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            dt = cursor.getInt(0);
        }
        return dt;
    }

    public ArrayList<HoaDon> getAllByMaPM(int ma) {
        Cursor cursor = database.rawQuery("SELECT * FROM HOADON WHERE maHD=?   ", new String[]{String.valueOf(ma)});
        ArrayList<HoaDon> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    list.add(new HoaDon(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            format.parse(cursor.getString(5))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return list;
    }

}
