package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.NhanVienDAO;
import com.example.myapplication.model.NhanVien;


public class DoiMkFragment extends Fragment {

    private EditText edMkcu, edMKmoi, ednhapLaiMK;
    private NhanVienDAO nhanVienDAO;
    private Context context;

    public DoiMkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doi_mk, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edMkcu = view.findViewById(R.id.ed_doiMK_MKcu);
        edMKmoi = view.findViewById(R.id.ed_doiMK_MKmoi);
        ednhapLaiMK = view.findViewById(R.id.ed_doiMK_nhapLaiMK);
        nhanVienDAO = new NhanVienDAO(getActivity());
        String maNV = getArguments().getString("TK");
        view.findViewById(R.id.btn_doiMK_doi).setOnClickListener(v -> {
            NhanVien nv = nhanVienDAO.getId(maNV);
            if(edMkcu.getText().toString().equals(nv.getPass()) && edMKmoi.getText().toString().equals(ednhapLaiMK.getText().toString())){
                nv.setPass(edMKmoi.getText().toString());
                int kq = nhanVienDAO.update(nv);
                if (kq == -1) {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq == 1) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }
            else if(edMkcu.length() ==0|| edMKmoi.length()==0||ednhapLaiMK.length() == 0){
                Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Sai thông tin", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.btn_doiMK_huy).setOnClickListener(v -> {
                System.exit(0);
        });
    }
}