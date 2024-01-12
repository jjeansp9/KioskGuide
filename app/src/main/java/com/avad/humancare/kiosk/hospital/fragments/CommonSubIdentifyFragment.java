package com.avad.humancare.kiosk.hospital.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.util.Common;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommonSubIdentifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommonSubIdentifyFragment extends SubBaseFragment {
    private static final String TAG = "identify";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String BUTTON_BACKSPACE = "BACKSPACE";
    public static final String BUTTON_CLEAR = "CLEAR";

    private static final String IDENTIFY_TYPE_PHONE_NO = "phone";
    private static final String IDENTIFY_TYPE_REGISTRATION_NO = "registration";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TabLayout _tabLayout;   //tab
    private PartialRegistrationNoView registrationNoView;
    private PartialPhoneNoView phoneNoView;
    private CheckBox cbAcceptPrivateInfo;

    public CommonSubIdentifyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommonSubIdentifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommonSubIdentifyFragment newInstance(String param1, String param2) {
        CommonSubIdentifyFragment fragment = new CommonSubIdentifyFragment();
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
        View view = inflater.inflate(R.layout.fragment_common_sub_identify, container, false);

        phoneNoView = view.findViewById(R.id.phoneview);
        registrationNoView = view.findViewById(R.id.registrationview);
        cbAcceptPrivateInfo = view.findViewById(R.id.checkbox_accept_private_info);
        _tabLayout = (TabLayout) view.findViewById(R.id.tab_select);
        _tabLayout.addTab(_tabLayout.newTab().setText(getString(R.string.hospital_sub_common_identify_type_phone_no)).setTag(IDENTIFY_TYPE_PHONE_NO));
        _tabLayout.addTab(_tabLayout.newTab().setText(getString(R.string.hospital_sub_common_identify_type_registration_no)).setTag(IDENTIFY_TYPE_REGISTRATION_NO));
        _tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getTag().equals(IDENTIFY_TYPE_PHONE_NO)){
                    phoneNoView.setVisibility(View.VISIBLE);
                    phoneNoView.clearNumber();
                }else if(tab.getTag().equals(IDENTIFY_TYPE_REGISTRATION_NO)){
                    registrationNoView.setVisibility(View.VISIBLE);
                    registrationNoView.clear();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getTag().equals(IDENTIFY_TYPE_PHONE_NO)){
                    phoneNoView.setVisibility(View.GONE);
                }else if(tab.getTag().equals(IDENTIFY_TYPE_REGISTRATION_NO)){
                    registrationNoView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TabLayout.Tab tab = _tabLayout.getTabAt(0);
        if(tab != null) tab.select();
    }

    @Override
    public void onClick(View view) {
        if(_tabLayout == null) return;
        if(view.getTag() == null) return;
        int tab_position = _tabLayout.getSelectedTabPosition();
        if(tab_position == 0){  //phoneNo
            if(phoneNoView != null){
                phoneNoView.inputNumber(view.getTag().toString());
            }else{
                Log.e(TAG, "phoneview is null");
            }
        }else if(tab_position == 1){    //registrationNo
            if(registrationNoView != null){
                registrationNoView.inputNumber(view.getTag().toString());
            }else{
                Log.e(TAG, "registrationview is null");
            }
        }
    }
    private boolean checkValidData(){
        if(_tabLayout == null) {
            Log.e(TAG, "tablayout is null");
            return false;
        }

        //check input value
        int tab_position = _tabLayout.getSelectedTabPosition();
        if(tab_position == 0){  //phoneNo
            if(phoneNoView != null && phoneNoView.isValidData()){
//                return true;
            }else{
                showNormalDialog(getString(R.string.common_dialog_title_error), getString(R.string.hospital_error_msg_wrong_phone_no));
                return false;

            }
        }else if(tab_position == 1){    //registrationNo
            if(registrationNoView != null && registrationNoView.isValidData()){
//                return true;
            }else{
                showNormalDialog(getString(R.string.common_dialog_title_error), getString(R.string.hospital_error_msg_wrong_registration_no));
                return false;
            }
        }
        //check accept private info
        if(cbAcceptPrivateInfo != null && cbAcceptPrivateInfo.isChecked()){
//            return true;
        }else{
            showNormalDialog(getString(R.string.common_dialog_title_error), getString(R.string.hospital_error_msg_not_access_private_info));
            return false;
        }
        return true;
    }
    @Override
    public void NavigateNextFragment() {
        if(checkValidData()){
            //next
            int mainMenu = ((HospitalFragmentActivity)getActivity()).getSelectedMenu();
            switch (mainMenu){
                case Common.MENU_RECEIPT:
                    ((HospitalFragmentActivity) getActivity()).addFragmentStack(new CommonSubSelectPatientFragment(), "select");
                    break;
                case Common.MENU_PAYMENT:
                    ((HospitalFragmentActivity) getActivity()).addFragmentStack(new PaymentSubConfirmToPayFragment(), "confirm");
                    break;
                case Common.MENU_RECORDS:
                    ((HospitalFragmentActivity) getActivity()).addFragmentStack(new CommonSubSelectPatientFragment(), "select");
                    break;
                case Common.MENU_INSURANCE:
                    break;
            }
        }
    }

    @Override
    public void NavigatePrevFragment() {

    }
}