package com.avad.humancare.kiosk.train;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.avad.humancare.kiosk.HumanCareKioskApplication;
import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.TrainReservationTicketListAdapter;
import com.avad.humancare.kiosk.model.TrainReservationInfo;
import com.avad.humancare.kiosk.util.Common;
import com.avad.humancare.kiosk.util.Utils;
import com.avad.humancare.kiosk.view.TrainHeaderView;

public class TrainPaymentConfirmActivity extends TrainBaseActivity {

    private Context mContext;

    private TextView mTrainNumTv, mSeatNumTv;
    private ListView mListView;
    private TrainReservationTicketListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_payment_confirm);

        mContext = this;


        setHeaderView();

        initView();
    }

    private void setHeaderView() {
        TrainHeaderView headerView = (TrainHeaderView) findViewById(R.id.header);
        headerView.setHeaderTitle(getString(R.string.train_payment_title));
        findViewById(R.id.btn_back_main).setOnClickListener(this);
    }

    private void initView() {

        // footer
        ((TextView)findViewById(R.id.footer_right_text)).setText(getString(R.string.order_pay));
        findViewById(R.id.footer_right_ly).setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.listView);
        mListAdapter = new TrainReservationTicketListAdapter(this, HumanCareKioskApplication.mTrainTempList, TrainReservationTicketListAdapter.TYPE_RESERVATION_ING);
        mListView.setAdapter(mListAdapter);

        TextView totalPriceTv = (TextView) findViewById(R.id.total_price_txt);


        // 총 카운트, 금액
        int totalPrice = 0;
        int totalCnt = 0;
        if(HumanCareKioskApplication.mTrainTempList != null) {
            for(TrainReservationInfo info : HumanCareKioskApplication.mTrainTempList) {
                if(info.trainInfo.selectedSeatType == Common.TrainSeatConstant.ROOM_BASIC) {
                    totalPrice += info.seatCount * info.trainInfo.basicPrice;
                } else {
                    totalPrice += info.seatCount * info.trainInfo.vipPrice;
                }
                totalCnt += info.seatCount;
            }
            HumanCareKioskApplication.mTrainTempList.get(0).totalPrice = totalPrice;
            HumanCareKioskApplication.mTrainTempList.get(0).totalSeatCnt = totalCnt;
            totalPriceTv.setText(Utils.getPriceFormat(this, totalPrice, true));
            ((TextView) findViewById(R.id.total_count_txt)).setText(getString(R.string.train_payment_total_count, totalCnt));
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;

        switch (view.getId()) {
            case R.id.footer_right_ly :
                intent = new Intent(this, TrainPaymentActivity.class);
//                intent.putExtra("data", mReservationInfo);
                startActivity(intent);
                break;
        }
    }
}