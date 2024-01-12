package com.avad.humancare.kiosk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.NumberFormat;

public class PaymentItemModel implements Parcelable {
    private boolean selected;
    private String date;
    private String endDate;
    private String department;
    private String doctor;
    /**
     * 외래 :1, 입원 : 2, 둘다 : 3
     */
    private int type;
    private int amount;
    public PaymentItemModel() {
    }
    protected  PaymentItemModel(Parcel in){
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (selected ? 1 : 0));
        parcel.writeString(date);
        parcel.writeString(endDate);
        parcel.writeString(department);
        parcel.writeString(doctor);
        parcel.writeInt(amount);
        parcel.writeInt(type);
    }
    private void readFromParcel(Parcel in){
        selected = in.readByte() != 0;
        date = in.readString();
        endDate = in.readString();
        department = in.readString();
        doctor = in.readString();
        amount = in.readInt();
        type = in.readInt();
    }
    public static final Creator<PaymentItemModel> CREATOR = new Creator<PaymentItemModel>() {
        @Override
        public PaymentItemModel createFromParcel(Parcel in) {
            return new PaymentItemModel(in);
        }

        @Override
        public PaymentItemModel[] newArray(int size) {
            return new PaymentItemModel[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAmountStr() {
        return NumberFormat.getInstance().format(amount);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
