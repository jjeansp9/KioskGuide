package com.avad.humancare.kiosk.train;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.avad.humancare.kiosk.HumanCareKioskApplication;
import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.TrainStationNameSearchListAdapter;
import com.avad.humancare.kiosk.dialog.PopupDialog;
import com.avad.humancare.kiosk.model.Day;
import com.avad.humancare.kiosk.model.TrainPassenger;
import com.avad.humancare.kiosk.model.TrainReservationInfo;
import com.avad.humancare.kiosk.util.Common;
import com.avad.humancare.kiosk.view.TrainHeaderView;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TrainReservationActivity extends TrainBaseActivity {

    private String TAG = TrainReservationActivity.class.getSimpleName();

    public static final String INTENT_REQUEST_PARAM_TYPE = "request_type";

    public static final int INTENT_PARAM_TYPE_PASSENGERCOUNT = 100;
    public static final int INTENT_PARAM_TYPE_CALENDAR_STARTDATE = 101; // 출발일
    public static final int INTENT_PARAM_TYPE_CALENDAR_ARRIVALDATE = 102; // 도착일
    public static final int INTENT_PARAM_TYPE_TRAIN_SEARCH = 103; // 열차 조회하기
    public static final int INTENT_PARAM_TYPE_SEAT_SELECT = 200;

    private Context mContext;

    private ViewGroup mTabLayout1, mTabLayout2, mStartDateLayout, mArrivalDateLayout, mStationSearchLayout;
    private View mTabBottom1, mTabBottom2;
    private TextView mTabTextView1, mTabTextView2, mStartStationTv, mArrivalStationTv, mPassengerCountTv;
    private TextView mStartDateTv, mArrivalDateTv;
    private GridView mStationSearchGridView;
    private EditText mSearchEditText;

    private TrainStationNameSearchListAdapter mSearchListAdapter;

    private ArrayList<String> mStationDataList = new ArrayList<String>();
    private int mSelectStationPosition = Common.TRAIN_DATE_TYPE_START;    // "start" or "arrive"
    private int mSelectedTab = 0;   // 선택한 탭 - 0 : 왼쪽(편도), 1 : 오른쪽(왕복)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_reservation);

        mContext = this;
        mReservationInfo = new TrainReservationInfo();
        mTempList = new ArrayList<>();

        setHeaderView();
        setStationData();
        initView();

        if(HumanCareKioskApplication.mTrainTempList == null) {
            HumanCareKioskApplication.mTrainTempList = new ArrayList<TrainReservationInfo>();
        } else {
            HumanCareKioskApplication.mTrainTempList.clear();
        }

    }

    private void setHeaderView() {
        TrainHeaderView headerView = (TrainHeaderView) findViewById(R.id.header);
        headerView.setHeaderTitle(getString(R.string.train_main_title1));
        findViewById(R.id.btn_back_main).setOnClickListener(this);
    }

    private void initView() {

        initStationSearchView();

        // footer
        ((TextView)findViewById(R.id.footer_right_text)).setText(getString(R.string.train_make_an_inquiry));
        findViewById(R.id.footer_right_ly).setOnClickListener(this);

        // tab
        mTabLayout1 = findViewById(R.id.tab_one_way);
        mTabLayout2 = findViewById(R.id.tab_round_trip);
        mTabTextView1 = (TextView) findViewById(R.id.tab_text1);
        mTabTextView2 = (TextView) findViewById(R.id.tab_text2);
        mTabBottom1 = findViewById(R.id.tab_bottom1);
        mTabBottom2 = findViewById(R.id.tab_bottom2);
        mTabLayout1.setOnClickListener(this);
        mTabLayout2.setOnClickListener(this);

        findViewById(R.id.seat_select_ly).setOnClickListener(this);

        mStartStationTv = (TextView) findViewById(R.id.train_start_station);
        mArrivalStationTv = (TextView) findViewById(R.id.train_arrival_station);
        mStartStationTv.setOnClickListener(this);
        mArrivalStationTv.setOnClickListener(this);

        // 출발일/도착일
        mStartDateLayout = findViewById(R.id.start_date_ly);
        mStartDateLayout.setOnClickListener(this);
        mArrivalDateLayout = findViewById(R.id.arrival_date_ly);
        mArrivalDateLayout.setOnClickListener(this);
        mArrivalDateLayout.setVisibility(View.GONE);
        mPassengerCountTv = (TextView) findViewById(R.id.train_seat_count_txt);
        mStartDateTv = (TextView) findViewById(R.id.train_start_date_txt);
        mArrivalDateTv = (TextView) findViewById(R.id.train_arrival_date_txt);
        findViewById(R.id.station_change).setOnClickListener(this);

    }

    private void initStationSearchView() {
        // search layout
        mStationSearchGridView = (GridView) findViewById(R.id.main_station_listview);
        mSearchListAdapter = new TrainStationNameSearchListAdapter(this, mStationDataList);
        mStationSearchGridView.setAdapter(mSearchListAdapter);

        mStationSearchLayout = findViewById(R.id.search_station_ly);
        mStationSearchLayout.setVisibility(View.GONE);
        findViewById(R.id.search_close_btn).setOnClickListener(this);
        mSearchEditText = (EditText) findViewById(R.id.input_station_txt);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if(str.length() > 0) {
                    mStationSearchGridView.setFilterText(str);
                } else {
                    mStationSearchGridView.clearTextFilter();
                }
            }
        });
    }

    private void setStationData() {

        String[] arr = { "서울","용산"
                , "광명","영등포"
                , "수원","평택"
                , "천안아산","천안"
                , "오송","조치원"
                , "대전","서대전"
                , "김천구미","구미"
                , "동대구","대구"
                , "울산(통도사)","포항"
                , "경산","밀양"
                , "부산","신경주"
                , "구포","창원중앙"
                , "평창","진부(오대산)"
                , "강릉","익산"
                , "전주","광주송정"
                , "목포","순천"
                , "청량리","여수EXPO"
                , "동해","정동진"
                , "안동","서원주"
                , "원주","마산"
        };

        for(int i=0; i<arr.length; i++) {
            mStationDataList.add(arr[i]);
        }

        mReservationInfo.startStation = arr[0];
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;

        switch (view.getId()) {
            case R.id.footer_right_ly:
                if(TextUtils.isEmpty(mReservationInfo.startStation)) {
                    showNormalDialog(getString(R.string.train_errmsg_no_select_start_station));
                    return ;
                }

                if(TextUtils.isEmpty(mReservationInfo.arrivalStation)) {
                    showNormalDialog(getString(R.string.train_errmsg_no_select_arrival_station));
                    return ;
                }

                // 출발일 선택 체크
                if(mReservationInfo.startYear == 0) {
                    showNormalDialog(getString(R.string.train_errmsg_no_select_start_date));
                    return ;
                }

                if(mSelectedTab == 1) {
                    mReservationInfo.isOneWayTicket = false;
                    // 도착일 선택 체크
                    if(mReservationInfo.endYear == 0) {
                        showNormalDialog(getString(R.string.train_errmsg_no_select_arrival_date));
                        return ;
                    }

                    // 출발일/도착일 비교
                    LocalDateTime ldt1 = LocalDateTime.of(mReservationInfo.startYear, mReservationInfo.startMonth, mReservationInfo.startDay,
                            mReservationInfo.startHour, 0);
                    LocalDateTime ldt2 = LocalDateTime.of(mReservationInfo.endYear, mReservationInfo.endMonth, mReservationInfo.endDay,
                            mReservationInfo.endHour, 0);

                    if(ldt1.isAfter(ldt2)) {// 출발일이 도착일보다 클 경우
                        showNormalDialog(getString(R.string.train_errmsg_check_date));
                        return ;
                    }
                } else {
                    mReservationInfo.isOneWayTicket = true;
                }

                if(mReservationInfo.personList == null || mReservationInfo.personList.size() == 0) {
                    showNormalDialog(getString(R.string.train_errmsg_no_select_seat));
                    return ;
                }

                mTempList.clear();
                mTempList.add(mReservationInfo);
                if(mSelectedTab == 1) {
                    TrainReservationInfo info = null;
                    try {
                        info = (TrainReservationInfo)mReservationInfo.clone();
                        info.startYear = info.endYear;
                        info.startMonth = info.endMonth;
                        info.startDay = info.endDay;
                        info.startHour = info.endHour;

                        String startStation = info.startStation;
                        String endStation = info.arrivalStation;
                        info.startStation = endStation;
                        info.arrivalStation = startStation;
                        info.ticketType = "end";
                        mTempList.add(info);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }

                intent = new Intent(this, TrainSearchActivity.class);
                intent.putExtra("data", mTempList.get(0));
                mLauncher.launch(intent);
                break;

            case R.id.tab_one_way:
                mSelectedTab = 0;
                mTabTextView1.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
                mTabTextView2.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_8));
                mTabBottom1.setVisibility(View.VISIBLE);
                mTabBottom2.setVisibility(View.GONE);
                findViewById(R.id.img_arrow).setVisibility(View.VISIBLE);
                findViewById(R.id.img_arrow_return).setVisibility(View.GONE);
                mArrivalDateLayout.setVisibility(View.GONE);
                break;

            case R.id.tab_round_trip:
                mSelectedTab = 1;
                mTabTextView1.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_8));
                mTabTextView2.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
                mTabBottom1.setVisibility(View.GONE);
                mTabBottom2.setVisibility(View.VISIBLE);
                findViewById(R.id.img_arrow).setVisibility(View.GONE);
                findViewById(R.id.img_arrow_return).setVisibility(View.VISIBLE);
                mArrivalDateLayout.setVisibility(View.VISIBLE);
                break;

            case R.id.start_date_ly :   // 출발일
                intent = new Intent(this, TrainCalendarActivity.class);
                intent.putExtra("type", Common.TRAIN_DATE_TYPE_START);
                mLauncher.launch(intent);
                break;

            case R.id.arrival_date_ly:  // 도착일
                intent = new Intent(this, TrainCalendarActivity.class);
                intent.putExtra("type", Common.TRAIN_DATE_TYPE_END);
                mLauncher.launch(intent);
                break;

            case R.id.station_change :
                String startStation = mReservationInfo.startStation;
                String arrivalStation = mReservationInfo.arrivalStation;

                mReservationInfo.startStation = arrivalStation;
                mReservationInfo.arrivalStation = startStation;

                if(TextUtils.isEmpty(mReservationInfo.startStation)) {
                    mStartStationTv.setText(R.string.select);
                } else {
                    mStartStationTv.setText(mReservationInfo.startStation);
                }

                if(TextUtils.isEmpty(mReservationInfo.arrivalStation)) {
                    mArrivalStationTv.setText(R.string.select);
                } else {
                    mArrivalStationTv.setText(mReservationInfo.arrivalStation);
                }
                break;

            case R.id.seat_select_ly :
                intent = new Intent(this, TrainPassengerSeatActivity.class);
                mLauncher.launch(intent);
                break;

            case R.id.train_start_station :
                mSelectStationPosition = Common.TRAIN_DATE_TYPE_START;
                mSearchEditText.setText("");
                mStationSearchLayout.setVisibility(View.VISIBLE);
                mStartStationTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
                mArrivalStationTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_b));
                break;

            case R.id.train_arrival_station :
                mSelectStationPosition = Common.TRAIN_DATE_TYPE_END;
                mSearchEditText.setText("");
                mStationSearchLayout.setVisibility(View.VISIBLE);
                mStartStationTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_b));
                mArrivalStationTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
                break;

            case R.id.search_close_btn :
                setStationName(null);
                break;
        }
    }

    private ActivityResultLauncher mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();

                int requestType = 0;
                if(intent != null) {
                    if(intent.hasExtra(INTENT_REQUEST_PARAM_TYPE)) {
                        requestType = intent.getIntExtra(INTENT_REQUEST_PARAM_TYPE, 0);
                    }
                }

                switch (requestType) {
                    // 승객연령 및 좌석수
                    case INTENT_PARAM_TYPE_PASSENGERCOUNT:
                        String str = "";
                        ArrayList<TrainPassenger> list = intent.getParcelableArrayListExtra("data");
                        mReservationInfo.seatCount = intent.getIntExtra("totalCnt", 0);
                        mReservationInfo.personList = list;

                        if(list != null) {
                            for(TrainPassenger t : list) {
                                if(t.count > 0) {
                                    if(!str.isEmpty()) str += " / ";
                                    switch (t.type) {
                                        case TrainPassenger.TRAIN_PASSENGER_TYPE_BABY:
                                            str += getString(R.string.train_passenger_type_baby) + " " +getString(R.string.train_passenger_seat_count2, t.count);
                                            break;
                                        case TrainPassenger.TRAIN_PASSENGER_TYPE_CHILDREN:
                                            str += getString(R.string.train_passenger_type_children) + " " +getString(R.string.train_passenger_seat_count2, t.count);
                                            break;
                                        case TrainPassenger.TRAIN_PASSENGER_TYPE_ADULT:
                                            str += getString(R.string.train_passenger_type_adult) + " " +getString(R.string.train_passenger_seat_count2, t.count);
                                            break;
                                        case TrainPassenger.TRAIN_PASSENGER_TYPE_OLD:
                                            str += getString(R.string.train_passenger_type_old) + " " +getString(R.string.train_passenger_seat_count2, t.count);
                                            break;
                                    }
                                }
                            }
                        }
                        mPassengerCountTv.setText(str);
                        mPassengerCountTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
                        break;

                    // 출발일
                    case INTENT_PARAM_TYPE_CALENDAR_STARTDATE :
                    {
                        String time = intent.getStringExtra("time");
                        Day day = intent.getParcelableExtra("day");
                        mReservationInfo.startYear = day.year;
                        mReservationInfo.startMonth = day.month;
                        mReservationInfo.startDay = day.day;
                        mReservationInfo.startHour = Integer.parseInt(time);

                        mStartDateTv.setText(mReservationInfo.getStartDateStr(mContext, true) + " " + time+":00");
                        mStartDateTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
                    }
                        break;

                    // 도착일
                    case INTENT_PARAM_TYPE_CALENDAR_ARRIVALDATE:
                    {
                        String time = intent.getStringExtra("time");
                        Day day = intent.getParcelableExtra("day");
                        mReservationInfo.endYear = day.year;
                        mReservationInfo.endMonth = day.month;
                        mReservationInfo.endDay = day.day;
                        mReservationInfo.endHour = Integer.parseInt(time);

                        mArrivalDateTv.setText(mReservationInfo.getArrivalDateStr(mContext, true) + " " + time+":00");
                        mArrivalDateTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
                    }
                        break;

                    // 열차 조회하기
                    case INTENT_PARAM_TYPE_TRAIN_SEARCH:
                    {
                        TrainReservationInfo info = intent.getParcelableExtra("data");

                        mReservationInfo = info;

                        if(info.ticketType.equals("start") && mSelectedTab == 0) {
                            mTempList.set(0, mReservationInfo);

                            HumanCareKioskApplication.mTrainTempList = mTempList;
                            intent = new Intent(mContext, TrainPaymentConfirmActivity.class);
                            startActivity(intent);
                        }
                        else if(info.ticketType.equals("end") && mSelectedTab == 1) {
                            mTempList.set(1, mReservationInfo);

                            HumanCareKioskApplication.mTrainTempList = mTempList;
                            intent = new Intent(mContext, TrainPaymentConfirmActivity.class);
                            startActivity(intent);
                        }
                        else {
                            mTempList.set(0, mReservationInfo);

                            PopupDialog dialog = new PopupDialog(mContext);
                            //dialog.setTitle(getString(R.string.common_dialog_title_info));
                            dialog.setContent(getString(R.string.train_round_trip_ticket_guide));
                            dialog.setOnOkButtonClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(mContext, TrainSearchActivity.class);
                                    intent.putExtra("data", mTempList.get(1));
                                    mLauncher.launch(intent);
                                }
                            });
                            dialog.show();
                        }
                    }
                        break;
                }


            }
        }
    });

    public void setStationName(String name) {
        hideEditTextKeyboard();
        mStationSearchLayout.setVisibility(View.GONE);

        if(name != null) {
            if(mSelectStationPosition == Common.TRAIN_DATE_TYPE_START) {
                mReservationInfo.startStation = name;
                mStartStationTv.setText(name);
            } else {
                mReservationInfo.arrivalStation = name;
                mArrivalStationTv.setText(name);
            }
        }

        mStartStationTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
        if(!mArrivalStationTv.getText().toString().isEmpty()) {
            mArrivalStationTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
        } else {
            mArrivalStationTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_b));
        }
    }

    private void hideEditTextKeyboard() {
        // 키보드 내리기
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
    }

}