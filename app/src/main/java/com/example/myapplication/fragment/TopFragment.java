package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TopAdapter;
import com.example.myapplication.database.HoaDonDAO;
import com.example.myapplication.model.SanPham;

import java.text.ParseException;
import java.util.ArrayList;


public class TopFragment extends Fragment {
    private ArrayList<SanPham> list = new ArrayList<>();
    private HoaDonDAO hoaDonDAO;
    private TopAdapter adapter;
    private RecyclerView recyclerView;
    public TopFragment() {
        // Required empty public constructor

    }

public  static TopFragment newIFragment(){
    TopFragment fragment = new TopFragment();
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
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycle_top10);
        hoaDonDAO = new HoaDonDAO(getActivity());
        list.clear();
        list = hoaDonDAO.top10();
        adapter = new TopAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}