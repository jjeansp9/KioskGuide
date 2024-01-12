package com.avad.humancare.kiosk.issue.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssuanceKeypadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssuanceKeypadFragment extends IssuanceBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView mCountTv;
    private String countStr="1";

    public IssuanceKeypadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IssuanceAmountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssuanceKeypadFragment newInstance(String param1, String param2) {
        IssuanceKeypadFragment fragment = new IssuanceKeypadFragment();
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
        return inflater.inflate(R.layout.fragment_issuance_keypad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_go_main).setOnClickListener(this);
        view.findViewById(R.id.btn_go_back).setVisibility(View.GONE);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);

        view.findViewById(R.id.btn_1).setOnClickListener(this);
        view.findViewById(R.id.btn_2).setOnClickListener(this);
        view.findViewById(R.id.btn_3).setOnClickListener(this);
        view.findViewById(R.id.btn_4).setOnClickListener(this);
        view.findViewById(R.id.btn_5).setOnClickListener(this);
        view.findViewById(R.id.btn_6).setOnClickListener(this);
        view.findViewById(R.id.btn_7).setOnClickListener(this);
        view.findViewById(R.id.btn_8).setOnClickListener(this);
        view.findViewById(R.id.btn_9).setOnClickListener(this);
        view.findViewById(R.id.btn_backspace).setOnClickListener(this);

        mCountTv = (TextView) view.findViewById(R.id.count_txt);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_ok :
                IssuanceBaseFragment fragment = new IssuanceFeeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("param1", countStr);
                fragment.setArguments(bundle);
                ((IssuanceMainActivity) getActivity()).addFragmentStack(fragment,"fee");
                break;
        }

        if(view.getTag() != null) {
            countStr = view.getTag().toString();
            mCountTv.setText(countStr + getString(R.string.issuance_unit));
        }
    }

}