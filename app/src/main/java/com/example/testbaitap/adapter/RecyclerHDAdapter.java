package com.example.testbaitap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testbaitap.R;
import com.example.testbaitap.entity.HoaDon;
import com.example.testbaitap.excercise.ItemClickInterface;

import java.util.ArrayList;

public class RecyclerHDAdapter extends RecyclerView.Adapter<RecyclerHDAdapter.ViewHolder>{
    private ArrayList<HoaDon> hoaDonArrayList = new ArrayList<>();
    private Context context;
    private ItemClickInterface itemClickInterface;

    public RecyclerHDAdapter(ArrayList<HoaDon> hoaDonArrayList, Context context) {
        this.hoaDonArrayList = hoaDonArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerHDAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.hoa_don_item, parent, false);
        RecyclerHDAdapter.ViewHolder viewHolder = new RecyclerHDAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHDAdapter.ViewHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        if(hoaDonArrayList.size()>0){
            return hoaDonArrayList.size();
        }
        else {
            return 0;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvHoTenHD, tvKhoaTapHD, tvTongTTHD, tvHinhThucTTHD;
        public CardView cardView;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.tvHoTenHD = (TextView) view.findViewById(R.id.tvHoTenHD);
            this.tvKhoaTapHD = (TextView) view.findViewById(R.id.tvKhoaTapHD);
            this.tvTongTTHD = (TextView) view.findViewById(R.id.tvTongTTHD);
            this.tvHinhThucTTHD = (TextView) view.findViewById(R.id.tvHinhThucTTHD);
            this.cardView = (CardView) view.findViewById(R.id.cvHD);
            this.setIsRecyclable(false);
        }


        public void bind(@NonNull RecyclerHDAdapter.ViewHolder viewHolder, int i){
            tvHoTenHD.setText(hoaDonArrayList.get(i).getHoTen());
            tvKhoaTapHD.setText(hoaDonArrayList.get(i).getTenKhoaTap());
            if(hoaDonArrayList.get(i).getHinhThucThanhToan()==0){
                tvHinhThucTTHD.setText("thanh toán trực tuyến");
            }
            if(hoaDonArrayList.get(i).getHinhThucThanhToan()==1){
                tvHinhThucTTHD.setText("thanh toán tiền mặt");
            }
            tvTongTTHD.setText(String.valueOf(hoaDonArrayList.get(i).getTongThanhToan())+" VND");
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickInterface.onClick(v, i);
                }
            });
        }
    }
}
