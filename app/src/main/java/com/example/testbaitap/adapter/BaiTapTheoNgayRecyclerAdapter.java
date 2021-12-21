package com.example.testbaitap.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testbaitap.R;
import com.example.testbaitap.entity.BaiTap;
import com.example.testbaitap.entity.ChiTietBaiTapChoHV;
import com.example.testbaitap.excercise.ItemClickInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BaiTapTheoNgayRecyclerAdapter extends RecyclerView.Adapter<BaiTapTheoNgayRecyclerAdapter.ViewHolder> {
    private ArrayList<BaiTap> baiTapArrayList = new ArrayList<>();
    private ArrayList<ChiTietBaiTapChoHV> chiTietBaiTapChoHVArrayList = new ArrayList<>();
    private Context context;
    private ItemClickInterface itemClickInterface;

    public BaiTapTheoNgayRecyclerAdapter(ArrayList<BaiTap> baiTapArrayList, Context context, ArrayList<ChiTietBaiTapChoHV> chiTietBaiTapChoHVArrayList) {
        this.chiTietBaiTapChoHVArrayList = chiTietBaiTapChoHVArrayList;
        this.baiTapArrayList = baiTapArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BaiTapTheoNgayRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.excercise_item, parent, false);
        BaiTapTheoNgayRecyclerAdapter.ViewHolder viewHolder = new BaiTapTheoNgayRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull BaiTapTheoNgayRecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return baiTapArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgHinhMinhHoa;
        public RelativeLayout rel2, rel1;
        public TextView tvTenBaiTap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTenBaiTap = (TextView) itemView.findViewById(R.id.tvTenBaiTap);
            this.imgHinhMinhHoa = (ImageView) itemView.findViewById(R.id.imgHinhMinhHoa);
            this.rel2 = (RelativeLayout) itemView.findViewById(R.id.relBaiTap);
            this.rel1 = (RelativeLayout) itemView.findViewById(R.id.rel1);
        }


        @RequiresApi(api = Build.VERSION_CODES.M)
        public void bind(int i) {
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
            if (baiTapArrayList.get(i).getMaBaiTap().equals(chiTietBaiTapChoHVArrayList.get(i).getMaBaiTap()) && chiTietBaiTapChoHVArrayList.get(i).getTrangThai()==1)
                rel1.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.colorBlueT)));
            else
                rel1.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.color_corner1)));
        }
    }

    public void setOnClickItemRecyclerView(ItemClickInterface itemRecyclerView) {
        itemClickInterface = itemRecyclerView;
    }
}
