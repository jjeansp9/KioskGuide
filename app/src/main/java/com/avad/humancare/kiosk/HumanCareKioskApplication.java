package com.avad.humancare.kiosk;

import android.app.Application;

import com.avad.humancare.kiosk.model.TrainReservationInfo;

import java.util.ArrayList;

public class HumanCareKioskApplication extends Application {

    public static TrainReservationInfo mTrainInstance_reservationInfo = null;   // 기차 예약정보
    public static ArrayList<TrainReservationInfo> mTrainTicketList = null;    // 기차 티켓정보
    public static ArrayList<TrainReservationInfo> mTrainTempList = null;  // 예매 진행시 기차 정보 저장

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
