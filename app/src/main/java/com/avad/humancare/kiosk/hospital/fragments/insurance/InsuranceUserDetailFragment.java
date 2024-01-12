package com.avad.humancare.kiosk.hospital.fragments.insurance;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFooterView;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.hospital.fragments.SubBaseFragment;

public class InsuranceUserDetailFragment extends SubBaseFragment {

    private LinearLayout lySelMe, lySelOther;
    private LinearLayout lyBottom;
    private EditText txeName, txeFirstResident, txeLastResident, txeNumber;
    private TextView lyKeyboard;

    private LinearLayout lyMinor;
    private ImageButton btnMinor;

    private InsuranceDataManager mManager;

    public InsuranceUserDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManager = InsuranceDataManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insurance_user_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        lySelMe = v.findViewById(R.id.ly_sel_me);
        lySelOther = v.findViewById(R.id.ly_sel_other);

        lyBottom = v.findViewById(R.id.ly_bottom);
        lyKeyboard = v.findViewById(R.id.ly_keyboard);

        txeName = v.findViewById(R.id.txe_name);
        txeFirstResident = v.findViewById(R.id.txe_first_resident);
        txeLastResident = v.findViewById(R.id.txe_last_resident);
        txeNumber = v.findViewById(R.id.txe_number);

        lyMinor = (LinearLayout) v.findViewById(R.id.ly_minor);
        btnMinor = (ImageButton) v.findViewById(R.id.btn_minor);
        btnMinor.setSelected(mManager.isMinor);

        lyMinor.setOnClickListener(this);
        lySelMe.setOnClickListener(this);
        lySelOther.setOnClickListener(this);
        lyKeyboard.setOnClickListener(this);
        setTopLayout(null);
    }

    @Override
    public void onClick(View view) {
        hideKeyBoard();
        switch (view.getId()) {
            case R.id.ly_sel_me:
            {
                setTopLayout("Y");
                break;
            }
            case R.id.ly_sel_other:
            {
                setTopLayout("N");
                break;
            }
            case R.id.ly_keyboard:
            {
                break;
            }
            case R.id.ly_minor:
            {
                mManager.isMinor = !mManager.isMinor;
                btnMinor.setSelected(mManager.isMinor);
                break;
            }
        }
    }

    public void setTopLayout(String status) {
        if(!TextUtils.isEmpty(status)) {
            if(lyBottom.getVisibility() == View.VISIBLE) saveTextToEditText();
            mManager.receiverType = status;
        }

        if(!TextUtils.isEmpty(mManager.receiverType)) {
            lyBottom.setVisibility(View.VISIBLE);
            lySelMe.setSelected(mManager.receiverType.equals("Y"));
            lySelOther.setSelected(mManager.receiverType.equals("N"));

            if(!TextUtils.isEmpty(mManager.elderName)) txeName.setText(mManager.elderName);
            if(!TextUtils.isEmpty(mManager.firstSecurityNum)) txeFirstResident.setText(mManager.firstSecurityNum);
            if(!TextUtils.isEmpty(mManager.lastSecurityNum)) txeLastResident.setText(mManager.lastSecurityNum);
            if(!TextUtils.isEmpty(mManager.phoneNumber)) txeNumber.setText(mManager.phoneNumber);

            hideKeyBoard();
        }else {
            lyBottom.setVisibility(View.GONE);
            lySelMe.setSelected(false);
            lySelOther.setSelected(false);
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
        ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BOTH);
    }

    @Override
    public void NavigateNextFragment() {
        hideKeyBoard();

        saveTextToEditText();
        if(TextUtils.isEmpty(mManager.receiverType)) {
            showNormalDialog("안내", "청구인 구분을 선택해주세요.");
        }else if(TextUtils.isEmpty(mManager.elderName) || mManager.elderName.length() < 2) {
            showNormalDialog("안내", "피보험자(진료 받으신분)의 이름을 입력해주세요.");
        }else if(TextUtils.isEmpty(mManager.firstSecurityNum) || mManager.firstSecurityNum.length() != 6
                || TextUtils.isEmpty(mManager.lastSecurityNum) || mManager.lastSecurityNum.length() != 7) {
            showNormalDialog("안내", "피보험자(진료 받으신분)의 주민등록번호를 입력해주세요.");
        }else if(TextUtils.isEmpty(mManager.phoneNumber) || mManager.phoneNumber.length() < 9) {
            showNormalDialog("안내", "피보험자(진료 받으신분)의 연락처를 입력해주세요.");
        }else {
            ((HospitalFragmentActivity) getActivity()).addFragmentStack(
                    InsuranceSelAccidentFragment.newInstance(InsuranceSelAccidentFragment.ACCIDENT),
                    InsuranceSelAccidentFragment.ACCIDENT
            );
        }
    }

    @Override
    public void NavigatePrevFragment() {
        hideKeyBoard();

        saveTextToEditText();
    }


    public void saveTextToEditText() {
        mManager.elderName = txeName.getText().toString().trim();
        mManager.firstSecurityNum = txeFirstResident.getText().toString().trim();
        mManager.lastSecurityNum = txeLastResident.getText().toString().trim();
        mManager.phoneNumber = txeNumber.getText().toString().trim();
    }

    private void hideKeyBoard() {
//        if(getActivity().getCurrentFocus().getWindowToken() == null) return;
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txeNumber.getWindowToken(), 0);
        txeName.clearFocus();
        txeFirstResident.clearFocus();
        txeLastResident.clearFocus();
        txeNumber.clearFocus();
    }
}