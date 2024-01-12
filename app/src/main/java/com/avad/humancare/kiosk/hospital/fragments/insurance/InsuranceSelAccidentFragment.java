package com.avad.humancare.kiosk.hospital.fragments.insurance;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFooterView;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.hospital.fragments.SubBaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class InsuranceSelAccidentFragment extends SubBaseFragment {
    private static final String ARG_PARAM1 = "param1";

    public static final String ACCIDENT = "insurance_accident";
    public static final String ACCOUNT = "insurance_account";

    private int[] btnAccidentTypeId = {
            R.id.btn_type_0, R.id.btn_type_1, R.id.btn_type_2
    };
    private Button[] btnAccidentType;

    private GridView gridView;
    private InsuranceSelAccidentGridViewAdapter mAdapter;
    private TextView txtMedicalDepartment;
    private EditText txeDiagnosisName;

    private TextView txtBankType;
    private EditText txeBankReceiver;
    private EditText txeBankNum;

    private Context mContext;
    private String infoType = ACCIDENT;
    private InsuranceDataManager mManager;

    private List<HashMap<String, String>> mSelectorList = new ArrayList<HashMap<String, String>>();
    private int pagingNum = 1;

    public InsuranceSelAccidentFragment() {
        // Required empty public constructor
    }

    public static InsuranceSelAccidentFragment newInstance(String param) {
        InsuranceSelAccidentFragment fragment = new InsuranceSelAccidentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_PARAM1)) {
            infoType = getArguments().getString(ARG_PARAM1);
        }else {
            infoType = ACCIDENT;
        }
        mManager = InsuranceDataManager.getInstance();
        mContext = getContext();
//        if(mManager.isAgreeTerms == null) {
//            mManager.isAgreeTerms = new boolean[lyUseId.length];
//            for (int i = 0; i < lyUseId.length; i++) {
//                mManager.isAgreeTerms[i] = false;
//            }
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insurance_sel_accident, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        ((LinearLayout) v.findViewById(R.id.ly_prev)).setOnClickListener(this);
        ((LinearLayout) v.findViewById(R.id.ly_next)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.ly_keyboard)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.ly_keyboard_2)).setOnClickListener(this);

        ((LinearLayout) v.findViewById(R.id.ly_accident)).setVisibility(
                (!TextUtils.isEmpty(infoType) && infoType.equals(ACCIDENT))?View.VISIBLE:View.GONE
        );
        ((LinearLayout) v.findViewById(R.id.ly_bank)).setVisibility(
                (!TextUtils.isEmpty(infoType) && infoType.equals(ACCOUNT))?View.VISIBLE:View.GONE
        );
        if(!TextUtils.isEmpty(infoType) && infoType.equals(ACCIDENT)) {
            btnAccidentType = new Button[btnAccidentTypeId.length];
            for (int i = 0; i < btnAccidentTypeId.length; i++) {
                btnAccidentType[i] = (Button) v.findViewById(btnAccidentTypeId[i]);
                btnAccidentType[i].setText(InsuranceDataManager.arrAccidentType[i]);
                int j = i;
                btnAccidentType[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideKeyBoard();
                        settingAccidentTypeButton(j);
                    }
                });
            }
            settingAccidentTypeButton(mManager.accidentType);

            txtMedicalDepartment = (TextView) v.findViewById(R.id.txt_medical_department);
            txeDiagnosisName = (EditText) v.findViewById(R.id.txe_diagnosis_name);
            txeDiagnosisName.setText(mManager.diagnosisName);
        }else if(!TextUtils.isEmpty(infoType) && infoType.equals(ACCOUNT)) {
            txtBankType = (TextView) v.findViewById(R.id.txt_bank_type);
            txeBankReceiver = (EditText) v.findViewById(R.id.txe_receiver_name);
            txeBankNum = (EditText) v.findViewById(R.id.txe_bank_num);

            txeBankReceiver.setText(mManager.receiverNm);
            txeBankNum.setText(mManager.bankNum);
        }
        mAdapter = new InsuranceSelAccidentGridViewAdapter(mContext, mSelectorList);
        gridView = (GridView) v.findViewById(R.id.gv_selector);
        if(mAdapter != null) {
            gridView.setAdapter(mAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    hideKeyBoard();
                    if(mSelectorList.get(i).containsKey("index")) {
                        try {
                            int index = Integer.parseInt(Objects.requireNonNull(mSelectorList.get(i).get("index")));
                            if(infoType.equals(ACCIDENT)) {
                                mManager.medicalDepartment = index;
                            }else if(infoType.equals(ACCOUNT)) {
                                mManager.bankType = index;
                            }
                        }catch (Exception e) {
                            Log.e("InsuranceSelAccidentFragment", "gridView >> "+Log.getStackTraceString(e));
                        }finally {
                            reloadAllList(0);
                        }
                    }
                }
            });
            reloadAllList(0);
        }
    }

    @Override
    public void onClick(View view) {
        hideKeyBoard();
        switch (view.getId()) {
            case R.id.ly_prev: reloadAllList(-1); break;
            case R.id.ly_next: reloadAllList(1); break;
            case R.id.ly_keyboard:
            case R.id.ly_keyboard_2:
            {
//                hideKeyBoard();
                break;
            }
        }
    }

    public void settingAccidentTypeButton(int index) {
        if(index > -1) {
            mManager.accidentType = index;
        }

        for (int i = 0; i < btnAccidentTypeId.length; i++) {
            if(mManager.accidentType == i) btnAccidentType[i].setSelected(true);
            else btnAccidentType[i].setSelected(false);
        }
    }

    public void reloadAllList(int add) {
        if(getActivity() != null) getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] names;
                int selected = -1;
                if(infoType.equals(ACCOUNT)) {
                    names = InsuranceDataManager.arrBank;
                    selected = mManager.bankType;
                    txtBankType.setText(mManager.bankType>=0?names[mManager.bankType]:"");
                }else {
                    names = InsuranceDataManager.arrDepartment;
                    selected = mManager.medicalDepartment;
                    txtMedicalDepartment.setText(mManager.medicalDepartment>=0?names[mManager.medicalDepartment]:"");
                }

                int maxPage = (int) Math.ceil(names.length / 9.0);
                int curPage = pagingNum + add;

                if(add != 0) {
                    if(add > 0 && maxPage < curPage) {
//                Toast.makeText(mContext, "다음내용없음", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(add < 0 && curPage == 0) {
//                Toast.makeText(mContext, "이전내용없음", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                pagingNum = curPage;

                mSelectorList.clear();
                int lastLength = Math.min((pagingNum * 9), names.length);
                for(int i = ((pagingNum-1) * 9); i < lastLength; i++) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("name", names[i]);
                    data.put("isChecked", (selected == i)?"Y":"N");
                    data.put("index", String.valueOf(i));
                    mSelectorList.add(data);
                }

                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BOTH);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BOTH);
    }

    @Override
    public void NavigateNextFragment() {
        hideKeyBoard();

        saveTextToEditText();

        if(infoType.equals(ACCIDENT)) {
            if(mManager.accidentType == -1) {
                showNormalDialog("안내", "사고유형을 선택해주세요.");
            }else if(mManager.medicalDepartment == -1) {
                showNormalDialog("안내", "진료과를 선택해주세요.");
            }else if(TextUtils.isEmpty(mManager.diagnosisName)) {
                showNormalDialog("안내", "진단명을 입력해주세요.");
            }else {
                if(mManager.receiverType.equals("Y")) {
                    ((HospitalFragmentActivity) getActivity()).addFragmentStack(
                            InsuranceInfoFragment.newInstance(InsuranceInfoFragment.CONFIRM),
                            InsuranceInfoFragment.CONFIRM
                    );
                }else if(mManager.receiverType.equals("N")) {
                    ((HospitalFragmentActivity) getActivity()).addFragmentStack(
                            InsuranceSelAccidentFragment.newInstance(InsuranceSelAccidentFragment.ACCOUNT),
                            InsuranceSelAccidentFragment.ACCOUNT
                    );
                }
            }
        }else if(infoType.equals(ACCOUNT)) {
            if(mManager.bankType == -1) {
                showNormalDialog("안내", "은행을 선택해주세요.");
            }else if(TextUtils.isEmpty(mManager.receiverNm) || mManager.receiverNm.length() < 2) {
                showNormalDialog("안내", "예금주를 입력해주세요");
            }else if(TextUtils.isEmpty(mManager.bankNum) || mManager.bankNum.length() < 11) {
                showNormalDialog("안내", "계좌번호를 입력해주세요.");
            }else {
                ((HospitalFragmentActivity) getActivity()).addFragmentStack(
                        InsuranceInfoFragment.newInstance(InsuranceInfoFragment.CONFIRM),
                        InsuranceInfoFragment.CONFIRM
                );
            }
        }
    }

    @Override
    public void NavigatePrevFragment() {
        hideKeyBoard();

        saveTextToEditText();
    }

    public void saveTextToEditText() {
        if(infoType.equals(ACCIDENT)) {
            mManager.diagnosisName = txeDiagnosisName.getText().toString().trim();
        }else if(infoType.equals(ACCOUNT)) {
            mManager.receiverNm = txeBankReceiver.getText().toString().trim();
            mManager.bankNum = txeBankNum.getText().toString().trim();
        }
    }

    private void hideKeyBoard() {
//        if(getActivity().getCurrentFocus().getWindowToken() == null) return;
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(infoType.equals(ACCIDENT)) {
            imm.hideSoftInputFromWindow(txeDiagnosisName.getWindowToken(), 0);
            txeDiagnosisName.clearFocus();
        }else if(infoType.equals(ACCOUNT)) {
            imm.hideSoftInputFromWindow(txeBankNum.getWindowToken(), 0);
            txeBankReceiver.clearFocus();
            txeBankNum.clearFocus();
        }
    }

    private static class InsuranceSelAccidentGridViewAdapter extends BaseAdapter {
        private Context mContext;
        private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();

        public InsuranceSelAccidentGridViewAdapter(Context context, List<HashMap<String, String>> list) {
            this.mContext = context;
            this.mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null) {
                holder = new ViewHolder();
                view = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_insurance_selector_item, null);

                holder.lyMain = (LinearLayout) view.findViewById(R.id.ly_main);
                holder.txtName = (TextView) view.findViewById(R.id.txt_name);
                view.setTag(holder);

            } else {
                holder =(ViewHolder) view.getTag();
            }

            HashMap<String, String> info = mList.get(i);

            holder.txtName.setText(info.get("name"));
            holder.lyMain.setSelected(info.get("isChecked").equals("Y"));

            return view;
        }

        class ViewHolder {
            LinearLayout lyMain;
            TextView txtName;
        }
    }
}