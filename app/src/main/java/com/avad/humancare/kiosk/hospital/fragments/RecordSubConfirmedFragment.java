package com.avad.humancare.kiosk.hospital.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFooterView;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordSubConfirmedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordSubConfirmedFragment extends SubBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COUNT = "count";

    // TODO: Rename and change types of parameters
    private int listCount;
    private TextView tvMainNotice, tvDiagnosisCount;
    public RecordSubConfirmedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param count 선택한 진료내역 count
     * @return A new instance of fragment RecordSubConfirmedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordSubConfirmedFragment newInstance(int count) {
        RecordSubConfirmedFragment fragment = new RecordSubConfirmedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNT, count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listCount = getArguments().getInt(ARG_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_sub_confirmed, container, false);
        tvMainNotice = view.findViewById(R.id.tv_notice_main);
        tvDiagnosisCount = view.findViewById(R.id.tv_diagnosis_count);
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.NEXT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(tvMainNotice != null){
            tvMainNotice.setText(getString(R.string.hospital_sub_payment_confirm_print, listCount));
        }
        if(tvDiagnosisCount != null) {
            tvDiagnosisCount.setText(getString(R.string.hospital_sub_payment_confirm_doc_unit, listCount));
        }
    }

    @Override
    public void NavigateNextFragment() {
        ((HospitalFragmentActivity) getActivity()).finish();
    }

    @Override
    public void NavigatePrevFragment() {

    }
}