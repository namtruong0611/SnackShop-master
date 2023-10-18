package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.LoaiSanPhamDAO;
import com.example.myapplication.database.NhanVienDAO;
import com.example.myapplication.model.LoaiSanPham;
import com.example.myapplication.model.NhanVien;

import java.util.ArrayList;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NhanVienViewHolder> {
    private ArrayList<NhanVien> arrayList;
    private NhanVienDAO nhanVienDAO ;
    private Context context ;
    public NhanVienAdapter(Context context ){this.context = context ;}
    public  void  setDATA (ArrayList<NhanVien>arrayList ){
        this.arrayList = arrayList ;

        nhanVienDAO = new NhanVienDAO(context);
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nhanvien ,parent,false);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
    NhanVien nhanVien = arrayList.get(position);
    if (nhanVien == null){
        return;
    }
        holder.ma.setText(" " + nhanVien.getMaNV());
        holder.ten.setText(" " + nhanVien.getTenNV());
        holder.pass.setText(" " + nhanVien.getPass());
        holder.del.setOnClickListener(v -> {
            dialogDelete(nhanVien);
        });
        holder.itemView.setOnLongClickListener(v -> {
            dialogUpdate(nhanVien);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null ){
            return  arrayList.size();
        }
        return 0;
    }

    public class  NhanVienViewHolder extends RecyclerView.ViewHolder {
         private  TextView ma , ten , pass ;
         private  ImageView del;
        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);

            ma = itemView.findViewById(R.id.NV_Ma);
            ten = itemView.findViewById(R.id.NV_ten);
            pass = itemView.findViewById(R.id.NV_Pass);
            del = itemView.findViewById(R.id.imgDeleteTV);
        }
    }

    private void dialogDelete(NhanVien nhanVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nhanVienDAO.delete(nhanVien);
                arrayList = nhanVienDAO.getAllNhanVien();
                setDATA(arrayList);
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
    public void dialogUpdate(NhanVien nhanVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_nv,null);
        EditText ed_maNV = v.findViewById(R.id.DAL_NV_ma);
        EditText ed_tenNV = v.findViewById(R.id.DAL_NV_ten);
        EditText ed_pass = v.findViewById(R.id.DAL_NV_pass);
        ed_maNV.setText(nhanVien.getMaNV());
        ed_tenNV.setText(nhanVien.getTenNV());
        ed_pass.setText(nhanVien.getPass());
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.btn_nv_save).setOnClickListener(v1 ->{
            nhanVienDAO = new NhanVienDAO(context);
            nhanVien.setMaNV(String.valueOf(ed_maNV.getText().toString()));
            nhanVien.setTenNV(String.valueOf(ed_tenNV.getText().toString()));
            nhanVien.setPass(String.valueOf(ed_pass.getText().toString()));
            int kq = nhanVienDAO.update(nhanVien);
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                arrayList = nhanVienDAO.getAllNhanVien();
                setDATA(arrayList);
            }
            alertDialog.cancel();
        });
        v.findViewById(R.id.btn_nv_cancle).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
}
