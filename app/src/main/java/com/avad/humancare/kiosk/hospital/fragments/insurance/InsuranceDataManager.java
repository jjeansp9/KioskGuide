package com.avad.humancare.kiosk.hospital.fragments.insurance;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InsuranceDataManager {

    private Context mContext = null;
    private static InsuranceDataManager mManager = null;

    /**
     * 진료받은 리스트
     */
    public static final String[] arrDiagnosisType = {
            "통원", "입원", "골절", "수술", "장해", "진단", "사망"
    };

    /**
     * 사고유형
     */
    public static final String[] arrAccidentType = {
            "질병", "사고", "교통사고"
    };

    /**
     * 진료학과
     */
    public static final String[] arrDepartment = {
            "일반의", "내과", "신경과", "이비인후과", "한방내과", "안과", "신경외과", "기타", "성형외과", "외과",
            "산부인과", "마취통증의학과", "정형외과", "정신건강의학과", "피부과", "비뇨의학과", "영상의학과", "방사선종양학과", "병리과", "결핵과",
            "재활의학과", "가정의학과", "응급의학과", "치과"
    };

    /**
     * 보험 합친목록
     */
    public static final String[] arrInsurance = {
            "한화생명", "삼성생명", "교보생명", "NH농협생명", "라이나생명", "오렌지라이프", "동양생명", "흥국생명", "신한생명",
            "AIA생명", "미래에셋생명", "메트라이프", "KDB생명", "푸르덴셜생명", "새마을금고", "ABL생명", "KB생명", "BNP파리바카디프생명",
            "DB생명(동부)", "DGB생명", "푸본현대생명", "수협", "우체국", "처브라이프", "하나생명", "한국교직원공제", "삼성화재",
            "DB손해(동부)", "현대해상", "한화손해", "롯데손해", "KB손해(LIG)", "메리츠화재", "흥국화재", "AIG손해", "MG손해",
            "NH농협손해", "더K손해보험", "에이스손해", "AXA다이렉트"
    };

    /**
     * 생명보험목록
     */
    public static final String[] arrLifeInsurance = {
            "한화생명", "삼성생명", "교보생명", "NH농협생명", "라이나생명", "오렌지라이프", "동양생명", "흥국생명", "신한생명", "AIA생명",
            "미래에셋생명", "메트라이프", "KDB생명", "푸르덴셜생명", "새마을금고", "ABL생명", "KB생명", "BNP파리바카디프생명", "DB생명(동부)",
            "DGB생명", "푸본현대생명", "수협", "우체국", "처브라이프", "하나생명", "한국교직원공제"
    };

    /**
     * 손해보험목록
     */
    public static final String[] arrDamageInsurance = {
            "삼성화재", "DB손해(동부)", "현대해상", "한화손해", "롯데손해", "KB손해(LIG)",
            "메리츠화재", "흥국화재", "AIG손해", "MG손해", "NH농협손해", "더K손해보험", "에이스손해", "AXA다이렉트"
    };

    private ArrayList<Integer> mSelectedDiagnosisType = new ArrayList<Integer>();   // 진료유형
    private ArrayList<Integer> mSelectedInsurance = new ArrayList<Integer>();   // 보험
    public int accidentType = -1; // 사고 유형
    public int medicalDepartment = -1;  // 진료학과
    public String diagnosisName = "";  // 진단명 (작성)

    public String receiverType = ""; // 빈 값 = 선택안함, Y = 본인, N = 다른 수익자
    public String elderName = "";   //이름 (일단 작성하도록 컨셉)
    public String firstSecurityNum = "";    //주민번호 앞자리
    public String lastSecurityNum = "";     //주민번호 뒷자리
    public String phoneNumber = "";     //연락처
    public boolean isMinor = false; //미성년자 여부

    public boolean[] isAgreeTerms;   //key== 1:개인정보처리동의, 2: 청구대행동의, 3: 제3자동의

    /**
     * 손해보험목록
     */
    public static final String[] arrBank = {
            "국민은행", "씨티은행", "신한은행", "하나은행", "우리은행", "외환은행", "IBK기업은행",
            "SC은행", "우체국", "전북은행", "광주은행", "신협", "대구은행", "경남은행", "부산은행",
            "제주은행", "농협", "수협", "기타"
    };

    public int bankType = -1;
    public String bankNum = "";
    public String receiverNm = ""; //수취인 이름

    public int scanCnt = 0; //스캔개수

    public static InsuranceDataManager getInstance() {
        if(mManager == null) {
            mManager = new InsuranceDataManager();
        }

        return mManager;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public static void clearInsuranceDataManager() {
        if(mManager != null){
            Log.e("InsuranceDataManager", "clearInsuranceDataManager ---");
            mManager = null;
        }
    }

    public ArrayList<Integer> getDiagnosisTypeArray() {
        return mSelectedDiagnosisType;
    }
    public ArrayList<Integer> getInsuranceArray() {
        return mSelectedInsurance;
    }
}
