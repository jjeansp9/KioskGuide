package com.avad.humancare.kiosk.hospital.fragments.insurance;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.hospital.HospitalFooterView;
import com.avad.humancare.kiosk.hospital.HospitalFragmentActivity;
import com.avad.humancare.kiosk.hospital.fragments.SubBaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InsuranceSelCompanyFragment extends SubBaseFragment {

    private GridView gvSelector;    //선택할 grid
    private InsuranceCompanySelectorAdapter mSelectorAdapter;
    private GridView gvSelected;    //선택된 grid
    private InsuranceCompanySelectedAdapter mSelectedAdapter;

    private InsuranceDataManager mManager;
    private Context mContext;
    private TextView txtCountSelected;

    private List<HashMap<String, String>> mSelectorList = new ArrayList<HashMap<String, String>>();
    private int pagingNum = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManager = InsuranceDataManager.getInstance();
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insurance_sel_company, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        txtCountSelected = (TextView) v.findViewById(R.id.txt_sel_cnt);
        ((LinearLayout) v.findViewById(R.id.ly_prev)).setOnClickListener(this);
        ((LinearLayout) v.findViewById(R.id.ly_next)).setOnClickListener(this);

        gvSelected = (GridView) v.findViewById(R.id.gv_selected);
        mSelectedAdapter = new InsuranceCompanySelectedAdapter(mContext, mManager.getInsuranceArray());
        mSelectedAdapter.setOnBtnItemListener(new InsuranceCompanySelectedAdapter.OnBtnItemListener() {
            @Override
            public void onClickDelete(int i) {
                mManager.getInsuranceArray().remove(i);
                reloadAllList(0);
            }
        });
        gvSelected.setAdapter(mSelectedAdapter);

        gvSelector = (GridView) v.findViewById(R.id.gv_selector);
        mSelectorAdapter = new InsuranceCompanySelectorAdapter(mContext, mSelectorList);
        gvSelector.setAdapter(mSelectorAdapter);
        gvSelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean isChecked = mSelectorList.get(i).get("isChecked").equals("Y");
                if(mSelectorList.get(i).containsKey("index")) {
                    try {
                        int index = Integer.parseInt(Objects.requireNonNull(mSelectorList.get(i).get("index")));
                        if(isChecked) {
                            mManager.getInsuranceArray().remove(mManager.getInsuranceArray().indexOf(index));
                        }else {
                            mManager.getInsuranceArray().add(index);
                        }
                    }catch (Exception e) {
                        Log.e("InsuranceSelCompanyFragment", "gvSelector >> "+Log.getStackTraceString(e));
                    }finally {
                        reloadAllList(0);
                    }
                }
            }
        });

        reloadAllList(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_prev: reloadAllList(-1); break;
            case R.id.ly_next: reloadAllList(1); break;
        }
    }

    //핸들러로 뺄까?
    public void reloadAllList(int add) {
        if(getActivity() != null) getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] names = InsuranceDataManager.arrInsurance;
                int maxPage = (int) Math.ceil(names.length / 9.0);
                int curPage = pagingNum + add;

                if(add != 0) {
                    if(add > 0 && maxPage < curPage) {
//                Toast.makeText(mContext, "다음내용없음", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(add < 0 && curPage == 0) {
//                Toast.makeText(mContext, "이전내용없음", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                pagingNum = curPage;

                mSelectorList.clear();
                int lastLength = Math.min((pagingNum * 9), names.length);
                for(int i = ((pagingNum-1) * 9); i < lastLength; i++) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("name", names[i]);
                    data.put("isChecked", (mManager.getInsuranceArray().contains(i))?"Y":"N");
                    data.put("index", String.valueOf(i));
                    mSelectorList.add(data);
                }

                txtCountSelected.setText(getString(R.string.hospital_insurance_sel_company_cnt, mManager.getInsuranceArray().size()));
                mSelectorAdapter.notifyDataSetChanged();
                mSelectedAdapter.notifyDataSetChanged();
            }
        });
        // 밑에 아답터
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BOTH);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Fragment 생명주기 최상위
        ((HospitalFragmentActivity) getActivity()).setNavigateButtonLayout(HospitalFooterView.SHOWTYPE.BOTH);
    }

    @Override
    public void NavigateNextFragment() {
        Log.e("InsuranceSelCompanyFragment", ">>> NavigateNextFragment");
        if(mManager.getInsuranceArray().size() > 0) {
            ((HospitalFragmentActivity) getActivity()).addFragmentStack(
                    InsuranceInfoFragment.newInstance(InsuranceInfoFragment.CONTENT_TO_USE),
                    InsuranceInfoFragment.CONTENT_TO_USE
            );
        }else {
            showNormalDialog("안내", "청구하실 보험사를 선택해주세요.");
        }
    }

    @Override
    public void NavigatePrevFragment() {

    }

    private static class InsuranceCompanySelectedAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<Integer> mSelectedList = new ArrayList<Integer>();

        // [[
        public OnBtnItemListener mListener = null;
        interface OnBtnItemListener {
            void onClickDelete(int i);
        }
        public void setOnBtnItemListener(OnBtnItemListener listener) {
            this.mListener = listener;
        }
        // ]]


        public InsuranceCompanySelectedAdapter(Context context, ArrayList<Integer> list) {
            this.mContext = context;
            this.mSelectedList = list;
        }

        @Override
        public int getCount() {
            return mSelectedList.size();
        }

        @Override
        public Object getItem(int i) {
            return mSelectedList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null) {
                holder = new ViewHolder();
                view = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_insurance_selected_item, null);

                holder.btnDel = (LinearLayout) view.findViewById(R.id.ly_del);
                holder.txtName = (TextView) view.findViewById(R.id.txt_name);
                view.setTag(holder);

            } else {
                holder =(ViewHolder) view.getTag();
            }

            int index = mSelectedList.get(i);

            holder.txtName.setText(InsuranceDataManager.arrInsurance[index]);
            holder.btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null) mListener.onClickDelete(i);
                }
            });


            return view;
        }

        class ViewHolder {
            LinearLayout btnDel;
            TextView txtName;
        }
    }


    private static class InsuranceCompanySelectorAdapter extends BaseAdapter {
        private Context mContext;
        private List<HashMap<String, String>> mSelectorList = new ArrayList<HashMap<String, String>>();

        public InsuranceCompanySelectorAdapter(Context context, List<HashMap<String, String>> list) {
            this.mContext = context;
            this.mSelectorList = list;
        }

        @Override
        public int getCount() {
            return mSelectorList.size();
        }

        @Override
        public Object getItem(int i) {
            return mSelectorList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null) {
                holder = new ViewHolder();
                view = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_insurance_selector_item, null);

                holder.lyMain = (LinearLayout) view.findViewById(R.id.ly_main);
                holder.txtName = (TextView) view.findViewById(R.id.txt_name);
                view.setTag(holder);

            } else {
                holder =(ViewHolder) view.getTag();
            }

            HashMap<String, String> info = mSelectorList.get(i);

            holder.txtName.setText(info.get("name"));
            holder.lyMain.setSelected(info.get("isChecked").equals("Y"));

            return view;
        }

        class ViewHolder {
            LinearLayout lyMain;
            TextView txtName;
        }
    }
}