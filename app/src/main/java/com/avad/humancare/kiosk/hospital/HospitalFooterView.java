package com.avad.humancare.kiosk.hospital;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.avad.humancare.kiosk.R;

public class HospitalFooterView extends ConstraintLayout implements View.OnClickListener {
    public enum SHOWTYPE{
        BACK,
        NEXT,
        BOTH,
        NONE
    }
    private Context _context;
    CardView _cardBack, _cardNext;
    public SHOWTYPE ShowType = SHOWTYPE.BOTH;
    private HospitalBaseActivity.NavigateButtonListener _navigateButtonListener;
    public HospitalFooterView(@NonNull Context context) {
        super(context);
        this._context = context;
        initViews();
    }

    public HospitalFooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        initViews();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.card_back:
                //todo 이전 fragment 이동 루틴
                if(_navigateButtonListener != null)
                    _navigateButtonListener.OnBackNavigated();
                break;
            case R.id.card_next:
                //todo 다음 fragment 이동루틴
                if(_navigateButtonListener != null)
                    _navigateButtonListener.OnNextNavigated();
                break;
        }
    }
    private void initViews(){
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_hospital_footer, this, true);

        _cardBack = (CardView) findViewById(R.id.card_back);
        _cardNext = (CardView) findViewById(R.id.card_next);

        _cardBack.setOnClickListener(this);
        _cardNext.setOnClickListener(this);
    }
    public void setButtonLayout(SHOWTYPE type){
        switch(type){
            case BACK:
                ShowType = SHOWTYPE.BACK;
                if(_cardBack != null && _cardBack.getVisibility() != VISIBLE){
                    _cardBack.setVisibility(VISIBLE);
                }
                if(_cardNext != null && _cardNext.getVisibility() != GONE){
                    _cardNext.setVisibility(GONE);
                }
                break;
            case NEXT:
                ShowType = SHOWTYPE.NEXT;
                if(_cardBack != null && _cardBack.getVisibility() != GONE){
                    _cardBack.setVisibility(GONE);
                }
                if(_cardNext != null && _cardNext.getVisibility() != VISIBLE){
                    _cardNext.setVisibility(VISIBLE);
                }
                break;
            case BOTH:
                ShowType = SHOWTYPE.BOTH;
                if(_cardBack != null && _cardBack.getVisibility() != VISIBLE){
                    _cardBack.setVisibility(VISIBLE);
                }
                if(_cardNext != null && _cardNext.getVisibility() != VISIBLE){
                    _cardNext.setVisibility(VISIBLE);
                }
                break;
            case NONE:
                ShowType = SHOWTYPE.NONE;
                if(_cardBack != null){
                    _cardBack.setVisibility(GONE);
                }
                if(_cardNext != null){
                    _cardNext.setVisibility(GONE);
                }
                break;
        }
    }
    public void setNavigateButtonListener(HospitalBaseActivity.NavigateButtonListener listener){
        this._navigateButtonListener = listener;
    }
}
