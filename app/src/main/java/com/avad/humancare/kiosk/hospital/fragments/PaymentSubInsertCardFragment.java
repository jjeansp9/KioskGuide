package com.avad.humancare.kiosk.hospital.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentSubInsertCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentSubInsertCardFragment extends SubBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int _diagnosisCount;

    public PaymentSubInsertCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PaymentSubInsertCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentSubInsertCardFragment newInstance(int diagnosisCount) {
        PaymentSubInsertCardFragment fragment = new PaymentSubInsertCardFragment();
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
        return inflater.inflate(R.layout.fragment_payment_sub_insert_card, container, false);
    }

    @Override
    public void NavigateNextFragment() {
        ((HospitalFragmentActivity) getActivity()).addFragmentStack(PaymentSubConfirmedFragment.newInstance(_diagnosisCount), "confirm");
    }

    @Override
    public void NavigatePrevFragment() {

    }
}