package com.avad.humancare.kiosk.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TrainCalendarTimeAdapter extends RecyclerView.Adapter<TrainCalendarTimeAdapter.ViewHolder> {

    private String TAG = TrainCalendarTimeAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<String> mList = null;
    private LayoutInflater mInflater;
    private int mSelectedPos = -1;

    public TrainCalendarTimeAdapter(Context context, ArrayList<String> list) {
        mContext = context;
        mList = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.calendar_time_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.timeTv.setText(mList.get(position) + mContext.getString(R.string.hour));

        if(mSelectedPos != -1 && position == mSelectedPos) {
            holder.timeLy.setBackgroundColor(ContextCompat.getColor(mContext, R.color.train_bg_color3));
            holder.timeTv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.timeLy.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.timeTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewGroup timeLy;
        TextView timeTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeLy = itemView.findViewById(R.id.time_ly);
            timeTv = (TextView) itemView.findViewById(R.id.time_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if(mSelectedPos != -1) {
                        notifyItemChanged(mSelectedPos);
                    }

                    mSelectedPos = position;
                    notifyItemChanged(position);

                }
            });
        }
    }

    public void setSelectedItem(int time) {
        mSelectedPos = time;
        notifyItemChanged(mSelectedPos);
    }

    public String getSelectedItem() {
        return mList.get(mSelectedPos);
    }

}
