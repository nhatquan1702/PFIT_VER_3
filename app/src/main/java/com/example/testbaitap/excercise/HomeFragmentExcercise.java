package com.example.testbaitap.excercise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.activity.ExcerciceByMuscleActivity;
import com.example.testbaitap.activity.WaterActivity;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.NhomCo;
import com.example.testbaitap.utils.Config;
import com.example.testbaitap.utils.TypefaceManager;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragmentExcercise extends Fragment {

    boolean doubleBackToExitPressedOnce = false;
    EditText edt_search;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefresh;

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
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(() -> {
            LoadData();
            mSwipeRefresh.setRefreshing(false);
        });


        LoadData();
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

    public void LoadData(){
        simpleAPI = Constants.instance();
        simpleAPI.getListNhomCo().enqueue(new Callback<ArrayList<NhomCo>>() {
            @Override
            public void onResponse(Call<ArrayList<NhomCo>> call, Response<ArrayList<NhomCo>> response) {
                try {
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
                    progressBar.setVisibility(View.GONE);
                }
                catch (Exception e){
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NhomCo>> call, Throwable t) {
                Log.d("quan", t.toString());
                //Toast.makeText(requireContext(),t.toString(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}