package com.avad.humancare.kiosk.train;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.dialog.PopupDialog;
import com.avad.humancare.kiosk.fastfood.FastfoodMainActivity;
import com.avad.humancare.kiosk.model.TrainReservationInfo;

import java.util.ArrayList;

public class TrainBaseActivity extends AppCompatActivity implements View.OnClickListener {

    public TrainReservationInfo mReservationInfo;
    public ArrayList<TrainReservationInfo> mTempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.btn_back_main :
                intent = new Intent(this, TrainMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void showNormalDialog(String title, String content) {
        PopupDialog dialog = new PopupDialog(this);

        if(!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        dialog.setContent(content);
        dialog.setOnOkButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showNormalDialog(String content) {
        showNormalDialog(null, content);
    }
}