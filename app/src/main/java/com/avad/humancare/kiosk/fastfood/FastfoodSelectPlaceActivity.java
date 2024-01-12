package com.avad.humancare.kiosk.fastfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.avad.humancare.kiosk.R;

public class FastfoodSelectPlaceActivity extends FastfoodBaseActivity {

    private String TAG = FastfoodSelectPlaceActivity.class.getSimpleName();

    private Context mContext;
    private View mViewOrderEat, mViewOrderTakeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastfood_select_place);

        mContext = this;

        initView();

    }

    private void initView() {

        // header
        findViewById(R.id.btn_back_main).setOnClickListener(this);

        mViewOrderEat = findViewById(R.id.view_order_eat);
        mViewOrderEat.setOnClickListener(this);

        mViewOrderTakeout = findViewById(R.id.view_order_takeout);
        mViewOrderTakeout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Log.d(TAG, "onClick()");
        Intent intent = null;

        switch (view.getId()) {
            case R.id.view_order_eat:
                mViewOrderEat.setBackgroundResource(R.drawable.btn_sel);
                mViewOrderTakeout.setBackgroundResource(R.drawable.btn_nor);
                intent = new Intent(mContext, FastfoodMenuActivity.class);
                startActivity(intent);
                break;

            case R.id.view_order_takeout:
                mViewOrderEat.setBackgroundResource(R.drawable.btn_nor);
                mViewOrderTakeout.setBackgroundResource(R.drawable.btn_sel);
                intent = new Intent(mContext, FastfoodMenuActivity.class);
                startActivity(intent);
                break;
        }
    }
}