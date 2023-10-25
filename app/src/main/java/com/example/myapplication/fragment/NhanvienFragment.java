package com.example.myapplication.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NhanVienAdapter;
import com.example.myapplication.database.NhanVienDAO;
import com.example.myapplication.model.NhanVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class NhanvienFragment extends Fragment implements  View.OnClickListener{
    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private ImageView img ;
    private NhanVienDAO nhanVienDAO;
    private NhanVienAdapter adapter;
    private static final int SELECT_IMAGE = 100;

    private ArrayList<NhanVien> list = new ArrayList<>();
    ;

    public NhanvienFragment() {
        // Required empty public constructor
    }


    public static NhanvienFragment newInstance( ) {
        NhanvienFragment fragment = new NhanvienFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nhanvien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_NV);
        actionButton = view.findViewById(R.id.floating_nhanvien);
        actionButton.setOnClickListener(this::onClick);
    }





    public void Dialog_NV() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_nv, null);
        img =  view.findViewById(R.id.DAL_NV_img);
        EditText maNV = view.findViewById(R.id.DAL_NV_ma);
        EditText tennv = view.findViewById(R.id.DAL_NV_ten);
        EditText pass = view.findViewById(R.id.DAL_NV_pass);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        img.setOnClickListener(v2 ->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,SELECT_IMAGE);
        });
        view.findViewById(R.id.btn_nv_save).setOnClickListener(v -> {
            nhanVienDAO = new NhanVienDAO(getActivity());
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNV(maNV.getText().toString());
            nhanVien.setTenNV(tennv.getText().toString());
            nhanVien.setPass(pass.getText().toString());
            int kq = nhanVienDAO.Insert(nhanVien);
            if (kq == -1) {
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                Log.e("nhanvien",""+list.size());
            }
            onResume();
            alertDialog.cancel();
        });
        view.findViewById(R.id.btn_nv_cancle).setOnClickListener(v -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
     public void onResume() {
         super.onResume();
         nhanVienDAO= new NhanVienDAO(getActivity());
         list.clear();
         list= nhanVienDAO.getAllNhanVien();
         adapter = new NhanVienAdapter(getActivity());
         adapter.setDATA(list);
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
         recyclerView.setLayoutManager(linearLayoutManager);
         recyclerView.setAdapter(adapter);


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
    public void onClick(View view) {
           switch (view.getId()){
               case R.id.floating_nhanvien: Dialog_NV();
               break;
           }

    }
}