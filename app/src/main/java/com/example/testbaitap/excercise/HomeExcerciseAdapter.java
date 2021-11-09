package com.example.testbaitap.excercise;

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
import com.example.testbaitap.entity.NhomCo;
import com.example.testbaitap.utils.TypefaceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeExcerciseAdapter extends RecyclerView.Adapter<HomeExcerciseAdapter.ViewHolder>{
    private ArrayList<NhomCo> nhomCoArrayList = new ArrayList<>();
    private Context context;
    private boolean isFilter = false;
    private TypefaceManager typefaceManager;
    private ItemClickInterface itemClickInterface;
    public HomeExcerciseAdapter(ArrayList<NhomCo> arrayList, Context context2) {
        this.context = context2;
        this.nhomCoArrayList = arrayList;
        this.typefaceManager = new TypefaceManager(context2.getAssets(), context2);
    }

    @NonNull
    @Override
    public HomeExcerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_excercise_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeExcerciseAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.nhomCoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgNhomCo;
        public RelativeLayout rl_home;
        public TextView tvTenNhomCo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTenNhomCo = (TextView) itemView.findViewById(R.id.tv_ten_nhom_co);
            this.imgNhomCo = (ImageView) itemView.findViewById(R.id.image_nhom_co);
            this.rl_home = (RelativeLayout) itemView.findViewById(R.id.rl_home_ex);
            this.tvTenNhomCo.setTypeface(HomeExcerciseAdapter.this.typefaceManager.getLight());
        }


        public void bind(int i){
            tvTenNhomCo.setText(nhomCoArrayList.get(i).getTenNhomCo());
            Picasso.get()
                    .load(nhomCoArrayList.get(i).getHinhMinhHoa())
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.mipmap.logo1)
                    .error(R.mipmap.logo1)
                    .into(imgNhomCo);
            rl_home.setOnClickListener(new View.OnClickListener() {
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
