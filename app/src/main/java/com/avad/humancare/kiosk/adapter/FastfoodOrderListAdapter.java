package com.avad.humancare.kiosk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.fastfood.FastfoodMenuActivity;
import com.avad.humancare.kiosk.util.Utils;
import com.avad.humancare.kiosk.model.FastfoodMenuItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FastfoodOrderListAdapter extends RecyclerView.Adapter<FastfoodOrderListAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<FastfoodMenuItem> mList = null;

    public FastfoodOrderListAdapter(Context context, ArrayList<FastfoodMenuItem> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fastfood_order_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = position;
        final FastfoodMenuItem item = mList.get(position);

        holder.imgView.setBackgroundResource(item.menuImgId);
        holder.nameTv.setText(item.menuNameId);
        holder.priceTv.setText(Utils.getPriceFormat(mContext, (item.price+item.additionalPrice), true));
        holder.countTv.setText(String.valueOf(item.count));

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.remove(pos);
                ((FastfoodMenuActivity)mContext).updateOrderDetailView();
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = item.count;
                if(count > 1) {
                    count = count - 1;
                }
                holder.countTv.setText(""+count);
                item.count = count;
                ((FastfoodMenuActivity)mContext).updateOrderDetailView();
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = item.count;
                count = count + 1;
                holder.countTv.setText(""+count);
                item.count = count;
                ((FastfoodMenuActivity)mContext).updateOrderDetailView();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView nameTv, priceTv, countTv;
        Button btnDel, btnMinus, btnPlus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView = (ImageView) itemView.findViewById(R.id.menu_img);
            nameTv = (TextView) itemView.findViewById(R.id.menu_name);
            priceTv = (TextView) itemView.findViewById(R.id.menu_price);
            countTv = (TextView) itemView.findViewById(R.id.menu_count);
            btnDel = (Button) itemView.findViewById(R.id.btn_del);
            btnMinus = (Button) itemView.findViewById(R.id.btn_minus);
            btnPlus = (Button) itemView.findViewById(R.id.btn_plus);
        }
    }
}
