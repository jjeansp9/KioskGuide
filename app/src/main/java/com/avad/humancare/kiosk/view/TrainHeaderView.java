package com.avad.humancare.kiosk.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;

public class TrainHeaderView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;

    private ViewGroup mBackBtnLayout;
    private TextView mTitleTextView;

    public TrainHeaderView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public TrainHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public TrainHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public TrainHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.train_header, this, true);

        mTitleTextView = (TextView) findViewById(R.id.header_title);
        mBackBtnLayout = findViewById(R.id.header_backbtn);
        mBackBtnLayout.setOnClickListener(this);
    }

    public void setHeaderTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setBackButtonListener(OnClickListener listener) {
        if(listener != null) {
            mBackBtnLayout.setOnClickListener(listener);
        }
    }

    public void setBackBtnVisible(boolean isVisible) {
        if(isVisible) {
            mBackBtnLayout.setVisibility(View.VISIBLE);
        } else {
            mBackBtnLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_backbtn:
                ((Activity) mContext).onBackPressed();
                break;
        }
    }
}
