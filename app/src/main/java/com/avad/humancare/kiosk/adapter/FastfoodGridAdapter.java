package com.avad.humancare.kiosk.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.fastfood.FastfoodMenuActivity;
import com.avad.humancare.kiosk.util.Utils;
import com.avad.humancare.kiosk.model.FastfoodMenuItem;

import java.util.ArrayList;

public class FastfoodGridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<FastfoodMenuItem> mList = null;

    public FastfoodGridAdapter(Context context, ArrayList<FastfoodMenuItem> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if(mList == null) return 0;
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        if(mList != null) return mList.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            holder = new ViewHolder();
            view = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.fastfood_menu_right_item, null);

            holder.imgView = (ImageView) view.findViewById(R.id.menu_img);
            holder.nameTv = (TextView) view.findViewById(R.id.menu_name);
            holder.priceTv = (TextView) view.findViewById(R.id.menu_price);
            view.setTag(holder);

        } else {
            holder =(ViewHolder) view.getTag();
        }

        if(mList != null) {
            final FastfoodMenuItem item = mList.get(i);
            holder.imgView.setBackgroundResource(item.menuImgId);
            holder.nameTv.setText(item.menuNameId);
            holder.priceTv.setText(Utils.getPriceFormat(mContext, item.price, true));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("FastfoodGridAdapter", "onClick() : " + item.type);
                    if(item.type == FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_BURGERSET) {
                        ((FastfoodMenuActivity)mContext).selectSideMenuOfSetMenu(item);
                    } else {
                        ((FastfoodMenuActivity)mContext).updateOrderListView(item);
                    }

                }
            });

        }


        return view;
    }

    class ViewHolder {
        ImageView imgView;
        TextView nameTv, priceTv;
    }
}
