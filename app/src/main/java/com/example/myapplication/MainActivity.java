package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myapplication.database.NhanVienDAO;
import com.example.myapplication.fragment.DoanhThuFragment;
import com.example.myapplication.fragment.DoiMkFragment;
import com.example.myapplication.fragment.HoaDonFragment;
import com.example.myapplication.fragment.LoaiSanPhamFragment;
import com.example.myapplication.fragment.NhanvienFragment;
import com.example.myapplication.fragment.SanPhamFragment;
import com.example.myapplication.fragment.ThanhVienFragment;
import com.example.myapplication.fragment.TopFragment;
import com.example.myapplication.model.NhanVien;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FrameLayout frameLayout;
    private NhanVienDAO nhanVienDAO;
    private TextView tv_header;

    private DoiMkFragment doiMkFragment = new DoiMkFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        frameLayout = findViewById(R.id.frameLayout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();

        Intent intent = getIntent();
        String maNV = intent.getStringExtra("TK");
        Bundle bundle = new Bundle();
        bundle.putString("TK", maNV);
        doiMkFragment.setArguments(bundle);

        nhanVienDAO = new NhanVienDAO(this);
        NhanVien nhanVien = nhanVienDAO.getId(maNV);


        MenuItem item = navigationView.getMenu().findItem(R.id.ql_nhanvien);
        if (nhanVien.getTenNV().equals("admin")) {
            item.setEnabled(true);
        } else {
            item.setEnabled(false);
        }

        View header = navigationView.getHeaderView(0);
        tv_header = header.findViewById(R.id.tv_head);
        tv_header.setText(nhanVien.getTenNV());
        replaceFragment(new SanPhamFragment());
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ql_sanpham) {
            toolbar.setTitle("Sản phẩm");
            replaceFragment(new SanPhamFragment());
        } else if (id == R.id.ql_loaiSp) {
            toolbar.setTitle("Loại sản phẩm");
            replaceFragment(new LoaiSanPhamFragment());
        } else if (id == R.id.ql_hoadon) {
            toolbar.setTitle("Hóa đơn");
            replaceFragment(new HoaDonFragment());
        } else if (id == R.id.ql_nhanvien) {
            toolbar.setTitle("Nhân viên");
            replaceFragment(new NhanvienFragment());
        }else if (id == R.id.ql_thanhVien) {
            toolbar.setTitle("Thành viên");
            replaceFragment(new ThanhVienFragment());
        } else if (id == R.id.tk_top10) {
            toolbar.setTitle("Top 10");
            replaceFragment(new TopFragment());
        } else if (id == R.id.tk_doanhThu) {
            toolbar.setTitle("Doanh thu");
            replaceFragment(new DoanhThuFragment());
        } else if (id == R.id.ngd_doiMK) {
            toolbar.setTitle("Đổi mật khẩu");
            replaceFragment(doiMkFragment);
        } else if (id == R.id.ngd_dangXuat) {
            finish();
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            super.onBackPressed();
        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}