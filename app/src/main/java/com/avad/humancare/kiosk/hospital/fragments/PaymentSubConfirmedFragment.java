package com.avad.humancare.kiosk.hospital.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentSubConfirmedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentSubConfirmedFragment extends SubBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private int _diagnosisCount = 0;

    private TextView tvMainNotice, tvReceiptCount, tvPrescriptionCount;
    public PaymentSubConfirmedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PaymentSubConfirmedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentSubConfirmedFragment newInstance(int diagnosisCount) {
        PaymentSubConfirmedFragment fragment = new PaymentSubConfirmedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, diagnosisCount);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _diagnosisCount = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_sub_confirmed, container, false);
        tvMainNotice = view.findViewById(R.id.tv_notice_main);
        tvReceiptCount = view.findViewById(R.id.tv_receipt_count);
        tvPrescriptionCount = view.findViewById(R.id.tv_prescription_count);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(tvMainNotice != null){
            tvMainNotice.setText(getString(R.string.hospital_sub_payment_confirm_print, (_diagnosisCount + 1)));
        }
        if(tvReceiptCount != null) {
            tvReceiptCount.setText(getString(R.string.hospital_sub_payment_confirm_doc_unit, 1));
        }
        if(tvPrescriptionCount != null) {
            tvPrescriptionCount.setText(getString(R.string.hospital_sub_payment_confirm_doc_unit, _diagnosisCount));
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