package com.avad.humancare.kiosk.issue.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssuanceInputResidentNumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssuanceInputResidentNumFragment extends IssuanceBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView mLeftTextView, mRightTextView;

    public IssuanceInputResidentNumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IssuanceInputResidentNumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssuanceInputResidentNumFragment newInstance(String param1, String param2) {
        IssuanceInputResidentNumFragment fragment = new IssuanceInputResidentNumFragment();
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
        return inflater.inflate(R.layout.fragment_issuance_input_resident_num, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleTv = (TextView) view.findViewById(R.id.title);
        titleTv.setText(R.string.issuance_input_resident_num_title);
        view.findViewById(R.id.sub_title).setVisibility(View.GONE);

        view.findViewById(R.id.btn_go_main).setOnClickListener(this);
        view.findViewById(R.id.btn_go_back).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);

        mLeftTextView = (TextView) view.findViewById(R.id.left_text);
        mRightTextView = (TextView) view.findViewById(R.id.right_text);

        view.findViewById(R.id.btn_0).setOnClickListener(this);
        view.findViewById(R.id.btn_1).setOnClickListener(this);
        view.findViewById(R.id.btn_2).setOnClickListener(this);
        view.findViewById(R.id.btn_3).setOnClickListener(this);
        view.findViewById(R.id.btn_4).setOnClickListener(this);
        view.findViewById(R.id.btn_5).setOnClickListener(this);
        view.findViewById(R.id.btn_6).setOnClickListener(this);
        view.findViewById(R.id.btn_7).setOnClickListener(this);
        view.findViewById(R.id.btn_8).setOnClickListener(this);
        view.findViewById(R.id.btn_9).setOnClickListener(this);
        view.findViewById(R.id.btn_backspace).setOnClickListener(this);
        view.findViewById(R.id.btn_clear).setOnClickListener(this);

        ((IssuanceMainActivity) getActivity()).setGuideMsg(getString(R.string.issuance_guide_msg1));

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        if(view.getId() == R.id.btn_ok) {
            ((IssuanceMainActivity) getActivity()).addFragmentStack(new IssuanceNotifyFragment(),IssuanceNotifyFragment.ARG_FINGERPRINT);
        }
        else if(view.getTag() != null) {
            StringBuilder sb = new StringBuilder();
            String key = view.getTag().toString();
            String leftTxt = mLeftTextView.getText().toString();
            String rightTxt = mRightTextView.getText().toString();
            sb.append(leftTxt + rightTxt);

            switch (key) {
                case IssuanceMainActivity.BUTTON_BACKSPACE:
                    if(sb.length() > 0){
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    break;
                case IssuanceMainActivity.BUTTON_CLEAR:
                    sb.setLength(0);
                    break;
                default:
                    if(sb.length() <13) {
                        sb.append(key);
                    }
                    break;
            }

            if(sb.length() > 6) {
                mLeftTextView.setText(sb.substring(0, 6));
                mRightTextView.setText(sb.substring(6));
            } else {
                mLeftTextView.setText(sb.toString());
                mRightTextView.setText("");
            }

        }
    }
}