package com.avad.humancare.kiosk.hospital.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.HospitalPatientListAdapter;
import com.avad.humancare.kiosk.adapter.HospitalPayHistoryListAdapter;
import com.avad.humancare.kiosk.adapter.HospitalRecordHistoryListAdapter;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.model.PaymentItemModel;
import com.avad.humancare.kiosk.util.DateUtil;
import com.avad.humancare.kiosk.util.Utils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordSubDiagnosisSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordSubDiagnosisSelectFragment extends SubBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CHECK_OUTPATIENT = "outpatient";
    private static final String ARG_CHECK_HOSPITALIZATION = "hospitalization";
    private static final String ARG_START_DATE = "start_date";
    private static final String ARG_END_DATE = "end_date";
    private static final String TAG = "recordSelect";
    // TODO: Rename and change types of parameters
    private boolean checkOutPatient, checkHospitalization;
    private String startDateStr, endDateStr;
    private Date startDate, endDate;

    private RecyclerView recyclerView;
    private HospitalRecordHistoryListAdapter adapter;
    private ArrayList<PaymentItemModel> diagnosisList;
    public RecordSubDiagnosisSelectFragment() {
        // Required empty public constructor
        diagnosisList = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param outpatient 외래여부.
     * @param hospitalization 입원여부.
     * @param startDateStr  시작일
     * @param endDateStr    종료일
     * @return A new instance of fragment RecordSubDiagnosisSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordSubDiagnosisSelectFragment newInstance(boolean outpatient, boolean hospitalization, String startDateStr, String endDateStr) {
        RecordSubDiagnosisSelectFragment fragment = new RecordSubDiagnosisSelectFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_CHECK_OUTPATIENT, outpatient);
        args.putBoolean(ARG_CHECK_HOSPITALIZATION, hospitalization);
        args.putString(ARG_START_DATE, startDateStr);
        args.putString(ARG_END_DATE, endDateStr);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            checkOutPatient = getArguments().getBoolean(ARG_CHECK_OUTPATIENT);
            checkHospitalization = getArguments().getBoolean(ARG_CHECK_HOSPITALIZATION);
            startDateStr = getArguments().getString(ARG_START_DATE);
            try {
                startDate = DateUtil.DATE_FORMAT_YYYY_MM_WITH_LETTER.parse(startDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            endDateStr = getArguments().getString(ARG_END_DATE);
            try{
                endDate = DateUtil.DATE_FORMAT_YYYY_MM_WITH_LETTER.parse(endDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            initDiagnosisData();
        }
    }
    private void initDiagnosisData(){
//        int temp = 0;
//        int op_outpatient = (checkOutPatient)? 0x01 : 0x00;
//        int op_hospitalization = (checkHospitalization)? 0x02 : 0x00;
//        int result = op_outpatient | temp | op_hospitalization;
//        Log.e(TAG, "result =" + result);
        int randomCount = Utils.getRandomNumber(4,10);
        int typeMin = 0;
        int typeMax = 0;
        if(checkOutPatient){
            typeMin = 1;
            if(checkHospitalization){
                typeMax = 2;
            }else{
                typeMax = 1;
            }
        }else {
            if(checkHospitalization){
                typeMin = 2;
                typeMax = 2;
            }else{
                //no case
            }
        }
        LocalDate localStartDate = startDate.toInstant() // Date -> Instant
                .atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
                .toLocalDate(); // ZonedDateTime -> LocalDateTime
        LocalDate localEndDate = endDate.toInstant() // Date -> Instant
                .atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
                .toLocalDate(); // ZonedDateTime -> LocalDateTime
        Period diff = Period.between(localStartDate, localEndDate);
        long day = ChronoUnit.DAYS.between(localStartDate, localEndDate);
        final int DATE_DIFF = 14;
        int maxday = (int)(day - DATE_DIFF);
        Log.e(TAG, "type min = " + typeMin + " /type max=" + typeMax);
        String[] departmentArr = getResources().getStringArray(R.array.array_hospital_departments);
        for(int i = 0; i < randomCount; i++){
            PaymentItemModel item = new PaymentItemModel();
            item.setType(Utils.getRandomNumber(typeMin, typeMax));
            Log.e(TAG, "gettype = " + item.getType());
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.DATE, Utils.getRandomNumber(1, maxday));
            item.setDate(DateUtil.DATE_FORMAT_YYYY_MM_DD.format(cal.getTime()));
            cal.add(Calendar.DATE, Utils.getRandomNumber(1, DATE_DIFF));
            item.setEndDate(DateUtil.DATE_FORMAT_YYYY_MM_DD.format(cal.getTime()));
            item.setDepartment(departmentArr[Utils.getRandomNumber(0, departmentArr.length - 1)]);
            diagnosisList.add(item);
        }
        diagnosisList.sort(Comparator.comparing(PaymentItemModel::getDate));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_sub_diagnosis_select, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llManager);
        Log.e(TAG, "diagnosisList size = " + diagnosisList.size());
        adapter = new HospitalRecordHistoryListAdapter(getContext(), diagnosisList, new HospitalPatientListAdapter.OnItemClickEventListener() {
            @Override
            public void OnItemClick(View view, int position) {
                PaymentItemModel item = diagnosisList.get(position);
                item.setSelected(!item.isSelected());
                adapter.notifyItemChanged(position);
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }
    private int checkedListCount(){
        int count = (int)diagnosisList.stream().filter(f -> f.isSelected()).count();
        return count;
    }
    private boolean checkValid(int count){

        if(count == 0){
            showNormalDialog(getString(R.string.common_dialog_title_error), getString(R.string.hospital_error_msg_wrong_history_check));
            return false;
        }
        return true;
    }
    @Override
    public void NavigateNextFragment() {
        int checkedCount = checkedListCount();
        if(checkValid(checkedCount)) {
            ((HospitalFragmentActivity) getActivity()).addFragmentStack(
                    RecordSubConfirmedFragment.newInstance(checkedCount)
                    , "confirm");
        }
    }

    @Override
    public void NavigatePrevFragment() {

    }
}