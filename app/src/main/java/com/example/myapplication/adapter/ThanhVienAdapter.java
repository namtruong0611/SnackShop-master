package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.NhanVienDAO;
import com.example.myapplication.database.ThanhVIenDAO;
import com.example.myapplication.model.NhanVien;
import com.example.myapplication.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhvienViewHolder> {
    private ArrayList<ThanhVien> arrayList;
    private ThanhVIenDAO thanhVienDAO ;
    private Context context ;
    public ThanhVienAdapter (Context context ){this.context = context ;}
    public  void  setDATA (ArrayList<ThanhVien>arrayList ){
        this.arrayList = arrayList ;

        thanhVienDAO = new ThanhVIenDAO(context);
        notifyDataSetChanged();

    }
    @NonNull
    @Override

    public ThanhvienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanhvien ,parent,false);
        return new ThanhvienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhvienViewHolder holder, int position) {
        ThanhVien thanhVien = arrayList.get(position);
        if (thanhVien == null){
            return;
        }
        holder.ma.setText(" " + thanhVien.getMaTv());
        holder.ten.setText(" " + thanhVien.getHoTenTV());
        holder.namsinh.setText("" + thanhVien.getNamSinhTV());
        holder.del.setOnClickListener(v -> {
            dialogDelete(thanhVien);
        });
        holder.itemView.setOnClickListener(v -> {
            dialogUpdate(thanhVien);
        });


    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public  class  ThanhvienViewHolder extends RecyclerView.ViewHolder {
        private TextView ma , ten , namsinh ;
        private ImageView img ,del;
        public ThanhvienViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_tv_img);
            ma = itemView.findViewById(R.id.item_tv_Ma);
            ten = itemView.findViewById(R.id.item_tv_ten);
            namsinh = itemView.findViewById(R.id.item_tv_namsinh);
            del = itemView.findViewById(R.id.item_tv_del);
        }
    }
    private void dialogDelete(ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                thanhVienDAO.delete(thanhVien);
                arrayList = thanhVienDAO.getAllThanhVien();
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
    public void dialogUpdate(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_tv,null);
        ImageView img = v.findViewById(R.id.DAL_TV_img);
        EditText ed_maTV = v.findViewById(R.id.DAL_TV_ma);
        EditText ed_tenTV = v.findViewById(R.id.DAL_TV_ten);
        EditText ed_ngaySinh = v.findViewById(R.id.DAL_TV_namsinh);

        Button btn_luu,btn_huy;
        btn_luu = v.findViewById(R.id.btn_tv_save);
        btn_huy = v.findViewById(R.id.btn_tv_cancle);
        ed_maTV.setText(String.valueOf(thanhVien.getMaTv()));
        ed_ngaySinh.setText(thanhVien.getNamSinhTV());
        ed_tenTV.setText(thanhVien.getHoTenTV());

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btn_luu.setOnClickListener(v1 ->{
            thanhVienDAO = new ThanhVIenDAO(context);
            thanhVien.setHoTenTV(ed_tenTV.getText().toString());
            thanhVien.setNamSinhTV(ed_ngaySinh.getText().toString());

            // ban đầu là insert
            int kq = thanhVienDAO.update(thanhVien);
            //
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                arrayList = thanhVienDAO.getAllThanhVien();
                setDATA(arrayList);
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
            alertDialog.cancel();
        });
        btn_huy.setOnClickListener(v1 -> {
            alertDialog.cancel();

        });
        alertDialog.show();

    }
}
