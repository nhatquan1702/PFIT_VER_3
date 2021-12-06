package com.example.testbaitap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testbaitap.R;
import com.example.testbaitap.entity.KhoaTap;
import com.example.testbaitap.excercise.ItemClickInterface;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TrainingCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<KhoaTap> mObjects;
    public static ItemClickInterface itemClickListener;

    public TrainingCourseAdapter(Context mContext, ArrayList<KhoaTap> mObjects) {
        this.mContext = mContext;
        this.mObjects = mObjects;
    }

    @Override
    public void onClick(View v) {

    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View itemView = layoutInflater.inflate(R.layout.item_khoa_tap,parent,false);
        return new KhoaTapViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        KhoaTapViewHolder khoaTapViewHolder = (KhoaTapViewHolder) holder;
        khoaTapViewHolder.mTitle.setText(mObjects.get(position).getTenKhoaTap());
        Picasso.get()
                .load(mObjects.get(position).getHinhQuangCao())
                .placeholder(R.mipmap.logo1)
                .error(R.mipmap.logo1)
                .into(khoaTapViewHolder.mImg);
    }

    @Override
    public int getItemCount() {
        if (mObjects != null)
            return mObjects.size();
        else
            return 0;
    }

    public void updateChange(ArrayList<KhoaTap> data) {
        mObjects = data;
        notifyDataSetChanged();
    }

    public KhoaTap getAtPosition(int position){
        return (KhoaTap) mObjects.get(position);
    }

    public void setOnItemClickListener(ItemClickInterface clickListener) {
        TrainingCourseAdapter.itemClickListener = clickListener;
    }

    public class KhoaTapViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImg;
        private TextView mTitle;


        public KhoaTapViewHolder(View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.imgHinhQuangCao);
            mTitle = itemView.findViewById(R.id.tvTenKhoaTap);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
