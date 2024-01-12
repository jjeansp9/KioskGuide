package com.avad.humancare.kiosk.train;

import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.model.TrainPassenger;
import com.avad.humancare.kiosk.util.Utils;
import com.avad.humancare.kiosk.view.TrainHeaderView;

import java.util.ArrayList;

public class TrainPassengerSeatActivity extends TrainBaseActivity {

    private String TAG = TrainPassengerSeatActivity.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private TextView mSeatCountTv;

    private ArrayList<TrainPassenger> mPassengerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_passenger_seat);

        mContext = this;
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setHeaderView();

        initView();
    }

    private void setHeaderView() {
        TrainHeaderView headerView = (TrainHeaderView) findViewById(R.id.header);
        headerView.setHeaderTitle(getString(R.string.train_main_title1));
        findViewById(R.id.btn_back_main).setOnClickListener(this);
    }

    private void initView() {

        int[] passengerArr = {R.string.train_passenger_type_baby, R.string.train_passenger_type_children, R.string.train_passenger_type_adult, R.string.train_passenger_type_old};
        int[] passengerDetailArr = {R.string.train_passenger_type_baby_detail, R.string.train_passenger_type_children_detail, R.string.train_passenger_type_adult_detail, R.string.train_passenger_type_old_detail};
        int[] passengerType = {TrainPassenger.TRAIN_PASSENGER_TYPE_BABY, TrainPassenger.TRAIN_PASSENGER_TYPE_CHILDREN, TrainPassenger.TRAIN_PASSENGER_TYPE_ADULT, TrainPassenger.TRAIN_PASSENGER_TYPE_OLD};

        findViewById(R.id.footer_right_ly).setOnClickListener(this);
        mSeatCountTv = (TextView) findViewById(R.id.seat_count_txt);

        ViewGroup passengerLayout = findViewById(R.id.passenger_ly);

        for(int i=0; i<passengerArr.length; i++) {
            TrainPassenger passenger = new TrainPassenger();
            passenger.type = passengerType[i];
            passenger.count = 0;

            if(passenger.type == TrainPassenger.TRAIN_PASSENGER_TYPE_ADULT) {
                passenger.count = 1;
            }
            mPassengerList.add(passenger);

            View v = mInflater.inflate(R.layout.train_passenger_type_layout, null);
            TextView typeTxt = v.findViewById(R.id.type);
            TextView typeDetailTxt = v.findViewById(R.id.type_detail);
            TextView countTxt = v.findViewById(R.id.count);
            ImageView minusImg = v.findViewById(R.id.btn_minus);
            ImageView plusImg = v.findViewById(R.id.btn_plus);

            typeTxt.setText(passengerArr[i]);
            float textSize = 16f;
            float textSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSize, getResources().getDisplayMetrics());
            typeTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx);
            typeDetailTxt.setText(passengerDetailArr[i]);
            typeDetailTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx);
            countTxt.setText(String.valueOf(passenger.count));
            countTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx);

            if(passenger.count > 0) {
                countTxt.setTextColor(ContextCompat.getColor(this, R.color.train_font_color_main));
                minusImg.setBackgroundResource(R.drawable.btn_minus_on);
            }

            v.setTag(i);
            minusImg.setTag(i);
            plusImg.setTag(i);

            minusImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int idx = (Integer)view.getTag();
                    TrainPassenger obj = mPassengerList.get(idx);

                    View child = passengerLayout.getChildAt(idx);
                    TextView countTv = (TextView)child.findViewById(R.id.count);

                    if(obj.count != 0) {
                        obj.count--;
                        countTv.setText(String.valueOf(obj.count));
                    }

                    if(obj.count > 0) {
                        view.setBackgroundResource(R.drawable.btn_minus_on);
                        countTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
                    } else {
                        view.setBackgroundResource(R.drawable.btn_minus_off);
                        countTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_b));
                    }

                    updatePassengerSeatCount();
                }
            });

            plusImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int idx = (Integer)view.getTag();
                    TrainPassenger obj = mPassengerList.get(idx);

                    View child = passengerLayout.getChildAt(idx);
                    TextView countTv = (TextView)child.findViewById(R.id.count);
                    ImageView minusIv = (ImageView)child.findViewById(R.id.btn_minus);

                    obj.count++;
                    countTv.setText(String.valueOf(obj.count));

                    minusIv.setBackgroundResource(R.drawable.btn_minus_on);
                    countTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));

                    updatePassengerSeatCount();
                }
            });

            passengerLayout.addView(v);
        }

        updatePassengerSeatCount();
    }

    private void updatePassengerSeatCount() {

        mSeatCountTv.setText(getString(R.string.train_passenger_seat_count, getTotalSeatCount()));
    }

    private int getTotalSeatCount() {
        int sum = 0;
        for(int i=0; i<mPassengerList.size(); i++) {
            sum += mPassengerList.get(i).count;
        }
        return sum;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.footer_right_ly :
                int totalCnt = getTotalSeatCount();

                if(totalCnt == 0) {
                    showNormalDialog(getString(R.string.train_errmsg_no_select_seat));
                    return ;
                }

                ArrayList<TrainPassenger> list = new ArrayList<>();
                for(TrainPassenger passenger : mPassengerList) {
                    if(passenger.count > 0) {
                        list.add(passenger);
                    }
                }

                Intent intent = new Intent();
                intent.putExtra(TrainReservationActivity.INTENT_REQUEST_PARAM_TYPE, TrainReservationActivity.INTENT_PARAM_TYPE_PASSENGERCOUNT);
                intent.putExtra("data", list);
                intent.putExtra("totalCnt", totalCnt);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}