package com.example.testbaitap.process.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewProcessAdapter extends RecyclerView.Adapter<RecyclerViewProcessAdapter.ViewHolder>{
    private static final int TYPE_SUMMARY = 0;
    private static final int TYPE_DAY_CHART = 1;
    private static final int TYPE_CHART = 2;
    private List<Object> mItems;
    private OnItemClickListener mItemClickListener;
    private TextView txtProgress ,tv1,tv2,tv3;
    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v;
//        ViewHolder vh;
//        switch (viewType) {
//            case TYPE_CHART:
//                v = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.card_activity_bar_chart, parent, false);
//                vh = new CombinedChartViewHolder(v);
//                break;
//            case TYPE_DAY_CHART:
//                v = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.card_activity_chart, parent, false);
//                vh = new ChartViewHolder(v);
//                break;
//            case TYPE_SUMMARY:
//            default:
//                v = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.card_activity_summary, parent, false);
//                vh = new SummaryViewHolder(v);
//                break;
//        }
//        return vh;
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
        }
    }
    public interface OnItemClickListener {
        //void onActivityChartDataTypeClicked(ActivityDayChart.DataType newDataType);

        void setActivityChartDataTypeChecked(Menu popup);

        void onPrevClicked();

        void onNextClicked();

        void onTitleClicked();

        void inflateWalkingModeMenu(Menu menu);

        void onWalkingModeClicked(int id);
    }
}
