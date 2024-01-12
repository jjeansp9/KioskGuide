package com.avad.humancare.kiosk.model;

public class IssuanceMenuItem {

    public String title;
    public String subTitle;
    public int fee;
    public String category; // 메뉴 카테고리 - 주민등록/가족관계/부동산등기
    public String type;

    public IssuanceMenuItem() {

    }

    public IssuanceMenuItem(String title, String subTitle, int fee, String category) {
        this.title = title;
        this.subTitle = subTitle;
        this.fee = fee;
        this.category = category;
    }
}
