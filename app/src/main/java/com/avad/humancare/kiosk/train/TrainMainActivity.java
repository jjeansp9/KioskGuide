package com.avad.humancare.kiosk.train;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.util.Utils;

public class TrainMainActivity extends TrainBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_main);

        findViewById(R.id.btn_title1).setOnClickListener(this);
        findViewById(R.id.btn_title2).setOnClickListener(this);
        findViewById(R.id.btn_title3).setOnClickListener(this);
        findViewById(R.id.btn_back_main).setOnClickListener(this);
        findViewById(R.id.btn_home).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_title1 :
                startActivity(new Intent(this, TrainReservationActivity.class));
                break;

            case R.id.btn_title2 :
                startActivity(new Intent(this, TrainTicketActivity.class));
                break;

            case R.id.btn_title3 :
                startActivity(new Intent(this, TrainReservationTicketActivity.class));
                break;

            case R.id.btn_back_main :
                finish();
                break;

            case R.id.btn_home :
                Utils.gotoElderApp(this);
                break;
        }
    }
}