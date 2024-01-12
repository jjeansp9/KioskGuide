package com.avad.humancare.kiosk.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FastfoodMenuItem implements Parcelable {

    public static final int FASTFOOD_MENU_CATEGORY_FAVORITE = 1;
    public static final int FASTFOOD_MENU_CATEGORY_BURGERSET = 2;
    public static final int FASTFOOD_MENU_CATEGORY_SIDE = 3;
    public static final int FASTFOOD_MENU_CATEGORY_DRINK = 4;
    public static final int FASTFOOD_MENU_CATEGORY_DESSERT = 5;

    public class FastfoodMenuType {
        public static final int BURGER = 1;
        public static final int BURGERSET = 2;
        public static final int SIDE = 3;
        public static final int DRINK = 4;
        public static final int DESSERT = 5;
    }

    public String name;
    public int price = 0;
    public int additionalPrice = 0;    // 세트메뉴의 추가가격(추가 사이드+음료)
    public int menuImgId;
    public int menuNameId;
    public int type;
    public int count;
    public int category;

    public FastfoodMenuItem() {
    }

    public FastfoodMenuItem(Parcel in) {
        name = in.readString();
        price = in.readInt();
        additionalPrice = in.readInt();
        menuImgId = in.readInt();
        menuNameId = in.readInt();
        type = in.readInt();
        count = in.readInt();
        category = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeInt(additionalPrice);
        parcel.writeInt(menuImgId);
        parcel.writeInt(menuNameId);
        parcel.writeInt(type);
        parcel.writeInt(count);
        parcel.writeInt(category);
    }

    public static final Creator<FastfoodMenuItem> CREATOR = new Creator<FastfoodMenuItem>() {
        @Override
        public FastfoodMenuItem createFromParcel(Parcel in) {
            return new FastfoodMenuItem(in);
        }

        @Override
        public FastfoodMenuItem[] newArray(int size) {
            return new FastfoodMenuItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
