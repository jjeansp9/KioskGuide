package com.avad.humancare.kiosk.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.fragments.CommonSubIdentifyFragment;
import com.avad.humancare.kiosk.hospital.fragments.ReceiptSubSelectTypeFragment;
import com.avad.humancare.kiosk.hospital.fragments.SubBaseFragment;
import com.avad.humancare.kiosk.hospital.fragments.insurance.InsuranceDataManager;
import com.avad.humancare.kiosk.hospital.fragments.insurance.InsuranceInfoFragment;
import com.avad.humancare.kiosk.model.PatientModel;
import com.avad.humancare.kiosk.util.Common;

public class HospitalFragmentActivity extends HospitalBaseActivity{
    private static final String TAG = "Fragments";
    private SubBaseFragment _fragment;
    private int selectedMenu = 0;
    private FragmentManager _fragmentManager;
    private HospitalFooterView _footer;
    private PatientModel selectedPatient;

    @Override
    public void onBackPressed() {
        if(_footer != null && _footer.ShowType == HospitalFooterView.SHOWTYPE.NONE) return;

        finishFragmentActivity();
    }

    public void finishFragmentActivity() {
        if(_fragmentManager != null && _fragmentManager.getBackStackEntryCount() > 0){
            if(_fragment != null)
                _fragment.NavigatePrevFragment();
            _fragmentManager.popBackStack();
        }else{
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_fragment);
        _fragmentManager = getSupportFragmentManager();
        _fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fr = _fragmentManager.findFragmentById(R.id.fragments);
                if(fr != null) _fragment = (SubBaseFragment) fr;
                Log.e(TAG, "fragment is " + _fragment.getClass().getName());
            }
        });
        _footer = findViewById(R.id.footer);
        _footer.setNavigateButtonListener(new NavigateButtonListener() {
            @Override
            public void OnBackNavigated() {
                finishFragmentActivity();
            }
            @Override
            public void OnNextNavigated() {
                Log.e(TAG, "fragment is " + _fragment.getClass().getName());
                if(_fragment != null)
                    _fragment.NavigateNextFragment();
            }
        });
        Intent intent = getIntent();
        if(intent.hasExtra("MENU")){
            int menu = intent.getIntExtra("MENU", 0);
            setSelectedMenu(menu);
        }
        Log.e(TAG, "selectedMenu=" + selectedMenu);
        if(savedInstanceState == null || _fragment == null){
            switch(selectedMenu){
                case Common.MENU_RECEIPT:
                    _fragment = new ReceiptSubSelectTypeFragment();
                    _fragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragments, _fragment, "receipt").commit();
                    break;
                case Common.MENU_PAYMENT:
                    _fragment = new CommonSubIdentifyFragment();
                    _fragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragments, _fragment, "payment").commit();
                    break;
                case Common.MENU_RECORDS:
                    _fragment = new CommonSubIdentifyFragment();
                    _fragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragments, _fragment, "records").commit();
                    break;
                case Common.MENU_INSURANCE:
                    InsuranceDataManager.clearInsuranceDataManager();
                    _fragment = InsuranceInfoFragment.newInstance(InsuranceInfoFragment.INTRO);
                    _fragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragments, _fragment, "insurance").commit();
                    break;
            }
        }
    }

    /**
     * 다음 Fragment 추가 (backstack에도 추가)
     * @param fragment
     * @param tag
     */
    public void addFragmentStack(SubBaseFragment fragment, String tag){
//        _fragment = fragment;
        setNavigateButtonEnabled(false);
        _fragmentManager.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.fragments, fragment, tag)
                .addToBackStack(tag).commit();
    }

    public void popBackFragmentStack(){

    }

    /**
     *  Set Footer Enabled
     * @param flag
     */
    public void setNavigateButtonEnabled(boolean flag){
        if(_footer != null){
            _footer.setEnabled(flag);
        }
    }

    /**
     * Set Footer Type
     * @param showtype BOTH, BACK, NEXT
     */
    public void setNavigateButtonLayout(HospitalFooterView.SHOWTYPE showtype){
        if(_footer != null){
            _footer.setButtonLayout(showtype);
        }
    }

    public int getSelectedMenu() {
        return selectedMenu;
    }
    public void setSelectedMenu(int selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    public PatientModel getSelectedPatient() {
        return selectedPatient;
    }
    public void setSelectedPatient(PatientModel selectedPatient) {
        this.selectedPatient = selectedPatient;
    }
}
