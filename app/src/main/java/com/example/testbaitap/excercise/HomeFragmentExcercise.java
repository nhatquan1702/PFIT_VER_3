package com.example.testbaitap.excercise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.testbaitap.activity.ExcerciceByMuscleActivity;
import com.example.testbaitap.activity.WaterActivity;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.NhomCo;
import com.example.testbaitap.utils.TypefaceManager;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragmentExcercise extends Fragment {

    boolean doubleBackToExitPressedOnce = false;
    EditText edt_search;

    //Home_Adapter home_adapter;
    private JazzyRecyclerViewScrollListener jazzyScrollListener;
    GridLayoutManager manager;
    RecyclerView recyclerview_homepage;
    TypefaceManager typefaceManager;
    private SimpleAPI simpleAPI;
    ArrayList<NhomCo> nhomCoArrayList;

    public HomeFragmentExcercise() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_excercise, container, false);
        recyclerview_homepage = view.findViewById(R.id.recyclerview_homepage);

        simpleAPI = Constants.instance();
        simpleAPI.getListNhomCo().enqueue(new Callback<ArrayList<NhomCo>>() {
            @Override
            public void onResponse(Call<ArrayList<NhomCo>> call, Response<ArrayList<NhomCo>> response) {
                nhomCoArrayList = response.body();
                HomeExcerciseAdapter homeExcerciseAdapter = new HomeExcerciseAdapter(nhomCoArrayList, requireContext());
                recyclerview_homepage.setAdapter(homeExcerciseAdapter);
                homeExcerciseAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(requireContext(), ExcerciceByMuscleActivity.class);
                        intent.putExtra("maNhomCo", String.valueOf(nhomCoArrayList.get(position).getMaNhomCo()));
                        intent.putExtra("tenNhomCo", String.valueOf(nhomCoArrayList.get(position).getTenNhomCo()));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<NhomCo>> call, Throwable t) {
                Toast.makeText(requireContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        this.manager = new GridLayoutManager(getActivity(), 2);
        this.typefaceManager = new TypefaceManager(getActivity().getAssets(), getActivity());
        this.recyclerview_homepage.setLayoutManager(this.manager);
        //recyclerview_homepage.setLayoutManager(new GridLayoutManager(requireContext(),1,GridLayoutManager.HORIZONTAL,false));
        this.jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        this.recyclerview_homepage.addOnScrollListener(this.jazzyScrollListener);
        this.jazzyScrollListener.setTransitionEffect(11);
        this.jazzyScrollListener.setMaxAnimationVelocity(0);

        return view;
    }
}