package com.avad.humancare.kiosk.train;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.avad.humancare.kiosk.HumanCareKioskApplication;
import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.TrainSearchListAdapter;
import com.avad.humancare.kiosk.dialog.PopupDialog;
import com.avad.humancare.kiosk.model.TrainInfo;
import com.avad.humancare.kiosk.util.DateUtil;
import com.avad.humancare.kiosk.view.TrainHeaderView;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class TrainSearchActivity extends TrainBaseActivity {

    private String TAG = TrainSearchActivity.class.getSimpleName();

    private Context mContext;
    private ListView mListView;
    private TextView mFooterTv, mTitleDateTv;

    private TrainSearchListAdapter mListAdapter = null;
    private ArrayList<TrainInfo> mTrainInfoList = new ArrayList<>();
    private boolean mIsStandBy = false; // 예약대기 표시 여부 : true - 예약대기, false - 일반 예매
    private TrainInfo mSelectedTrain;
    private String mSelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_search);

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
        headerView.setHeaderTitle(getString(R.string.train_search_title));
        findViewById(R.id.btn_back_main).setOnClickListener(this);
    }

    private void initView() {

        // footer
        findViewById(R.id.footer_right_ly).setOnClickListener(this);
        mFooterTv = (TextView)findViewById(R.id.footer_right_text);
        mFooterTv.setText(R.string.train_search_reservation);

        mListView = (ListView) findViewById(R.id.listView);
        mListAdapter = new TrainSearchListAdapter(this, mTrainInfoList);
        mListView.setAdapter(mListAdapter);

        TextView startStationTv = (TextView) findViewById(R.id.start_station);
        TextView endStationTv = (TextView) findViewById(R.id.end_station);
        mTitleDateTv = (TextView) findViewById(R.id.ym_text);

        findViewById(R.id.last_day_btn).setOnClickListener(this);
        findViewById(R.id.next_day_btn).setOnClickListener(this);

        if(mReservationInfo != null) {
            mSelectedDate = mReservationInfo.getStartDateStr(this);

            startStationTv.setText(mReservationInfo.startStation);
            endStationTv.setText(mReservationInfo.arrivalStation);
            mTitleDateTv.setText(mReservationInfo.getStartDateStr(this, true));
        }

    }

    private void setData() {

        // 기차 전체 데이터 리스트
        ArrayList<TrainInfo> allList = new ArrayList<>();
        allList.add(new TrainInfo("무궁화호", 1201, "06:31", "10:04", 18500, 0, false, false, false, false));
        allList.add(new TrainInfo("ITX-새마을", 1001, "07:34", "10:31", 27400, 0, false, false, false, false));
        allList.add(new TrainInfo("KTX", 121, "08:46", "10:38", 31300, 43800, false, false, false, false));
        allList.add(new TrainInfo("ITX-새마을", 1005, "11:00", "14:41", 27400, 0, false, false, true, false));
        allList.add(new TrainInfo("KTX-산천", 125, "16:41", "18:32", 31300, 43800, true, false, false, false));
        allList.add(new TrainInfo("KTX-산천", 126, "17:41", "18:32", 31300, 43800, true, false, false, true));
        allList.add(new TrainInfo("KTX-산천", 128, "23:09", "23:58", 31300, 43800, false, false, false, true));

        for(TrainInfo info : allList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(info.startTime, formatter);
            LocalTime time = LocalTime.of(mReservationInfo.startHour, 0);

            if(startTime.isAfter(time) || time.equals(startTime)) {
                mTrainInfoList.add(info);
            }
        }

    }

    public void setFooterText(String str, boolean isStandBy) {
        mFooterTv.setText(str);
        mIsStandBy = isStandBy;
    }

    public void setSelectedTrainInfo(TrainInfo info) {
        mSelectedTrain = info;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        Intent intent = null;

        switch (view.getId()) {
            case R.id.footer_right_ly:
                if(mSelectedTrain == null) {
                    showNormalDialog(getString(R.string.train_search_no_select));
                    return ;
                }

                mReservationInfo.trainInfo = mSelectedTrain;
                mReservationInfo.setDate(0, mSelectedDate); // 선택한 아이템의 날짜로 set

                if(mIsStandBy) {
                    showReservationRequestDialog();
                    return ;
                }

                intent = new Intent();
                intent.putExtra(TrainReservationActivity.INTENT_REQUEST_PARAM_TYPE, TrainReservationActivity.INTENT_PARAM_TYPE_TRAIN_SEARCH);
                intent.putExtra("data", mReservationInfo);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.last_day_btn :{
                String dateStr = mTitleDateTv.getText().toString();

                try {
                    // 현재 이전일 체크
                    SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_YMD);
                    Date date = sdf.parse(dateStr);

                    if(date.compareTo(new Date()) < 0) {
                        showNormalDialog(mContext.getString(R.string.train_calendar_no_selected_day));
                        return ;
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }

                mSelectedDate= DateUtil.getCalculationDate(dateStr, -1);
                mTitleDateTv.setText(DateUtil.getDateFormatter(mSelectedDate));
            }
                break;

            case R.id.next_day_btn : {
                String dateStr = mTitleDateTv.getText().toString();
                mSelectedDate = DateUtil.getCalculationDate(dateStr, 1);
                mTitleDateTv.setText(DateUtil.getDateFormatter(mSelectedDate));
            }
                break;
        }
    }

    private void showReservationRequestDialog() {

        PopupDialog dialog = new PopupDialog(this);
        dialog.setTitle(getString(R.string.train_reservation_request_complete_title));
        dialog.setContent(getString(R.string.train_reservation_request_complete));
        dialog.setOnOkButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HumanCareKioskApplication.mTrainInstance_reservationInfo = mReservationInfo;

                dialog.dismiss();
                findViewById(R.id.btn_back_main).performClick();
                finish();
            }
        });

        dialog.show();
    }

    public void goSeatSelect() {
        mReservationInfo.trainInfo = mSelectedTrain;

        Intent intent = new Intent(this, TrainSeatSelectActivity.class);
        intent.putExtra("data", mReservationInfo);
        mLauncher.launch(intent);
    }

    private ActivityResultLauncher mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {

        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();

                mReservationInfo = intent.getParcelableExtra("data");

            } else if(result.getResultCode() == RESULT_CANCELED) {
                mListAdapter.setSelectedPosition(-1);
                mListAdapter.notifyDataSetChanged();
                mSelectedTrain = null;
            }

        }
    });


}