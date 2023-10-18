package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.HoaDonDAO;
import com.example.myapplication.database.SanPhamDAO;
import com.example.myapplication.database.ThanhVIenDAO;
import com.example.myapplication.model.HoaDon;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder> {
    private ArrayList<HoaDon> arrayList;
    private Context context;
    private HoaDonDAO hoaDonDAO;
    private ThanhVIenDAO thanhVienDAO;
    private SanPhamDAO sanPhamDAO;
    private Spinner spn_tenTV, spn_tenSP;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public HoaDonAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<HoaDon> arrayList) {
        this.arrayList = arrayList;

        for (int i = 0; i < arrayList.size(); i++) {
            Log.e("HD",arrayList.get(i).toString());
        }
        hoaDonDAO = new HoaDonDAO(context);
        thanhVienDAO = new ThanhVIenDAO(context);
        sanPhamDAO = new SanPhamDAO(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon,parent,false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDon HD = arrayList.get(position);
        if (HD == null) {
            return;
        }
        holder.ma.setText(String.valueOf(HD.getMaHD()));

        ThanhVien thanhVien = thanhVienDAO.getID(HD.getMaTV());
        holder.thanhvien.setText(thanhVien.getHoTenTV());
        SanPham sp = sanPhamDAO.getID(HD.getMaSP());
        holder.sanpham.setText(sp.getTenSP());
        holder.tongtien.setText(String.valueOf(HD.getTongTien()));
        holder.ngaymua.setText(new SimpleDateFormat("dd/MM/yyyy").format(HD.getNgayMua()));
        holder.del.setOnClickListener(v -> {
            dialogDelete(HD);
        });
        holder.itemView.setOnClickListener(v -> {
            dialogUpdate(HD);
        });

    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }

        return 0;
    }
    public class  HoaDonViewHolder extends  RecyclerView.ViewHolder{
   private TextView ma , thanhvien , sanpham,tongtien , ngaymua;
   private ImageView del ;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.HD_ma);
            thanhvien = itemView.findViewById(R.id.HD_ThanhVien);
            sanpham = itemView.findViewById(R.id.HD_TenSP);
            tongtien = itemView.findViewById(R.id.HD_TongTien);
            ngaymua = itemView.findViewById(R.id.HD_NgayMua);
            del = itemView.findViewById(R.id.HD_del);
        }
    }

    private void dialogDelete(HoaDon HD) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hoaDonDAO.delete(HD);
                arrayList = hoaDonDAO.getAllHoaDon();
                setData(arrayList);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    @SuppressLint("MissingInflatedId")
    public void dialogUpdate(HoaDon HD) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_hd, null);
        TextView NgayMua, tongtien,maHD;
        maHD = v.findViewById(R.id.DAL_HD_ma);
        spn_tenTV = v.findViewById(R.id.DAL_HD_TV);
        spn_tenSP = v.findViewById(R.id.DAL_HD_SP);
        NgayMua = v.findViewById(R.id.DAL_HD_ngaymua);
        tongtien = v.findViewById(R.id.DAL_HD_TongTien);
        thanhVienDAO = new ThanhVIenDAO(context);
        sanPhamDAO = new SanPhamDAO(context);

        maHD.setText(String.valueOf(HD.getMaHD()));

        List<String> tenTV = new ArrayList<>();
        for (ThanhVien list : thanhVienDAO.getAllThanhVien()) {
            tenTV.add(list.getMaTv() + "." + list.getHoTenTV());
        }
        Spn_Adapter(spn_tenTV, tenTV);

        for (int i = 0; i < tenTV.size(); i++) {
            String chuoi = tenTV.get(i); // co van de
            String[] chuoi2 = chuoi.split("\\.");
            if (HD.getMaTV() == Integer.parseInt(chuoi2[0])) {
                spn_tenTV.setSelection(i);
            }
        }
        List<String> sanpham = new ArrayList<>();
        for (SanPham listSP : sanPhamDAO.getAllSP()) {
            sanpham.add(listSP.getMaSp() + "." + listSP.getTenSP());
        }
        Spn_Adapter(spn_tenSP, sanpham);

        for (int i = 0; i < sanpham.size(); i++) {
            String chuoi = sanpham.get(i);//co
            String[] chuoi2 = chuoi.split("\\.");
            if (HD.getMaSP() == Integer.parseInt(chuoi2[0])) {
                spn_tenSP.setSelection(i);

            }
        }
        NgayMua.setText("Ngày thuê: " + new SimpleDateFormat("dd/MM/yyyy").format(HD.getNgayMua()));
        spn_tenSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SanPham sp1 = sanPhamDAO.getID(split(spn_tenSP));
                tongtien.setText(String.valueOf(sp1.getGiaSp()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.DAL_HD_save).setOnClickListener(v1 -> {
            hoaDonDAO = new HoaDonDAO(context);
            HD.setMaTV(split(spn_tenTV));
            HD.setMaSP(split(spn_tenSP));
            HD.setTongTien(Integer.parseInt(tongtien.getText().toString()));
            HD.setNgayMua(new Date());
            Log.e("EM en dung nhieu Log.e ", HD.toString());
            int kq = hoaDonDAO.update(HD);

            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(context, "Cập nhât thành công", Toast.LENGTH_SHORT).show();
            }
            arrayList = hoaDonDAO.getAllHoaDon();
            setData(arrayList);
            alertDialog.cancel();
        });
        v.findViewById(R.id.DAL_HD_cancle).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }

    public void Spn_Adapter(Spinner spn, List<String> list) {

        if (list !=null){
            ArrayAdapter<String> adapterSach = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
            spn.setAdapter(adapterSach);

        }else {
            Toast.makeText(context, "Hien tai dang ko co du lieu,Vui long them du lieu de xuat hoa don", Toast.LENGTH_SHORT).show();}
    }

    public int split(Spinner spn) {

        if (spn.getSelectedItem() != null) {
            String chuoi = (String) spn.getSelectedItem();
            String[] chuoi2 = chuoi.split("\\.");
            return Integer.parseInt(chuoi2[0]);
        } else {

            Toast.makeText(context, "Thành viên hoặc sản phẩm hiện đang ko có ,bạn cần thêm dữ liệu   ,", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

}
