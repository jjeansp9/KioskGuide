package com.avad.humancare.kiosk.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PatientModel implements Parcelable {
    private int idNo;
    /**
     * patient name
     */
    private String name;
    /**
     * patient sex (M or F)
     */
    private String sex = "M";
    private String lastVisitDate;
    private boolean isChecked = false;
    public PatientModel() {
    }
    protected  PatientModel(Parcel in){
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idNo);
        parcel.writeString(name);
        parcel.writeString(sex);
        parcel.writeString(lastVisitDate);
        parcel.writeByte((byte) (isChecked ? 1 : 0));
    }
    private void readFromParcel(Parcel in){
        idNo = in.readInt();
        name = in.readString();
        sex = in.readString();
        lastVisitDate = in.readString();
        isChecked = in.readByte() != 0;
    }
    public static final Creator<PatientModel> CREATOR = new Creator<PatientModel>() {
        @Override
        public PatientModel createFromParcel(Parcel in) {
            return new PatientModel(in);
        }

        @Override
        public PatientModel[] newArray(int size) {
            return new PatientModel[size];
        }
    };

    //getter/setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(String lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
