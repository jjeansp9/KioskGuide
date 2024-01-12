package com.avad.humancare.kiosk.issue.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.IssuanceMenuListAdapter;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;
import com.avad.humancare.kiosk.model.IssuanceMenuItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssuanceMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssuanceMenuFragment extends IssuanceBaseFragment {

    private String TAG = IssuanceMenuFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mViewType;

    private Context mContext;
    private GridView mGridView;
    private IssuanceMenuListAdapter mListAdapter;
    private ArrayList<IssuanceMenuItem> mList = new ArrayList<>();

    public IssuanceMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment IssuanceMainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssuanceMenuFragment newInstance(String param1) {
        IssuanceMenuFragment fragment = new IssuanceMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_PARAM1)) {
            mViewType = getArguments().getString(ARG_PARAM1);
        } else {
            mViewType = IssuanceMainActivity.MAIN_MENU;
        }
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_issuance_main_menu, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleTv = (TextView) view.findViewById(R.id.title);
        TextView subTitleTv = (TextView) view.findViewById(R.id.sub_title);
//        SpannableStringBuilder ssb = new SpannableStringBuilder();
//        ssb.append(getString(R.string.issuance_title_main));
//        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.issuance_font_color_blue)), 9, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.issuance_font_color_blue)), 14, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        titleTv.setText(getString(R.string.issuance_title_main));
        subTitleTv.setText(R.string.issuance_title_main_sub);
        ((IssuanceMainActivity)getActivity()).setGuideMsg(getString(R.string.issuance_guide_msg_main));

        mGridView = (GridView) view.findViewById(R.id.gridView);
        ViewGroup bottomBtnLy = view.findViewById(R.id.bottom_btn_layout);
        view.findViewById(R.id.btn_go_main).setOnClickListener(this);
        view.findViewById(R.id.btn_go_back).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setVisibility(View.GONE);

        mList.clear();

        // 주민등록
        if(mViewType.equals(IssuanceMainActivity.SUB_MENU_JUMIN)) {
            mList.add(new IssuanceMenuItem("주민등록표(등본)", "200원", 200, IssuanceMainActivity.SUB_MENU_JUMIN));
            mList.add(new IssuanceMenuItem("주민등록표(초본)", "200원", 200, IssuanceMainActivity.SUB_MENU_JUMIN));
            mGridView.setNumColumns(1);
            bottomBtnLy.setVisibility(View.VISIBLE);
        }
        // 가족관계등록부
        else if(mViewType.equals(IssuanceMainActivity.SUB_MENU_FAMILY)) {
            mList.add(new IssuanceMenuItem("가족관계증명서", "500원", 500, IssuanceMainActivity.SUB_MENU_FAMILY));
            mGridView.setNumColumns(1);
            bottomBtnLy.setVisibility(View.VISIBLE);
        }
        // 부동산등기사항증명서
        else if(mViewType.equals(IssuanceMainActivity.SUB_MENU_REAL_ESTATE)) {

        }
        else {    //IssuanceMainActivity.MAIN_MENU
            int[] menuTitleArr = {R.string.issuance_menu1, R.string.issuance_menu2, R.string.issuance_menu3};
            String[] menuSubTitleArr = {"200", "500", "1000"};

            for(int i=0; i<menuTitleArr.length; i++) {
                mList.add(new IssuanceMenuItem(getString(menuTitleArr[i]), menuSubTitleArr[i], Integer.parseInt(menuSubTitleArr[i]) , IssuanceMainActivity.MAIN_MENU+i));
            }
            mGridView.setNumColumns(1);
            bottomBtnLy.setVisibility(View.INVISIBLE);
        }

        mListAdapter = new IssuanceMenuListAdapter(mContext, mList);
        mListAdapter.setItemClickListener(new IssuanceMenuListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos, IssuanceMenuItem item) {
                //Log.e(TAG, pos + " >> onItemClick() : " + item.title + " / " + item.category);
                IssuanceMainActivity.mMenuItem = item;

                switch (item.category) {
                    case IssuanceMainActivity.MAIN_MENU_0:
                        ((IssuanceMainActivity) getActivity()).addFragmentStack(IssuanceMenuFragment.newInstance(IssuanceMainActivity.SUB_MENU_JUMIN), IssuanceMainActivity.SUB_MENU_JUMIN);
                        break;

                    case IssuanceMainActivity.MAIN_MENU_1:
                        ((IssuanceMainActivity) getActivity()).addFragmentStack(IssuanceMenuFragment.newInstance(IssuanceMainActivity.SUB_MENU_FAMILY), IssuanceMainActivity.SUB_MENU_FAMILY);
                        break;

                    case IssuanceMainActivity.MAIN_MENU_2:
                        ((IssuanceMainActivity) getActivity()).addFragmentStack(RealEstateMenuFragment.newInstance(RealEstateMenuFragment.ARG_MENU_MAIN), RealEstateMenuFragment.ARG_MENU_MAIN);
                        break;

                    case IssuanceMainActivity.SUB_MENU_JUMIN:
                        ((IssuanceMainActivity) getActivity()).addFragmentStack(new IssuanceInputResidentNumFragment(),"inputNum");
                        break;

                    case IssuanceMainActivity.SUB_MENU_FAMILY:
                        ((IssuanceMainActivity) getActivity()).addFragmentStack(IssuanceNotifyFragment.newInstance(IssuanceNotifyFragment.ARG_FAMILY_1, ""),IssuanceNotifyFragment.ARG_FAMILY_1);
                        break;

                    case IssuanceMainActivity.SUB_MENU_REAL_ESTATE:
                        break;
                }
            }
        });
        mGridView.setAdapter(mListAdapter);
    }

}