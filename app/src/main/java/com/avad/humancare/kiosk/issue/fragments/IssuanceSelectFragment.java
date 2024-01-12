package com.avad.humancare.kiosk.issue.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssuanceSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssuanceSelectFragment extends IssuanceBaseFragment {

    private String TAG = IssuanceSelectFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button mGroup1, mGroup2, mGroup3, mGroup4, mGroup5;
    private ViewGroup mGroup;
    private ViewGroup mCheckView1_1, mCheckView1_2, mCheckView2_1, mCheckView2_2, mCheckView2_3;
    private ViewGroup mCheckView3_1, mCheckView3_2, mCheckView4_1, mCheckView4_2;
    private ViewGroup mCheckView5_1, mCheckView5_2, mCheckView6_1, mCheckView6_2;
    private ViewGroup mCheckView7_1, mCheckView7_2, mCheckView7_3, mCheckView8_1, mCheckView8_2;

    private CheckBox mCheckBtn1_1, mCheckBtn1_2, mCheckBtn2_1, mCheckBtn2_2, mCheckBtn2_3;
    private CheckBox mCheckBtn3_1, mCheckBtn3_2, mCheckBtn4_1, mCheckBtn4_2;
    private CheckBox mCheckBtn5_1, mCheckBtn5_2, mCheckBtn6_1, mCheckBtn6_2;
    private CheckBox mCheckBtn7_1, mCheckBtn7_2, mCheckBtn7_3, mCheckBtn8_1, mCheckBtn8_2;

    public IssuanceSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IssuanceSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssuanceSelectFragment newInstance(String param1, String param2) {
        IssuanceSelectFragment fragment = new IssuanceSelectFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_issuance_select, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleTv = (TextView) view.findViewById(R.id.title);
        titleTv.setText(R.string.issuance_title_main);
        TextView subTitleTv = (TextView) view.findViewById(R.id.sub_title);
        subTitleTv.setText(R.string.issuance_title_main_sub);

        view.findViewById(R.id.btn_go_main).setOnClickListener(this);
        view.findViewById(R.id.btn_go_back).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);

        mGroup = view.findViewById(R.id.group);
        mGroup1 = view.findViewById(R.id.group1);
        mGroup2 = view.findViewById(R.id.group2);
        mGroup3 = view.findViewById(R.id.group3);
        mGroup4 = view.findViewById(R.id.group4);
        mGroup5 = view.findViewById(R.id.group5);
        mGroup1.setOnClickListener(this);
        mGroup2.setOnClickListener(this);
        mGroup3.setOnClickListener(this);
        mGroup4.setOnClickListener(this);
        mGroup5.setOnClickListener(this);

        mCheckView1_1 = view.findViewById(R.id.checklayout_1_1);    mCheckView1_2 = view.findViewById(R.id.checklayout_1_2);
        mCheckView2_1 = view.findViewById(R.id.checklayout_2_1);    mCheckView2_2 = view.findViewById(R.id.checklayout_2_2);    mCheckView2_3 = view.findViewById(R.id.checklayout_2_3);
        mCheckView3_1 = view.findViewById(R.id.checklayout_3_1);    mCheckView3_2 = view.findViewById(R.id.checklayout_3_2);
        mCheckView4_1 = view.findViewById(R.id.checklayout_4_1);    mCheckView4_2 = view.findViewById(R.id.checklayout_4_2);
        mCheckView5_1 = view.findViewById(R.id.checklayout_5_1);    mCheckView5_2 = view.findViewById(R.id.checklayout_5_2);
        mCheckView6_1 = view.findViewById(R.id.checklayout_6_1);    mCheckView6_2 = view.findViewById(R.id.checklayout_6_2);
        mCheckView7_1 = view.findViewById(R.id.checklayout_7_1);    mCheckView7_2 = view.findViewById(R.id.checklayout_7_2);    mCheckView7_3 = view.findViewById(R.id.checklayout_7_3);
        mCheckView8_1 = view.findViewById(R.id.checklayout_8_1);    mCheckView8_2 = view.findViewById(R.id.checklayout_8_2);

        mCheckView1_1.setOnClickListener(this); mCheckView1_2.setOnClickListener(this);
        mCheckView2_1.setOnClickListener(this); mCheckView2_2.setOnClickListener(this); mCheckView2_3.setOnClickListener(this);
        mCheckView3_1.setOnClickListener(this); mCheckView3_2.setOnClickListener(this);
        mCheckView4_1.setOnClickListener(this); mCheckView4_2.setOnClickListener(this);
        mCheckView5_1.setOnClickListener(this); mCheckView5_2.setOnClickListener(this);
        mCheckView6_1.setOnClickListener(this); mCheckView6_2.setOnClickListener(this);
        mCheckView7_1.setOnClickListener(this); mCheckView7_2.setOnClickListener(this); mCheckView7_3.setOnClickListener(this);
        mCheckView8_1.setOnClickListener(this); mCheckView8_2.setOnClickListener(this);

        mCheckBtn1_1 = (CheckBox) view.findViewById(R.id.check_1_1); mCheckBtn1_2 = (CheckBox) view.findViewById(R.id.check_1_2);
        mCheckBtn2_1 = (CheckBox) view.findViewById(R.id.check_2_1); mCheckBtn2_2 = (CheckBox) view.findViewById(R.id.check_2_2); mCheckBtn2_3 = (CheckBox) view.findViewById(R.id.check_2_3);
        mCheckBtn3_1 = (CheckBox) view.findViewById(R.id.check_3_1); mCheckBtn3_2 = (CheckBox) view.findViewById(R.id.check_3_2);
        mCheckBtn4_1 = (CheckBox) view.findViewById(R.id.check_4_1); mCheckBtn4_2 = (CheckBox) view.findViewById(R.id.check_4_2);
        mCheckBtn5_1 = (CheckBox) view.findViewById(R.id.check_5_1); mCheckBtn5_2 = (CheckBox) view.findViewById(R.id.check_5_2);
        mCheckBtn6_1 = (CheckBox) view.findViewById(R.id.check_6_1); mCheckBtn6_2 = (CheckBox) view.findViewById(R.id.check_6_2);
        mCheckBtn7_1 = (CheckBox) view.findViewById(R.id.check_7_1); mCheckBtn7_2 = (CheckBox) view.findViewById(R.id.check_7_2); mCheckBtn7_3 = (CheckBox) view.findViewById(R.id.check_7_3);
        mCheckBtn8_1 = (CheckBox) view.findViewById(R.id.check_8_1); mCheckBtn8_2 = (CheckBox) view.findViewById(R.id.check_8_2);

        mCheckBtn1_1.setChecked(true);
        mCheckBtn2_1.setChecked(true);
        mCheckBtn3_1.setChecked(true);
        mCheckBtn4_1.setChecked(true);
        mCheckBtn5_1.setChecked(true);
        mCheckBtn6_1.setChecked(true);
        mCheckBtn7_3.setChecked(true);
        mCheckBtn8_2.setChecked(true);
        mCheckView1_1.setBackgroundResource(R.drawable.img_textbox09);
        mCheckView2_1.setBackgroundResource(R.drawable.img_textbox09);
        mCheckView3_1.setBackgroundResource(R.drawable.img_textbox09);
        mCheckView4_1.setBackgroundResource(R.drawable.img_textbox09);
        mCheckView5_1.setBackgroundResource(R.drawable.img_textbox09);
        mCheckView6_1.setBackgroundResource(R.drawable.img_textbox09);
        mCheckView7_3.setBackgroundResource(R.drawable.img_textbox09);
        mCheckView8_2.setBackgroundResource(R.drawable.img_textbox09);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_ok :
                ((IssuanceMainActivity) getActivity()).addFragmentStack(new IssuanceKeypadFragment(),"keypad");
                break;

            case R.id.group1 :
                updateGroupButtonView(0);
                break;

            case R.id.group2 :
                updateGroupButtonView(1);
                break;

            case R.id.group3 :
                updateGroupButtonView(2);
                break;

            case R.id.group4 :
                updateGroupButtonView(3);
                break;

            case R.id.group5 :
                updateGroupButtonView(4);
                break;

            case R.id.checklayout_1_1 :
                if(!mCheckBtn1_1.isChecked()) {
                    mCheckView1_1.setBackgroundResource(R.drawable.img_textbox09);
                    mCheckView1_2.setBackgroundResource(R.drawable.img_textbox08);
                    mCheckBtn1_1.setChecked(true);
                    mCheckBtn1_2.setChecked(false);
                }
                break;

            case R.id.checklayout_1_2 :
                mCheckView1_1.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView1_2.setBackgroundResource(R.drawable.img_textbox09);
                mCheckBtn1_1.setChecked(false);
                mCheckBtn1_2.setChecked(true);
                break;

            case R.id.checklayout_2_1 :
                mCheckView2_1.setBackgroundResource(R.drawable.img_textbox09);
                mCheckView2_2.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView2_3.setBackgroundResource(R.drawable.img_textbox08);
                mCheckBtn2_1.setChecked(true);
                mCheckBtn2_2.setChecked(false);
                mCheckBtn2_3.setChecked(false);
                break;

            case R.id.checklayout_2_2 :
                mCheckView2_1.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView2_2.setBackgroundResource(R.drawable.img_textbox09);
                mCheckView2_3.setBackgroundResource(R.drawable.img_textbox08);
                mCheckBtn2_1.setChecked(false);
                mCheckBtn2_2.setChecked(true);
                mCheckBtn2_3.setChecked(false);
                break;

            case R.id.checklayout_2_3 :
                mCheckView2_1.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView2_2.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView2_3.setBackgroundResource(R.drawable.img_textbox09);
                mCheckBtn2_1.setChecked(false);
                mCheckBtn2_2.setChecked(false);
                mCheckBtn2_3.setChecked(true);
                break;

            case R.id.checklayout_3_1 :
                mCheckView3_1.setBackgroundResource(R.drawable.img_textbox09);
                mCheckView3_2.setBackgroundResource(R.drawable.img_textbox08);
                mCheckBtn3_1.setChecked(true);
                mCheckBtn3_2.setChecked(false);
                break;

            case R.id.checklayout_3_2 :
                mCheckView3_1.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView3_2.setBackgroundResource(R.drawable.img_textbox09);
                mCheckBtn3_1.setChecked(false);
                mCheckBtn3_2.setChecked(true);
                break;

            case R.id.checklayout_4_1 :
                mCheckView4_1.setBackgroundResource(R.drawable.img_textbox09);
                mCheckView4_2.setBackgroundResource(R.drawable.img_textbox08);
                mCheckBtn4_1.setChecked(true);
                mCheckBtn4_2.setChecked(false);
                break;

            case R.id.checklayout_4_2 :
                mCheckView4_1.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView4_2.setBackgroundResource(R.drawable.img_textbox09);
                mCheckBtn4_1.setChecked(false);
                mCheckBtn4_2.setChecked(true);
                break;

            case R.id.checklayout_5_1 :
                mCheckView5_1.setBackgroundResource(R.drawable.img_textbox09);
                mCheckView5_2.setBackgroundResource(R.drawable.img_textbox08);
                mCheckBtn5_1.setChecked(true);
                mCheckBtn5_2.setChecked(false);
                break;

            case R.id.checklayout_5_2 :
                mCheckView5_1.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView5_2.setBackgroundResource(R.drawable.img_textbox09);
                mCheckBtn5_1.setChecked(false);
                mCheckBtn5_2.setChecked(true);
                break;

            case R.id.checklayout_6_1 :
                mCheckView6_1.setBackgroundResource(R.drawable.img_textbox09);
                mCheckView6_2.setBackgroundResource(R.drawable.img_textbox08);
                mCheckBtn6_1.setChecked(true);
                mCheckBtn6_2.setChecked(false);
                break;

            case R.id.checklayout_6_2 :
                mCheckView6_1.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView6_2.setBackgroundResource(R.drawable.img_textbox09);
                mCheckBtn6_1.setChecked(false);
                mCheckBtn6_2.setChecked(true);
                break;

            case R.id.checklayout_7_1 :
                mCheckView7_1.setBackgroundResource(R.drawable.img_textbox09);
                mCheckView7_2.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView7_3.setBackgroundResource(R.drawable.img_textbox08);
                mCheckBtn7_1.setChecked(true);
                mCheckBtn7_2.setChecked(false);
                mCheckBtn7_3.setChecked(false);
                break;

            case R.id.checklayout_7_2 :
                mCheckView7_1.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView7_2.setBackgroundResource(R.drawable.img_textbox09);
                mCheckView7_3.setBackgroundResource(R.drawable.img_textbox08);
                mCheckBtn7_1.setChecked(false);
                mCheckBtn7_2.setChecked(true);
                mCheckBtn7_3.setChecked(false);
                break;

            case R.id.checklayout_7_3 :
                mCheckView7_1.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView7_2.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView7_3.setBackgroundResource(R.drawable.img_textbox09);
                mCheckBtn7_1.setChecked(false);
                mCheckBtn7_2.setChecked(false);
                mCheckBtn7_3.setChecked(true);
                break;

            case R.id.checklayout_8_1 :
                mCheckView8_1.setBackgroundResource(R.drawable.img_textbox09);
                mCheckView8_2.setBackgroundResource(R.drawable.img_textbox08);
                mCheckBtn8_1.setChecked(true);
                mCheckBtn8_2.setChecked(false);
                break;

            case R.id.checklayout_8_2 :
                mCheckView8_1.setBackgroundResource(R.drawable.img_textbox08);
                mCheckView8_2.setBackgroundResource(R.drawable.img_textbox09);
                mCheckBtn8_1.setChecked(false);
                mCheckBtn8_2.setChecked(true);
                break;

        }
    }

    private void updateGroupButtonView(int pos) {
        for(int i=0; i<mGroup.getChildCount(); i++) {
            View child = mGroup.getChildAt(i);

            if(i == pos) {
                child.setBackgroundResource(R.drawable.img_textbox07);
                ((Button)child).setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            } else {
                child.setBackgroundResource(R.drawable.img_textbox06);
                ((Button)child).setTextColor(ContextCompat.getColor(getContext(), R.color.text_color));
            }
        }
    }
}