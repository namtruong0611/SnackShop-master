package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.database.NhanVienDAO;
import com.example.myapplication.fragment.DoiMkFragment;
import com.example.myapplication.model.NhanVien;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    private NhanVienDAO nhanVienDAO;
    private EditText ed_Username, ed_Password;
    private Button btnLogin, btnCancel;
    private CheckBox chk_luu;
    private List<NhanVien> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_Username = findViewById(R.id.ed_login_TK);
        ed_Password = findViewById(R.id.ed_login_MK);
        chk_luu = findViewById(R.id.chk_luuMK);
        btnLogin = findViewById(R.id.btn_login_dangNhap);
        btnCancel = findViewById(R.id.btn_login_huy);

        nhanVienDAO = new NhanVienDAO(Login.this);
        DoiMkFragment doiMkFragment = new DoiMkFragment();
        List<String> list = new ArrayList<>();
        list = readPreference();
        if (list.size() > 0) {
            ed_Username.setText(list.get(0));
            ed_Password.setText(list.get(1));
            chk_luu.setChecked(Boolean.parseBoolean(list.get(2)));
        }

        btnCancel.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });
        arrayList = nhanVienDAO.getAllNhanVien();
        Log.e("DU LIEU DATABASE ", ""+arrayList.size());
        btnLogin.setOnClickListener(v -> {
            String tk = ed_Username.getText().toString();
            String mk = ed_Password.getText().toString();
            boolean status = chk_luu.isChecked();

            for (int i = 0; i < nhanVienDAO.getAllNhanVien().size(); i++) {
                NhanVien nhanVien = arrayList.get(i);
                Log.e("user", ""+arrayList.size());
                if (tk.equals(nhanVien.getMaNV()) && mk.equals(nhanVien.getPass())) {
                    savePreference(tk, mk, status);
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("TK", tk);
                    startActivity(intent);
                    return;
                }
            }
            if (tk.length() == 0 || mk.length() == 0) {
                Toast.makeText(this, "Không để trống", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sai thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void savePreference(String tk, String mk, Boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status) {
            editor.clear();
        } else {
            editor.putString("TK", tk);
            editor.putString("MK", mk);
            editor.putBoolean("CHK", status);
        }
        editor.commit();
    }

    private List<String> readPreference() {
        List<String> list = new ArrayList<>();
        SharedPreferences s = getSharedPreferences("MY_FILE", MODE_PRIVATE);
        list.add(s.getString("TK", ""));
        list.add(s.getString("MK", ""));
        list.add(String.valueOf(s.getBoolean("CHK", false)));
        return list;
    }
    @Override
    protected void onResume() {
        super.onResume();
        arrayList = nhanVienDAO.getAllNhanVien();
    }
}