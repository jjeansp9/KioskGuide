package com.avad.humancare.kiosk.issue.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssuanceNotifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssuanceNotifyFragment extends IssuanceBaseFragment {

    private String TAG = IssuanceNotifyFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String ARG_FINGERPRINT = "fingerprint"; // 주민등록등본-지문인식기 사용안내
    public static final String ARG_FAMILY_1 = "family-1";   // 가족관계증명서 - 발급안내
    public static final String ARG_FAMILY_2 = "family-2";   //  가족관계증명서 - 증명구분 선택후 안내
    public static final String ARG_COMPLETE = "complete";   //  증명서 - 증명서 출력 완료

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewGroup mTopTitleView, mTitleView, mContentView, mCompletedView;
    private ViewGroup mFirgerView, mBottomOkBtn, mGoMainBtn;
    private TextView mNotifyTitleTv, mContentTv;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1 :
                    ((IssuanceMainActivity) getActivity()).addFragmentStack(new IssuanceSelectFragment(),"select");
                    break;
            }
        }
    };

    public IssuanceNotifyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IssuanceNotifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssuanceNotifyFragment newInstance(String param1, String param2) {
        IssuanceNotifyFragment fragment = new IssuanceNotifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.e(TAG, "onCreate() param1="+mParam1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issuance_notify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated() param1="+mParam1);
        mTopTitleView = view.findViewById(R.id.top_title);
        mTitleView = view.findViewById(R.id.title_ly);
        mContentView = view.findViewById(R.id.content_ly);
        mCompletedView = view.findViewById(R.id.complete_ly);

        TextView titleTv = (TextView) view.findViewById(R.id.title);
        TextView subTitleTv = (TextView) view.findViewById(R.id.sub_title);
        mNotifyTitleTv = (TextView) view.findViewById(R.id.title_txt);
        mFirgerView = view.findViewById(R.id.fingerprint_view);
        mContentTv = (TextView) view.findViewById(R.id.text_identify_success);
        TextView contentMsgTv = (TextView) view.findViewById(R.id.text_content_msg);

        mGoMainBtn = view.findViewById(R.id.btn_go_main);
        mGoMainBtn.setOnClickListener(this);
        view.findViewById(R.id.btn_go_back).setOnClickListener(this);
        mBottomOkBtn = view.findViewById(R.id.btn_ok);
        mBottomOkBtn.setOnClickListener(this);

        SpannableStringBuilder ssb = new SpannableStringBuilder();

        if(mParam1 != null && mParam1.equals(ARG_COMPLETE)) {
            mTopTitleView.setVisibility(View.INVISIBLE);
            mTitleView.setVisibility(View.GONE);
            mContentView.setVisibility(View.GONE);
            mCompletedView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.btn_go_back).setVisibility(View.GONE);
            ((IssuanceMainActivity) getActivity()).setGuideMsg(getString(R.string.issuance_guide_msg));
        }
        else if(mParam1 != null && mParam1.equals(ARG_FAMILY_1)) {
            titleTv.setText(R.string.issuance_notify_title2);
            subTitleTv.setVisibility(View.GONE);
            mNotifyTitleTv.setText("[ " + getString(R.string.issuance_notify2) + " ]" );

            ssb.append(getString(R.string.issuance_notify_subtitle2));
            ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.issuance_font_color_red)), 11, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.issuance_font_color_red)), 25, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.issuance_font_color_red)), 62, 68, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.issuance_font_color_red)), 89, 96, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            contentMsgTv.setText(ssb);
            contentMsgTv.setGravity(Gravity.CENTER_VERTICAL);
            contentMsgTv.setVisibility(View.VISIBLE);
            mContentTv.setVisibility(View.GONE);
            mFirgerView.setVisibility(View.GONE);

            ((IssuanceMainActivity) getActivity()).setGuideMsg(getString(R.string.issuance_guide_msg2));
        }
        else if(mParam1 != null && mParam1.equals(ARG_FAMILY_2)) {
            ssb.append(getString(R.string.issuance_notify_subtitle3));
            ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.issuance_font_color_red)), 24, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mTopTitleView.setVisibility(View.INVISIBLE);
            mNotifyTitleTv.setText("[ " + getString(R.string.issuance_notify) + " ]" );
            contentMsgTv.setText(ssb);
            contentMsgTv.setVisibility(View.VISIBLE);
            mContentTv.setVisibility(View.GONE);
            mFirgerView.setVisibility(View.GONE);


        }
        else {
            // ARG_FINGERPRINT
            titleTv.setText(R.string.issuance_notify_title1);
            subTitleTv.setText(R.string.issuance_notify_subtitle1);
            ((IssuanceMainActivity) getActivity()).setGuideMsg(getString(R.string.issuance_guide_msg1));
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_ok :
                if(mParam1 != null && mParam1.equals(ARG_COMPLETE)) {
                    mGoMainBtn.performClick();
                }
                else if(mParam1 != null && mParam1.equals(ARG_FAMILY_1)) {
                    ((IssuanceMainActivity) getActivity()).addFragmentStack(IssuanceCertificateSelectFragment.newInstance(IssuanceCertificateSelectFragment.ARG_CERTIFICATE_SELECT, ""),IssuanceCertificateSelectFragment.ARG_CERTIFICATE_SELECT);
                }
                else if(mParam1 != null && mParam1.equals(ARG_FAMILY_2)) {
                    ((IssuanceMainActivity) getActivity()).addFragmentStack(IssuanceCertificateSelectFragment.newInstance(IssuanceCertificateSelectFragment.ARG_RESIDENT_NUM_SELECT, ""),IssuanceCertificateSelectFragment.ARG_RESIDENT_NUM_SELECT);
                }
                else {
                    // ARG_FINGERPRINT
                    mNotifyTitleTv.setText("[ " + getString(R.string.issuance_notify) + " ]" );
                    mContentTv.setVisibility(View.VISIBLE);
                    mFirgerView.setVisibility(View.GONE);
                    mBottomOkBtn.setVisibility(View.GONE);

                    mHandler.sendEmptyMessageDelayed(1, 2000);
                }

            break;
        }
    }

}