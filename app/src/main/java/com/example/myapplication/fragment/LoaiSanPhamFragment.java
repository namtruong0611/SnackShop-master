package com.example.myapplication.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.LoaiSanPhamAdapter;
import com.example.myapplication.database.LoaiSanPhamDAO;
import com.example.myapplication.model.LoaiSanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class LoaiSanPhamFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private LoaiSanPhamDAO loaiSpDAO;
    private LoaiSanPhamAdapter adapter;
    private ImageView img;
    private static final int SELECT_IMAGE = 100;
    private ArrayList<LoaiSanPham> list = new ArrayList<>();
    private Context context;
    public LoaiSanPhamFragment() {
    }


    public static LoaiSanPhamFragment newInstance( ) {
        LoaiSanPhamFragment fragment = new LoaiSanPhamFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loai_san_pham, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_LSP);
        actionButton = view.findViewById(R.id.floatingaction_LSP);

        actionButton.setOnClickListener((View.OnClickListener) this);
    }
    public void dialog_LSP(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_lsp,null);
         img =  v.findViewById(R.id.DAL_LSP_img);
        EditText ed_tenLoai = v.findViewById(R.id.DAL_LSP_Ten);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
       img.setOnClickListener(v2 ->{
           Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           startActivityForResult(intent,SELECT_IMAGE);
       });
        v.findViewById(R.id.btn_lsp_save).setOnClickListener(v1 ->{
       if (ed_tenLoai.length() == 0){
           Toast.makeText(getActivity(), "không được để trống ", Toast.LENGTH_SHORT).show();
       }else {
           loaiSpDAO = new LoaiSanPhamDAO(getActivity());
           LoaiSanPham loaiSp = new LoaiSanPham();
           loaiSp.setTenloai(ed_tenLoai.getText().toString());
           int kq = loaiSpDAO.Insert(loaiSp);
           if (kq == -1) {
               Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
           }
           if (kq == 1) {
               Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
           }
       }
            onResume();
            alertDialog.cancel();
        });
        v.findViewById(R.id.btn_lsp_cancle).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });

        alertDialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            img.setImageURI(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loaiSpDAO= new LoaiSanPhamDAO(getActivity());
        list.clear();
        list = loaiSpDAO.getAllLoaiSP();
        adapter = new LoaiSanPhamAdapter(getActivity());
        adapter.setDATA(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingaction_LSP:
                dialog_LSP();
                break;
        }
    }
}