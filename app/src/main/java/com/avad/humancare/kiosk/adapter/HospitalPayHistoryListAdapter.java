package com.avad.humancare.kiosk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.model.PaymentItemModel;

import java.util.ArrayList;

public class HospitalPayHistoryListAdapter extends RecyclerView.Adapter<HospitalPayHistoryListAdapter.ViewHolder>{
    private ArrayList<PaymentItemModel> _list;
    private Context _context;
    public HospitalPayHistoryListAdapter(Context context, ArrayList<PaymentItemModel> list){
        this._context = context;
        this._list = list;
    }
    @NonNull
    @Override
    public HospitalPayHistoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hospital_item_payment_history, parent, false);
        return new HospitalPayHistoryListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalPayHistoryListAdapter.ViewHolder holder, int position) {
        PaymentItemModel item = _list.get(position);
        holder.tvDate.setText(item.getDate());
        holder.tvDepartment.setText(item.getDepartment());
        holder.tvDoctor.setText(item.getDoctor());
        holder.tvAmount.setText(item.getAmountStr());
    }

    @Override
    public int getItemCount() {
        if(_list == null) return 0;
        return _list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDate, tvDepartment, tvDoctor, tvAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date);
            tvDepartment = itemView.findViewById(R.id.tv_department);
            tvDoctor = itemView.findViewById(R.id.tv_doctor);
            tvAmount = itemView.findViewById(R.id.tv_amount);
        }
    }
}
