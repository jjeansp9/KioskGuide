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
import com.avad.humancare.kiosk.model.TrainSeat;
import com.avad.humancare.kiosk.train.TrainReservationActivity;
import com.avad.humancare.kiosk.train.TrainReservationTicketActivity;

import java.util.ArrayList;

public class TrainReservationTicketListAdapter extends BaseAdapter {

    public static final int TYPE_RESERVATION_ING = 0;
    public static final int TYPE_RESERVATION_COMPLETE =1;

    private Context mContext;
    private ArrayList<TrainReservationInfo> mList = null;
    private LayoutInflater mInflater;
    private int mType;

    public TrainReservationTicketListAdapter(Context context, ArrayList<TrainReservationInfo> list, int ticketType) {
        mContext = context;
        mList = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mType = ticketType;
    }

    @Override
    public int getCount() {
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
            view = mInflater.inflate(R.layout.train_reservation_list_item, null);
            holder = new ViewHolder();
            holder.dateTv = (TextView) view.findViewById(R.id.date);
            holder.trainInfoTv = (TextView) view.findViewById(R.id.train_info);
            holder.seatIntoTv = (TextView) view.findViewById(R.id.seat_info);
            holder.stateTv = (TextView) view.findViewById(R.id.reservation_state_txt);
            holder.cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        TrainReservationInfo item = mList.get(i);

        if(mType == TYPE_RESERVATION_ING) {
            holder.seatIntoTv.setVisibility(View.VISIBLE);
            holder.stateTv.setVisibility(View.GONE);
            holder.cancelBtn.setVisibility(View.GONE);

            // 좌석정보
            String seatStr = "";
            if(item.seatList != null && item.seatList.size() > 0) {
                for(TrainSeat seat : item.seatList) {
                    if(!seatStr.isEmpty()) {
                        seatStr += ", ";
                    }
                    seatStr += seat.name;
                }
            }
            holder.seatIntoTv.setText(seatStr);
        } else {
            holder.seatIntoTv.setVisibility(View.GONE);
            holder.stateTv.setVisibility(View.VISIBLE);
            holder.cancelBtn.setVisibility(View.VISIBLE);
        }

        holder.dateTv.setText(item.getStartDateStr(mContext, true));
        holder.trainInfoTv.setText("["+item.trainInfo.name + " " + item.trainInfo.nameNum+ "] "
                + item.startStation + " " + item.trainInfo.startTime
                + " → "
                + item.arrivalStation + " " + item.trainInfo.endTime
        );
        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TrainReservationTicketActivity)mContext).showReservationCancelDialog(i);
            }
        });

        return view;
    }

    class ViewHolder {
        TextView dateTv, trainInfoTv;
        TextView stateTv, seatIntoTv;
        Button cancelBtn;
    }
}
