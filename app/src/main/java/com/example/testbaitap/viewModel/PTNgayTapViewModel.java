package com.example.testbaitap.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PTNgayTapViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Integer>>  arrayListMutableLiveData;
    private ArrayList<Integer> integerArrayList;

    public PTNgayTapViewModel(MutableLiveData<ArrayList<Integer>> arrayListMutableLiveData) {
        this.arrayListMutableLiveData = arrayListMutableLiveData;
        initData();
    }

    private void initData() {
        integerArrayList = new ArrayList<>();
        integerArrayList.add(0);
        arrayListMutableLiveData.setValue(integerArrayList);
    }

    public MutableLiveData<ArrayList<Integer>> getArrayListMutableLiveData() {
        return arrayListMutableLiveData;
    }

    public void addData(Integer integer){
        integerArrayList.add(integer);
        arrayListMutableLiveData.setValue(integerArrayList);
    }
}
