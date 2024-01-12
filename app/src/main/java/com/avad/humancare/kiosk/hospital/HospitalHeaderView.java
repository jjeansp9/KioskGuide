package com.avad.humancare.kiosk.hospital;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.train.TrainMainActivity;
import com.avad.humancare.kiosk.util.Utils;

public class HospitalHeaderView extends ConstraintLayout implements View.OnClickListener {

    private Context _context;
    private Button btnMain, btnFinish;
    private ImageButton btnHome;

    public HospitalHeaderView(@NonNull Context context) {
        super(context);
        this._context = context;
        initViews();
    }

    public HospitalHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        initViews();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_back_main:
                //todo
                Intent intent = new Intent(_context, HospitalMainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                _context.startActivity(intent);
                if(_context instanceof Activity){
                    ((Activity)_context).finish();
                }
                break;
            case R.id.btn_finish:
                if(_context instanceof Activity){
                    ((Activity)_context).finish();
                }
                break;
            case R.id.btn_home:
                Utils.gotoElderApp((Activity)_context);
//                ((Activity)_context).finishAffinity();
//                System.exit(0);
                break;
        }
    }

    private void initViews(){
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_hospital_header, this, true);

        btnMain = (Button) findViewById(R.id.btn_back_main);
        btnMain.setOnClickListener(this);
        btnFinish = (Button) findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(this);
        btnHome = (ImageButton) findViewById(R.id.btn_home);
        btnHome.setOnClickListener(this);
    }
    public void setMainButtonVisibility(int viewProp){
        if(btnMain != null){
            btnMain.setVisibility(viewProp);
        }
    }
    public void setBackButtonVisibility(int viewProp){
        if(btnFinish != null){
            btnFinish.setVisibility(viewProp);
        }
    }
    public void setHomeButtonVisibility(int viewProp){
        if(btnHome != null) {
            btnHome.setVisibility(viewProp);
        }
    }

}
