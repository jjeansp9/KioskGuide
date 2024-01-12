package com.avad.humancare.kiosk.issue.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;
import com.avad.humancare.kiosk.util.HangulMaker;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RealEstateInputAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RealEstateInputAddressFragment extends IssuanceBaseFragment {

    private String TAG = RealEstateInputAddressFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String ARG_DISTRICT = "district";   // 시군구 목록
    public static final String ARG_APT = "apt"; // 아파트 목록
    public static final String ARG_DETAIL = "detail"; // 상세주소 입력

    private ViewGroup mModeAreaViewGroup, mModeDetailViewGroup;

    private TextView mSearchKeywordTv, mKeywordDetailTv;
    private TextView mArea1Tv, mArea2Tv;
    private GridLayout mGridLayout;

    private BaseInputConnection mInputConnection;
    private EditorInfo mEditorInfo;
    private HangulMaker mHangulMaker;

    private String[] mKeypadArr= {
            "ㄲ", "ㄸ", "ㅃ", "ㅆ", "ㅉ", "", "ㅏ", "ㅑ", "ㅓ", "ㅕ",
            "ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ", "", "ㅗ", "ㅛ", "ㅜ", "ㅠ",
            "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ", "", "ㅡ", "ㅣ", "←", "@",
            "ㅋ", "ㅌ", "ㅍ", "ㅎ", "-", "└┘", "ㅐ", "ㅔ", "ㅒ", "ㅖ",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };

    public RealEstateInputAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RealEstateSearchAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RealEstateInputAddressFragment newInstance(String param1, String param2) {
        RealEstateInputAddressFragment fragment = new RealEstateInputAddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_real_estate_input_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_go_main).setOnClickListener(this);
        view.findViewById(R.id.btn_go_back).setOnClickListener(this);

        mHeaderTitleView = view.findViewById(R.id.header_title);
        mSearchKeywordTv = view.findViewById(R.id.search_keyword);
        mModeAreaViewGroup = view.findViewById(R.id.mode_basic);
        mModeDetailViewGroup = view.findViewById(R.id.mode_detail);
        mKeywordDetailTv = view.findViewById(R.id.keyword_detail);
        mArea1Tv = view.findViewById(R.id.area1);
        mArea2Tv = view.findViewById(R.id.area2);
        mArea1Tv.setOnClickListener(this);
        mArea2Tv.setOnClickListener(this);
        mGridLayout = view.findViewById(R.id.gridLayout);

        if(mParam1.equals(ARG_DISTRICT)) {
            mHeaderTitleView.setText(R.string.issuance_real_estate_title4);
            mSearchKeywordTv.setText("인천광역시");
            mModeAreaViewGroup.setVisibility(View.VISIBLE);
            mModeDetailViewGroup.setVisibility(View.GONE);
        }
        else if(mParam1.equals(ARG_APT)) {
            mHeaderTitleView.setText(R.string.issuance_real_estate_title4);
            mSearchKeywordTv.setText(mParam2);
            mModeAreaViewGroup.setVisibility(View.VISIBLE);
            mModeDetailViewGroup.setVisibility(View.GONE);
            mArea1Tv.setText("경남아파트");
            mArea2Tv.setText("경신아파트");

        } else {
            mHeaderTitleView.setText(R.string.issuance_real_estate_title5);
            mSearchKeywordTv.setText(mParam2);
            mModeAreaViewGroup.setVisibility(View.GONE);
            mModeDetailViewGroup.setVisibility(View.VISIBLE);
            view.findViewById(R.id.btn_ok).setOnClickListener(this);

            // 키보드 이벤트 처리
            TextKeyListener keyListener = TextKeyListener.getInstance();
            mKeywordDetailTv.setFocusable(true);
            mKeywordDetailTv.setFocusableInTouchMode(true); // 이게 있어야 TextView 안의 text가 변경된다
            mKeywordDetailTv.requestFocus();
            mKeywordDetailTv.setKeyListener(keyListener);

            // Text 정보를 전달하기 위해 InputConnection을 생성
            createInputConnection();
            mHangulMaker = new HangulMaker(mInputConnection);

            drawKeypadButtonView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mHangulMaker != null) {
            mHangulMaker.clear();
        }
        if(mInputConnection != null){
            mInputConnection.getEditable().clear();
        }
    }

    public BaseInputConnection createInputConnection() {

        if(mInputConnection == null) {
            mInputConnection = new EditableInputConnection(mKeywordDetailTv);
        }

        return mInputConnection;
    }

    public void showButtonEvent(String key) {

        CharSequence cursorcs = mInputConnection.getSelectedText(InputConnection.GET_TEXT_WITH_STYLES);
        if(cursorcs != null && cursorcs.length() >= 2) {
            mHangulMaker.clear();
        }

        if(key.equals("←")) {
            mHangulMaker.delete();
        }
        else if(key.equals("-")) {
            mHangulMaker.directlyCommit();
            mInputConnection.commitText(key, 1);
        }
        else if(key.equals("└┘")) {
            mHangulMaker.commitSpace();
        }
        else {
            try {
                // 숫자 여부 체크
                int num = Integer.parseInt(key);
                mHangulMaker.directlyCommit();
                mInputConnection.commitText(key, 1);

            } catch(NumberFormatException ex) {
                // 숫자가 아닌 경우 글자 처리
                mHangulMaker.commit(key.charAt(0));
            }
        }
    }

    private void drawKeypadButtonView() {
        mGridLayout.removeAllViews();
        int column = 10;
        int row = mKeypadArr.length / column;
        mGridLayout.setColumnCount(column);
        mGridLayout.setRowCount(row+1);
        for(int i=0, c=0, r=0; i<mKeypadArr.length; i++, c++) {
            if(c == column) {
                c = 0;
                r++;
            }

            Button button = new Button(getContext());
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams(mGridLayout.getLayoutParams());
            lp.width = 58;
            lp.height = 60;
            if(c < column-1) {
                lp.rightMargin = 6;
            }
            if(r < row-1) {
                lp.bottomMargin = 8;
            }
            lp.setGravity(Gravity.FILL);

            if(r==2 && c==8) {
                lp.columnSpec = GridLayout.spec(c, 2, GridLayout.FILL, 1.f);
                lp.rowSpec = GridLayout.spec(r, 1, GridLayout.FILL, 1.f);
            } else
            {
                lp.columnSpec = GridLayout.spec(c, 1, GridLayout.FILL, 1.f);
                lp.rowSpec = GridLayout.spec(r, 1, GridLayout.FILL, 1.f);
            }

            button.setLayoutParams(lp);
            button.setBackgroundResource(R.drawable.img_box04_selector);
            button.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color));
            button.setTextSize(getResources().getDimension(R.dimen.font_size_small));

            String str = mKeypadArr[i];
            if(str.isEmpty()) {
                button.setVisibility(View.INVISIBLE);
            }
            else if(str.equals("@")) {
                button.setVisibility(View.GONE);
            }
            else {
                button.setVisibility(View.VISIBLE);
                button.setText(str);
            }
            button.setTag(str);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showButtonEvent((String)view.getTag());
                }
            });

            mGridLayout.addView(button);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.area1:
            case R.id.area2:
                String address = mSearchKeywordTv.getText().toString() + " ";
                if(view.getId() == R.id.area1) {
                    address += mArea1Tv.getText().toString();
                } else {
                    address += mArea2Tv.getText().toString();
                }
                if(mParam1.equals(ARG_DISTRICT)) {
                    ((IssuanceMainActivity) getActivity()).addFragmentStack(RealEstateInputAddressFragment.newInstance(RealEstateInputAddressFragment.ARG_APT, address), RealEstateInputAddressFragment.ARG_APT);
                } else {
                    ((IssuanceMainActivity) getActivity()).addFragmentStack(RealEstateInputAddressFragment.newInstance(RealEstateInputAddressFragment.ARG_DETAIL, address), RealEstateInputAddressFragment.ARG_DETAIL);
                }
                break;

            case R.id.btn_ok :
                String str = mKeywordDetailTv.getText().toString().trim();
                if(str.length() > 0) {
                    str = mSearchKeywordTv.getText().toString() + " " + str;
                    ((IssuanceMainActivity) getActivity()).addFragmentStack(RealEstateIssueFragment.newInstance(str), "issue");
                }
                break;
        }
    }

    public class EditableInputConnection extends BaseInputConnection {

        private View _view;
        private SpannableStringBuilder _editable;

        public EditableInputConnection(View targetView) {
            super(targetView, true);
            this._view = targetView;
            this._editable = (SpannableStringBuilder) Editable.Factory.getInstance().newEditable("");
        }

        public Editable getEditable() {
            return _editable;
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            super.commitText(text, newCursorPosition);
            //Log.e(TAG, newCursorPosition + " >> KHJ++ commitText() : " + text  + " / _editable=" + _editable.toString());
            mKeywordDetailTv.setText(_editable.toString());
            return true;
        }

        @Override
        public boolean setComposingText(CharSequence text, int newCursorPosition) {
            super.setComposingText(text, newCursorPosition);
            // 입력 바로 전달 받기
            //Log.e(TAG, newCursorPosition + " >> KHJ++ setComposingText() : " + text );

            if(text.equals("←")) {
                _editable.delete(_editable.length()-1, _editable.length());
            }

            //Log.e(TAG, newCursorPosition + " >> KHJ++ setComposingText() >>>>>> : _editable=" + _editable.toString() );
            mKeywordDetailTv.setText(_editable.toString());
            return true;
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            super.deleteSurroundingText(beforeLength, afterLength);
            //Log.e(TAG, "KHJ++ deleteSurroundingText() : before = " + beforeLength + " / afterLength="+afterLength + " / "+_editable.toString());
            mKeywordDetailTv.setText(_editable.toString());
            return true;
        }
    }

}