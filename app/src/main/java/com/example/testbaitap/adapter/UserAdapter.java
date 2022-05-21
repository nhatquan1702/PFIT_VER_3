package com.example.testbaitap.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testbaitap.R;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.TaiKhoan;
import com.example.testbaitap.excercise.ItemClickInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context mContext;
    private ArrayList<HocVien> mObjects;
    private ArrayList<HocVien> tmpObjecs;
    public static ItemClickInterface itemClickListener;

    public UserAdapter(Context context, ArrayList<HocVien> objects) {
            mContext = context;
            mObjects = objects;
            tmpObjecs = objects;
            }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View itemView = layoutInflater.inflate(R.layout.item_list_user,parent,false);
            return new BaiVietViewHolder(itemView);
            }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        HocVien baiViet = (HocVien) mObjects.get(position);
            BaiVietViewHolder baiVietViewHolder = (BaiVietViewHolder) holder;
            baiVietViewHolder.mTK.setText(baiViet.getMaHocVien());
            baiVietViewHolder.mT.setText(baiViet.getNgayThamGia());
    }

    @Override
    public int getItemCount() {
            if (mObjects != null)
            return mObjects.size();
            else
            return 0;
            }

    public void updateChange(ArrayList<HocVien> data) {
            mObjects = data;
            notifyDataSetChanged();
            }

    @Override
    public void onClick(View v) {

            }

    public HocVien getAtPosition(int position){
            return (HocVien) mObjects.get(position);
            }

    public void setOnItemClickListener(ItemClickInterface clickListener) {
            UserAdapter.itemClickListener = clickListener;
    }

    public class BaiVietViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTK;
        private TextView mT;


        public BaiVietViewHolder(View itemView) {
            super(itemView);
            mTK = itemView.findViewById(R.id.tvHoTenHD);
            mT = itemView.findViewById(R.id.tvKhoaTapHD);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
