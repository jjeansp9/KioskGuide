package com.avad.humancare.kiosk.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;

public class TrainReservationInfo implements Parcelable, Cloneable {

    public boolean isOneWayTicket = true;    // true - 편도, fales - 왕복
    public String ticketType = "start"; // "start" - 편도인 경우 가는(출발) 티켓, "end" - 왕복인 경우 오는(다음) 티켓
    public String startStation;     // 출발역
    public String arrivalStation;   // 도착역
    private String startDateStr;        // 출발일 : yyyy년 MM월 dd일
    private String arrivalDateStr;      // 도착일 :yyyy년 MM월 dd일
    public String startTime;
    public String arrivalTime;
    public int seatCount;    // 좌석수
    public int totalSeatCnt;
    public int totalPrice;      // 총 가격
    public int startYear;
    public int startMonth;
    public int startDay;
    public int startHour;
    public int startMin;
    public int endYear;
    public int endMonth;
    public int endDay;
    public int endHour;
    public int endMin;

    public ArrayList<TrainPassenger> personList = new ArrayList<TrainPassenger>();
    public ArrayList<TrainSeat> seatList = new ArrayList<>();
    public TrainInfo trainInfo;

    public TrainReservationInfo() {

    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected TrainReservationInfo(Parcel in) {
        ticketType = in.readString();
        startStation = in.readString();
        arrivalStation = in.readString();
        startDateStr = in.readString();
        arrivalDateStr = in.readString();
        startTime = in.readString();
        arrivalTime = in.readString();
        seatCount = in.readInt();
        totalSeatCnt = in.readInt();
        totalPrice = in.readInt();

        startYear = in.readInt();
        startMonth = in.readInt();
        startDay= in.readInt();
        startHour = in.readInt();
        startMin = in.readInt();
        endYear = in.readInt();
        endMonth = in.readInt();
        endDay= in.readInt();
        endHour = in.readInt();
        endMin = in.readInt();

        in.readTypedList(personList, TrainPassenger.CREATOR);
        in.readTypedList(seatList, TrainSeat.CREATOR);
        trainInfo = (TrainInfo)in.readSerializable();
        isOneWayTicket = in.readByte() != 0;
    }

    public static final Creator<TrainReservationInfo> CREATOR = new Creator<TrainReservationInfo>() {
        @Override
        public TrainReservationInfo createFromParcel(Parcel in) {
            return new TrainReservationInfo(in);
        }

        @Override
        public TrainReservationInfo[] newArray(int size) {
            return new TrainReservationInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ticketType);
        parcel.writeString(startStation);
        parcel.writeString(arrivalStation);
        parcel.writeString(startDateStr);
        parcel.writeString(arrivalDateStr);
        parcel.writeString(startTime);
        parcel.writeString(arrivalTime);
        parcel.writeInt(seatCount);
        parcel.writeInt(totalSeatCnt);
        parcel.writeInt(totalPrice);

        parcel.writeInt(startYear);
        parcel.writeInt(startMonth);
        parcel.writeInt(startDay);
        parcel.writeInt(startHour);
        parcel.writeInt(startMin);
        parcel.writeInt(endYear);
        parcel.writeInt(endMonth);
        parcel.writeInt(endDay);
        parcel.writeInt(endHour);
        parcel.writeInt(endMin);

        parcel.writeTypedList(personList);
        parcel.writeTypedList(seatList);
        parcel.writeSerializable(trainInfo);
        parcel.writeByte((byte) (isOneWayTicket ? 1 : 0));
    }

    public String getStartDateStr(Context context) {
        return getStartDateStr(context, false);
    }

    public String getStartDateStr(Context context, boolean isExistWeekday) {
        startDateStr = startYear + context.getString(R.string.year)
                + " " + String.format("%02d", startMonth) + context.getString(R.string.month)
                + " " + String.format("%02d", startDay) + context.getString(R.string.day);

        if(isExistWeekday) {
            startDateStr = DateUtil.getDateFormatter(startDateStr);
        }

        return startDateStr;
    }

    public void setDate(int dateType, String dateStr) { // dateType : 0 -출발일, 1 - 도착일
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_YMD);

        try {
            Date date = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            if(dateType == 0) {
                startYear = cal.get(Calendar.YEAR);
                startMonth = cal.get(Calendar.MONTH)+1;
                startDay = cal.get(Calendar.DATE);
            } else {
                endYear = cal.get(Calendar.YEAR);
                endMonth = cal.get(Calendar.MONTH)+1;
                endDay = cal.get(Calendar.DATE);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public String getArrivalDateStr(Context context, boolean isExistWeekday) {
        arrivalDateStr = endYear + context.getString(R.string.year)
                + " " + String.format("%02d", endMonth) + context.getString(R.string.month)
                + " " + String.format("%02d", endDay) + context.getString(R.string.day);

        if(isExistWeekday) {
            arrivalDateStr = DateUtil.getDateFormatter(arrivalDateStr);
        }

        return arrivalDateStr;
    }
}
