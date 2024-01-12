package com.avad.humancare.kiosk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.util.Common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PopupDialog extends Dialog {

    private Context mContext;
    private TextView mTitleTv, mContentTv, mNoteTv;
    private Button mCancelBtn, mOkBtn;
    private ViewGroup mTitleLayout;
    private View mTitleUnderline;

    public PopupDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public PopupDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
    }

    protected PopupDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initView();
    }

    private void initView() {
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.custom_dialog_train);

        mTitleLayout = findViewById(R.id.title_ly);
        mTitleTv = (TextView) findViewById(R.id.title);
        mTitleUnderline = findViewById(R.id.title_underline);
        mContentTv = (TextView) findViewById(R.id.content);
        mNoteTv = (TextView) findViewById(R.id.note);
        mCancelBtn = (Button) findViewById(R.id.cancelBtn);
        mOkBtn = (Button) findViewById(R.id.okBtn);

        mTitleLayout.setVisibility(View.GONE);
        mNoteTv.setVisibility(View.GONE);
        mCancelBtn.setVisibility(View.GONE);
    }

    public void setTitle(String str) {
        if(!TextUtils.isEmpty(str)) {
            mTitleTv.setText(str);
            mTitleLayout.setVisibility(View.VISIBLE);
        } else {
            mTitleLayout.setVisibility(View.GONE);
        }
    }

    public void setContent(String str) {
        mContentTv.setText(str);
    }

    public void setNote(String str) {
        if(!TextUtils.isEmpty(str)) {
            mNoteTv.setVisibility(View.VISIBLE);
            mNoteTv.setText(str);
        } else {
            mNoteTv.setVisibility(View.GONE);
        }
    }

    public void setOkButtonText(String str) {
        mOkBtn.setText(str);
    }

    public void setOnOkButtonClickListener(View.OnClickListener listener) {
        mOkBtn.setOnClickListener(listener);
    }

    public void setOnCancelButtonClickListener(View.OnClickListener listener) {
        mCancelBtn.setVisibility(View.VISIBLE);
        mCancelBtn.setOnClickListener(listener);
    }

    /**
     * set Dialog Colors
     * @param colorMain maincolor : title font + underline color, okBtn background color
     * @param colorSub cancelBtn background color
     */
    public void setColors(int colorMain, int colorSub){
        if(mTitleTv != null) mTitleTv.setTextColor(colorMain);
        if(mTitleUnderline != null) mTitleUnderline.setBackgroundColor(colorMain);
        if(mOkBtn != null) mOkBtn.setBackgroundColor(colorMain);
        if(mCancelBtn != null) mCancelBtn.setBackgroundColor(colorSub);
    }

    public void setDialogType(Common.KIOSK_TYPE type) {
        if(type.equals(Common.KIOSK_TYPE.FASTFOOD)) {
            setColors(mContext.getColor(R.color.fastfood_bg_btn_ok), mContext.getColor(R.color.fastfood_bg_btn_cancel));
        } else if(type.equals(Common.KIOSK_TYPE.HOSPITAL)) {
            setColors(mContext.getColor(R.color.hospital_sub_color), mContext.getColor(R.color.hospital_main_color));
        } else {
            // train - layout default
        }
    }

}
