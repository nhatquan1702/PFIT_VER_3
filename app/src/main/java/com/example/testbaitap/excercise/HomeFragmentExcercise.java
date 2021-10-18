package com.example.testbaitap.excercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.utils.TypefaceManager;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;


public class HomeFragmentExcercise extends Fragment {

    boolean doubleBackToExitPressedOnce = false;
    EditText edt_search;
    ArrayList<NhomCo> nhomCoArrayList = new ArrayList<>();
    //Home_Adapter home_adapter;
    private JazzyRecyclerViewScrollListener jazzyScrollListener;
    GridLayoutManager manager;
    RecyclerView recyclerview_homepage;
    TypefaceManager typefaceManager;

    public HomeFragmentExcercise() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_excercise, container, false);
        recyclerview_homepage = view.findViewById(R.id.recyclerview_homepage);

        NhomCo nhomCo1 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo2 = new NhomCo(1, "Tay trước","a");
        NhomCo nhomCo3 = new NhomCo(1, "Bụng","a");
        NhomCo nhomCo4 = new NhomCo(1, "Đùi","a");
        NhomCo nhomCo5 = new NhomCo(1, "Tay Sau","a");
        NhomCo nhomCo6 = new NhomCo(1, "Mông","a");
        NhomCo nhomCo7 = new NhomCo(1, "Chân","a");
        NhomCo nhomCo8 = new NhomCo(1, "Vai","a");
        NhomCo nhomCo9 = new NhomCo(1, "Lưng","a");
        NhomCo nhomCo10 = new NhomCo(1, "Xô","a");
        NhomCo nhomCo11 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo12 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo13 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo14 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo15 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo16 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo17 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo18 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo19 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo20 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo21 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo22 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo23 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo24 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo25 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo26 = new NhomCo(1, "Ngực","a");
        NhomCo nhomCo27 = new NhomCo(1, "Ngực","a");


        nhomCoArrayList.add(nhomCo1);
        nhomCoArrayList.add(nhomCo2);
        nhomCoArrayList.add(nhomCo3);
        nhomCoArrayList.add(nhomCo4);
        nhomCoArrayList.add(nhomCo5);
        nhomCoArrayList.add(nhomCo6);
        nhomCoArrayList.add(nhomCo7);
        nhomCoArrayList.add(nhomCo8);
        nhomCoArrayList.add(nhomCo9);
        nhomCoArrayList.add(nhomCo10);
        nhomCoArrayList.add(nhomCo11);
        nhomCoArrayList.add(nhomCo12);
        nhomCoArrayList.add(nhomCo13);
        nhomCoArrayList.add(nhomCo14);
        nhomCoArrayList.add(nhomCo15);
        nhomCoArrayList.add(nhomCo16);
        nhomCoArrayList.add(nhomCo17);
        nhomCoArrayList.add(nhomCo18);
        nhomCoArrayList.add(nhomCo19);
        nhomCoArrayList.add(nhomCo21);
        nhomCoArrayList.add(nhomCo22);
        nhomCoArrayList.add(nhomCo23);
        nhomCoArrayList.add(nhomCo24);
        nhomCoArrayList.add(nhomCo25);
        nhomCoArrayList.add(nhomCo26);
        nhomCoArrayList.add(nhomCo27);


        this.manager = new GridLayoutManager(getActivity(), 2);
        this.typefaceManager = new TypefaceManager(getActivity().getAssets(), getActivity());
        this.recyclerview_homepage.setLayoutManager(this.manager);
        HomeExcerciseAdapter homeExcerciseAdapter = new HomeExcerciseAdapter(nhomCoArrayList, requireContext());
        recyclerview_homepage.setAdapter(homeExcerciseAdapter);
        homeExcerciseAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(requireContext(),String.valueOf(nhomCoArrayList.get(position)),Toast.LENGTH_SHORT).show();
            }
        });
        //recyclerview_homepage.setLayoutManager(new GridLayoutManager(requireContext(),1,GridLayoutManager.HORIZONTAL,false));
        this.jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        this.recyclerview_homepage.addOnScrollListener(this.jazzyScrollListener);
        this.jazzyScrollListener.setTransitionEffect(11);
        this.jazzyScrollListener.setMaxAnimationVelocity(0);

        return view;
    }
}