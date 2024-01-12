package com.avad.humancare.kiosk.train;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.avad.humancare.kiosk.HumanCareKioskApplication;
import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.model.TrainReservationInfo;
import com.avad.humancare.kiosk.util.Utils;
import com.avad.humancare.kiosk.view.TrainHeaderView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class TrainPaymentResultActivity extends TrainBaseActivity {

    private Context mContext;

    private String mCardNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_payment_result);

        mContext = this;

        Intent intent = getIntent();
        if(intent != null) {
            mCardNum = intent.getStringExtra("cardNum");
        }

        setHeaderView();

        initView();

        setTicketList();

    }

    private void setHeaderView() {
        TrainHeaderView headerView = (TrainHeaderView) findViewById(R.id.header);
        headerView.setHeaderTitle(getString(R.string.train_payment_complete_title));
        findViewById(R.id.btn_back_main).setOnClickListener(this);

        headerView.setBackBtnVisible(false);
    }

    private void initView() {

        // footer
        findViewById(R.id.footer_right_ly).setOnClickListener(this);

        TextView cardNumTv = (TextView) findViewById(R.id.card_number);
        TextView approvalNumTv = (TextView) findViewById(R.id.approval_num);
        TextView approvalDateTv = (TextView) findViewById(R.id.approval_date);
        TextView totalPriceTv = (TextView) findViewById(R.id.total_price);

        if(!TextUtils.isEmpty(mCardNum)) {
            cardNumTv.setText(mCardNum);
        } else {
            Random random = new Random();
            int r1 = random.nextInt(100000000);
            int r2 = random.nextInt(100000000);

            cardNumTv.setText(String.format("%08d", r1) + String.format("%08d", r2));
        }

        approvalDateTv.setText(LocalDate.now().toString());
        approvalNumTv.setText(String.valueOf(System.currentTimeMillis()));
        totalPriceTv.setText(Utils.getPriceFormat(this, HumanCareKioskApplication.mTrainTempList.get(0).totalPrice, true));

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.footer_right_ly :
                findViewById(R.id.btn_back_main).performClick();
                break;
        }
    }

    private void setTicketList() {
        HumanCareKioskApplication.mTrainTicketList = (ArrayList<TrainReservationInfo>)HumanCareKioskApplication.mTrainTempList.clone();

    }
}