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

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.HospitalPatientListAdapter;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.model.PatientModel;
import com.avad.humancare.kiosk.util.Common;
import com.avad.humancare.kiosk.util.Preference;
import com.avad.humancare.kiosk.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommonSubSelectPatientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommonSubSelectPatientFragment extends SubBaseFragment {
    private static final String TAG = "selectpatient" ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    SimpleDateFormat dateFormat = new SimpleDateFormat("( yyyy/MM/dd )");
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private HospitalPatientListAdapter adapter;
    private ArrayList<PatientModel> patientList;
    public CommonSubSelectPatientFragment() {
        // Required empty public constructor
        patientList = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommonSubSelectPatientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommonSubSelectPatientFragment newInstance(String param1, String param2) {
        CommonSubSelectPatientFragment fragment = new CommonSubSelectPatientFragment();
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
        initPatientData();
    }
    //환자 데이터 2건만 다른 성별로 생성
    private void initPatientData(){
        if(patientList != null) patientList.clear();
        //1st
        PatientModel item = new PatientModel();
        item.setName(Preference.getElderName(getContext()));
        item.setSex(Preference.getElderSex(getContext()));
        item.setLastVisitDate(initLastVisitDate());
        item.setChecked(true);
        //2nd
        PatientModel anotherItem = new PatientModel();
        anotherItem.setSex(item.getSex().equals("M")? "F" : "M");
        if(anotherItem.getSex().equals("M")){
            anotherItem.setName(getString(R.string.hospital_sub_temp_patient_name_male_name));
        }else{
            anotherItem.setName(getString(R.string.hospital_sub_temp_patient_name_female_name));
        }
        anotherItem.setLastVisitDate(initLastVisitDate());
        patientList.add(item);
        patientList.add(anotherItem);
    }
    private String initLastVisitDate(){
        Date date = new Date();
        Calendar cal =  new GregorianCalendar();
        cal.setTime(date);
        Log.e(TAG, "date = " + date);
//        cal.add(GregorianCalendar.MONTH, Utils.getRandomNumber(0, cal.get(GregorianCalendar.MONTH) - 1) * -1); // random
        cal.add(GregorianCalendar.DATE, Utils.getRandomNumber(0, 100) * -1); // random
        return  dateFormat.format(cal.getTime());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_common_sub_select_patient, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llManager);
        Log.e(TAG, "patientlist size = " + patientList.size());
        adapter = new HospitalPatientListAdapter(getContext(), patientList, new HospitalPatientListAdapter.OnItemClickEventListener() {
            @Override
            public void OnItemClick(View view, int position) {
                patientList.stream().forEach(item -> item.setChecked(false));
                patientList.get(position).setChecked(true);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(recyclerView.findViewHolderForAdapterPosition(0) != null) {
            recyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
        }
    }

    @Override
    public void NavigateNextFragment() {
        PatientModel selItem = patientList.stream().filter(t->t.isChecked()).findFirst().get();
        ((HospitalFragmentActivity) getActivity()).setSelectedPatient(selItem);
        int mainMenu = ((HospitalFragmentActivity)getActivity()).getSelectedMenu();
        switch (mainMenu){
            case Common.MENU_RECEIPT:
                ((HospitalFragmentActivity) getActivity()).addFragmentStack(new ReceiptSubConfirmedFragment(), "confirm");
                break;
            case Common.MENU_PAYMENT:
                //not used
                break;
            case Common.MENU_RECORDS:
                ((HospitalFragmentActivity) getActivity()).addFragmentStack(new RecordSubDiagnosisSearchFragment(), "search");
                break;
            case Common.MENU_INSURANCE:
                break;
        }
    }

    @Override
    public void NavigatePrevFragment() {

    }
}