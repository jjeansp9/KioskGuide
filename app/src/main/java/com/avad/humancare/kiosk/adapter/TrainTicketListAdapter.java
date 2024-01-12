package com.avad.humancare.kiosk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.model.TrainReservationInfo;
import com.avad.humancare.kiosk.train.TrainTicketActivity;
import com.avad.humancare.kiosk.util.Common;
import com.avad.humancare.kiosk.util.Utils;

import java.util.ArrayList;

import androidx.appcompat.widget.ButtonBarLayout;

public class TrainTicketListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TrainReservationInfo> mList;

    public TrainTicketListAdapter(Context context, ArrayList<TrainReservationInfo> list) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;
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
        ViewHolder holder= null;

        if(view == null) {
            view = mInflater.inflate(R.layout.train_ticket_list_time, null);
            holder = new ViewHolder();
            holder.dateTv = (TextView) view.findViewById(R.id.date);
            holder.ticketCntTv = (TextView) view.findViewById(R.id.ticket_count);
            holder.startStationTv = (TextView) view.findViewById(R.id.train_start_station);
            holder.startTimeTv = (TextView) view.findViewById(R.id.train_start_time);
            holder.endStationTv = (TextView) view.findViewById(R.id.train_arrival_station);
            holder.endTimeTv = (TextView) view.findViewById(R.id.train_arrival_time);
            holder.trainNameTv = (TextView) view.findViewById(R.id.train_name);
            holder.trainInfoTv = (TextView) view.findViewById(R.id.train_infos);
            holder.leftBtn = (Button) view.findViewById(R.id.leftBtn);
            holder.rightBtn = (Button) view.findViewById(R.id.rightBtn);
            holder.seatInfoViewGroup = view.findViewById(R.id.seat_layout);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        TrainReservationInfo item = mList.get(i);

        holder.dateTv.setText(item.getStartDateStr(mContext, true));
        holder.ticketCntTv.setText(mContext.getString(R.string.train_ticket_count, item.personList.size()));
        holder.startStationTv.setText(item.startStation);
        holder.endStationTv.setText(item.arrivalStation);
        holder.startTimeTv.setText(item.trainInfo.startTime);
        holder.endTimeTv.setText(item.trainInfo.endTime);
        holder.trainNameTv.setText(item.trainInfo.name + " " + item.trainInfo.nameNum);

        // 좌석정보1 - 예) 일반실 | 순방향 | 어른
        StringBuilder seatInfos = new StringBuilder();
        if(item.trainInfo.selectedSeatType == Common.TrainSeatConstant.ROOM_BASIC) {
            seatInfos.append(mContext.getString(R.string.train_search_subtitle_basic));
        } else {
            seatInfos.append(mContext.getString(R.string.train_search_subtitle_vip));
        }
        seatInfos.append(" | ");
        if(item.seatList.get(0).direction == Common.TrainSeatConstant.DIRECTION_FORWARD) {
            seatInfos.append(mContext.getString(R.string.train_seat_direction));
        } else {
            seatInfos.append(mContext.getString(R.string.train_seat_reverse_direction));
        }
        holder.trainInfoTv.setText(seatInfos.toString());

        // 좌석정보2
        holder.seatInfoViewGroup.removeAllViews();
        for(int idx=0; idx<item.seatList.size(); idx++) {
            View v = mInflater.inflate(R.layout.train_ticket_list_seat_info_item, null);
            TextView trainNumTv = (TextView) v.findViewById(R.id.train_number);
            TextView seatNumTv = (TextView) v.findViewById(R.id.seat_number);
            TextView ticketNumTv = (TextView) v.findViewById(R.id.ticket_number);
            trainNumTv.setText(item.seatList.get(idx).trainNumName);
            seatNumTv.setText(item.seatList.get(idx).name);
            ticketNumTv.setText(Utils.getRandomNumber(10000, 99999) + "-" + Utils.getRandomNumber(1000, 9999) + "-" + Utils.getRandomNumber(10000, 99999) + "-"+ Utils.getRandomNumber(10, 99));
            holder.seatInfoViewGroup.addView(v);
        }

        holder.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TrainTicketActivity)mContext).goTicketDelivery(i);
            }
        });

        holder.rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TrainTicketActivity)mContext).showTicketReturnDialog(i);
            }
        });

        return view;
    }

    class ViewHolder {
        TextView dateTv, ticketCntTv;
        TextView startStationTv, startTimeTv, endStationTv, endTimeTv;
        TextView trainNameTv, trainInfoTv;
        Button leftBtn, rightBtn;
        ViewGroup seatInfoViewGroup;
    }
}
