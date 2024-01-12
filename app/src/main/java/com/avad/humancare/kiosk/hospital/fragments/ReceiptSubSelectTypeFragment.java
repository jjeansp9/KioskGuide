package com.avad.humancare.kiosk.hospital.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFooterView;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.util.Common;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceiptSubSelectTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiptSubSelectTypeFragment extends SubBaseFragment {
    private static final String TAG = "SelectTypeFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReceiptSubSelectTypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceiptSubSelectTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceiptSubSelectTypeFragment newInstance(String param1, String param2) {
        ReceiptSubSelectTypeFragment fragment = new ReceiptSubSelectTypeFragment();
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
        final View view =  inflater.inflate(R.layout.fragment_receipt_sub_select_type, container, false);
        view.findViewById(R.id.card_ticket).setOnClickListener(this);
        view.findViewById(R.id.card_identify).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.card_ticket:
                Log.e(TAG, "card ticket");
                ((HospitalFragmentActivity) getActivity()).addFragmentStack(new ReceiptSubTicketFragment(), "ticket");

//                getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
//                        .add(new ReceiptSubTicketFragment(), "ticket")
//                        .addToBackStack("ticket").commit();
                break;
            case R.id.card_identify:
                ((HospitalFragmentActivity) getActivity()).addFragmentStack(new CommonSubIdentifyFragment(), "identify");
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BACK);
    }

    @Override
    public void NavigateNextFragment() {
        //no caller
    }

    @Override
    public void NavigatePrevFragment() {

    }
}