package com.avad.humancare.kiosk.hospital.fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.avad.humancare.kiosk.R;

import java.util.regex.Pattern;

public class PartialRegistrationNoView extends ConstraintLayout {

    private static final String TAG = "registrationNo";
    private Context _context;
    TextView tvNumberStart, tvNumberEnd;
    private StringBuilder sbOrigin = new StringBuilder();
    public PartialRegistrationNoView(Context context) {
        super(context);
        this._context = context;
        initViews();
    }

    public PartialRegistrationNoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        initViews();
    }
    private void initViews() {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_partial_registration_no, this, true);

        tvNumberStart = findViewById(R.id.tv_number_start);
        tvNumberEnd = findViewById(R.id.tv_number_end);
    }
    public void inputNumber(String key){
        StringBuilder sbDisplay = new StringBuilder();
//        if(tvNumberStart != null){
//            sbDisplay.append(tvNumberStart.getText());
//        }
//        if(tvNumberEnd != null){
//            sbDisplay.append(tvNumberEnd.getText());
//        }
        switch(key){
            case CommonSubIdentifyFragment.BUTTON_BACKSPACE:
                if(sbOrigin.length() > 0){
                    sbOrigin.deleteCharAt(sbOrigin.length() - 1);
                }
                break;
            case CommonSubIdentifyFragment.BUTTON_CLEAR:
                sbOrigin.setLength(0);
                break;
            default:
                if(sbOrigin.length() <13) {
                    sbOrigin.append(key);
                }
                break;
        }
        //start, end
        sbDisplay = new StringBuilder(sbOrigin);
        if(sbOrigin.length() > 6){
            if(tvNumberStart != null) tvNumberStart.setText(sbDisplay.substring(0, 6));
            if(tvNumberEnd != null) tvNumberEnd.setText(sbDisplay.substring(6, sbDisplay.length()).replaceAll("[\\d]", "*"));
        }else{  //start
            if(tvNumberStart != null) tvNumberStart.setText(sbDisplay.toString());
            if(tvNumberEnd != null) tvNumberEnd.setText("");
        }
        Log.e(TAG, "registration no =" + sbOrigin.toString());
    }
    public void clear(){
        sbOrigin.setLength(0);
        if(tvNumberStart != null) tvNumberStart.setText("");
        if(tvNumberEnd != null) tvNumberEnd.setText("");
    }
    private Boolean checkRegistrationNo(String str) {
        // 곱해지는 수 배열 구성
        int[] chk = { 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5 };

        // - check~!!!
        // 곱셈 연산 후 누적합
        int tot = 0;

        /*
         *  입력받은 주민번호를 유효성 검사 => 정규 표현식(regex) 패턴 적용
         */
        String regex = "^[0-9]{2}[0-1][0-9][0-3][0-9][1234][0-9]{6}$"; // 주민번호 정규 표현식 패턴
        // 대괄호 안 = 메타문자 - 특별한 문자를 가지고있는 문자  ==> 범위 : 0-9까지
        // 중괄호 반복횟수 ==> 6번 반복해라

        boolean check = Pattern.matches(regex, str);
        if(!check) {
            Log.e(TAG, "pattern not match");
            return false;
        }
        //월체크
        Log.e(TAG, "month " + str.substring(2, 4));
        int month = Integer.parseInt(str.substring(2,4));
        if(month ==0 || month > 12) {
            Log.e(TAG, "invalid month");
            return false;
        }
        //일체크
        Log.e(TAG, "date " + str.substring(4, 6));
        if(!checkDay(str.substring(2,4), str.substring(4,6))) {
            Log.e(TAG, "invalid date");
            return false;
        }
        //윤달체크
        int date = Integer.parseInt(str.substring(4,6));
        if(!leapYearCheck(str)){
            if(month == 2 && date > 28) {
                Log.e(TAG, "invalid feb date in !leapyear");
                return false;
            }
        }else{
            if(month == 2 && date > 29) {
                Log.e(TAG, "invalid feb date in leapyear");
                return false;
            }
        }
        for (int i = 0; i < chk.length; i++) {
//            if (i == 6)
//                continue;
            tot += chk[i] * Integer.parseInt(str.substring(i, (i + 1)));
        }

        // -- 여기까지 수행하면 ① 과 ② 를 모두 끝낸 상황이며
        // 규칙에 맞게 곱셈 연산을 수행한 결과를 모두 더한 값은
        // 변수 tot 에 담겨있는 상태가 된다.
        int number = 11 - tot % 11;

        // -------
        // ----------
        // 확인
        // System.out.println(su);
        // --==>> 11

        // 최종 결과 출력 이전에 추가 연산 필요~!!!
        // -> su에 대한 연산 결과가 두 자리로 나올 경우
        // 주민번호 마지막 자릿수와 비교할 수 없는 상황
        if (number >= 10) {
            number %= 10;
        }

        // -- 여기까지 수행하면 ③ 과 ④ 를 모두 끝낸 상황이며
        // 최종 연산 결과는 변수 su 에 담겨있는 상황이 된다.
        Log.e(TAG, "last calculated no is " + number);
        // 최종 결과 출력
        if (number == Integer.parseInt(str.substring(12))) {
            return true;
        } else {
            Log.e(TAG, "invalid last number");
            return false;
        }
    }

    /**
     * 말일 유효성 체크
     * @param monthString
     * @param dayString
     * @return
     */
    private boolean checkDay(String monthString, String dayString){
        String[] thirtyOne = {"01", "03", "05", "07", "08", "10", "12"};
        String[] thirty= {"04", "06", "09", "11"};
        boolean valid = true;
        int date = Integer.parseInt(dayString);
        for(String month : thirtyOne){
            if(month.contains(monthString)){
                if(date > 31 || date < 1) valid = false;
            }
        }
        for(String month : thirty){
            if(month.contains(monthString)){
                if(date > 30 || date < 1) valid = false;
            }
        }
        return true;
    }

    /**
     * 윤년여부 체크
     * @param registrationStr
     * @return
     */
    private boolean leapYearCheck(String registrationStr){
        boolean localLeapYear = false;
        int localYear = Integer.parseInt(registrationStr.substring(0,2));
        int sex = Integer.parseInt(registrationStr.substring(6,7));

        if(sex == 1 || sex == 2){
            localYear += 1900;
        }else if(sex == 3 || sex == 4){
            localYear += 2000;
        }
        if((localYear % 400 == 0) || (localYear % 4 == 0 && localYear % 100 != 0)){
            localLeapYear = true;
        }
        return localLeapYear;
    }
    public boolean isValidData(){
        if(tvNumberStart == null || tvNumberEnd == null || sbOrigin == null) return false;
        if(checkRegistrationNo(sbOrigin.toString())){
            return true;
        }
        return false;
    }

}
