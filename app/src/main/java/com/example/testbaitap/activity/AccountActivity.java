package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.TaiKhoan;
import com.example.testbaitap.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefresh;
    EditText edtTKED, edtMKED, edtXNMKED, edtHTED, edtSDTED, edtDCED;
    CheckBox checkboxHienMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefresh = findViewById(R.id.swipe_refresh);

        sharedPreferences = getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        //Toast.makeText(AccountActivity.this, sharedPreferences.getString("email", "username"), Toast.LENGTH_SHORT).show();
        edtTKED = findViewById(R.id.edtTKED);
        edtMKED = findViewById(R.id.edtMKED);
        edtXNMKED = findViewById(R.id.edtXNMKED);
        edtHTED = findViewById(R.id.edtHTED);
        edtSDTED = findViewById(R.id.edtSDTED);
        edtDCED = findViewById(R.id.edtDCED);
        checkboxHienMK = findViewById(R.id.checkboxHienMK);

        editor = sharedPreferences.edit();
        LoadThongTinCaNhan(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""));
        mSwipeRefresh.setOnRefreshListener(() -> {
            LoadThongTinCaNhan(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""));
            mSwipeRefresh.setRefreshing(false);
        });
        CardView button = findViewById(R.id.cvDKTK);
        button.setOnClickListener(v -> {

            editor.remove(Config.DATA_LOGIN_USERNAME);
            editor.remove(Config.DATA_LOGIN_PASS);
            editor.remove(Config.DATA_LOGIN_ROLE);

            editor.commit();
            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });


    }

    public void LoadThongTinCaNhan(String tk){
        progressBar.setVisibility(View.VISIBLE);
        SimpleAPI simpleAPI = Constants.instance();
        simpleAPI.getThongTinTaiKhoan(tk).enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
               try {
                   TaiKhoan taiKhoan = response.body();
                   edtTKED.setText(taiKhoan.getTaiKhoan());
                   edtMKED.setText(taiKhoan.getMatKhau());
                   edtXNMKED.setText(taiKhoan.getMatKhau());
                   edtHTED.setText(taiKhoan.getHoTen());
                   edtSDTED.setText(taiKhoan.getSoDienThoai());
                   edtDCED.setText(taiKhoan.getDiaChi());
                   progressBar.setVisibility(View.GONE);
               }catch (Exception e){
                   e.printStackTrace();
                   progressBar.setVisibility(View.GONE);
               }
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                Toast.makeText(AccountActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadThongTinCaNhan(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""));
    }
    //    public BottomSheetDialog diaLogBottomHotten() {
//        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
//        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_hoten, null);
//        sheetDialog.setContentView(viewDialog);
//        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sheetDialog.dismiss();
//            }
//        });
//        return sheetDialog;
//    }
//    @SuppressLint("ResourceAsColor")
//    public BottomSheetDialog diaLogBottomTuoi() {
//        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
//        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_tuoi, null);
//        sheetDialog.setContentView(viewDialog);
//        NumberPicker np = (NumberPicker) viewDialog.findViewById(R.id.numberPickTuoi);
//        np.setMaxValue(100); // max value 100
//        np.setMinValue(1);   // min value 0
//        np.setValue(22);
//        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sheetDialog.dismiss();
//            }
//        });
//        return sheetDialog;
//    }
//    public BottomSheetDialog diaLogBottomGioiTinh() {
//        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
//        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_gioitinh, null);
//        sheetDialog.setContentView(viewDialog);
//        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sheetDialog.dismiss();
//            }
//        });
//        return sheetDialog;
//    }
//    public BottomSheetDialog diaLogBottomSDT() {
//        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
//        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_sdt, null);
//        sheetDialog.setContentView(viewDialog);
//        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sheetDialog.dismiss();
//            }
//        });
//        return sheetDialog;
//    }
//    public BottomSheetDialog diaLogBottomDiaChi() {
//        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
//        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_diachi, null);
//        sheetDialog.setContentView(viewDialog);
//        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sheetDialog.dismiss();
//            }
//        });
//        return sheetDialog;
//    }
//    public BottomSheetDialog diaLogBottomMK() {
//        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
//        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_matkhau, null);
//        sheetDialog.setContentView(viewDialog);
//        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sheetDialog.dismiss();
//            }
//        });
//        return sheetDialog;
//    }
//    public BottomSheetDialog diaLogBottomQR() {
//        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
//        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_qr, null);
//        sheetDialog.setContentView(viewDialog);
//        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sheetDialog.dismiss();
//            }
//        });
//        String data = sharedPreferences.getString("email", "")+"||"+sharedPreferences.getString("password", "");
//        ImageView imageView = (ImageView)viewDialog.findViewById(R.id.imgQR);
//        QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, data.length());
//        try {
//            // Getting QR-Code as Bitmap
//            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
//            // Setting Bitmap to ImageView
//            imageView.setImageBitmap(bitmap);
//        } catch (Exception e) {
//            Toast.makeText(AccountActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//        }
////        Picasso.get()
////                .load(post.getPost_img())
////                .placeholder(R.drawable.gallery)
////                .error(R.drawable.gallery)
////                .into(imageView);
//        return sheetDialog;
//    }
}