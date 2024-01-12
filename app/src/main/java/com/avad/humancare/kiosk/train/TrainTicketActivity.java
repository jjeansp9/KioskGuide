package com.avad.humancare.kiosk.train;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.avad.humancare.kiosk.HumanCareKioskApplication;
import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.TrainTicketListAdapter;
import com.avad.humancare.kiosk.dialog.PopupDialog;
import com.avad.humancare.kiosk.model.TrainReservationInfo;
import com.avad.humancare.kiosk.view.TrainHeaderView;

import java.util.ArrayList;

public class TrainTicketActivity extends TrainBaseActivity {

    private Context mContext;
    private ListView mListView;
    private TextView mNoDataTv;
    private TrainTicketListAdapter mListAdapter;

    private ArrayList<TrainReservationInfo> mList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_ticket);

        mContext = this;

        setHeaderView();

        initView();
    }

    private void setHeaderView() {
        TrainHeaderView headerView = (TrainHeaderView) findViewById(R.id.header);
        headerView.setHeaderTitle(getString(R.string.train_ticket_confirm_title));
        findViewById(R.id.btn_back_main).setOnClickListener(this);
    }

    private void initView() {

        if(HumanCareKioskApplication.mTrainTicketList != null) {
            mList = HumanCareKioskApplication.mTrainTicketList;
        } else {
            mList = new ArrayList<TrainReservationInfo>();
        }

        mListView = (ListView) findViewById(R.id.listView);
        mListAdapter = new TrainTicketListAdapter(this, mList);
        mListView.setAdapter(mListAdapter);

        mNoDataTv = (TextView) findViewById(R.id.no_data);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListView();
    }

    public void goTicketDelivery(int position) {
        Intent intent = new Intent(this, TrainTicketDeliveryActivity.class);
        intent.putExtra("idx", position);
        startActivity(intent);
    }

    public void showTicketReturnDialog(int position) {

        PopupDialog dialog = new PopupDialog(this);
        dialog.setTitle(getString(R.string.train_dialog_return_popup_title));
        dialog.setContent(getString(R.string.train_dialog_return_popup_content));
        dialog.setNote(getString(R.string.train_dialog_return_popup_note));
        dialog.setOkButtonText(getString(R.string.train_ticket_return));
        dialog.setOnOkButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                PopupDialog dlg = new PopupDialog(mContext);
                dlg.setContent(getString(R.string.train_ticket_return_complete));
                dlg.setOnOkButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dlg.dismiss();
                    }
                });
                dlg.show();

                mList.remove(position);
                updateListView();
            }
        });
        dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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