package com.avad.humancare.kiosk.fastfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;

public class FastfoodPaymentSelectActivity extends FastfoodBaseActivity {

    private Context mContext;

    private ViewGroup mPaymentCardLy, mPaymentCouponLy;
    private TextView mSaveDiscountMethodBtn1, mSaveDiscountMethodBtn2, mSaveDiscountMethodBtn3;

    private String mPaymentType = FastfoodPayResultActivity.PAYMENT_TYPE_CARD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastfood_payment_select);

        mContext = this;

        initView();
    }

    private void initView() {

        // header/footer
        findViewById(R.id.btn_back_main).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        ((TextView)findViewById(R.id.btn_next_txt)).setText(R.string.order_pay);

        mPaymentCardLy = findViewById(R.id.payment_card_ly);
        mPaymentCardLy.setOnClickListener(this);

        mPaymentCouponLy = findViewById(R.id.payment_coupon_ly);
        mPaymentCouponLy.setOnClickListener(this);

        mSaveDiscountMethodBtn1 = (TextView) findViewById(R.id.save_discount_method1);
        mSaveDiscountMethodBtn2 = (TextView) findViewById(R.id.save_discount_method2);
        mSaveDiscountMethodBtn3 = (TextView) findViewById(R.id.save_discount_method3);
        mSaveDiscountMethodBtn1.setOnClickListener(this);
        mSaveDiscountMethodBtn2.setOnClickListener(this);
        mSaveDiscountMethodBtn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;

        switch (view.getId()) {
            case R.id.btn_back :
                finish();
                break;

            case R.id.btn_next :
                intent = new Intent(mContext, FastfoodPayResultActivity.class);
                intent.putExtra("type", mPaymentType);
                startActivity(intent);
                break;

            case R.id.payment_card_ly :
                mPaymentCardLy.setBackgroundResource(R.drawable.btn_sel);
                mPaymentCouponLy.setBackgroundResource(R.drawable.btn_nor);
                mPaymentType = FastfoodPayResultActivity.PAYMENT_TYPE_CARD;
                break;

            case R.id.payment_coupon_ly :
                mPaymentCardLy.setBackgroundResource(R.drawable.btn_nor);
                mPaymentCouponLy.setBackgroundResource(R.drawable.btn_sel);
                mPaymentType = FastfoodPayResultActivity.PAYMENT_TYPE_COUPON;
                break;

            case R.id.save_discount_method1 :
                mSaveDiscountMethodBtn1.setBackgroundResource(R.drawable.btn_sel);
                mSaveDiscountMethodBtn2.setBackgroundResource(R.drawable.btn_nor);
                mSaveDiscountMethodBtn3.setBackgroundResource(R.drawable.btn_nor);
                break;

            case R.id.save_discount_method2 :
                mSaveDiscountMethodBtn1.setBackgroundResource(R.drawable.btn_nor);
                mSaveDiscountMethodBtn2.setBackgroundResource(R.drawable.btn_sel);
                mSaveDiscountMethodBtn3.setBackgroundResource(R.drawable.btn_nor);
                break;

            case R.id.save_discount_method3 :
                mSaveDiscountMethodBtn1.setBackgroundResource(R.drawable.btn_nor);
                mSaveDiscountMethodBtn2.setBackgroundResource(R.drawable.btn_nor);
                mSaveDiscountMethodBtn3.setBackgroundResource(R.drawable.btn_sel);
                break;
        }
    }
}