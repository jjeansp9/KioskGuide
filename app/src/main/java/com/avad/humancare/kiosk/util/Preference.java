package com.avad.humancare.kiosk.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    private static final String CONFIG_TAG = "HumanCareKiosk";

    public static final String PREF_MEDIA_VOL = "PREF_MEDIA_VOL";

    //이름
    public static final String PREF_ELDER_NAME = "PREF_ELDER_NAME";
    //성별
    public static final String PREF_ELDER_SEX = "PREF_ELDER_SEX";
    /**
     * 미디어 볼륨 설정
     *
     * @param context
     * @param vol 미디어 볼륨
     */
    public static void setMediaVol(Context context, int vol) {
        SharedPreferences pref = context.getSharedPreferences(CONFIG_TAG, Context.MODE_PRIVATE);
        pref.edit().putInt(PREF_MEDIA_VOL, vol).commit();
    }

    /**
     * 미디어 볼륨 조회
     *
     * @param context
     * @return 미디어 볼륨
     */
    public static int getMediaVol(Context context) {
        SharedPreferences pref = context.getSharedPreferences(CONFIG_TAG, Context.MODE_PRIVATE);
        return pref.getInt(PREF_MEDIA_VOL, 100);
    }

    /**
     * get elder name(for hospital re-visit)
     *
     * @param context
     * @return name
     */
    public static String getElderName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(CONFIG_TAG, Context.MODE_PRIVATE);
        return pref.getString(PREF_ELDER_NAME, "홍길동");
    }

    /**
     * set Elder name
     * @param context
     * @param name
     */
    public static void setElderName(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(CONFIG_TAG, Context.MODE_PRIVATE);
        pref.edit().putString(PREF_ELDER_NAME, name).commit();
    }

    /**
     * get elder sex(for hospital re-visit)
     *
     * @param context
     * @return sex
     */
    public static String getElderSex(Context context) {
        SharedPreferences pref = context.getSharedPreferences(CONFIG_TAG, Context.MODE_PRIVATE);
        return pref.getString(PREF_ELDER_SEX, "M");
    }

    /**
     * set Elder sex
     * @param context
     * @param sex
     */
    public static void setElderSex(Context context, String sex) {
        SharedPreferences pref = context.getSharedPreferences(CONFIG_TAG, Context.MODE_PRIVATE);
        pref.edit().putString(PREF_ELDER_SEX, sex).commit();
    }
}
