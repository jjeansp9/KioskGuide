package com.avad.humancare.kiosk.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Day implements Parcelable {

    public int year;
    public int month;
    public int day;
    public String dayStr;
    public String comment;
    public boolean bInMonth = false;   // 검색월 여부
    public boolean bIsToday = false;
    public boolean bTodayMonth = false; // 현재월 여부

    public Day() {

    }

    protected Day(Parcel in) {
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        dayStr = in.readString();
        comment = in.readString();
        bInMonth = in.readByte() != 0;
        bIsToday = in.readByte() != 0;
        bTodayMonth = in.readByte() != 0;
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(year);
        parcel.writeInt(month);
        parcel.writeInt(day);
        parcel.writeString(dayStr);
        parcel.writeString(comment);
        parcel.writeByte((byte) (bInMonth ? 1 : 0));
        parcel.writeByte((byte) (bIsToday ? 1 : 0));
        parcel.writeByte((byte) (bTodayMonth ? 1 : 0));
    }
}
