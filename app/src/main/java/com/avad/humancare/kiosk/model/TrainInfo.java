package com.avad.humancare.kiosk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.avad.humancare.kiosk.util.Common;

import java.io.Serializable;

public class TrainInfo implements Parcelable, Serializable {

    public String name; // 열차명
    public int nameNum;  // 열차번호
    public String startTime;    // 출발시간
    public String endTime;      // 도착시간
    public int basicPrice;
    public int vipPrice;
    public boolean isBasicSoldOut = false;  // 일반실 매진여부
    public boolean isVipSoldOut = false;
    public boolean waitingForBasic = false; // 일반실 예약대기 여부
    public boolean waitingForVip = false;   // 특우등 예약대기 여부
    public int selectedSeatType = Common.TrainSeatConstant.ROOM_BASIC;    // 선택한 좌석 타입 : 0 - 일반실, 1 특/우등

    public TrainInfo() {

    }

    public TrainInfo(String name, int nameNum, String startTime, String endTime, int price1, int price2
            , boolean waitingForBasic, boolean waitingForVip, boolean isBasicSoldOut, boolean isVipSoldOut) {
        this.name = name;
        this.nameNum = nameNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.basicPrice = price1;
        this.vipPrice = price2;
        this.waitingForBasic = waitingForBasic;
        this.waitingForVip = waitingForVip;
        this.isBasicSoldOut = isBasicSoldOut;
        this.isVipSoldOut = isVipSoldOut;

    }


    protected TrainInfo(Parcel in) {
        name = in.readString();
        nameNum = in.readInt();
        startTime = in.readString();
        endTime = in.readString();
        basicPrice = in.readInt();
        vipPrice = in.readInt();
        isBasicSoldOut = in.readByte() != 0;
        isVipSoldOut = in.readByte() != 0;
        waitingForBasic = in.readByte() != 0;
        waitingForVip = in.readByte() != 0;
        selectedSeatType = in.readInt();
    }

    public static final Creator<TrainInfo> CREATOR = new Creator<TrainInfo>() {
        @Override
        public TrainInfo createFromParcel(Parcel in) {
            return new TrainInfo(in);
        }

        @Override
        public TrainInfo[] newArray(int size) {
            return new TrainInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(nameNum);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
        parcel.writeInt(basicPrice);
        parcel.writeInt(vipPrice);
        parcel.writeByte((byte) (isBasicSoldOut ? 1 : 0));
        parcel.writeByte((byte) (isVipSoldOut ? 1 : 0));
        parcel.writeByte((byte) (waitingForBasic ? 1 : 0));
        parcel.writeByte((byte) (waitingForVip ? 1 : 0));
        parcel.writeInt(selectedSeatType);
    }
}
