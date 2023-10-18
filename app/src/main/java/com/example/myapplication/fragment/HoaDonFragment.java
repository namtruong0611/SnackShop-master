package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HoaDonAdapter;
import com.example.myapplication.database.HoaDonDAO;
import com.example.myapplication.database.SanPhamDAO;
import com.example.myapplication.database.ThanhVIenDAO;
import com.example.myapplication.model.HoaDon;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HoaDonFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private HoaDonAdapter adapter;
    private ArrayList<HoaDon> list = new ArrayList<>();
    private HoaDonDAO hoadonDAO;
    private ThanhVIenDAO thanhVienDAO;
    private SanPhamDAO spDAO;
    private Spinner spn_tenTV,spn_tenSp;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


    public HoaDonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_PT);
        actionButton = view.findViewById(R.id.floatingaction_PT);
        actionButton.setOnClickListener((View.OnClickListener) this);

        EditText ed_timKiem = view.findViewById(R.id.ed_timKiem);
        Button btn_timKiem = view.findViewById(R.id.btn_timKiem);
        btn_timKiem.setOnClickListener(v -> {

            try {
                hoadonDAO = new HoaDonDAO(getActivity());
                list = hoadonDAO.getAllByMaPM(Integer.parseInt(ed_timKiem.getText().toString()));
                adapter = new HoaDonAdapter(getActivity());
                adapter.setData(list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(), "giá trị nhập vào là số  không phải kí tự ", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void diaLog_HD(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_hd,null);
        TextView tv_tongtien, tv_ngaymua;
        spn_tenTV = v.findViewById(R.id.DAL_HD_TV);
        spn_tenSp = v.findViewById(R.id.DAL_HD_SP);
        tv_ngaymua = v.findViewById(R.id.DAL_HD_ngaymua);
        tv_tongtien = v.findViewById(R.id.DAL_HD_TongTien);

        thanhVienDAO = new ThanhVIenDAO(getContext());
        spDAO = new SanPhamDAO(getActivity());
        List<String> tenTV = new ArrayList<>();
        for(ThanhVien thanhVien : thanhVienDAO.getAllThanhVien()) {
            tenTV.add(thanhVien.getMaTv()+"."+thanhVien.getHoTenTV());
        }
        Spn_Adapter(spn_tenTV,tenTV);

        List<String> sanpham = new ArrayList<>();
        for(SanPham listSp: spDAO.getAllSP() ){
            sanpham.add(listSp.getMaSp()+"."+listSp.getTenSP());
        }
        Spn_Adapter(spn_tenSp,sanpham);

        tv_ngaymua.setText("Ngày mua: "+new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        spn_tenSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SanPham sp1 = spDAO.getID(split(spn_tenSp));
                tv_tongtien.setText(String.valueOf(sp1.getGiaSp()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.DAL_HD_save).setOnClickListener(v1 -> {
            hoadonDAO = new HoaDonDAO(getActivity());
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaTV(split(spn_tenTV));
            hoaDon.setMaSP(split(spn_tenSp));
            hoaDon.setTongTien(Integer.parseInt(tv_tongtien.getText().toString()));
            hoaDon.setNgayMua(new Date());
            int kq = hoadonDAO.Insert(hoaDon);
            if (kq == -1) {
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
            onResume();
            alertDialog.cancel();
        });
        v.findViewById(R.id.DAL_HD_cancle).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        hoadonDAO = new HoaDonDAO(getActivity());
        list.clear();
        list = hoadonDAO.getAllHoaDon();
        adapter = new HoaDonAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingaction_PT:
                diaLog_HD();
                break;
        }
    }
    public void Spn_Adapter(Spinner spn, List<String> list){
        if (list !=null){
            ArrayAdapter<String> adapterSp = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);

            spn.setAdapter(adapterSp);

        }else {
            Toast.makeText(getActivity(), "Hien tai dang ko co du lieu,Vui long them du lieu de xuat hoa don", Toast.LENGTH_SHORT).show();}
    }
    public int split(Spinner spn){
        if (spn.getSelectedItem() != null) {
            String chuoi = (String) spn.getSelectedItem();
            String[] chuoi2 = chuoi.split("\\.");
            return Integer.parseInt(chuoi2[0]);
        } else {

            Toast.makeText(getActivity(), "Thành viên hoặc san pham hiện đang ko có ,bạn cần thêm dữ liệu   ,", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }
}