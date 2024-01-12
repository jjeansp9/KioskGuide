package com.avad.humancare.kiosk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.model.PaymentItemModel;
import com.avad.humancare.kiosk.util.DateUtil;
import com.avad.humancare.kiosk.util.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HospitalRecordHistoryListAdapter extends RecyclerView.Adapter<HospitalRecordHistoryListAdapter.ViewHolder>{
    private ArrayList<PaymentItemModel> _list;
    private Context _context;
    private HospitalPatientListAdapter.OnItemClickEventListener onItemClickListener;
    public HospitalRecordHistoryListAdapter(Context context, ArrayList<PaymentItemModel> list, HospitalPatientListAdapter.OnItemClickEventListener listener){
        this._context = context;
        this._list = list;
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hospital_item_record_history, parent, false);
        return new HospitalRecordHistoryListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentItemModel item = _list.get(position);
        holder.cbSelected.setChecked(item.isSelected());
//        int maxday = 14;
        String dateStr = item.getDate();
        String originDateStr = "";
        String endDateStr = "";
        Date date = new Date();
        try {
            date = DateUtil.DATE_FORMAT_YYYY_MM_DD.parse(dateStr);
            originDateStr = DateUtil.DATE_FORMAT_YYdotMMdotDD.format(date);
            date = DateUtil.DATE_FORMAT_YYYY_MM_DD.parse(item.getEndDate());
            endDateStr = DateUtil.DATE_FORMAT_YYdotMMdotDD.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.DATE, Utils.getRandomNumber(1, maxday));
//        String rangeDateStr = DateUtil.DATE_FORMAT_YYdotMMdotDD.format(cal.getTime());

        holder.tvDate.setText(String.format("%s - %s", originDateStr, endDateStr));
        holder.tvDepartment.setText(item.getDepartment());
        holder.tvType.setText(item.getType() == 1? _context.getString(R.string.hospital_sub_record_diagnosis_type_outpatient) : _context.getString(R.string.hospital_sub_record_diagnosis_type_hospitalization));
    }

    @Override
    public int getItemCount() {
        if(_list == null)
            return 0;
        return _list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox cbSelected;
        private TextView tvDate, tvDepartment, tvType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.OnItemClick(view, position);
                    }

                }
            });
            cbSelected = itemView.findViewById(R.id.cb_select);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDepartment = itemView.findViewById(R.id.tv_department);
            tvType = itemView.findViewById(R.id.tv_type);
        }
    }
}
