package com.avad.humancare.kiosk.issue.fragments;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RealEstateSelectAddressMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RealEstateSelectAddressMapFragment extends IssuanceBaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public static final String ARG_ADDRESS = "address";
    public static final String ARG_MAP = "map";

    private String[] mLeftCityNames = {"서울특별시", "인천광역시", "충청북도", "세종특별자치시", "대전광역시", "충청남도", "전라북도","광주광역시", "전라남도"};
    private String[] mRightCityNames = {"경기도", "강원도", "경상북도", "대구광역시", "울산광역시", "부산광역시", "경상남도"};

    private ViewGroup mMapLeftLy, mMapRightLy;

    public RealEstateSelectAddressMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RealEstateSelectAddressMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RealEstateSelectAddressMapFragment newInstance(String param1) {
        RealEstateSelectAddressMapFragment fragment = new RealEstateSelectAddressMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_real_estate_select_address_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_go_main).setOnClickListener(this);
        view.findViewById(R.id.btn_go_back).setOnClickListener(this);
        view.findViewById(R.id.btn_road_address).setOnClickListener(this);
        view.findViewById(R.id.btn_parcel_address).setOnClickListener(this);

        mHeaderTitleView = view.findViewById(R.id.header_title);

        mMapLeftLy = view.findViewById(R.id.city_left_ly);
        mMapRightLy = view.findViewById(R.id.city_right_ly);

        if(mParam1.equals(ARG_ADDRESS)) {
            mHeaderTitleView.setText(R.string.issuance_real_estate_title2);
            view.findViewById(R.id.layout_address).setVisibility(View.VISIBLE);
            view.findViewById(R.id.layout_map).setVisibility(View.GONE);
        } else
        {   // ARG_MAP
            mHeaderTitleView.setText(R.string.issuance_real_estate_title3);
            view.findViewById(R.id.layout_address).setVisibility(View.GONE);
            view.findViewById(R.id.layout_map).setVisibility(View.VISIBLE);

            mMapLeftLy.removeAllViews();
            for(int i=0; i<mLeftCityNames.length; i++) {
                if(i == 1) {
                    mMapLeftLy.addView(createCityTextView(mLeftCityNames[i], true));
                } else
                    mMapLeftLy.addView(createCityTextView(mLeftCityNames[i], false));
            }

            mMapRightLy.removeAllViews();
            for(int i=0; i<mRightCityNames.length; i++) {
                mMapRightLy.addView(createCityTextView(mRightCityNames[i], false));
            }

        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_road_address :
            case R.id.btn_parcel_address:
                ((IssuanceMainActivity) getActivity()).addFragmentStack(RealEstateSelectAddressMapFragment.newInstance(RealEstateSelectAddressMapFragment.ARG_MAP), RealEstateSelectAddressMapFragment.ARG_MAP);
                break;
        }
    }

    private TextView createCityTextView(String name, boolean isAvailable) {
        TextView tv = new TextView(getContext());
        tv.setText(name);
        tv.setTextSize(20);

        if(isAvailable) {
            tv.setTextColor(ContextCompat.getColor(getContext(), R.color.issuance_font_color_red));
            tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);   // 밑줄 긋기
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((IssuanceMainActivity) getActivity()).addFragmentStack(RealEstateInputAddressFragment.newInstance(RealEstateInputAddressFragment.ARG_DISTRICT, ""), RealEstateInputAddressFragment.ARG_DISTRICT);
                }
            });
        } else {
            tv.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color));
        }

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.topMargin = 10;
        param.bottomMargin = 10;
        tv.setLayoutParams(param);

        return tv;
    }
}