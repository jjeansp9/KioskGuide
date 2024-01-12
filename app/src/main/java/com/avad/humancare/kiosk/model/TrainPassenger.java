package com.avad.humancare.kiosk.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainPassenger implements Parcelable {

    public static final int TRAIN_PASSENGER_TYPE_BABY = 0;      // 유아
    public static final int TRAIN_PASSENGER_TYPE_CHILDREN = 1;  // 어린이
    public static final int TRAIN_PASSENGER_TYPE_ADULT = 2;     // 어른
    public static final int TRAIN_PASSENGER_TYPE_OLD = 3;       // 경로

    public int type = TRAIN_PASSENGER_TYPE_ADULT;
    public int count = 0;

    public TrainPassenger() {

    }

    protected TrainPassenger(Parcel in) {
        type = in.readInt();
        count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeInt(count);
    }

    public static final Creator<TrainPassenger> CREATOR = new Creator<TrainPassenger>() {
        @Override
        public TrainPassenger createFromParcel(Parcel in) {
            return new TrainPassenger(in);
        }

        @Override
        public TrainPassenger[] newArray(int size) {
            return new TrainPassenger[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
