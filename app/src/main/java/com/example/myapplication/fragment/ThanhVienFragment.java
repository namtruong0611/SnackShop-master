package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ThanhVienAdapter;
import com.example.myapplication.database.ThanhVIenDAO;
import com.example.myapplication.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThanhVienFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private ImageView img ;
    private static final int SELECT_IMAGE = 100;
    private ArrayList<ThanhVien> list = new ArrayList<>();
    private ThanhVienAdapter adapter;
    private ThanhVIenDAO thanhVienDAO;
    private int day,month,year;

    public ThanhVienFragment() {
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
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_TV);
        actionButton = view.findViewById(R.id.floatingactionTV);
        actionButton.setOnClickListener(this::onClick);
    }
    @SuppressLint("MissingInflatedId")
    public void dialog_TV(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_tv,null);
        img = v.findViewById(R.id.DAL_TV_img);
        EditText ed_tenTV = v.findViewById(R.id.DAL_TV_ten);
        EditText ed_namSinh = v.findViewById(R.id.DAL_TV_namsinh);

        Button btn_luu,btn_huy;
        btn_luu = v.findViewById(R.id.btn_tv_save);
        btn_huy = v.findViewById(R.id.btn_tv_cancle);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        img.setOnClickListener(v2 ->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,SELECT_IMAGE);
        });
        ed_namSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        ed_namSinh.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        btn_luu.setOnClickListener(v1 ->{
            if(ed_namSinh.length() == 0 || ed_tenTV.length()==0 ){
                Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
            }else{
                thanhVienDAO = new ThanhVIenDAO(getActivity());
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setHoTenTV(ed_tenTV.getText().toString());
                thanhVien.setNamSinhTV(ed_namSinh.getText().toString());

                int kq = thanhVienDAO.Insert(thanhVien);
                if (kq == -1) {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq == 1) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
                onResume();
                alertDialog.cancel();
            }
        });
        btn_huy.setOnClickListener(v2 ->{
            alertDialog.cancel();
        });


        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        thanhVienDAO = new ThanhVIenDAO(getActivity());
        list.clear();
        list= thanhVienDAO.getAllThanhVien();
        adapter = new ThanhVienAdapter(getActivity());
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingactionTV:
                dialog_TV();
                break;
        }
    }
}