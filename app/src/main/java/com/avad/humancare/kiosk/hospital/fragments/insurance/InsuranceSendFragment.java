package com.avad.humancare.kiosk.hospital.fragments.insurance;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFooterView;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.hospital.fragments.SubBaseFragment;

import java.util.Random;

public class InsuranceSendFragment extends SubBaseFragment {

    private static final String ARG_PARAM1 = "param1";

    public static final String SEND = "insurance_send";
    public static final String END = "insurance_process_end";

    private final int MAX_TEXTVIEW_OUTPUT = 3; // 3이면 보험회사 이름이 3번 나열되고 나머지를 그외로 처리

    private String infoType = SEND;
    private InsuranceDataManager mManager;

    public InsuranceSendFragment() {
        // Required empty public constructor
    }

    public static InsuranceSendFragment newInstance(String param) {
        InsuranceSendFragment fragment = new InsuranceSendFragment();
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
            infoType = SEND;
        }
        mManager = InsuranceDataManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insurance_send, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        ((LinearLayout) v.findViewById(R.id.ly_send)).setVisibility(
                (!TextUtils.isEmpty(infoType) && infoType.equals(SEND))?View.VISIBLE:View.GONE
        );
        ((LinearLayout) v.findViewById(R.id.ly_end)).setVisibility(
                (!TextUtils.isEmpty(infoType) && infoType.equals(END))?View.VISIBLE:View.GONE
        );
        if(infoType.equals(SEND)) {
            ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BACK);
            ((TextView) v.findViewById(R.id.txt_name)).setText(mManager.elderName + " 님");

            String strCompany = "";
            if(mManager.getInsuranceArray().size() == 1) {
                strCompany = InsuranceDataManager.arrInsurance[mManager.getInsuranceArray().get(0)];
            }else {
                for(int i = 0; i < mManager.getInsuranceArray().size(); i ++) {
                    strCompany += InsuranceDataManager.arrInsurance[mManager.getInsuranceArray().get(i)];
                    if(mManager.getInsuranceArray().size() > MAX_TEXTVIEW_OUTPUT && i == (MAX_TEXTVIEW_OUTPUT-1)) {
                        strCompany = strCompany + " 외 " + (mManager.getInsuranceArray().size() - MAX_TEXTVIEW_OUTPUT) + "개소";
                        break;
                    } else if(i < mManager.getInsuranceArray().size() -1) strCompany += ", ";
                }
            }
            ((TextView) v.findViewById(R.id.txt_company)).setText(strCompany);
            ((TextView) v.findViewById(R.id.txt_doc)).setText("청구서, 개인정보처리동의서, 스캔서류 " + String.valueOf(mManager.scanCnt) + "장");

            ((CardView) v.findViewById(R.id.btn_scan)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendFragmentDialog dialog = new SendFragmentDialog();
                    dialog.show(getActivity().getSupportFragmentManager(), "sendDialog");
                }
            });

        }else if(infoType.equals(END)) {
            ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.NONE);
            ((CardView) v.findViewById(R.id.btn_end)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(infoType.equals(SEND)) {
            ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BACK);
        }else if(infoType.equals(END)) {
            ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.NONE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void NavigateNextFragment() {

    }

    @Override
    public void NavigatePrevFragment() {

    }

    public static class SendFragmentDialog extends DialogFragment {
        private LinearLayout lyScan;
        private ProgressBar bar;

        public SendFragmentDialog() {
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.layout_insurance_scan_dialog, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(v, savedInstanceState);
            setCancelable(false);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            lyScan = (LinearLayout) v.findViewById(R.id.ly_scan);
            bar = (ProgressBar) v.findViewById(R.id.progressBar);
            lyScan.setVisibility(View.VISIBLE);

            ((TextView) v.findViewById(R.id.txt_scan)).setText("전송중입니다");
            ((TextView) v.findViewById(R.id.txt_scan_sub)).setText("※ 청구전송 후 접수까지 각 보험사 사정에 따라 1-2일이 소요됩니다.");

            bar.setProgress(0);
            bar.setMax(4);

            ((LinearLayout) v.findViewById(R.id.ly_confirm)).setVisibility(View.GONE);
        }

        @Override
        public void onStart() {
            super.onStart();
            startProgress();
        }

        private void startProgress() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (bar.getMax() > bar.getProgress()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        bar.setProgress(bar.getProgress() + 1);
                    }
                    dismiss();
                    ((HospitalFragmentActivity) getActivity()).addFragmentStack(
                            InsuranceSendFragment.newInstance(InsuranceSendFragment.END),
                            InsuranceSendFragment.END
                    );
                }
            }).start();
        }
    }
}