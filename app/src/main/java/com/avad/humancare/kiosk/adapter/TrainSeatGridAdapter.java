package com.avad.humancare.kiosk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.model.TrainSeat;
import com.avad.humancare.kiosk.util.Common;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainSeatGridAdapter extends BaseAdapter {

    private String TAG = TrainSeatGridAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TrainSeat> mList = null;
    private int mMaxSeatCnt = 0;

    private HashMap<Integer, TrainSeat> mSeatMap = new HashMap<Integer, TrainSeat>();

    public TrainSeatGridAdapter(Context context, ArrayList<TrainSeat> list, int maxSeatCnt) {
        mContext= context;
        mList = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMaxSeatCnt = maxSeatCnt;
    }

    @Override
    public int getCount() {
        if(mList == null) return 0;
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if(view == null) {
            view = mInflater.inflate(R.layout.train_seat_list_item, null);
            holder = new ViewHolder();
            holder.seatLayout = view.findViewById(R.id.seat_ly);
            holder.seatNameTv = view.findViewById(R.id.seat_txt);
            holder.directionImg = view.findViewById(R.id.direction_img);
            holder.directionImg.setVisibility(View.GONE);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final TrainSeat item = mList.get(i);

        holder.seatNameTv.setText(item.name);
        if(item.isSeat == false) {
            holder.directionImg.setVisibility(View.VISIBLE);
            holder.seatLayout.setVisibility(View.GONE);
        } else {
            holder.directionImg.setVisibility(View.GONE);
            holder.seatLayout.setVisibility(View.VISIBLE);
        }

        switch (item.seatType) {
            case Common.TrainSeatConstant.TYPE_NONE :
                if(item.direction == Common.TrainSeatConstant.DIRECTION_REVERSE) {
                    holder.seatLayout.setBackgroundResource(R.drawable.img_seat03_turn);
                } else {
                    holder.seatLayout.setBackgroundResource(R.drawable.img_seat03);
                }
                break;

            case Common.TrainSeatConstant.TYPE_SELECT:
                if(item.direction == Common.TrainSeatConstant.DIRECTION_REVERSE) {
                    holder.seatLayout.setBackgroundResource(R.drawable.img_seat02_turn);
                } else {
                   holder.seatLayout.setBackgroundResource(R.drawable.img_seat02);
                }
                break;

            case Common.TrainSeatConstant.TYPE_SOLDOUT:
                if(item.direction == Common.TrainSeatConstant.DIRECTION_REVERSE) {
                    holder.seatLayout.setBackgroundResource(R.drawable.img_seat01_turn);
                } else {
                    holder.seatLayout.setBackgroundResource(R.drawable.img_seat01);
                }
                break;
        }

        holder.seatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.seatType == Common.TrainSeatConstant.TYPE_SOLDOUT) {
                    return ;
                }

                if(item.seatType == Common.TrainSeatConstant.TYPE_NONE) {   // 선택시
                    if(mMaxSeatCnt == mSeatMap.size()) {
                        return ;
                    }

                    if(item.direction == Common.TrainSeatConstant.DIRECTION_REVERSE) {
                        view.setBackgroundResource(R.drawable.img_seat02_turn);
                    } else {
                        view.setBackgroundResource(R.drawable.img_seat02);
                    }

                    item.seatType = Common.TrainSeatConstant.TYPE_SELECT;
                    mSeatMap.put(i, item);

                } else {    // 선택취소시
                    if(item.direction == Common.TrainSeatConstant.DIRECTION_REVERSE) {
                        view.setBackgroundResource(R.drawable.img_seat03_turn);
                    } else {
                        view.setBackgroundResource(R.drawable.img_seat03);
                    }

                    item.seatType = Common.TrainSeatConstant.TYPE_NONE;
                    mSeatMap.remove(i);
                }

            }
        });

        return view;
    }

    public HashMap<Integer, TrainSeat> getSelectedSeatInfo() {
        return mSeatMap;
    }

    class ViewHolder {
        ImageView directionImg;
        ViewGroup seatLayout;
        TextView seatNameTv;
    }
}
