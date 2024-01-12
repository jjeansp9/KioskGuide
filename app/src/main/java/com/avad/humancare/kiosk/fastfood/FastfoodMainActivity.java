package com.avad.humancare.kiosk.fastfood;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.util.Utils;

public class FastfoodMainActivity extends FastfoodBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastfood_main);

        findViewById(R.id.main_view).setOnClickListener(this);
        findViewById(R.id.btn_back_main).setOnClickListener(this);
        findViewById(R.id.btn_home).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.main_view :
                startActivity(new Intent(this, FastfoodSelectPlaceActivity.class));
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