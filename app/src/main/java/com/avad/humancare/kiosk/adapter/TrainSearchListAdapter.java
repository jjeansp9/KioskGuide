package com.avad.humancare.kiosk.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.model.TrainInfo;
import com.avad.humancare.kiosk.train.TrainSearchActivity;
import com.avad.humancare.kiosk.util.Common;
import com.avad.humancare.kiosk.util.Utils;

import java.util.ArrayList;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TrainSearchListAdapter extends BaseAdapter {

    private String TAG = TrainSearchListAdapter.class.getSimpleName();

    private TrainSearchActivity mActivity;
    private Context mContext;
    private ArrayList<TrainInfo> mList = null;
    private LayoutInflater mInflater;

    private int mSelectedPos = -1;
    private int mSelectedSeatType = -1;    // 0 : 일반실, 1 : 특우등

    public TrainSearchListAdapter(Activity activity, ArrayList<TrainInfo> list) {
        mActivity = (TrainSearchActivity)activity;
        mContext = mActivity.getBaseContext();
        mList = list;
        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = mInflater.inflate(R.layout.train_search_list_item, null);
            holder = new ViewHolder();
            holder.trainNameTv = (TextView) view.findViewById(R.id.train_name);
            holder.startTimeTv = (TextView) view.findViewById(R.id.start_time);
            holder.endTimeTv = (TextView) view.findViewById(R.id.end_time);
            holder.basicPriceTv = (TextView) view.findViewById(R.id.price1);
            holder.vipPriceTv = (TextView) view.findViewById(R.id.price2);
            holder.basicLayout = view.findViewById(R.id.basic_ly);
            holder.vipLayout = view.findViewById(R.id.vip_ly);
            holder.basicPriceSubTv = (TextView) view.findViewById(R.id.price1_subtitle);
            holder.rootLayout = view.findViewById(R.id.train_search_ly);
            holder.selectImg1 = (ImageView) view.findViewById(R.id.chooseImg1);
            holder.selectImg2 = (ImageView) view.findViewById(R.id.chooseImg2);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        TrainInfo item = mList.get(i);

        holder.trainNameTv.setText(item.name + "\n" + item.nameNum);
        holder.startTimeTv.setText(item.startTime);
        holder.endTimeTv.setText(item.endTime);
        holder.basicPriceTv.setText(Utils.getPriceFormat(mContext, item.basicPrice, false));
        holder.basicPriceSubTv.setVisibility(View.GONE);
        holder.basicLayout.setBackgroundResource(R.drawable.train_search_listitem_box_blue);
        holder.vipLayout.setBackgroundResource(R.drawable.train_search_listitem_box_blue);

        if(item.vipPrice != 0) {
            holder.vipPriceTv.setText(Utils.getPriceFormat(mContext, item.vipPrice, false));
            holder.vipLayout.setBackgroundResource(R.drawable.train_search_listitem_box_blue);
        } else {
            holder.vipPriceTv.setText("-");
            holder.vipLayout.setBackgroundColor(Color.WHITE);
        }

        // 매진
        if(item.isBasicSoldOut) {
            holder.basicPriceTv.setText(R.string.train_search_seat_soldout);
            holder.basicPriceTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_red));
            holder.basicLayout.setBackgroundResource(R.drawable.train_search_listitem_box_red);
        } else {
            holder.basicPriceTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
        }

        if(item.isVipSoldOut) {
            holder.vipPriceTv.setText(R.string.train_search_seat_soldout);
            holder.vipPriceTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_red));
            holder.vipLayout.setBackgroundResource(R.drawable.train_search_listitem_box_red);
        } else {
            holder.vipPriceTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
        }

        // 일반식 예약대기
        if (item.waitingForBasic) {
            holder.basicPriceTv.setText(R.string.train_search_reservation_seat);
            holder.basicPriceSubTv.setText(R.string.train_search_waiting_reservation);
            holder.basicPriceSubTv.setVisibility(View.VISIBLE);
            //holder.basicPriceTv.setTextSize(Dimension.DP, 14);
            holder.basicPriceTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_b));

            if(mActivity.mReservationInfo.isOneWayTicket == false) {
                // 왕복인 경우 예약대기 불가
                holder.basicPriceSubTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_b));
            } else {
                holder.basicPriceSubTv.setTextColor(ContextCompat.getColor(mContext, R.color.train_font_color_main));
            }

        } else {
            //holder.basicPriceTv.setTextSize(Dimension.DP, 18);
        }

        // 선택된 아이템 list bg 변경
        if(mSelectedPos != -1 && mSelectedPos == i) {
            holder.rootLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.train_bg_color4));

            // 선택된 아이템 표시
            if(mSelectedSeatType == Common.TrainSeatConstant.ROOM_BASIC && item.basicPrice > 0) {
                holder.selectImg1.setVisibility(View.VISIBLE);
                holder.selectImg2.setVisibility(View.GONE);

            } else if(mSelectedSeatType == Common.TrainSeatConstant.ROOM_VIP && item.vipPrice > 0) {
                holder.selectImg1.setVisibility(View.GONE);
                holder.selectImg2.setVisibility(View.VISIBLE);
            } else {
                holder.selectImg1.setVisibility(View.GONE);
                holder.selectImg2.setVisibility(View.GONE);
            }

        } else {
            holder.rootLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.selectImg1.setVisibility(View.GONE);
            holder.selectImg2.setVisibility(View.GONE);
        }

        holder.basicLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = i;
                TrainInfo item = mList.get(position);
                mSelectedSeatType = 0;
                //Log.d(TAG, "KHJ++ basic onClick() position="+position + " / mSelectedPos="+mSelectedPos);

                if(item.isBasicSoldOut) { // 일반실 매진
                    return ;
                }

                item.selectedSeatType = mSelectedSeatType;
                mActivity.setSelectedTrainInfo(item);

                if(item.waitingForBasic) {
                    if(mActivity.mReservationInfo.isOneWayTicket == false) {
                        // 왕복인 경우 예약대기 선택 불가하도록 처리
                        return ;
                    }

                    // footer 글자 변경
                    mActivity.setFooterText(mContext.getString(R.string.train_search_waiting_for_reservation), true);
                } else {
                    mActivity.setFooterText(mContext.getString(R.string.train_search_reservation), false);
                }

                // 금액이 있는 좌석만 선택표시
                if(item.basicPrice > 0) {
                    if(!item.waitingForBasic) {
                        mActivity.goSeatSelect();
                    }
                }

                if(mSelectedPos == position) {
                    mActivity.setSelectedTrainInfo(item);
                } else if(mSelectedPos != -1) {
                    // 이전에 선택된 아이템 원래 color로 변경
                }

                mSelectedPos = position;
                notifyDataSetChanged();

            }
        });

        holder.vipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = i;
                TrainInfo item = mList.get(position);
                mSelectedSeatType = 1;
                //Log.d(TAG, "KHJ++ vip onClick() position="+position + " / mSelectedPos="+mSelectedPos);

                if(item.isVipSoldOut) { // 특실 매진
                    return ;
                }

                item.selectedSeatType = mSelectedSeatType;
                mActivity.setSelectedTrainInfo(item);

                if(item.waitingForVip) {
                    if(mActivity.mReservationInfo.isOneWayTicket == false) {
                        // 왕복인 경우 예약대기 선택 불가하도록 처리
                        return ;
                    }

                    // footer 글자 변경
                    mActivity.setFooterText(mContext.getString(R.string.train_search_waiting_for_reservation), true);
                } else {
                    mActivity.setFooterText(mContext.getString(R.string.train_search_reservation), false);
                }

                // 금액이 있는 좌석만 선택표시
                if(item.vipPrice > 0) {
                    if(!item.waitingForVip) {
                        mActivity.goSeatSelect();
                    }
                }

                if(mSelectedPos == position) {
                    mActivity.setSelectedTrainInfo(item);
                } else if(mSelectedPos != -1) {
                    // 이전에 선택된 아이템 원래 color로 변경
                }

                mSelectedPos = position;
                notifyDataSetChanged();
            }
        });


        return view;
    }

    public void setSelectedPosition(int pos) {
        mSelectedPos = pos;
    }

    class ViewHolder {
        TextView trainNameTv, startTimeTv, endTimeTv, basicPriceTv, vipPriceTv;
        TextView basicPriceSubTv;
        ViewGroup basicLayout, vipLayout, rootLayout;
        ImageView selectImg1, selectImg2;
    }
}
