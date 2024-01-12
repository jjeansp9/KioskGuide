package com.avad.humancare.kiosk.train;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.HumanCareKioskApplication;
import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.util.Utils;
import com.avad.humancare.kiosk.view.TrainHeaderView;

public class TrainPaymentActivity extends TrainBaseActivity {

    private Context mContext;
    private ViewGroup mTabLeft, mTabRight, mLeftViewGroup, mRightViewGroup;
    private View mTabBottom1, mTabBottom2;
    private TextView mTabTextView1, mTabTextView2, mPeriodTv;
    private EditText mCardNumEdit1, mCardNumEdit2, mCardNumEdit3, mCardNumEdit4;
    private EditText mPeriodMonthEdit, mPeriodYearEdit, mPwEdit, mAuthNumberEdit;
    private RadioButton mRadioBtn1, mRadioBtn2;
    private RadioGroup mRadioGroup;
    private Button mCheckboxBtn;

    private int mSelectedTab = 0;   // 0 : 왼쪽, 1 : 오른쪽
    private boolean mIsAgreeChecked = false;    // 개인정보 동의

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_payment);

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
        ((TextView)findViewById(R.id.footer_right_text)).setText(getString(R.string.train_payment_footer_text));
        findViewById(R.id.footer_right_ly).setOnClickListener(this);
        findViewById(R.id.footer_left_ly).setVisibility(View.VISIBLE);
        findViewById(R.id.footer_left_ly).setOnClickListener(this);

        // tab
        mTabLeft = findViewById(R.id.tab_left);
        mTabRight = findViewById(R.id.tab_right);
        mTabLeft.setOnClickListener(this);
        mTabRight.setOnClickListener(this);
        mTabBottom1 = findViewById(R.id.tab_bottom1);
        mTabBottom2 = findViewById(R.id.tab_bottom2);
        mTabTextView1 = (TextView) findViewById(R.id.tab_text1);
        mTabTextView2 = (TextView) findViewById(R.id.tab_text2);

        mLeftViewGroup = findViewById(R.id.card_ly);
        mRightViewGroup = findViewById(R.id.direct_ly);

        mCardNumEdit1 = (EditText) findViewById(R.id.card_num_edit1);
        mCardNumEdit2 = (EditText) findViewById(R.id.card_num_edit2);
        mCardNumEdit3 = (EditText) findViewById(R.id.card_num_edit3);
        mCardNumEdit4 = (EditText) findViewById(R.id.card_num_edit4);

        mPeriodMonthEdit = (EditText) findViewById(R.id.period_month);
        mPeriodYearEdit = (EditText) findViewById(R.id.period_year);
        mPwEdit = (EditText) findViewById(R.id.password);
        mAuthNumberEdit = (EditText) findViewById(R.id.authnumber);
        mPeriodTv = (TextView) findViewById(R.id.period_text);
        findViewById(R.id.period_select_ly).setOnClickListener(this);

        mRadioBtn1 = (RadioButton) findViewById(R.id.radio1);
        mRadioBtn2 = (RadioButton) findViewById(R.id.radio2);
        mRadioBtn1.setChecked(true);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radio1) {
                } else {
                }
            }
        });

        mCheckboxBtn = (Button) findViewById(R.id.checkBox);
        mCheckboxBtn.setBackgroundResource(R.drawable.btn_check_nor);
        mCheckboxBtn.setOnClickListener(this);

        // 총 카운트, 금액
        ((TextView) findViewById(R.id.total_count_txt)).setText(getString(R.string.train_payment_total_count, HumanCareKioskApplication.mTrainTempList.get(0).totalSeatCnt));
        ((TextView) findViewById(R.id.total_price_txt)).setText(Utils.getPriceFormat(this, HumanCareKioskApplication.mTrainTempList.get(0).totalPrice, true));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;

        switch (view.getId()) {
            case R.id.footer_left_ly:
                finish();
                break;

            case R.id.footer_right_ly :
                if(mSelectedTab == 0 && !mIsAgreeChecked) {
                    showNormalDialog(getString(R.string.train_payment_card_agreement_msg));
                    return ;
                }

                String cardNum = mCardNumEdit1.getText().toString() + mCardNumEdit2.getText().toString() + mCardNumEdit3.getText().toString() + mCardNumEdit4.getText().toString();

                intent = new Intent(this, TrainPaymentResultActivity.class);
                intent.putExtra("cardNum", cardNum);
                startActivity(intent);
                break;

            case R.id.tab_left :
                mSelectedTab = 0;
                mLeftViewGroup.setVisibility(View.VISIBLE);
                mRightViewGroup.setVisibility(View.GONE);
                mTabTextView1.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
                mTabTextView2.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_8));
                mTabBottom1.setVisibility(View.VISIBLE);
                mTabBottom2.setVisibility(View.GONE);
                break;

            case R.id.tab_right:
                mSelectedTab = 1;
                mLeftViewGroup.setVisibility(View.GONE);
                mRightViewGroup.setVisibility(View.VISIBLE);
                mTabTextView1.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_8));
                mTabTextView2.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
                mTabBottom1.setVisibility(View.GONE);
                mTabBottom2.setVisibility(View.VISIBLE);
                break;

            case R.id.period_select_ly :
                hideEditTextKeyboard();
                showPeriodDialog();
                break;

            case R.id.checkBox :
                hideEditTextKeyboard();
                mIsAgreeChecked = !mIsAgreeChecked;
                if(mIsAgreeChecked) {
                    mCheckboxBtn.setBackgroundResource(R.drawable.btn_check_sel);
                } else {
                    mCheckboxBtn.setBackgroundResource(R.drawable.btn_check_nor);
                }
                break;

        }
    }

    private void showPeriodDialog() { // 할부기간 dialog

        int[] arr = {R.string.train_payment_card_pay_full, R.string.train_payment_card_monthly_installment1, R.string.train_payment_card_monthly_installment2};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        for(int i=0; i<arr.length; i++) {
            adapter.add(getString(arr[i]));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPeriodTv.setText(arr[i]);
            }
        });
        builder.show();

    }

    private void hideEditTextKeyboard() {
        // 키보드 내리기
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mCardNumEdit1.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mCardNumEdit2.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mCardNumEdit3.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mCardNumEdit4.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mPeriodMonthEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mPeriodYearEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mPwEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mAuthNumberEdit.getWindowToken(), 0);
    }
}