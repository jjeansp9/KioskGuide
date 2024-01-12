package com.avad.humancare.kiosk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.avad.humancare.kiosk.util.Common;

public class TrainSeat implements Parcelable {

    public String name; // 좌석번호
    public String trainNumName; // 열차번호
    public int direction = Common.TrainSeatConstant.DIRECTION_FORWARD;
    public boolean isSeat = true;  // view에서 좌석 자리인지 화살표 이미지 자리인지 여부 : true인 경우 화살표 표시
    public int seatType = Common.TrainSeatConstant.TYPE_NONE;
    public int seatRowNum;  // 좌석 라인 번호 1~14
    public String seatColumn = ""; // A, B, C, D

    public TrainSeat() {

    }

    protected TrainSeat(Parcel in) {
        name = in.readString();
        trainNumName = in.readString();
        direction = in.readInt();
        isSeat = in.readByte() != 0;
        seatType = in.readInt();
        seatRowNum = in.readInt();
        seatColumn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(trainNumName);
        dest.writeInt(direction);
        dest.writeByte((byte) (isSeat ? 1 : 0));
        dest.writeInt(seatType);
        dest.writeInt(seatRowNum);
        dest.writeString(seatColumn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrainSeat> CREATOR = new Creator<TrainSeat>() {
        @Override
        public TrainSeat createFromParcel(Parcel in) {
            return new TrainSeat(in);
        }

        @Override
        public TrainSeat[] newArray(int size) {
            return new TrainSeat[size];
        }
    };
}
