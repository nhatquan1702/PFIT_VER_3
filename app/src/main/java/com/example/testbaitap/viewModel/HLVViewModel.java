package com.example.testbaitap.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.os.Handler;

import com.example.testbaitap.entity.HuanLuyenVien;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class HLVViewModel extends ViewModel {
    private MutableLiveData<ArrayList<HuanLuyenVien>> hlvList;
    private ArrayList<HuanLuyenVien> huanLuyenVienArrayList;

    public HLVViewModel(MutableLiveData<ArrayList<HuanLuyenVien>> hlvList, ArrayList<HuanLuyenVien> huanLuyenVienArrayList) {
        this.hlvList = hlvList;
        this.huanLuyenVienArrayList = huanLuyenVienArrayList;
    }

    public LiveData<ArrayList<HuanLuyenVien>> getHLVList() {
        if (hlvList == null) {
            hlvList = new MutableLiveData<>();
            loadHLVList();
        }
        return hlvList;
    }
    private void loadHLVList() {
        // 1
        Handler myHandler = new Handler();
        // 2
        myHandler.postDelayed(() -> {
            // 3
            HuanLuyenVien huanLuyenVien = new HuanLuyenVien("00", "Nhật Quang", "0356 329 294", "TP Thủ Đức", "", 1);
            huanLuyenVienArrayList = new ArrayList<>();
            huanLuyenVienArrayList.add(0, huanLuyenVien);
            // 4
            long seed = System.nanoTime();
            Collections.shuffle(huanLuyenVienArrayList, new Random(seed));
            // 5
            hlvList.setValue(huanLuyenVienArrayList);
            // 6
        }, 5000);
    }

    public void addHLVData(HuanLuyenVien huanLuyenVien){
        huanLuyenVienArrayList.add(huanLuyenVien);
        hlvList.setValue(huanLuyenVienArrayList);
    }
}
