package com.avad.humancare.kiosk.train;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.TrainCalendarDayAdapter;
import com.avad.humancare.kiosk.adapter.TrainCalendarTimeAdapter;
import com.avad.humancare.kiosk.model.Day;
import com.avad.humancare.kiosk.util.Common;
import com.avad.humancare.kiosk.util.DateUtil;
import com.avad.humancare.kiosk.view.TrainHeaderView;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

public class TrainCalendarActivity extends TrainBaseActivity {

    private String TAG = TrainCalendarActivity.class.getSimpleName();

    private Context mContext;

    private ViewGroup mLastMonthLy, mNextMonthly;
    private TextView mCalendarTitleTv, mTitleTv;
    private RecyclerView mCalendarDayView, mTimeView;

    private Calendar mCalendar;
    private ArrayList<Day> mDayList = new ArrayList<>();
    private ArrayList<String> mTimeList = new ArrayList<>();

    private TrainCalendarDayAdapter mDayAdapter;
    private TrainCalendarTimeAdapter mTimeAdapter;
    private int mDateType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_calendar);

        mContext = this;

        Intent intent = getIntent();
        if(intent != null) {
            mDateType = intent.getIntExtra("type", Common.TRAIN_DATE_TYPE_NONE);
        }

        setHeaderView();

        initView();
    }

    private void setHeaderView() {
        TrainHeaderView headerView = (TrainHeaderView) findViewById(R.id.header);
        headerView.setHeaderTitle(getString(R.string.train_main_title1));
        findViewById(R.id.btn_back_main).setOnClickListener(this);
    }

    private void initView() {

        // footer
        findViewById(R.id.footer_right_ly).setOnClickListener(this);

        ///////// calendar
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);    // 달력을 그리기 위해서 현재월 1일로 set

        // title
        mTitleTv = (TextView) findViewById(R.id.title);
        if(mDateType == Common.TRAIN_DATE_TYPE_START) {
            mTitleTv.setText(R.string.train_start_date);
        } else {
            mTitleTv.setText(R.string.train_arrival_date);
        }

        mCalendarTitleTv = (TextView) findViewById(R.id.ym_text);
        mLastMonthLy = findViewById(R.id.last_month_btn);
        mNextMonthly = findViewById(R.id.next_month_btn);
        mLastMonthLy.setOnClickListener(this);
        mNextMonthly.setOnClickListener(this);
        mLastMonthLy.setVisibility(View.GONE);

        // 요일
        RecyclerView weekListView = (RecyclerView) findViewById(R.id.calendarWeekListView);
        weekListView.setItemViewCacheSize(7);

        String[] weekArr = getResources().getStringArray(R.array.calendar_week);
        ArrayList<Day> weekList = new ArrayList<Day>();
        for(int i=0; i< weekArr.length; i++) {
            Day day = new Day();
            day.dayStr = weekArr[i];
            weekList.add(day);
        }
        TrainCalendarDayAdapter weekAdapter = new TrainCalendarDayAdapter(this, weekList, TrainCalendarDayAdapter.TYPE_WEEK, Common.TRAIN_DATE_TYPE_NONE);
        weekListView.setAdapter(weekAdapter);

        // 날짜
        mCalendarDayView = (RecyclerView) findViewById(R.id.calendarDayView);
        mCalendarDayView.setItemViewCacheSize(42);

        mDayAdapter = new TrainCalendarDayAdapter(this, mDayList, TrainCalendarDayAdapter.TYPE_DATE, mDateType);
        mCalendarDayView.setAdapter(mDayAdapter);

        setCalendarView(mCalendar);

        /////////// time
        int nowHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        for(int i=0; i<24; i++) {
            String time = String.format("%02d", i);
            mTimeList.add(time);
        }
        mTimeView = (RecyclerView) findViewById(R.id.time_view);
        mTimeAdapter = new TrainCalendarTimeAdapter(this, mTimeList);
        mTimeView.setAdapter(mTimeAdapter);
        mTimeAdapter.setSelectedItem(nowHour);

        int timeScrollPosition = nowHour - 3;
        if(timeScrollPosition < 0) timeScrollPosition = 0;
        mTimeView.scrollToPosition(timeScrollPosition); // scroll 위치 이동

    }

    private void setCalendarView(Calendar calendar) {
        int dayOfMonth;     // 이번달 시작 요일
        int thisMonthLastDay;   // 이번달 마지막일자
        int lastMonthStartDay;  // 지난달 마지막일자

        // title
        mCalendarTitleTv.setText(DateUtil.getYearMonth(mCalendar.getTime()));

        mDayList.clear();

        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DATE);
        Log.d(TAG, "KHJ++ setCalendarView() : current >> m="+ cal.get(Calendar.MONTH) + " / date="+cal.get(Calendar.DATE) + " / " + cal.get(Calendar.YEAR));
        Log.d(TAG, "KHJ++ setCalendarView() : cal >> m="+ calendar.get(Calendar.MONTH) + " / date="+calendar.get(Calendar.DATE)+ " / " + calendar.get(Calendar.YEAR));

        // 이번달 시작 요일
        dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);    // 요일
        thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, -1);
        // 지난달 마지막일자
        lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.MONTH, 1);
        lastMonthStartDay -= (dayOfMonth-1)-1;
        Log.d(TAG, "KHJ++ setCalendarView() dayOfMonth="+dayOfMonth +  " / thisMonthLastDay="+thisMonthLastDay + " / lastMonthStartDay="+lastMonthStartDay);
        Day day = null;

        // 검색월 이전달 빈칸 채우기
        for(int i=0; i<dayOfMonth-1; i++) {
            day = new Day();
            day.day = 0;   //Integer.toString(lastMonthStartDay + i);
            day.dayStr = "";
            day.bInMonth = false;
            mDayList.add(day);
        }

        // 검색월
        int todayIndex = -1;
        for(int i=1; i<=thisMonthLastDay; i++) {
            day = new Day();
            day.year = calendar.get(Calendar.YEAR);
            day.month = calendar.get(Calendar.MONTH)+1;
            day.day = i;
            day.dayStr = String.valueOf(i);
            day.bInMonth = true;

            if(calendar.get(Calendar.YEAR) == currentYear
                    && calendar.get(Calendar.MONTH) == currentMonth
                    && i == currentDay
            ) {
                day.bIsToday = true;
                day.comment = getString(R.string.train_calendar_today);
                todayIndex = mDayList.size();
            }

            if(calendar.get(Calendar.YEAR) == currentYear
                    && calendar.get(Calendar.MONTH) == currentMonth) {
                day.bTodayMonth = true;
            }

            mDayList.add(day);
        }

        // 검색월 다음달 빈칸 채우기
        for(int i=1; i<35-(thisMonthLastDay+dayOfMonth)+1; i++) {
            day = new Day();
            day.day = 0;   //Integer.toString(i);
            day.dayStr = "";
            day.bInMonth = false;
            mDayList.add(day);
        }

        // 현재월인 경우 오늘 날짜에 포커스 표시되도록
        if(todayIndex != -1) {
            mDayAdapter.setSelectedItem(todayIndex, true);
        }

        mDayAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;

        switch (view.getId()) {
            case R.id.last_month_btn :
                mDayAdapter.setInitSelectedPosition();
                mCalendar.add(Calendar.MONTH, -1);
                setCalendarView(mCalendar);
                mLastMonthLy.setVisibility(View.GONE);
                mNextMonthly.setVisibility(View.VISIBLE);
                break;

            case R.id.next_month_btn :
                mDayAdapter.setInitSelectedPosition();
                mCalendar.add(Calendar.MONTH, 1);
                setCalendarView(mCalendar);
                mLastMonthLy.setVisibility(View.VISIBLE);
                mNextMonthly.setVisibility(View.GONE);
                break;

            case R.id.footer_right_ly :
                Day day = mDayAdapter.getSelectedItem();
                String time = mTimeAdapter.getSelectedItem();

                // 현재 시간보다 이전 시간일 경우 에러메시지
                LocalDateTime now = LocalDateTime.now();
                now = now.truncatedTo(ChronoUnit.HOURS);    // 시(hour)보다 작은 단위를 0으로 set

                if (day == null || day.day == 0) { // 월 변경 후 날짜를 선택하지 않고 확인누르면 Exception으로 인한 방어코드
                    showNormalDialog(getString(R.string.train_calendar_empty_selected_time));
                    return;
                }

                LocalDateTime ldt = LocalDateTime.of(day.year, day.month, day.day, Integer.parseInt(time), 0);
                if(ldt.isBefore(now)) {
                    showNormalDialog(getString(R.string.train_calendar_no_selected_time));
                    return;
                }

                intent = new Intent();
                if(mDateType == Common.TRAIN_DATE_TYPE_START) {
                    intent.putExtra(TrainReservationActivity.INTENT_REQUEST_PARAM_TYPE, TrainReservationActivity.INTENT_PARAM_TYPE_CALENDAR_STARTDATE);
                } else {
                    intent.putExtra(TrainReservationActivity.INTENT_REQUEST_PARAM_TYPE, TrainReservationActivity.INTENT_PARAM_TYPE_CALENDAR_ARRIVALDATE);
                }
                intent.putExtra("day", day);
                intent.putExtra("time", time);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}