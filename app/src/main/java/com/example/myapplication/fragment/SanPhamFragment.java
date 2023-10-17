package com.example.myapplication.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamAdapter;
import com.example.myapplication.database.LoaiSanPhamDAO;
import com.example.myapplication.database.SanPhamDAO;
import com.example.myapplication.model.LoaiSanPham;
import com.example.myapplication.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class SanPhamFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton floating;
    private SanPhamDAO spDao;
    private RecyclerView recyclerView;
    private SanPhamAdapter adapter;
    private LoaiSanPhamDAO loaiSanPhamDAO;
    private ArrayList<SanPham> list = new ArrayList<>();

    EditText ed_timKiem;
    Button bnt_timKiem;

    public SanPhamFragment() {
        // Required empty public constructor
    }

    public static SanPhamFragment newInstance(){
        SanPhamFragment fragment = new SanPhamFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spDao = new SanPhamDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_san_pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_SanPham);
        floating = view.findViewById(R.id.floatingaction_SanPham);
        floating.setOnClickListener(this);
        ed_timKiem = view.findViewById(R.id.ed_timKiemSanPham);
        bnt_timKiem = view.findViewById(R.id.btn_timKiemSanPham);
        bnt_timKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_timKiem.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "bạn phải nhập thông tin tên san pham để tìm kiếm thông tin ", Toast.LENGTH_SHORT).show();
                }
                adapter.getFilter().filter(ed_timKiem.getText().toString().trim());
            }
        });


    }

    public void dialog_SanPham() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sp, null);
        EditText ed_tenSp,ed_giaSp;
        Spinner spn_loaiSp;
        Button btn_luu,btn_huy;
        ed_tenSp = v.findViewById(R.id.DAL_SP_ten);
        ed_giaSp = v.findViewById(R.id.DAL_SP_gia);
        spn_loaiSp = v.findViewById(R.id.DAL_SP_spin);
        btn_luu = v.findViewById(R.id.btn_sp_save);
        btn_huy = v.findViewById(R.id.btn_sp_cancle);
        loaiSanPhamDAO = new LoaiSanPhamDAO(getActivity());
        List<String> loaiSp =  new ArrayList<>();
        for(LoaiSanPham listLoaiSp: loaiSanPhamDAO.getAllLoaiSP()){
            loaiSp.add(listLoaiSp.getMaloai()+"."+listLoaiSp.getTenloai());
        }
        ArrayAdapter<String> adapterLoaiSp = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,loaiSp);
        spn_loaiSp.setAdapter(adapterLoaiSp);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btn_luu.setOnClickListener(v1 -> {
            if(ed_tenSp.length() ==0 || ed_giaSp.length()==0) {
                Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
            }else {
                SanPham sp = new SanPham();
                String LoaiSp = (String) spn_loaiSp.getSelectedItem();
                if (LoaiSp != null) {
                    String[] maloai = LoaiSp.split("\\.");
                    sp.setTenSP(ed_tenSp.getText().toString());
                    sp.setGiaSp(Integer.parseInt(ed_giaSp.getText().toString()));
                    sp.setMaLoaiSp(Integer.parseInt(maloai[0]));

                    int kq = spDao.Insert(sp);

                    if (kq == -1) {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    if (kq == 1) {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }

                    onResume();
                    alertDialog.cancel();
                }   else {
                    Toast.makeText(getContext(), "Vui lòng thêm loại san pham trước rồi mới thêm san pham ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_huy.setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        SanPhamDAO SanPhamDAO = new SanPhamDAO(getActivity());
        list.clear();
        list = SanPhamDAO.getAllSP();
        adapter = new SanPhamAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floatingaction_SanPham:
                dialog_SanPham();
                break;
        }
    }
}