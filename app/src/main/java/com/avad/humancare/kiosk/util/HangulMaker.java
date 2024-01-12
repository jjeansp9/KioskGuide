package com.avad.humancare.kiosk.util;

import android.util.Log;
import android.view.inputmethod.BaseInputConnection;

import java.util.Arrays;

public class HangulMaker {

    private final String TAG = HangulMaker.class.getSimpleName();

    private final char UNICODE_NULL = '\u0000';

    private char cho = '\u0000';    // 초성
    private char jun = '\u0000';    // 중성
    private char jon = '\u0000';    // 종성
    private char jonFlag = '\u0000';
    private char doubleJonFlag = '\u0000';
    private char junFlag = '\u0000';

    private Integer[] chos = {0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139, 0x3141,0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};
    private Integer[] juns = {0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163};
    private Integer[] jons = {0x0000, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};

    /**
     * state
     * 0:""
     * 1: 모음 입력상태
     * 2: 모음 + 자음 입력상태
     * 3: 모음 + 자음 + 모음입력상태(초 중 종성)
     * 초성과 종성에 들어갈 수 있는 문자가 다르기 때문에 필요에 맞게 수정이 필요함.(chos != jons)
     */
    private int mState = 0;
    private BaseInputConnection mInputConnection;

    public HangulMaker(BaseInputConnection inputConnection) {
        this.mInputConnection = inputConnection;
    }

    public char makeHan() {
        //Log.i(TAG, "KHJ++ makeHan() mState="+mState + " / cho=" + (char)cho + " / jun="+(char)jun);
        if(mState == 0) {
            return UNICODE_NULL;
        }
        if(mState == 1) {
            return cho;
        }

        try {
            int choIndex = Arrays.asList(chos).indexOf((int)cho);
            int junIndex = Arrays.asList(juns).indexOf((int)jun);
            int jonIndex = Arrays.asList(jons).indexOf((int)jon);

            // 한글 '가' 유니코드 \uAC00
            int result = 0xAC00 + 28 * 21 * choIndex + 28 * junIndex + jonIndex;
            return (char)result;

        } catch(Exception e) {
            e.printStackTrace();
            return UNICODE_NULL;
        }
    }

    public void commit(char c) {

        int choIndex = Arrays.asList(chos).indexOf((int)c);
        int junIndex = Arrays.asList(juns).indexOf((int)c);
        int jonIndex = Arrays.asList(jons).indexOf((int)c);
        //Log.i(TAG, c + " >> KHJ++ commit() : choIndex= " + choIndex + " / junIndex="+junIndex + " / jonIndex="+jonIndex + " / mState="+mState + " / " + (char)c);

        if(choIndex < 0 && junIndex < 0 && jonIndex < 0) {
            Log.e(TAG, "commit >>>> return !!!");
            return ;
        }
        else if(mInputConnection == null) {
            Log.e(TAG, "commit >>>> mInputConnection NULL ## return !!!");
            return ;
        }

        switch (mState) {
            case 0 :
                if(junIndex >= 0) {
                    mInputConnection.commitText(Character.toString((char)c), 1);
                    clear();
                } else {
                    // 초성일 경우
                    mState = 1;
                    cho = c;
                    mInputConnection.setComposingText(Character.toString((char)cho), 1);
                }
                break;

            case 1 :    // 모음 입력
                if(choIndex >= 0) {
                    mInputConnection.commitText(Character.toString((char)cho), 1);
                    clear();
                    cho = c;
                    mInputConnection.setComposingText(Character.toString((char)cho), 1);
                } else {
                    // 중성일 경우
                    mState = 2;
                    jun = c;
                    mInputConnection.setComposingText(Character.toString(makeHan()), 1);
                }
                break;

            case 2 :    // 모음 + 자음 입력
                if(junIndex >= 0) {
                    if(doubleJunEnable(c)) {
                        mInputConnection.setComposingText(Character.toString(makeHan()), 1);
                    } else {
                        mInputConnection.commitText(Character.toString(makeHan()), 1);
                        mInputConnection.commitText(Character.toString(c), 1);
                        clear();
                        mState = 0;
                    }
                }
                else if(jonIndex >= 0) {
                    // 종성인 경우
                    jon = c;
                    mInputConnection.setComposingText(Character.toString(makeHan()), 1);
                    mState = 3;
                }
                else {
                    directlyCommit();
                    cho = c;
                    mState = 1;
                    mInputConnection.setComposingText(Character.toString(makeHan()), 1);
                }

                break;

            case 3 :    // 모음 + 자음 + 모음입력(초 중 종성)
                if(jonIndex >= 0) {
                    if(doubleJonEnable(c)) {
                        mInputConnection.setComposingText(Character.toString(makeHan()), 1);
                    } else {
                        mInputConnection.commitText(Character.toString(makeHan()), 1);
                        clear();
                        mState = 1;
                        cho = c;
                        mInputConnection.setComposingText(Character.toString(cho), 1);
                    }
                }
                else if(choIndex >= 0) {
                    mInputConnection.commitText(Character.toString(makeHan()), 1);
                    mState = 1;
                    clear();
                    cho = c;
                    mInputConnection.setComposingText(Character.toString(cho), 1);
                }
                else {
                    // 중성인 경우
                    char temp = UNICODE_NULL;
                    if(doubleJonFlag == UNICODE_NULL) {
                        temp = jon;
                        jon = UNICODE_NULL;
                        mInputConnection.commitText(Character.toString(makeHan()), 1);
                    }
                    else {
                        temp = doubleJonFlag;
                        jon = jonFlag;
                        mInputConnection.commitText(Character.toString(makeHan()), 1);
                    }
                    mState = 2;
                    clear();
                    cho = temp;
                    jun = c;
                    mInputConnection.setComposingText(Character.toString(makeHan()), 1);
                }
                break;
        }

    }

    /*
     * 커서 이동, 띄어쓰기, 한영 키보드 변경과 같은 이벤트 발생시
     * setComposingText 상태의 문자를 commit 하는 함수
     */
    public void directlyCommit() {
        if(mState == 0) {
            return ;
        }

        mInputConnection.commitText(Character.toString(makeHan()), 1);
        mState = 0;
        clear();
    }

    // 공백 입력
    public void commitSpace() {
        directlyCommit();
        mInputConnection.commitText(" ", 1);
    }

    public void clear() {
        cho = '\u0000';
        jun = '\u0000';
        jon = '\u0000';
        jonFlag = '\u0000';
        doubleJonFlag = '\u0000';
        junFlag = '\u0000';
    }

    public void delete() {
        switch (mState) {
            case 0 :
                mInputConnection.deleteSurroundingText(1, 0);
                break;

            case 1 :
                cho = UNICODE_NULL;
                mState = 0;
                mInputConnection.setComposingText("", 1);
                mInputConnection.commitText("", 1);
                break;

            case 2 :
                if(junFlag != UNICODE_NULL) {
                    jun = junFlag;
                    junFlag = UNICODE_NULL;
                    mState = 2;
                    mInputConnection.setComposingText(Character.toString(makeHan()), 1);
                }
                else {
                    jun = UNICODE_NULL;
                    junFlag = UNICODE_NULL;
                    mState = 1;
                    mInputConnection.setComposingText(Character.toString(cho), 1);
                }
                break;

            case 3 :
                if(doubleJonFlag == UNICODE_NULL) {
                    jon = UNICODE_NULL;
                    mState = 2;
                }
                else {
                    jon = jonFlag;
                    jonFlag = UNICODE_NULL;
                    doubleJonFlag = UNICODE_NULL;
                    mState = 3;
                }
                mInputConnection.setComposingText(Character.toString(makeHan()), 1);
                break;
        }
    }

    private boolean doubleJunEnable(char c) {
        switch (jun) {
            case 'ㅗ' :
                if(c == 'ㅏ') {
                    junFlag = jun;
                    jun = 'ㅘ';
                    return true;
                }
                if(c == 'ㅐ') {
                    junFlag = jun;
                    jun = 'ㅙ';
                    return true;
                }
                if(c == 'ㅣ') {
                    junFlag = jun;
                    jun = 'ㅚ';
                    return true;
                }
                break;

            case 'ㅜ' :
                if(c == 'ㅓ') {
                    junFlag = jun;
                    jun = 'ㅝ';
                    return true;
                }
                if(c == 'ㅔ') {
                    junFlag = jun;
                    jun = 'ㅞ';
                    return true;
                }
                if(c == 'ㅣ') {
                    junFlag = jun;
                    jun = 'ㅟ';
                    return true;
                }
                break;

            case 'ㅡ' :
                if (c == 'ㅣ') {
                    junFlag = jun;
                    jun = 'ㅢ';
                    return true;
                }
                break;
        }

        return false;
    }

    private boolean doubleJonEnable(char c) {
        jonFlag = jon;
        doubleJonFlag = c;

        switch (jon) {
            case 'ㄱ' :
                if(c == 'ㅅ') {
                    jon = 'ㄳ';
                    return true;
                }
                break;

            case 'ㄴ' :
                if(c == 'ㅈ') {
                    jon = 'ㄵ';
                    return true;
                }
                if(c == 'ㅎ') {
                    jon = 'ㄶ';
                    return true;
                }
                break;

            case 'ㄹ' :
                if(c == 'ㄱ') {
                    jon = 'ㄺ';
                    return true;
                }
                if(c == 'ㅁ') {
                    jon = 'ㄻ';
                    return true;
                }
                if(c == 'ㅂ') {
                    jon = 'ㄼ';
                    return true;
                }
                if(c == 'ㅅ') {
                    jon = 'ㄽ';
                    return true;
                }
                if(c == 'ㅌ') {
                    jon = 'ㄾ';
                    return true;
                }
                if(c == 'ㅍ') {
                    jon = 'ㄿ';
                    return true;
                }
                if(c == 'ㅎ') {
                    jon = 'ㅀ';
                    return true;
                }
                break;

            case 'ㅂ' :
                if(c == 'ㅅ') {
                    jon = 'ㅄ';
                    return true;
                }
                break;
        }

        return false;
    }

    private boolean junAvailable() {
        if(jun == 'ㅙ' || jun == 'ㅞ' || jun == 'ㅢ' || jun == 'ㅐ' || jun == 'ㅔ' || jun == 'ㅛ' || jun == 'ㅒ' || jun == 'ㅖ') {
            return false;
        }

        return true;
    }

    private boolean isDoubleJun() {
        if(jun == 'ㅙ' || jun == 'ㅞ' || jun == 'ㅚ' || jun == 'ㅝ' || jun == 'ㅟ' || jun == 'ㅘ' || jun == 'ㅢ') {
            return true;
        }

        return false;
    }

}
