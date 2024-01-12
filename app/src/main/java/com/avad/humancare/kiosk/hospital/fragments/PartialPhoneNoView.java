package com.avad.humancare.kiosk.hospital.fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.avad.humancare.kiosk.R;

public class PartialPhoneNoView extends ConstraintLayout {

    private Context _context;
    Spinner spinner;
    TextView tvNumber;
    public PartialPhoneNoView(Context context) {
        super(context);
        this._context = context;
        initViews();
    }

    public PartialPhoneNoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        initViews();
    }
    private void initViews() {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_partial_phone_no, this, true);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(_context, R.array.array_telecommunication, R.layout.layout_spinner_item);
        adapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        tvNumber = findViewById(R.id.tv_number);
    }
    public void inputNumber(String key){
        StringBuilder sb = new StringBuilder();
        if(tvNumber != null){
            sb.append(tvNumber.getText());
        }
        switch(key){
            case CommonSubIdentifyFragment.BUTTON_BACKSPACE:
                if(sb.length() > 0){
                    sb.deleteCharAt(sb.length() - 1);
                }
                break;
            case CommonSubIdentifyFragment.BUTTON_CLEAR:
                sb.setLength(0);
                break;
            default:
                if(sb.length() <11) {
                    sb.append(key);
                }
                break;
        }
        tvNumber.setText(sb.toString());
    }
    public void clearNumber(){
        if(tvNumber != null) tvNumber.setText("");
    }

    /**
     * 전화번호 유효성 체크
     * @return
     */
    public boolean isValidData(){
        if(tvNumber == null) return false;
        String no = tvNumber.getText().toString();
        if(no.length() == 11 && no.startsWith("010")){
            return true;
        }
        return false;
    }
}
