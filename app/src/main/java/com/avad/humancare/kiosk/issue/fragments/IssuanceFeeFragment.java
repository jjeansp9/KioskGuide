package com.avad.humancare.kiosk.issue.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssuanceFeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssuanceFeeFragment extends IssuanceBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IssuanceFeeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IssuanceFeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssuanceFeeFragment newInstance(String param1, String param2) {
        IssuanceFeeFragment fragment = new IssuanceFeeFragment();
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
            if(getArguments().containsKey(ARG_PARAM1)) {
                mParam1 = getArguments().getString(ARG_PARAM1); // 신청부수
            }
            if(getArguments().containsKey(ARG_PARAM2)) {
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issuance_fee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_go_main).setOnClickListener(this);
        view.findViewById(R.id.btn_go_back).setVisibility(View.GONE);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);

        TextView countTxt = view.findViewById(R.id.count_txt);
        TextView feeTxt = view.findViewById(R.id.fee);
        TextView totalFeeTxt = view.findViewById(R.id.total_fee);

        if(IssuanceMainActivity.mMenuItem != null) {
            feeTxt.setText(IssuanceMainActivity.mMenuItem.fee + getString(R.string.won));

            try {
                if(!TextUtils.isEmpty(mParam1)) {
                    countTxt.setText(mParam1 + getString(R.string.issuance_unit));

                    int charge = Integer.parseInt(mParam1);
                    totalFeeTxt.setText(charge*IssuanceMainActivity.mMenuItem.fee + getString(R.string.won));
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_ok :
                ((IssuanceMainActivity) getActivity()).addFragmentStack(IssuanceNotifyFragment.newInstance(IssuanceNotifyFragment.ARG_COMPLETE, ""),IssuanceNotifyFragment.ARG_COMPLETE);
                break;
        }
    }
}