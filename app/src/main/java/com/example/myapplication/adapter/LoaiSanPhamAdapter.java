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
import com.example.myapplication.model.LoaiSanPham;
import com.example.myapplication.model.SanPham;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends RecyclerView.Adapter<LoaiSanPhamAdapter.LoaiSPViewHolder> {
private Context context ;
private ArrayList<LoaiSanPham>  arrayList ;
private LoaiSanPhamDAO loaiSanPhamDAO ;
public  LoaiSanPhamAdapter(Context context){this.context = context ;}
    public  void  setDATA (ArrayList<LoaiSanPham>arrayList ){
    this.arrayList = arrayList ;
    loaiSanPhamDAO = new LoaiSanPhamDAO(context);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public LoaiSPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaisp,parent,false);
        return new LoaiSPViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSPViewHolder holder, int position) {
    LoaiSanPham lsp = arrayList.get(position);
    if (lsp ==null){
     return;
     }
     holder.ma.setText(" " + lsp.getMaloai());
     holder.ten.setText(" " + lsp.getTenloai());

        holder.del.setOnClickListener(v -> {
            dialogDelete(lsp);
        });
        holder.itemView.setOnClickListener(v -> {
            dialogUpdate(lsp);
        });

    }


    @Override
    public int getItemCount() {
    if (arrayList != null){
return  arrayList.size();
    }
        return 0;
    }

    public  class  LoaiSPViewHolder extends RecyclerView.ViewHolder {
        private TextView ma , ten;
        private ImageView img,del ;
        public LoaiSPViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_loaisp_img);
            ma = itemView.findViewById(R.id.LSP_ma);
            ten = itemView.findViewById(R.id.LSP_ten);
            del = itemView.findViewById(R.id.LSP_del);
        }
    }
    private void dialogDelete(LoaiSanPham lsp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loaiSanPhamDAO.delete(lsp);
                arrayList = loaiSanPhamDAO.getAllLoaiSP();
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
    public void dialogUpdate(LoaiSanPham lsp){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_lsp,null);
        ImageView img = v.findViewById(R.id.DAL_LSP_img);
        EditText ed_tenLoai = v.findViewById(R.id.DAL_LSP_Ten);
        EditText ed_maLoai = v.findViewById(R.id.DAL_LSP_Ma);
        ed_maLoai.setText(String.valueOf(lsp.getMaloai()));
        ed_tenLoai.setText(String.valueOf(lsp.getTenloai()));
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.btn_lsp_save).setOnClickListener(v1 ->{
            loaiSanPhamDAO = new LoaiSanPhamDAO(context);
            lsp.setTenloai(ed_tenLoai.getText().toString());
            int kq = loaiSanPhamDAO.update(lsp);
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                arrayList = loaiSanPhamDAO.getAllLoaiSP();
                setDATA(arrayList);
            }
            alertDialog.cancel();
        });
        v.findViewById(R.id.btn_lsp_cancle).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }

}
