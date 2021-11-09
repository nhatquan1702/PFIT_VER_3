package com.example.testbaitap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testbaitap.R;
import com.example.testbaitap.entity.BaiTap;
import com.example.testbaitap.excercise.ItemClickInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BaiTapTheoNhomCoRecyclerAdapter extends RecyclerView.Adapter<BaiTapTheoNhomCoRecyclerAdapter.ViewHolder> {
    private ArrayList<BaiTap> baiTapArrayList = new ArrayList<>();
    private Context context;
    private ItemClickInterface itemClickInterface;

    public BaiTapTheoNhomCoRecyclerAdapter(ArrayList<BaiTap> baiTapArrayList, Context context) {
        this.baiTapArrayList = baiTapArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BaiTapTheoNhomCoRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.excercise_item, parent, false);
        BaiTapTheoNhomCoRecyclerAdapter.ViewHolder viewHolder = new BaiTapTheoNhomCoRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaiTapTheoNhomCoRecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return baiTapArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhMinhHoa;
        public RelativeLayout rel2;
        public TextView tvTenBaiTap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTenBaiTap = (TextView) itemView.findViewById(R.id.tvTenBaiTap);
            this.imgHinhMinhHoa = (ImageView) itemView.findViewById(R.id.imgHinhMinhHoa);
            this.rel2 = (RelativeLayout) itemView.findViewById(R.id.relBaiTap);
        }


        public void bind(int i){
            tvTenBaiTap.setText(baiTapArrayList.get(i).getTenBaiTap());
            Picasso.get()
                    .load(baiTapArrayList.get(i).getHinhMinhHoa())
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.mipmap.logo1)
                    .error(R.mipmap.logo1)
                    .into(imgHinhMinhHoa);
            rel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickInterface.onClick(v, i);
                }
            });
        }
    }
    public void setOnClickItemRecyclerView(ItemClickInterface itemRecyclerView){
        itemClickInterface = itemRecyclerView;
    }
}
