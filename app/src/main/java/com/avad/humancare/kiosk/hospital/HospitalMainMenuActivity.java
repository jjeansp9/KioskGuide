package com.avad.humancare.kiosk.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.util.Common;

public class HospitalMainMenuActivity extends AppCompatActivity implements View.OnClickListener{


    HospitalHeaderView _headerView;
    private int selectedMenu = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_main_menu);
        initViews();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.card_receipt:
                selectedMenu = Common.MENU_RECEIPT;
                break;
            case R.id.card_payment:
                selectedMenu = Common.MENU_PAYMENT;
                break;
            case R.id.card_records:
                selectedMenu = Common.MENU_RECORDS;
                break;
            case R.id.card_insurance:
                selectedMenu = Common.MENU_INSURANCE;
                break;
            default:
                return;
        }
        Intent intent = new Intent(this, HospitalFragmentActivity.class);
        intent.putExtra("MENU", selectedMenu);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }
    private void initViews(){
        findViewById(R.id.card_receipt).setOnClickListener(this);
        findViewById(R.id.card_payment).setOnClickListener(this);
        findViewById(R.id.card_records).setOnClickListener(this);
        findViewById(R.id.card_insurance).setOnClickListener(this);
        _headerView = findViewById(R.id.header);
        _headerView.setMainButtonVisibility(View.INVISIBLE);
        _headerView.setBackButtonVisibility(View.VISIBLE);
        _headerView.setHomeButtonVisibility(View.VISIBLE);
    }
}