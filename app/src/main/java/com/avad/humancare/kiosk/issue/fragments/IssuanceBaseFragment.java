package com.avad.humancare.kiosk.issue.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;
import com.avad.humancare.kiosk.util.Utils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class IssuanceBaseFragment extends Fragment implements View.OnClickListener {

    private FragmentManager mFragmentManager;
    public TextView mHeaderTitleView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go_main :
                ((IssuanceMainActivity)getActivity()).setFragmentMainStack();
                mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Log.e("IssuanceBaseFragment", "FragmentManager popBackStack Count : "+mFragmentManager.getBackStackEntryCount());
                break;

            case R.id.btn_go_back :
                mFragmentManager.popBackStack();
                Log.e("IssuanceBaseFragment", "FragmentManager popBackStack Count : "+mFragmentManager.getBackStackEntryCount());
                break;
        }
    }
}
