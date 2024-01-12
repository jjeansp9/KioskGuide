package com.avad.humancare.kiosk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.model.IssuanceMenuItem;

import java.util.ArrayList;

public class IssuanceMenuListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<IssuanceMenuItem> mList;
    private ItemClickListener mListener;

    public interface ItemClickListener {
        void onItemClick(int pos, IssuanceMenuItem item);
    }

    public void setItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    public IssuanceMenuListAdapter(Context context, ArrayList<IssuanceMenuItem> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if(view == null) {
            view = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_issuance_menu_item, null);

            holder = new ViewHolder();
            holder.titleTv = (TextView) view.findViewById(R.id.title);
            holder.subTitleTv = (TextView) view.findViewById(R.id.sub_title);
            view.setTag(holder);

        } else {
            holder =(ViewHolder) view.getTag();
        }

        IssuanceMenuItem item = mList.get(i);

        holder.titleTv.setText(item.title);
        if(item.fee == 0) {
            holder.subTitleTv.setText(item.subTitle);
        } else {
            holder.subTitleTv.setText(item.fee + mContext.getString(R.string.won));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null) mListener.onItemClick(i, item);
            }
        });

        return view;
    }

    class ViewHolder {
        TextView titleTv, subTitleTv;
    }
}
