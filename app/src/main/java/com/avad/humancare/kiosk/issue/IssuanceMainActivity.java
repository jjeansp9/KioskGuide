package com.avad.humancare.kiosk.issue;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.issue.fragments.IssuanceBaseFragment;
import com.avad.humancare.kiosk.issue.fragments.IssuanceMenuFragment;
import com.avad.humancare.kiosk.model.IssuanceMenuItem;
import com.avad.humancare.kiosk.util.Utils;

public class IssuanceMainActivity extends FragmentActivity implements View.OnClickListener {

    private String TAG = IssuanceMainActivity.class.getSimpleName();

    public static final String MAIN_MENU = "main_menu";
    public static final String MAIN_MENU_0 = "main_menu0";  // 메인-주민등록
    public static final String MAIN_MENU_1 = "main_menu1";  // 메인-가족관계등록부
    public static final String MAIN_MENU_2 = "main_menu2";  // 메인-부동산등기사항증명서
    public static final String SUB_MENU_JUMIN = "sub_jumin";    // 서브메뉴 - 주민등록
    public static final String SUB_MENU_FAMILY = "sub_family";  // 서브메뉴 - 가족관계
    public static final String SUB_MENU_REAL_ESTATE = "sub_real_estate";    // 서브메뉴-부동산
    public static final String INPUT_RESIDENT_NUM = "input_resident_num";   // 주민번호 입력

    public static final String BUTTON_BACKSPACE = "BACKSPACE";
    public static final String BUTTON_CLEAR = "CLEAR";

    private Context mContext;
    private TextView mGuideMsg;

    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private int mSelectedMenu = 0;

    public static IssuanceMenuItem mMenuItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issuance_main);

        mContext = this;

        findViewById(R.id.btn_back_main).setOnClickListener(this);
        findViewById(R.id.btn_home).setOnClickListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fr = mFragmentManager.findFragmentById(R.id.fragments);
                if(fr != null) mFragment = (IssuanceBaseFragment) fr;
                Log.e(TAG, "onBackStack : fragment is " + mFragment.getClass().getName());
            }
        });

        if (savedInstanceState == null || mFragment == null) {
            switch (mSelectedMenu) {
                case 0 :
                    setFragmentMainStack();
                    break;
            }
        }

        mGuideMsg = (TextView) findViewById(R.id.text_msg);

    }

    public void setFragmentMainStack() {
        mFragment = new IssuanceMenuFragment();
        mFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragments, mFragment, MAIN_MENU).commit();
    }

    /**
     * 다음 Fragment 추가 (backstack에도 추가)
     * @param fragment
     * @param tag
     */
    public void addFragmentStack(IssuanceBaseFragment fragment, String tag){
//        setNavigateButtonEnabled(false);
        mFragmentManager.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.fragments, fragment, tag)
                .addToBackStack(tag).commit();
    }

    public void setGuideMsg(String text) {
        if(text == null) {
            text = "";
        }
        mGuideMsg.setText(text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_main :
                finish();
                break;

            case R.id.btn_home :
                Utils.gotoElderApp(this);
                break;

        }
    }
}

