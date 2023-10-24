package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.LoaiSanPhamDAO;
import com.example.myapplication.database.SanPhamDAO;
import com.example.myapplication.model.LoaiSanPham;
import com.example.myapplication.model.SanPham;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SPViewHodelder>implements Filterable {
    private Context context;
    private ArrayList<SanPham> listSP;
    private ArrayList<SanPham> arrayList;
    private LoaiSanPhamDAO loaiSanPhamDAO;
    private SanPhamDAO sanPhamDAO;
    private ImageView img ;
    public SanPhamAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<SanPham> arrayList){
        this.arrayList = arrayList;
        this.listSP = arrayList;

        sanPhamDAO = new SanPhamDAO(context);
        loaiSanPhamDAO = new LoaiSanPhamDAO(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SPViewHodelder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham,parent,false);
        return new SPViewHodelder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SPViewHodelder holder, int position) {
        SanPham sanPham = arrayList.get(position);
        if(sanPham == null){
            return;
        }
        holder.ma.setText(" Mã sản phẩm  : "+sanPham.getMaSp());
        holder.ten.setText(" Tên sản phẩm  :"+sanPham.getTenSP());
        holder.gia.setText(" Giá tiền  :"+ sanPham.getGiaSp());
        LoaiSanPham loaiSP = loaiSanPhamDAO.getID(sanPham.getMaLoaiSp());
        holder.loaiSP.setText("Loại sản phẩm  : " + (loaiSP != null ? loaiSP.getTenloai() : "Loại sách không tồn tại"));
        holder.del.setOnClickListener(v -> {
            dialogDelete(sanPham);
        });

        holder.itemView.setOnClickListener(v -> {
            if (sanPham == null) {

                Toast.makeText(context, "Cần phải thêm dữ liệu cho loại sách", Toast.LENGTH_SHORT).show();

            } else {
                dialogUpdate(sanPham);
            }

        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public  class  SPViewHodelder extends RecyclerView.ViewHolder {
        private TextView ma ,ten, gia , loaiSP;
        private ImageView img,del;
        public SPViewHodelder(@NonNull View itemView) {
            super(itemView);
            img =itemView.findViewById(R.id.item_sp_img);
            ma = itemView.findViewById(R.id.item_sp_ma);
            ten = itemView.findViewById(R.id.item_sp_ten);
            gia = itemView.findViewById(R.id.item_sp_gia);
            loaiSP = itemView.findViewById(R.id.item_sp_loaiSP);
            del = itemView.findViewById(R.id.item_sp_delete);
        }
    }
    private void dialogDelete(SanPham sp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sanPhamDAO.delete(sp);
                arrayList = sanPhamDAO.getAllSP();
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
    public void dialogUpdate(SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sp, null);
        EditText ed_maSP,ed_tenSP,ed_giaSP;
        Spinner spn_loaiSP;
        Button btn_luu,btn_huy;
        ImageView imageView ;

//        imageView =v.findViewById(R.id.DAL_SP_img);
        ed_maSP = v.findViewById(R.id.DAL_SP_ma);
        ed_tenSP = v.findViewById(R.id.DAL_SP_ten);
        ed_giaSP = v.findViewById(R.id.DAL_SP_gia);
        spn_loaiSP = v.findViewById(R.id.DAL_SP_spin);
        btn_luu = v.findViewById(R.id.btn_sp_save);
        btn_huy = v.findViewById(R.id.btn_sp_cancle);
        loaiSanPhamDAO = new LoaiSanPhamDAO(context);
        List<String> loaiSP =  new ArrayList<>();


        for(LoaiSanPham listLoaiSP: loaiSanPhamDAO.getAllLoaiSP()){
            loaiSP.add(listLoaiSP.getMaloai()+"."+listLoaiSP.getTenloai());
        }
        ArrayAdapter<String> adapterLoaiSP = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,loaiSP);
        spn_loaiSP.setAdapter(adapterLoaiSP);


        ed_maSP.setText(String.valueOf(sanPham.getMaSp()));
        ed_tenSP.setText(sanPham.getTenSP());
        ed_giaSP.setText(String.valueOf(sanPham.getGiaSp()));
        for(int i =0;i<loaiSP.size();i++){
            String chuoi = loaiSP.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if(sanPham.getMaLoaiSp()==Integer.parseInt(chuoi2[0])){
                spn_loaiSP.setSelection(i);
            }
        }
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btn_luu.setOnClickListener(v1 -> {
            String LoaiSach = (String)spn_loaiSP.getSelectedItem();
            String[] maloai= LoaiSach.split("\\.");

            sanPham.setTenSP(ed_tenSP.getText().toString());
            sanPham.setGiaSp(Integer.parseInt(ed_giaSP.getText().toString()));
            sanPham.setMaLoaiSp(Integer.parseInt(maloai[0]));
            int kq = sanPhamDAO.update(sanPham);
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                arrayList = sanPhamDAO.getAllSP();
                setData(arrayList);
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
            alertDialog.cancel();
        });
        btn_huy.setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();

    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String tenSp = constraint.toString();
                ArrayList<SanPham> list = new ArrayList<>();
                if(tenSp.isEmpty()){
                    arrayList = listSP;
                }
                for(SanPham list2: listSP){
                    if(list2.getTenSP().toLowerCase().contains(tenSp.toLowerCase())){
                        list.add(list2);
                    }
                }
                arrayList = list;
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<SanPham>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
