package com.avad.humancare.kiosk.issue.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;
import com.avad.humancare.kiosk.util.Utils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RealEstateIssueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RealEstateIssueFragment extends IssuanceBaseFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private TextView mCountTv, mPriceTv;
    private Dialog mDlg;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0 :
                    mDlg.dismiss();
                    ((IssuanceMainActivity) getActivity()).addFragmentStack(IssuanceNotifyFragment.newInstance(IssuanceNotifyFragment.ARG_COMPLETE, ""),IssuanceNotifyFragment.ARG_COMPLETE);
                    break;
            }
        }
    };

    public RealEstateIssueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RealEstateIssueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RealEstateIssueFragment newInstance(String param1) {
        RealEstateIssueFragment fragment = new RealEstateIssueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_real_estate_issue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_go_main).setOnClickListener(this);
        view.findViewById(R.id.btn_go_back).setOnClickListener(this);
        view.findViewById(R.id.btn_plus).setOnClickListener(this);
        view.findViewById(R.id.btn_minus).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);

        mHeaderTitleView = view.findViewById(R.id.header_title);
        mHeaderTitleView.setText(R.string.issuance_real_estate_title6);
        ((TextView)view.findViewById(R.id.display)).setText(mParam1);
        mCountTv = (TextView) view.findViewById(R.id.count_txt);
        mPriceTv = (TextView) view.findViewById(R.id.price);

        int[] checkViewArr = {R.id.checkView1, R.id.checkView2, R.id.checkView3, R.id.checkView4, R.id.checkView5, R.id.checkView6, R.id.checkView7, R.id.checkView8};
        int[] checkArr = {R.id.check1, R.id.check2, R.id.check3, R.id.check4, R.id.check5, R.id.check6, R.id.check7, R.id.check8};
        ArrayList<CompoundButton> checkBtnList = new ArrayList<>();

        for(int i=0; i<checkArr.length; i++) {
            CheckBox checkbox = (CheckBox)view.findViewById(checkArr[i]);
            checkbox.setTag(i);
            checkbox.setClickable(false);

            if(i%2 == 1) {
                checkbox.setChecked(true);
            } else {
                checkbox.setChecked(false);
            }

            ViewGroup checkView = view.findViewById(checkViewArr[i]);
            checkView.setTag(i);
            checkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int idx = (int)view.getTag();

                    if(idx%2 == 1) {  // 오른쪽 체크박스
                        if(checkBtnList.get(idx).isChecked() == false) {
                            checkBtnList.get(idx).setChecked(true);
                            checkBtnList.get(idx-1).setChecked(false);
                        }
//                        else {
//                            checkBtnList.get(idx).setChecked(false);
//                            checkBtnList.get(idx-1).setChecked(true);
//                        }
                    } else {    // 왼쪽 체크박스
                        if(checkBtnList.get(idx).isChecked() == false) {
                            checkBtnList.get(idx).setChecked(true);
                            checkBtnList.get(idx+1).setChecked(false);
                        }
//                        else {
//                            checkBtnList.get(idx).setChecked(false);
//                            checkBtnList.get(idx+1).setChecked(true);
//                        }
                    }
                }
            });

            checkBtnList.add(checkbox);
        }

        createDialogPopup();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int count = Integer.parseInt(mCountTv.getText().toString());

        switch (view.getId()) {
            case R.id.btn_plus :
                if(count >= 9) {
                    count = 9;
                } else {
                    count++;
                }
                mCountTv.setText(String.valueOf(count));
                mPriceTv.setText(String.valueOf(Utils.getPriceFormat(getContext(), count*1000, false)));
                break;

            case R.id.btn_minus:
                if(count <= 1) {
                    count = 1;
                } else {
                    count--;
                }
                mCountTv.setText(String.valueOf(count));
                mPriceTv.setText(String.valueOf(Utils.getPriceFormat(getContext(), count*1000, false)));
                break;

            case R.id.btn_ok :
                mDlg.show();
                mHandler.sendEmptyMessageDelayed(0, 3000);
                break;

        }
    }

    private void createDialogPopup() {
        mDlg = new Dialog(getContext());
        mDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀 제거
        mDlg.setContentView(R.layout.layout_dialog_real_estate_issue);
        mDlg.setCancelable(false);
        mDlg.setCanceledOnTouchOutside(false);
    }
}