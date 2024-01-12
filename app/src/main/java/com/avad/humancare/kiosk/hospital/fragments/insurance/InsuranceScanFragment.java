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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.dialog.PopupDialog;
import com.avad.humancare.kiosk.hospital.HospitalFooterView;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.hospital.fragments.SubBaseFragment;
import com.avad.humancare.kiosk.util.Common;

import java.security.Policy;
import java.util.Random;

public class InsuranceScanFragment extends SubBaseFragment {

    private LinearLayout lyCnt;
    private TextView txtInfo, txtCnt;

    private InsuranceDataManager mManager;

    public InsuranceScanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManager = InsuranceDataManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insurance_scan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        txtInfo = (TextView) v.findViewById(R.id.txt_info);
        lyCnt = (LinearLayout) v.findViewById(R.id.ly_cnt);
        txtCnt = (TextView) v.findViewById(R.id.txt_cnt);
        setInfoTextView(false);

        ((CardView) v.findViewById(R.id.btn_scan)).setOnClickListener(this);
    }

    public void setInfoTextView(boolean isScan) {
        if(isScan) mManager.scanCnt++;

        String strInfo = "서류를 아래 스캐너 위치에 끼우고,\n스캔 버튼을 눌러주세요.";
        SpannableStringBuilder ssb = new SpannableStringBuilder(strInfo);
        if(mManager.scanCnt > 0) {
            lyCnt.setVisibility(View.VISIBLE);
            ssb.setSpan(new ForegroundColorSpan(getActivity().getColor(R.color.text_color)),
                    strInfo.indexOf("스캔 버튼"), strInfo.lastIndexOf("을 "),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            txtCnt.setText(mManager.scanCnt + "장");
            ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BOTH);
        }else {
            lyCnt.setVisibility(View.GONE);
            ssb.setSpan(new ForegroundColorSpan(getActivity().getColor(R.color.hospital_sub_color)),
                    strInfo.indexOf("스캔 버튼"), strInfo.lastIndexOf("을 "),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BACK);
        }
        txtInfo.setText(ssb);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
            {
                ScanFragmentDialog dialog = new ScanFragmentDialog(this);
                dialog.show(getActivity().getSupportFragmentManager(), "scanDialog");
                break;
            }
        }
    }

    @Override
    public void NavigateNextFragment() {
        ((HospitalFragmentActivity) getActivity()).addFragmentStack(
                InsuranceSendFragment.newInstance(InsuranceSendFragment.SEND),
                InsuranceSendFragment.SEND
        );
    }

    @Override
    public void NavigatePrevFragment() {

    }

    public static class ScanFragmentDialog extends DialogFragment implements View.OnClickListener {
        private LinearLayout lyScan, lyConfirm;
        private ProgressBar bar;
        private ImageView ivScan;
        private CardView btnRestart, btnConfirm;

        private InsuranceScanFragment fragment;

        private int CMD_FRAGMENT_NEXT_STEP = 4002;
        private Handler fragmentHandler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == CMD_FRAGMENT_NEXT_STEP) {
                    int[] img = {
                            R.drawable.img_insurance_scan_01,
                            R.drawable.img_insurance_scan_02,
                            R.drawable.img_insurance_scan_03,
                            R.drawable.img_insurance_scan_04,
                            R.drawable.img_insurance_scan_05
                    };

                    bar.setProgress(0);

                    ivScan.setImageResource(img[new Random().nextInt(img.length)]);
                    lyScan.setVisibility(View.GONE);
                    lyConfirm.setVisibility(View.VISIBLE);
                }
            }
        };

        public ScanFragmentDialog(InsuranceScanFragment fragment) {
            this.fragment = fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.layout_insurance_scan_dialog, container, false);
            setCancelable(false);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            lyScan = (LinearLayout) v.findViewById(R.id.ly_scan);
            bar = (ProgressBar) v.findViewById(R.id.progressBar);
            lyScan.setVisibility(View.VISIBLE);

            ((TextView) v.findViewById(R.id.txt_scan)).setText("스캔중입니다");
            ((TextView) v.findViewById(R.id.txt_scan_sub)).setText("※ 다른 종이를 넣지말고 기다려주세요.");

            bar.setProgress(0);
            bar.setMax(3);

            lyConfirm = (LinearLayout) v.findViewById(R.id.ly_confirm);
            ivScan = (ImageView) v.findViewById(R.id.iv_scan);
            btnConfirm = (CardView) v.findViewById(R.id.btn_confirm);
            btnRestart = (CardView) v.findViewById(R.id.btn_restart);
            lyConfirm.setVisibility(View.GONE);

            String strInfo = "스캔이 잘되었는지 확인해 주세요";
            SpannableStringBuilder ssb = new SpannableStringBuilder(strInfo);
            ssb.setSpan(new ForegroundColorSpan(getActivity().getColor(R.color.hospital_sub_color)),
                    strInfo.indexOf("스캔"), strInfo.indexOf("이 "),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new ForegroundColorSpan(getActivity().getColor(R.color.hospital_sub_color)),
                    strInfo.indexOf("확인"), strInfo.indexOf("해 "),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((TextView) v.findViewById(R.id.txt_confirm_info)).setText(ssb);


            btnConfirm.setOnClickListener(this);
            btnRestart.setOnClickListener(this);

            return v;
        }

        @Override
        public void onStart() {
            super.onStart();
            startProgress();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_restart:
                {
                    startProgress();
                    break;
                }
                case R.id.btn_confirm:
                {
                    fragment.setInfoTextView(true);
                    this.dismiss();
                    break;
                }
            }
        }

        private void startProgress() {
            lyScan.setVisibility(View.VISIBLE);
            lyConfirm.setVisibility(View.GONE);

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

                    fragmentHandler.sendEmptyMessage(CMD_FRAGMENT_NEXT_STEP);
                }
            }).start();
        }
    }
}