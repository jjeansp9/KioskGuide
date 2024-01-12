package com.avad.humancare.kiosk.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.avad.humancare.kiosk.R;

import java.text.DecimalFormat;
import java.util.Random;

public class Utils {

    public static String getPriceFormat(Context context, int price, boolean unit) {
        String str = "";

        DecimalFormat df = new DecimalFormat("###,###");    // 콤마 표시
        str = df.format(price);

        if(unit) {
            str += context.getString(R.string.won);
        }

        return str;
    }

    /**
     * 난수 생성 (범위)
     * @param min 최소값
     * @param max 최대값
     * @return
     */
    public static int getRandomNumber(int min, int max) {
        if(min == max) return min;
        Random random = new Random();
        return random.ints(min,max + 1).findFirst().getAsInt();
    }

    public static void gotoElderApp(Activity activity) {
        activity.finishAffinity();
        System.exit(0);
    }

    /**
     * 실행중인 앱의 가로길이가 600이상이면 true
     * */
    public static boolean isScreenWidthGreaterThan600dp(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        return screenWidthDp >= 600;
    }

    /**
     * dp값 or px값 가져오기
     * */
    public static int fromPxToDp(float px) { return (int) (px / Resources.getSystem().getDisplayMetrics().density); }
    public static int fromDpToPx(float dp) { return (int) (dp * Resources.getSystem().getDisplayMetrics().density); }
}
