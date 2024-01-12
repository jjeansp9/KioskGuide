package com.avad.humancare.kiosk.train;

import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.HumanCareKioskApplication;
import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.dialog.PopupDialog;
import com.avad.humancare.kiosk.view.TrainHeaderView;

public class TrainTicketDeliveryActivity extends TrainBaseActivity {

    private Context mContext;
    private TextView mTab1, mTab2, mTab3;
    private TextView mConfirmMsgTv;
    private ViewGroup mMberTypeView, mMberConfirmView;
    private ViewGroup mPwView, mPwView2;
    private EditText mEditMberConfirm, mEditName, mEditPhone, mEditPw, mEditPw2;
    private RadioButton mMberRadioBtn1, mMberRadioBtn2;
    private InputMethodManager imm;
    private int mSelectedTab = 0;
    private boolean mIsCheckMberConfirm = false;     // 코레일톡인 경우 회원확인방법 조회 여부
    private int mListPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_ticket_delivery);

        mContext = this;

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        Intent intent = getIntent();
        if(intent != null) {
            mListPosition = intent.getIntExtra("idx", 0);
        }

        setHeaderView();

        initView();
    }

    private void setHeaderView() {
        TrainHeaderView headerView = (TrainHeaderView) findViewById(R.id.header);
        headerView.setHeaderTitle(getString(R.string.train_ticket_delivery_title));
        findViewById(R.id.btn_back_main).setOnClickListener(this);

    }

    private void initView() {

        // footer
        findViewById(R.id.footer_right_ly).setOnClickListener(this);
        ((TextView)findViewById(R.id.footer_right_text)).setText(R.string.train_ticket_delivery);

        mTab1 = (TextView) findViewById(R.id.tab1);
        mTab2 = (TextView) findViewById(R.id.tab2);
        mTab3 = (TextView) findViewById(R.id.tab3);
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);

        mMberTypeView = findViewById(R.id.mber_type_ly);
        mEditName = (EditText) findViewById(R.id.edit_name);
        mEditPhone = (EditText) findViewById(R.id.edit_phonenum);
        mEditMberConfirm = (EditText) findViewById(R.id.edit_mber_confirm);
        mMberRadioBtn1 =  (RadioButton) findViewById(R.id.mber_radio1);
        mMberRadioBtn2 =  (RadioButton) findViewById(R.id.mber_radio2);
        mMberRadioBtn1.setChecked(true);
        ((RadioButton) findViewById(R.id.mber_confirm1)).setChecked(true);

        RadioGroup mberRadioGroup = (RadioGroup) findViewById(R.id.mber_radiogroup);
        mberRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.mber_radio1) {
                    mPwView.setVisibility(View.GONE);
                    mPwView2.setVisibility(View.GONE);
                } else {
                    mEditPw.setText("");
                    mEditPw2.setText("");
                    mPwView.setVisibility(View.VISIBLE);
                    mPwView2.setVisibility(View.VISIBLE);
                }
            }
        });

        RadioGroup mberConfirmRadioGroup = (RadioGroup) findViewById(R.id.mber_confirm_radiogroup);
        mberConfirmRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.mber_confirm1:
                        mEditMberConfirm.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;

                    case R.id.mber_confirm2:
                        mEditMberConfirm.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;

                    case R.id.mber_confirm3:
                        mEditMberConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        break;
                }
            }
        });

        mMberConfirmView = findViewById(R.id.mber_confirm_layout);
        mConfirmMsgTv = (TextView) findViewById(R.id.confirm_msg);
        mConfirmMsgTv.setVisibility(View.INVISIBLE);
        findViewById(R.id.inquiryBtn).setOnClickListener(this);

        mPwView = findViewById(R.id.password_ly);
        mPwView2 = findViewById(R.id.password_ly2);
        mPwView.setVisibility(View.GONE);
        mPwView2.setVisibility(View.GONE);
        mEditPw = (EditText) findViewById(R.id.edit_pw);
        mEditPw2 = (EditText) findViewById(R.id.edit_pw2);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.footer_right_ly :
                if(mEditName.getText().toString().isEmpty()) {
                    showNormalDialog(getString(R.string.train_dialog_ticket_delivery_err1));
                    return ;
                }
                if(mEditPhone.getText().toString().isEmpty()) {
                    showNormalDialog(getString(R.string.train_dialog_ticket_delivery_err2));
                    return ;
                }

                if(mSelectedTab == 0) { // 코레일톡
                    if(mEditMberConfirm.getText().toString().isEmpty() || mIsCheckMberConfirm == false) {
                        showNormalDialog(getString(R.string.train_dialog_ticket_delivery_err3));
                        return ;
                    }

                    if(mMberRadioBtn2.isChecked()) { // 비회원인 경우
                        String pw1 = mEditPw.getText().toString();
                        String pw2 = mEditPw2.getText().toString();

                        if(pw1.isEmpty()) {
                            showNormalDialog(getString(R.string.train_dialog_ticket_delivery_err4));
                            return ;
                        }

                        if(pw2.isEmpty()) {
                            showNormalDialog(getString(R.string.train_dialog_ticket_delivery_err4));
                            return ;
                        }

                        if(!pw1.equals(pw2)) {
                            showNormalDialog(getString(R.string.train_dialog_ticket_delivery_err5));
                            return ;
                        }

                    }

                }

                PopupDialog dialog = new PopupDialog(this);
                dialog.setTitle(getString(R.string.train_dialog_ticket_delivery_success_title));
                dialog.setContent(getString(R.string.train_dialog_ticket_delivery_success));
                dialog.setOnOkButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        HumanCareKioskApplication.mTrainTicketList.remove(mListPosition);
                        finish();
                    }
                });
                dialog.show();
                break;

            case R.id.tab1 :
                mSelectedTab = 0;
                updateView();
                break;

            case R.id.tab2 :
                mSelectedTab = 1;
                updateView();
                break;

            case R.id.tab3 :
                mSelectedTab = 2;
                updateView();
                break;

            case R.id.inquiryBtn :
                if(mSelectedTab == 0 && mEditMberConfirm.getText().toString().isEmpty()) {
                    showNormalDialog(getString(R.string.train_dialog_ticket_delivery_err3));
                    return ;
                }

                mConfirmMsgTv.setVisibility(View.VISIBLE);
                mIsCheckMberConfirm = true;
                break;


        }
    }

    private void updateView() {
        if(mSelectedTab == 0) {
            mIsCheckMberConfirm = false;
            mEditMberConfirm.setText("");
            mConfirmMsgTv.setVisibility(View.INVISIBLE);

            mTab1.setBackgroundColor(ContextCompat.getColor(this, R.color.train_bg_color1));
            mTab2.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mTab3.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mTab1.setTextColor(ContextCompat.getColor(this, R.color.white));
            mTab2.setTextColor(ContextCompat.getColor(this, R.color.black));
            mTab3.setTextColor(ContextCompat.getColor(this, R.color.black));
            mMberConfirmView.setVisibility(View.VISIBLE);
            mMberTypeView.setVisibility(View.VISIBLE);

            if(mMberRadioBtn2.isChecked()) {
                mEditPw.setText("");
                mEditPw2.setText("");
                mPwView.setVisibility(View.VISIBLE);
                mPwView2.setVisibility(View.VISIBLE);
            } else {
                mPwView.setVisibility(View.GONE);
                mPwView2.setVisibility(View.GONE);
            }

        } else if(mSelectedTab == 1) {
            mTab1.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mTab2.setBackgroundColor(ContextCompat.getColor(this, R.color.train_bg_color1));
            mTab3.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mTab1.setTextColor(ContextCompat.getColor(this, R.color.black));
            mTab2.setTextColor(ContextCompat.getColor(this, R.color.white));
            mTab3.setTextColor(ContextCompat.getColor(this, R.color.black));
            mMberConfirmView.setVisibility(View.GONE);
            mMberTypeView.setVisibility(View.GONE);
            mPwView.setVisibility(View.GONE);
            mPwView2.setVisibility(View.GONE);
        } else {
            mTab1.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mTab2.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mTab3.setBackgroundColor(ContextCompat.getColor(this, R.color.train_bg_color1));
            mTab1.setTextColor(ContextCompat.getColor(this, R.color.black));
            mTab2.setTextColor(ContextCompat.getColor(this, R.color.black));
            mTab3.setTextColor(ContextCompat.getColor(this, R.color.white));
            mMberConfirmView.setVisibility(View.GONE);
            mMberTypeView.setVisibility(View.GONE);
            mPwView.setVisibility(View.GONE);
            mPwView2.setVisibility(View.GONE);
        }
    }

    private void hideEditTextKeyboard() {
        // 키보드 내리기
        imm.hideSoftInputFromWindow(mEditMberConfirm.getWindowToken(), 0);
    }

    private void showEditTextKeyboard() {
        imm.showSoftInput(mEditMberConfirm, 0);
    }
}