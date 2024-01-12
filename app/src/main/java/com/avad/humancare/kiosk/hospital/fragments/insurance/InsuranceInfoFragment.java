package com.avad.humancare.kiosk.hospital.fragments.insurance;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFooterView;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.hospital.fragments.SubBaseFragment;

import java.util.HashMap;

/**
 * 보험금 간편청구 관련 Fragment Stack 순서
 * InsuranceInfoFragment ( 필요 서류 안내 ) -> InsuranceSelDiagnosisTypeFragment ( 받은 진료 선택 ) -> InsuranceInfoFragment ( 받은 진료에 따른 필요 서류 안내 )
 * -> InsuranceSelCompanyFragment ( 보험사 선택 ) -> InsuranceInfoFragment ( 개인정보 수집 동의 ) -> InsuranceUserDetailFragment ( 청구인구분, 피보험자 정보입력 )
 * -> InsuranceSelAccidentFragment ( 청구내용, 진료내용 입력 ) [ -> 피보험자 != 수익자일 경우 InsuranceSelAccidentFragment ( 보험금 수령계좌입력 ) ]
 * -> InsuranceInfoFragment ( 청구서류 확인 ) -> InsuranceScanFragment ( 서류 스캔 ) -> InsuranceSendFragment ( 전송 ) -> InsuranceSendFragment ( 종료 )
 */
public class InsuranceInfoFragment extends SubBaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";

    public static final String INTRO = "insurance_intro";
    public static final String DIAGNOSIS_TYPE = "insurance_diagnosis_type";
    public static final String CONTENT_TO_USE = "insurance_content_to_use";
    public static final String CONFIRM = "insurance_info_confirm";

    private int[] lyUseId = {
            R.id.ly_use_0, R.id.ly_use_1, R.id.ly_use_2, R.id.ly_use_3
    };
    private int[] btnUseId = {
            R.id.btn_use_0, R.id.btn_use_1, R.id.btn_use_2, R.id.btn_use_3
    };
    private LinearLayout[] lyUse;
    private ImageButton[] btnUse;

    private String infoType = INTRO;
    private InsuranceDataManager mManager;

    public InsuranceInfoFragment() {
    }

    public static InsuranceInfoFragment newInstance(String param) {
        InsuranceInfoFragment fragment = new InsuranceInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_PARAM1)) {
            infoType = getArguments().getString(ARG_PARAM1);
        }else {
            infoType = INTRO;
        }
        mManager = InsuranceDataManager.getInstance();
        if(infoType.equals(CONTENT_TO_USE) && mManager.isAgreeTerms == null) {
            mManager.isAgreeTerms = new boolean[lyUseId.length];
            for (int i = 0; i < lyUseId.length; i++) {
                mManager.isAgreeTerms[i] = false;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insurance_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        ((LinearLayout) v.findViewById(R.id.ly_intro)).setVisibility(
                (!TextUtils.isEmpty(infoType) && infoType.equals(INTRO))?View.VISIBLE:View.GONE
        );
        ((LinearLayout) v.findViewById(R.id.ly_diagnosis_type)).setVisibility(
                (!TextUtils.isEmpty(infoType) && infoType.equals(DIAGNOSIS_TYPE))?View.VISIBLE:View.GONE
        );
        ((LinearLayout) v.findViewById(R.id.ly_content_to_use)).setVisibility(
                (!TextUtils.isEmpty(infoType) && infoType.equals(CONTENT_TO_USE))?View.VISIBLE:View.GONE
        );
        ((LinearLayout) v.findViewById(R.id.ly_info_confirm)).setVisibility(
                (!TextUtils.isEmpty(infoType) && infoType.equals(CONFIRM))?View.VISIBLE:View.GONE
        );
        if(!TextUtils.isEmpty(infoType) && infoType.equals(CONTENT_TO_USE)) {
            lyUse = new LinearLayout[lyUseId.length];
            btnUse = new ImageButton[lyUseId.length];
            for (int i = 0; i < lyUseId.length; i++) {
                lyUse[i] = (LinearLayout) v.findViewById(lyUseId[i]);
                btnUse[i] = (ImageButton) v.findViewById(btnUseId[i]);
                lyUse[i].setOnClickListener(this);
            }
            settingCheckedBox();
        }else if (!TextUtils.isEmpty(infoType) && infoType.equals(CONFIRM)) {
            ((TextView) v.findViewById(R.id.txt_elder_name)).setText(mManager.elderName);
            ((TextView) v.findViewById(R.id.txt_security)).setText(mManager.firstSecurityNum + "-" +mManager.lastSecurityNum);
            ((TextView) v.findViewById(R.id.txt_contractor)).setText(mManager.receiverType.equals("Y")?mManager.elderName:mManager.receiverNm);
            ((TextView) v.findViewById(R.id.txt_number)).setText(mManager.phoneNumber);
            ((TextView) v.findViewById(R.id.txt_accident_type)).setText(InsuranceDataManager.arrAccidentType[mManager.accidentType]);
            ((TextView) v.findViewById(R.id.txt_department)).setText(InsuranceDataManager.arrDepartment[mManager.medicalDepartment]);
            ((TextView) v.findViewById(R.id.txt_diagnosis_name)).setText(mManager.diagnosisName);
            if(mManager.receiverType.equals("Y")) {
                ((LinearLayout) v.findViewById(R.id.ly_me)).setVisibility(View.VISIBLE);
                ((LinearLayout) v.findViewById(R.id.ly_other)).setVisibility(View.GONE);
            }else {
                ((LinearLayout) v.findViewById(R.id.ly_me)).setVisibility(View.GONE);
                ((LinearLayout) v.findViewById(R.id.ly_other)).setVisibility(View.VISIBLE);
                ((TextView) v.findViewById(R.id.txt_bank_name)).setText(InsuranceDataManager.arrBank[mManager.bankType]);
                ((TextView) v.findViewById(R.id.txt_bank_num)).setText(mManager.bankNum);
                ((TextView) v.findViewById(R.id.txt_receiver)).setText(mManager.receiverNm);
            }
        }
    }


    @Override
    public void onClick(View v) {
        if(infoType == CONTENT_TO_USE) {
            for (int i = 0; i < lyUseId.length; i++) {
                if(v.getId() == lyUseId[i]){
                    if(i == 0) {
                        int cnt = 0;
                        for (int j = 1; j < lyUseId.length; j++) {
                            if(mManager.isAgreeTerms[j]) cnt++;
                        }
                        for (int j = 1; j < lyUseId.length; j++) {
                            if(cnt == 3) mManager.isAgreeTerms[j] = false;
                            else mManager.isAgreeTerms[j] = true;
                        }
                    }else {
                        mManager.isAgreeTerms[i] = !mManager.isAgreeTerms[i];
                    }
                    break;
                }
            }
            settingCheckedBox();
        }
    }

    public void settingCheckedBox() {
        int cnt = 0;
        for (int i = 1; i < lyUseId.length; i++) {
            if(mManager.isAgreeTerms[i]) {
                cnt++;
                lyUse[i].setBackgroundResource(R.drawable.btn_insurance_basic_checked);
                btnUse[i].setSelected(true);
            }else {
                lyUse[i].setBackgroundResource(R.drawable.btn_insurance_basic_selector);
                btnUse[i].setSelected(false);
            }
        }
        if(cnt == 3) {
            lyUse[0].setBackgroundResource(R.drawable.btn_insurance_basic_checked);
            btnUse[0].setSelected(true);
            mManager.isAgreeTerms[0] = true;
        } else {
            lyUse[0].setBackgroundResource(R.drawable.btn_insurance_basic_selector);
            btnUse[0].setSelected(false);
            mManager.isAgreeTerms[0] = false;
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BOTH);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Fragment 생명주기 최상위
        ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BOTH);
    }

    @Override
    public void NavigateNextFragment() {
        Log.e("InsuranceInfoFragment", ">>> NavigateNextFragment");

        if(!TextUtils.isEmpty(infoType) && infoType.equals(INTRO))
            ((HospitalFragmentActivity) getActivity()).addFragmentStack(new InsuranceSelDiagnosisTypeFragment(), "selDiagnosisType");
//            ((HospitalFragmentActivity) getActivity()).addFragmentStack(
//                    InsuranceSelAccidentFragment.newInstance(InsuranceSelAccidentFragment.ACCIDENT),
//                    InsuranceSelAccidentFragment.ACCIDENT
//            );
        else if(!TextUtils.isEmpty(infoType) && infoType.equals(DIAGNOSIS_TYPE))
            ((HospitalFragmentActivity) getActivity()).addFragmentStack(new InsuranceSelCompanyFragment(), "selCompany");
        else if(!TextUtils.isEmpty(infoType) && infoType.equals(CONTENT_TO_USE)) {
            if(mManager.isAgreeTerms != null && mManager.isAgreeTerms[1] && mManager.isAgreeTerms[2]) {
                ((HospitalFragmentActivity) getActivity()).addFragmentStack(new InsuranceUserDetailFragment(), "userDetail");
            }else {
                showNormalDialog("안내", "필수 약관에 동의해주세요.");
            }
        }else if(!TextUtils.isEmpty(infoType) && infoType.equals(CONFIRM)) {
            ((HospitalFragmentActivity) getActivity()).addFragmentStack(new InsuranceScanFragment(), "insuranceScan");
        }
    }

    @Override
    public void NavigatePrevFragment() {

    }
}