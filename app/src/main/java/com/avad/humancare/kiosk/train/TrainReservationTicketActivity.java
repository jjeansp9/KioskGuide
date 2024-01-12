package com.avad.humancare.kiosk.train;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.avad.humancare.kiosk.HumanCareKioskApplication;
import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.TrainReservationTicketListAdapter;
import com.avad.humancare.kiosk.dialog.PopupDialog;
import com.avad.humancare.kiosk.model.TrainReservationInfo;
import com.avad.humancare.kiosk.view.TrainHeaderView;

import java.util.ArrayList;

public class TrainReservationTicketActivity extends TrainBaseActivity {

    private Context mContext;
    private TextView mNoDataTv;
    private ListView mListView;
    private TrainReservationTicketListAdapter mListAdapter;
    private ArrayList<TrainReservationInfo> mList = new ArrayList<TrainReservationInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_reservation_ticket);

        mContext = this;

        setHeaderView();

        initView();
    }

    private void setHeaderView() {
        TrainHeaderView headerView = (TrainHeaderView) findViewById(R.id.header);
        headerView.setHeaderTitle(getString(R.string.train_ticket_reservation_check_title));
        findViewById(R.id.btn_back_main).setOnClickListener(this);
    }

    private void initView() {

        if(HumanCareKioskApplication.mTrainInstance_reservationInfo != null) {
            mList.add(HumanCareKioskApplication.mTrainInstance_reservationInfo);    // 일단 마지막 하나만 리스트에 담아서 표시하자..
        }

        mListView = (ListView) findViewById(R.id.listView);
        mListAdapter = new TrainReservationTicketListAdapter(this, mList, TrainReservationTicketListAdapter.TYPE_RESERVATION_COMPLETE);
        mListView.setAdapter(mListAdapter);

        mNoDataTv = (TextView) findViewById(R.id.no_data);

        updateListView();

    }

    public void showReservationCancelDialog(int position) {
        PopupDialog dialog = new PopupDialog(this);
        dialog.setTitle(getString(R.string.train_ticket_reservation_cancel_dialog_title));
        dialog.setContent(getString(R.string.train_ticket_reservation_cancel_dialog_content));
        dialog.setOnOkButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mList.remove(position);
                HumanCareKioskApplication.mTrainInstance_reservationInfo = null;
                updateListView();
            }
        });

        dialog.show();
    }

    private void updateListView() {
        if(mList != null && mList.size() > 0) {
            mListAdapter.notifyDataSetChanged();
            mNoDataTv.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        } else {
            mNoDataTv.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }
    }
}