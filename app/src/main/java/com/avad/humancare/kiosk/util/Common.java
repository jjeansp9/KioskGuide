package com.avad.humancare.kiosk.util;

public class Common {
    public enum KIOSK_TYPE{
        FASTFOOD,
        HOSPITAL,
        TRAIN
    }
    /**** 기차 ****/

    // 기차 날짜 타입 - 출발일/도착일
    public static final int TRAIN_DATE_TYPE_NONE = 0;
    public static final int TRAIN_DATE_TYPE_START = 10;   // 출발일
    public static final int TRAIN_DATE_TYPE_END = 11;   // 도착일

    public class TrainSeatConstant {
        // 좌석 방향
        public static final int DIRECTION_FORWARD = 1;  // 순방향
        public static final int DIRECTION_REVERSE = 0;  // 역방향

        // 좌석 타입
        public static final int TYPE_NONE = 0; // 잔여석
        public static final int TYPE_SELECT = 1;   // 선택좌석
        public static final int TYPE_SOLDOUT = 2;  // 마감석

        // 일반실/특실
        public static final int ROOM_BASIC = 0; // 일반실
        public static final int ROOM_VIP =1;    // 특실

    }


    ////////////hospital//////////////////
    /**
     * 접수하기
     */
    public static final int MENU_RECEIPT = 1;
    /**
     * 수납하기
     */
    public static final int MENU_PAYMENT = 2;
    /**
     * 진료내역서 발행
     */
    public static final int MENU_RECORDS = 3;
    /**
     * 보험금 청구
     */
    public static final int MENU_INSURANCE = 4;



}
