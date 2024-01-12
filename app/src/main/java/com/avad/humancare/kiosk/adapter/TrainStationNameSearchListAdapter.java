package com.avad.humancare.kiosk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.train.TrainReservationActivity;

import java.util.ArrayList;

public class TrainStationNameSearchListAdapter extends BaseAdapter implements Filterable {

    private TrainReservationActivity mActivity;
    private ArrayList<String> mList = null;
    private ArrayList<String> mFilteredList = null;
    private Filter mFilter;

    public TrainStationNameSearchListAdapter(Activity activity, ArrayList<String> list) {
        mActivity = (TrainReservationActivity)activity;
        mList = list;
        mFilteredList = list;
    }

    @Override
    public int getCount() {
        if(mFilteredList == null) return 0;
        return mFilteredList.size();
    }

    @Override
    public Object getItem(int i) {
        return mFilteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        ViewHolder holder = null;

        if(view == null) {
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.train_station_name_search_list_item, null);
            holder = new ViewHolder();
            holder.stateTxt = (TextView) view.findViewById(R.id.station_txt);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final String data = mFilteredList.get(i);

        if(data != null) {
            holder.stateTxt.setText(data);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.setStationName(data);
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        if(mFilter == null) {
            mFilter = new ListFilter();
        }
        return mFilter;
    }

    private class ViewHolder {
        TextView stateTxt;
    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                result.values = mList;
                result.count = mList.size();
            } else {
                ArrayList<String> list = new ArrayList<>();

                for(String str : mList) {
                    if(str.contains(charSequence.toString())) {
                        list.add(str);
                    }
                }

                result.values = list;
                result.count = list.size();

            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mFilteredList = (ArrayList<String>) filterResults.values;

            if(filterResults.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
