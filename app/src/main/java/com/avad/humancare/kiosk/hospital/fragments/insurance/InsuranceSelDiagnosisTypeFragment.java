package com.avad.humancare.kiosk.hospital.fragments.insurance;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFooterView;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.hospital.fragments.SubBaseFragment;

import java.util.ArrayList;

public class InsuranceSelDiagnosisTypeFragment extends SubBaseFragment implements View.OnClickListener {

    private final int[] btnIds = {
            R.id.btn_0,
            R.id.btn_1,
            R.id.btn_2,
            R.id.btn_3,
            R.id.btn_4,
            R.id.btn_5,
            R.id.btn_6
    };

    private Button[] btnCheck;

    private InsuranceDataManager mManager;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManager = InsuranceDataManager.getInstance();
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insurance_diagnosis_type_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        String strInfo = "진료받으신 내용을\n선택해 주세요";
        SpannableStringBuilder ssb = new SpannableStringBuilder(strInfo);
        ssb.setSpan(new ForegroundColorSpan(getActivity().getColor(R.color.hospital_sub_color)),
                strInfo.indexOf("진료받으신 내용"), strInfo.indexOf("을"),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) v.findViewById(R.id.txt_info)).setText(ssb);

        btnCheck = new Button[btnIds.length];
        for(int i = 0; i < btnIds.length; i++) {
            btnCheck[i] = (Button) v.findViewById(btnIds[i]);
            btnCheck[i].setTag(i);
            btnCheck[i].setOnClickListener(this);
            if(i < InsuranceDataManager.arrDiagnosisType.length) {
                btnCheck[i].setText(InsuranceDataManager.arrDiagnosisType[i]);
            }
            btnCheck[i].setSelected(!mManager.getDiagnosisTypeArray().isEmpty() && mManager.getDiagnosisTypeArray().contains(i));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        int tag = (int) view.getTag();
        ArrayList<Integer> selected = mManager.getDiagnosisTypeArray();
        if(!selected.isEmpty() && selected.contains(tag)) {
            selected.remove(selected.indexOf(tag));
            view.setSelected(false);
        }else {
            selected.add(tag);
            view.setSelected(true);
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
        Log.e("InsuranceSelDiagnosisTypeFragment", ">>> NavigateNextFragment");
        if(mManager != null && mManager.getDiagnosisTypeArray().size() > 0) {
            ((HospitalFragmentActivity) getActivity()).addFragmentStack(
                    InsuranceInfoFragment.newInstance(InsuranceInfoFragment.DIAGNOSIS_TYPE),
                    InsuranceInfoFragment.DIAGNOSIS_TYPE
            );
        }else {
            showNormalDialog("안내", "진료받으신 내용을 하나 이상 선택해주세요.");
        }
    }

    @Override
    public void NavigatePrevFragment() {

    }
}