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
 * Use the {@link IssuanceCertificateSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssuanceCertificateSelectFragment extends IssuanceBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String ARG_CERTIFICATE_SELECT = "certi_select";
    public static final String ARG_RESIDENT_NUM_SELECT = "resident_num_select";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IssuanceCertificateSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IssuanceCertificateSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssuanceCertificateSelectFragment newInstance(String param1, String param2) {
        IssuanceCertificateSelectFragment fragment = new IssuanceCertificateSelectFragment();
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
        return inflater.inflate(R.layout.fragment_issuance_certificate_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleTv = (TextView) view.findViewById(R.id.title);
        TextView subTitleTv = (TextView) view.findViewById(R.id.sub_title);

        view.findViewById(R.id.btn_go_main).setOnClickListener(this);
        view.findViewById(R.id.btn_go_back).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setVisibility(View.GONE);

        ViewGroup statusKindView = view.findViewById(R.id.status_kind);
        ViewGroup statusResidentNumberView = view.findViewById(R.id.status_resident_number);

        if(mParam1 != null && mParam1.equals(ARG_RESIDENT_NUM_SELECT)) {
            titleTv.setText(R.string.issuance_certificate_select_title2);
            subTitleTv.setVisibility(View.GONE);

            statusKindView.setVisibility(View.GONE);
            statusResidentNumberView.setVisibility(View.VISIBLE);

            view.findViewById(R.id.number_include).setOnClickListener(this);
            view.findViewById(R.id.number_exclude).setOnClickListener(this);
        }
        else {
            // ARG_CERTIFICATE_SELECT
            titleTv.setText(R.string.issuance_certificate_select_title);
            subTitleTv.setText(R.string.issuance_certificate_select_subtitle);

            statusKindView.setVisibility(View.VISIBLE);
            statusResidentNumberView.setVisibility(View.GONE);

            view.findViewById(R.id.basic_layout).setOnClickListener(this);
            view.findViewById(R.id.detail_layout).setOnClickListener(this);

        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.basic_layout :
            case R.id.detail_layout :
                ((IssuanceMainActivity) getActivity()).addFragmentStack(IssuanceNotifyFragment.newInstance(IssuanceNotifyFragment.ARG_FAMILY_2, ""),IssuanceNotifyFragment.ARG_FAMILY_2);
                break;

            case R.id.number_include:
            case R.id.number_exclude:
                ((IssuanceMainActivity) getActivity()).addFragmentStack(new IssuanceKeypadFragment(),"keypad");
                break;
        }
    }
}