package com.avad.humancare.kiosk.hospital.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.dialog.PopupDialog;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.util.DateUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordSubDiagnosisSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordSubDiagnosisSearchFragment extends SubBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CheckBox cbOutpatient, cbHospitalization;
    private TextView tvStartDate, tvEndDate;

    public RecordSubDiagnosisSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordSubDiagnosisSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordSubDiagnosisSearchFragment newInstance(String param1, String param2) {
        RecordSubDiagnosisSearchFragment fragment = new RecordSubDiagnosisSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_record_sub_diagnosis_search, container, false);
        cbOutpatient = view.findViewById(R.id.cb_outpatient);
        cbHospitalization = view.findViewById(R.id.cb_hospitalization);
        tvStartDate = view.findViewById(R.id.tv_start_date);
        tvEndDate = view.findViewById(R.id.tv_end_date);
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_start_date:
                if(tvStartDate != null)
                    showDatePickerDialog(view.getId(), tvStartDate.getText().toString());
                break;
            case R.id.tv_end_date:
                if(tvEndDate != null)
                    showDatePickerDialog(view.getId(), tvEndDate.getText().toString());
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        tvEndDate.setText(DateUtil.DATE_FORMAT_YYYY_MM_WITH_LETTER.format(cal.getTime()));
        cal.add(Calendar.MONTH, -6);
        cal.set(Calendar.DATE, 1);
        tvStartDate.setText(DateUtil.DATE_FORMAT_YYYY_MM_WITH_LETTER.format(cal.getTime()));
    }
    private void showDatePickerDialog(int resId, String str) {
        int year = 0;
        int month = 0;
        int date = 0;
        try {
            Date selDate = DateUtil.DATE_FORMAT_YYYY_MM_WITH_LETTER.parse(str);
            Calendar cal = Calendar.getInstance();
            cal.setTime(selDate);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            date = cal.get(Calendar.DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DATE, dayOfMonth);
                //todo 시작일 < 종료일 조건 처리...귀차니즘...
                switch(resId){
                    case R.id.tv_start_date:
                        tvStartDate.setText(DateUtil.DATE_FORMAT_YYYY_MM_WITH_LETTER.format(cal.getTime()));
                        break;
                    case R.id.tv_end_date:
                        tvEndDate.setText(DateUtil.DATE_FORMAT_YYYY_MM_WITH_LETTER.format(cal.getTime()));
                        break;
                }
            }
        }, year, month, date);
//        datePickerDialog.datePicker.findViewById<View (resources.getIdentifier("day","id","android")).visibility = View.GONE
//        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        datePickerDialog.show();
    }
    private boolean checkValid(){
        if(cbOutpatient == null || cbHospitalization == null) return false;
        if(!cbOutpatient.isChecked() && !cbHospitalization.isChecked()){
            showNormalDialog(getString(R.string.common_dialog_title_error), getString(R.string.hospital_error_msg_wrong_type_check));
            return false;
        }
        if(tvStartDate == null || tvEndDate == null) return false;
        Date startDate = null, endDate = null;
        try {
            startDate = DateUtil.DATE_FORMAT_YYYY_MM_WITH_LETTER.parse(tvStartDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endDate = DateUtil.DATE_FORMAT_YYYY_MM_WITH_LETTER.parse(tvEndDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(startDate == null || endDate == null){
            showNormalDialog(getString(R.string.common_dialog_title_error), getString(R.string.hospital_error_msg_wrong_date1));
            return false;
        }
        if(startDate.after(endDate)){
            showNormalDialog(getString(R.string.common_dialog_title_error), getString(R.string.hospital_error_msg_wrong_date2));
            return false;
        }
        return true;
    }
    @Override
    public void NavigateNextFragment() {
        if(checkValid()) {
            ((HospitalFragmentActivity) getActivity()).addFragmentStack(
                    RecordSubDiagnosisSelectFragment.newInstance(cbOutpatient.isChecked(),
                            cbHospitalization.isChecked(),
                            tvStartDate.getText().toString(),
                            tvEndDate.getText().toString())
                    , "select");
        }
    }

    @Override
    public void NavigatePrevFragment() {

    }
}