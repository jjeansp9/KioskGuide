package com.avad.humancare.kiosk.train;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.TrainSeatGridAdapter;
import com.avad.humancare.kiosk.model.TrainSeat;
import com.avad.humancare.kiosk.util.Common;
import com.avad.humancare.kiosk.view.TrainHeaderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TrainSeatSelectActivity extends TrainBaseActivity {

    private String TAG = TrainSeatSelectActivity.class.getSimpleName();

    private Context mContext;
    private GridView mGridView;
    private TextView mTrainNumberTv;

    private TrainSeatGridAdapter mListAdapter;

    private ArrayList<TrainSeat> mList = new ArrayList<>();
    private int mRowCount = 14;
    private int mSoldOutSeatCount = 0;  // 마감석 카운트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_seat_select);

        mContext = this;

        Intent intent = getIntent();
        if(intent != null) {
            mReservationInfo = intent.getParcelableExtra("data");
        }

        setHeaderView();
        setData();
        initView();
    }

    private void setHeaderView() {
        TrainHeaderView headerView = (TrainHeaderView) findViewById(R.id.header);
        headerView.setHeaderTitle(getString(R.string.train_seat_select_title));
        findViewById(R.id.btn_back_main).setOnClickListener(this);
        headerView.setBackButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void initView() {

        // footer
        findViewById(R.id.footer_right_ly).setOnClickListener(this);
        ((TextView)findViewById(R.id.footer_right_text)).setText(R.string.train_seat_footer_title);

        mGridView = (GridView) findViewById(R.id.gridView);
        mListAdapter = new TrainSeatGridAdapter(this, mList, mReservationInfo.seatCount);
        mGridView.setAdapter(mListAdapter);

        TextView trainNameTv = (TextView) findViewById(R.id.train_name);
        TextView trainSeatInfoTv = (TextView) findViewById(R.id.train_seat_info);
        mTrainNumberTv = (TextView) findViewById(R.id.train_number);

        mTrainNumberTv.setText(mList.get(0).trainNumName);
        trainNameTv.setText(mReservationInfo.trainInfo.name + " "+ mReservationInfo.trainInfo.nameNum);
        trainSeatInfoTv.setText(getString(R.string.train_seat_count_remain, (mRowCount*4-mSoldOutSeatCount)) + " / " + getString(R.string.train_seat_count_remain, (mRowCount*4)));

    }

    private void setData() {

        String[] arr = {"A", "B", "", "C", "D"};
        mSoldOutSeatCount = 0;

        TrainSeat seat = null;
        for(int i=mRowCount; i>0; i--) {
            for(int j=0; j<arr.length; j++) {
                seat = new TrainSeat();
                seat.trainNumName = "2"+getString(R.string.train_seat_train_number);
                seat.seatRowNum = i;
                seat.seatColumn = arr[j];
                seat.name= i + arr[j];
                if(arr[j].isEmpty()) {
                    seat.isSeat = false;
                }

                if(i <= mRowCount/2) {
                    seat.direction = Common.TrainSeatConstant.DIRECTION_REVERSE;
                    //Log.d(TAG, i + " >> KHJ++ j="+j + " / direction="+seat.direction);
                }

                // temp - 해당 라인까지 마감석,잔여석 랜덤표시
                if(i > 8) {
                    Random random = new Random();
                    int r = random.nextInt(2);
                    if(r == 0) {
                        seat.seatType = Common.TrainSeatConstant.TYPE_NONE;
                    } else {
                        seat.seatType = Common.TrainSeatConstant.TYPE_SOLDOUT;
                        mSoldOutSeatCount++;
                    }
                }

                mList.add(seat);
            }
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;

        switch (view.getId()) {
            case R.id.footer_right_ly :
                HashMap<Integer, TrainSeat> seatMap = mListAdapter.getSelectedSeatInfo();
                if(mReservationInfo.seatCount != seatMap.size()) {
                    showNormalDialog(getString(R.string.train_errmsg_not_all_seat_selected));
                    return ;
                }

                ArrayList<TrainSeat> list = new ArrayList<TrainSeat>();
                for(Integer key : seatMap.keySet()) {
                    list.add(seatMap.get(key));
                }
                mReservationInfo.seatList = list;

                intent = new Intent();
                intent.putExtra(TrainReservationActivity.INTENT_REQUEST_PARAM_TYPE, TrainReservationActivity.INTENT_PARAM_TYPE_SEAT_SELECT);
                intent.putExtra("data", mReservationInfo);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}