package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.SanPhamDAO;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder> {
    private Context context;
    private ArrayList<SanPham> arrayList;

    public  TopAdapter (Context context){this.context = context;}
    public void setData (ArrayList<SanPham> arrayList){
        this.arrayList =arrayList ;
        notifyDataSetChanged();


    }
    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top,parent,false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
    SanPham sanPham = arrayList.get(position);
    if (sanPham == null){
        return;
    }
        holder.ma.setText("Mã SP: "+sanPham.getMaSp());
        holder.ten.setText("Tên SP: "+sanPham.getTenSP());
        holder.gia.setText("Giá tiền: "+ String.valueOf(sanPham.getGiaSp()));
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return  arrayList.size();
        }
        return 0;
    }

    public  class  TopViewHolder extends RecyclerView.ViewHolder {
        private TextView ma , ten , gia;
        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.tv_top10_maSP);
            ten = itemView.findViewById(R.id.tv_top10_tenSP);
            gia = itemView.findViewById(R.id.tv_top10_gia);
        }
    }
}
