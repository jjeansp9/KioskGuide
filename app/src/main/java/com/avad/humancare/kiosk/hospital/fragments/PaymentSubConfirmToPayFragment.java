package com.avad.humancare.kiosk.hospital.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.HospitalPayHistoryListAdapter;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.model.PaymentItemModel;
import com.avad.humancare.kiosk.util.DateUtil;
import com.avad.humancare.kiosk.util.Preference;
import com.avad.humancare.kiosk.util.Utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentSubConfirmToPayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentSubConfirmToPayFragment extends SubBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "paymentconfirm";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvPatientId, tvPatientName, tvTotalAmount;
    private RecyclerView recyclerView;
    private HospitalPayHistoryListAdapter adapter;
    private ArrayList<PaymentItemModel> paymentList;

    public PaymentSubConfirmToPayFragment() {
        // Required empty public constructor
        paymentList = new ArrayList<>();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentSubConfirmToPayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentSubConfirmToPayFragment newInstance(String param1, String param2) {
        PaymentSubConfirmToPayFragment fragment = new PaymentSubConfirmToPayFragment();
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
        initPaymentData();
    }
    private void initPaymentData(){
        if(paymentList != null) paymentList.clear();
        String[] departmentArr = getResources().getStringArray(R.array.array_hospital_departments);
        String[] doctorArr = getResources().getStringArray(R.array.array_hospital_doctor);
        int randomCount = Utils.getRandomNumber(1, 4);
        for(int i = 0; i < randomCount; i++) {
            int randomIndex = Utils.getRandomNumber(0, departmentArr.length - 1);
            PaymentItemModel item = new PaymentItemModel();
            item.setDate(DateUtil.DATE_FORMAT_YYYY_MM_DD.format(new Date()));
            item.setDepartment(departmentArr[randomIndex]);
            if(randomIndex < doctorArr.length){
                item.setDoctor(doctorArr[randomIndex]);
            }else{
                item.setDoctor(getString(R.string.hospital_sub_temp_patient_name_male_name));
            }
            int amount = Utils.getRandomNumber(10, 200);
            item.setAmount(amount * 100);
            paymentList.add(item);
        }


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_sub_confirm_to_pay, container, false);
        tvPatientId = view.findViewById(R.id.tv_patient_id);
        tvPatientName = view.findViewById(R.id.tv_patient_name);
        tvTotalAmount = view.findViewById(R.id.tv_total_amount);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llManager);
        Log.e(TAG, "paymentList size = " + paymentList.size());
        adapter = new HospitalPayHistoryListAdapter(getContext(), paymentList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(tvPatientId != null){
            int year = Utils.getRandomNumber(0, 22);
            int seq = Utils.getRandomNumber(0, 9999);
            tvPatientId.setText(String.format("%02d%04d", year, seq));
        }
        if(tvPatientName!= null){
            tvPatientName.setText(Preference.getElderName(getContext()));
        }
        int amount = 0;
        if(tvTotalAmount != null){
            if(paymentList != null)
            {
                amount = paymentList.stream().mapToInt(PaymentItemModel::getAmount).sum();
            }
            tvTotalAmount.setText(NumberFormat.getInstance().format(amount));
        }
    }

    @Override
    public void NavigateNextFragment() {
        ((HospitalFragmentActivity) getActivity()).addFragmentStack(PaymentSubInsertCardFragment.newInstance(paymentList.size()), "insert");
    }

    @Override
    public void NavigatePrevFragment() {

    }
}