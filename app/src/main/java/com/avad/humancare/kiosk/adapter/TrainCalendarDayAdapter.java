package com.avad.humancare.kiosk.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.dialog.PopupDialog;
import com.avad.humancare.kiosk.model.Day;
import com.avad.humancare.kiosk.train.TrainCalendarActivity;
import com.avad.humancare.kiosk.util.Common;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TrainCalendarDayAdapter extends RecyclerView.Adapter<TrainCalendarDayAdapter.ViewHolder> {

    private String TAG = TrainCalendarDayAdapter.class.getSimpleName();

    public static final int TYPE_WEEK = 0; // 요일
    public static final int TYPE_DATE = 1; // 날짜

    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<Day> mList = null;
    private int mType = TYPE_DATE;
    private int mDateType = Common.TRAIN_DATE_TYPE_START;
    private int mSelectedPos = -1;
    private int mTodayPos = -1;

    public TrainCalendarDayAdapter(Context context, ArrayList<Day> list, int type, int dateType) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;
        mType = type;
        mDateType = dateType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.calendar_day_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day = mList.get(position);

        switch (mType) {
            case TYPE_WEEK:
                holder.dayTv.setText(day.dayStr);
                holder.dayTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                holder.dayCommentTv.setVisibility(View.INVISIBLE);
                break;

            case TYPE_DATE:
                holder.dayTv.setText(String.valueOf(day.dayStr));
                holder.dayTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_8));
                if(mDateType == Common.TRAIN_DATE_TYPE_START) {
                    holder.dayCommentTv.setText(R.string.train_start_date);
                } else if(mDateType == Common.TRAIN_DATE_TYPE_END) {
                    holder.dayCommentTv.setText(R.string.train_arrival_date);
                }
                break;
        }

        if(position % 7 == 0) {
            holder.dayTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_red));
        } else if(position % 7 == 6) {
            holder.dayTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_blue));
        }

        // 선택된 아이템 color 변경
        //Log.e(TAG, position + " >> KHJ++ onBindViewHolder() mSelectedPos="+mSelectedPos + " / day.comment="+day.comment);
        if(mType == TYPE_DATE) {
            if(mSelectedPos != -1 && position == mSelectedPos) { // 선택된 날짜인 경우
                if(day.day != 0) { // 빈칸이 아닌 날짜가 있는 경우
                    holder.viewLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.train_bg_color3));
                    holder.dayTv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    holder.dayCommentTv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    holder.dayCommentTv.setVisibility(View.VISIBLE);
                } else {
                    holder.dayCommentTv.setVisibility(View.INVISIBLE);
                }
            }
            else {
                holder.viewLayout.setBackgroundColor(Color.WHITE);
                holder.dayCommentTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));

                if(day.bIsToday) { // "오늘" 표시
                    holder.dayCommentTv.setText(day.comment);
                    holder.dayCommentTv.setVisibility(View.VISIBLE);

                    if(mSelectedPos == -1) {
                        holder.viewLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.train_bg_color3));
                        holder.dayTv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        holder.dayCommentTv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    }

                } else {
                    holder.dayCommentTv.setVisibility(View.INVISIBLE);
                }
            }
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dayTv, dayCommentTv;
        ViewGroup viewLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTv = itemView.findViewById(R.id.day_text);
            dayCommentTv = itemView.findViewById(R.id.day_comment);
            viewLayout = itemView.findViewById(R.id.day_ly);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Day day = mList.get(position);
            //Log.d(TAG, position + " >> onClick() : mSelectedPos="+mSelectedPos + " / day="+day.day + " / mTodayPos="+mTodayPos);

            if(dayTv.getText().toString().isEmpty()) {
                // 날짜가 없는 빈칸은 click 이벤트 되지 않도록 처리
                return ;
            }
            else if(day.bTodayMonth && position < mTodayPos) {
                // 오늘 이전 날짜는 선택 불가하도록
                ((TrainCalendarActivity)mContext).showNormalDialog(mContext.getString(R.string.train_calendar_no_selected_day));
                return ;
            }

            if(mSelectedPos != -1) {
                // 이전에 선택된 아이템 원래대로 color 변경
                notifyItemChanged(mSelectedPos);
            }

            mSelectedPos = position;
            notifyItemChanged(position);

        }
    }

    public void setSelectedItem(int position, boolean isToday) {
        mSelectedPos = position;
        if(isToday) {
            mTodayPos = position;
        }
    }

    public void setInitSelectedPosition() {
        mSelectedPos = -1;
    }

    public Day getSelectedItem() {
        if (mSelectedPos == -1) return new Day();
        else return mList.get(mSelectedPos);
    }

}
