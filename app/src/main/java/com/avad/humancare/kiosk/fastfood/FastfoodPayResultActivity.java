package com.avad.humancare.kiosk.fastfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;

public class FastfoodPayResultActivity extends FastfoodBaseActivity {

    public static final String PAYMENT_TYPE_CARD = "card";
    public static final String PAYMENT_TYPE_COUPON = "coupon";
    public static final String PAYMENT_TYPE_COMPLETE = "complete";

    private Context mContext;

    private ViewGroup mBarcodeLayout, mCardLayout, mCompleteLayout;

    private String mPaymentType = PAYMENT_TYPE_CARD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastfood_pay_result);

        mContext = this;

        Intent intent = getIntent();
        if(intent != null) {
            mPaymentType = intent.getStringExtra("type");
        }

        initView();
    }

    private void initView() {

        // header/footer
        findViewById(R.id.btn_back_main).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        ((TextView)findViewById(R.id.btn_next_txt)).setText(R.string.ok);

        mBarcodeLayout = findViewById(R.id.barcode_ly);
        mCardLayout = findViewById(R.id.card_ly);
        mCompleteLayout = findViewById(R.id.complete_ly);

        if(mPaymentType.equals(PAYMENT_TYPE_COUPON)) {
            findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
            mBarcodeLayout.setVisibility(View.VISIBLE);
            mCardLayout.setVisibility(View.GONE);
            mCompleteLayout.setVisibility(View.GONE);
        } else if(mPaymentType.equals(PAYMENT_TYPE_CARD)) {
            findViewById(R.id.btn_back).setVisibility(View.GONE);
            mBarcodeLayout.setVisibility(View.GONE);
            mCardLayout.setVisibility(View.VISIBLE);
            mCompleteLayout.setVisibility(View.GONE);
        }
//        else {
//            findViewById(R.id.btn_back).setVisibility(View.GONE);
//            mBarcodeLayout.setVisibility(View.GONE);
//            mCardLayout.setVisibility(View.GONE);
//            mCompleteLayout.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_back :
                finish();
                break;

            case R.id.btn_next :
                if(mPaymentType.equals(PAYMENT_TYPE_COUPON) || mPaymentType.equals(PAYMENT_TYPE_CARD)) {
                    findViewById(R.id.btn_back).setVisibility(View.GONE);
                    mBarcodeLayout.setVisibility(View.GONE);
                    mCardLayout.setVisibility(View.GONE);
                    mCompleteLayout.setVisibility(View.VISIBLE);
                    mPaymentType = PAYMENT_TYPE_COMPLETE;
                } else {
                    findViewById(R.id.btn_back_main).performClick();
                    finish();
                }
                break;

        }
    }
}