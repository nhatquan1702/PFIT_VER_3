package com.example.testbaitap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.testbaitap.R;
import com.example.testbaitap.entity.NgayTap;
import com.example.testbaitap.excercise.ItemCleckInterfaceCheckBox;
import com.example.testbaitap.excercise.ItemClickInterface;

import java.util.ArrayList;

public class NgayTapRecyclerAdapter extends RecyclerView.Adapter<NgayTapRecyclerAdapter.ViewHolder> {
    private ArrayList<NgayTap> ngayTapArrayList = new ArrayList<>();
    private ArrayList<Integer> process = new ArrayList<>();
    private Context context;
    private ItemClickInterface itemClickInterface;
    private ItemCleckInterfaceCheckBox itemCleckInterfaceCheckBox;

    public NgayTapRecyclerAdapter(ArrayList<NgayTap> ngayTapArrayList, Context context, ArrayList<Integer> pro) {
        this.ngayTapArrayList = ngayTapArrayList;
        this.context = context;
        this.process = pro;
    }

    @NonNull
    @Override
    public NgayTapRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ngay_tap_item, parent, false);
        NgayTapRecyclerAdapter.ViewHolder viewHolder = new NgayTapRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NgayTapRecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        if(ngayTapArrayList==null){
            return 0;
        }
        else {
            return ngayTapArrayList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ngayTap;
        public NumberProgressBar numberProgressBar;
        public CardView cardView;
        public ImageView imgNgayNghi;
        public RelativeLayout rel1,rel2;
        public CheckBox checkboxHT;


        public ViewHolder(@NonNull View view) {
            super(view);
            this.ngayTap = (TextView) view.findViewById(R.id.row_day);
            this.cardView = (CardView) view.findViewById(R.id.cardviewone);
            this.imgNgayNghi = (ImageView) view.findViewById(R.id.restImageView);
            this.numberProgressBar = (NumberProgressBar) view.findViewById(R.id.progressbar);
            this.rel1 = (RelativeLayout) view.findViewById(R.id.rel1);
            this.rel2 = (RelativeLayout) view.findViewById(R.id.rel2);
            this.checkboxHT = (CheckBox) view.findViewById(R.id.checkboxHT);
            if (checkboxHT.isChecked()) {
                checkboxHT.setChecked(false);
            }
            else {
                checkboxHT.setChecked(true);
            }
            this.setIsRecyclable(false);
        }


        public void bind(@NonNull ViewHolder viewHolder, int i){
            viewHolder.numberProgressBar.setMax(100);
            if ((i + 1) % 4 != 0 || i > 27) {
                viewHolder.imgNgayNghi.setVisibility(View.GONE);
                viewHolder.rel2.setVisibility(View.GONE);

                viewHolder.ngayTap.setText("Ngày "+ String.valueOf(ngayTapArrayList.get(i).getNgayTap()));
                viewHolder.ngayTap.setVisibility(View.VISIBLE);
                viewHolder.rel1.setVisibility(View.VISIBLE);
                if (((int) (Integer.parseInt(process.get(i).toString()))) >= 99) {
                    viewHolder.numberProgressBar.setProgress(100);
                } else {
                    viewHolder.numberProgressBar.setProgress((int) (Integer.parseInt(process.get(i).toString())));
                }
            } else {
                viewHolder.numberProgressBar.setVisibility(View.GONE);
                viewHolder.checkboxHT.setVisibility(View.GONE);
                viewHolder.rel1.setVisibility(View.GONE);
                viewHolder.ngayTap.setText("Ngày nghỉ");
                viewHolder.imgNgayNghi.setVisibility(View.VISIBLE);
                viewHolder.rel2.setVisibility(View.VISIBLE);

            }
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickInterface.onClick(v, i);
                }
            });
            viewHolder.checkboxHT.setOnCheckedChangeListener(null);
            viewHolder.checkboxHT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    itemCleckInterfaceCheckBox.onClick(buttonView, i, isChecked);
                }
            });


        }
    }
    public void setOnClickItemRecyclerView(ItemClickInterface itemRecyclerView){
        itemClickInterface = itemRecyclerView;
    }
    public void setItemCleckInterfaceCheckBox(ItemCleckInterfaceCheckBox itemCleckInterfaceCB){
        itemCleckInterfaceCheckBox = itemCleckInterfaceCB;
    }
    public int getItemViewType(int i) {
        return i;
    }
    public long getItemId(int i) {
        return (long) i;
    }
}
