package com.avad.humancare.kiosk.hospital.fragments;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.dialog.PopupDialog;
import com.avad.humancare.kiosk.hospital.HospitalFooterView;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.util.Common;

public abstract class SubBaseFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "subbasefragment";
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((HospitalFragmentActivity)getActivity()).setNavigateButtonEnabled(true);
        ((HospitalFragmentActivity)getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BOTH);
    }

    /**
     * footer 에서 '확인' 버튼을 눌렀을 때 callback 에서 호출
     */
    public abstract void NavigateNextFragment();
    public abstract void NavigatePrevFragment();

    public void showNormalDialog(String title, String content) {

        PopupDialog dialog = new PopupDialog(getActivity());
        dialog.setDialogType(Common.KIOSK_TYPE.HOSPITAL);
        dialog.setTitle(title);
        dialog.setContent(content);
        dialog.setOnOkButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
