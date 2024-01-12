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
import com.avad.humancare.kiosk.model.PatientModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class HospitalPatientListAdapter extends RecyclerView.Adapter<HospitalPatientListAdapter.ViewHolder> {

    public interface OnItemClickEventListener {
        void OnItemClick(View view, int position);
    }
    private OnItemClickEventListener onItemClickListener;
    private ArrayList<PatientModel> _list;
    private Context _context;
    public HospitalPatientListAdapter(Context context, ArrayList<PatientModel> list, OnItemClickEventListener listener){
        this._context = context;
        this._list = list;
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hospital_item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PatientModel item = _list.get(position);
        if(item.getSex() == "F"){
            holder.ivSex.setImageResource(R.drawable.img_woman);
        }else{
            holder.ivSex.setImageResource(R.drawable.img_man);
        }
        holder.tvName.setText(item.getName());
        holder.tvLastVisitDate.setText(item.getLastVisitDate());
        if(item.isChecked()){
            holder.itemView.setSelected(true);
            holder.cvRoot.setStrokeColor(_context.getColor(R.color.hospital_sub_color));
            holder.cvRoot.setCardBackgroundColor(_context.getColor(R.color.hospital_main_color));
        }else{
            holder.itemView.setSelected(false);
            holder.cvRoot.setStrokeColor(_context.getColor(R.color.hospital_gray_color));
            holder.cvRoot.setCardBackgroundColor(_context.getColor(R.color.white));
        }
        holder.cbSelected.setChecked(item.isChecked());

    }

    @Override
    public int getItemCount() {
        if(_list == null) return 0;
        return _list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private MaterialCardView cvRoot;
        private ImageView ivSex;
        private TextView tvName, tvLastVisitDate;
        private CheckBox cbSelected;
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
            ivSex = itemView.findViewById(R.id.iv_sex);
            cvRoot = itemView.findViewById(R.id.cv_root);
            tvName = itemView.findViewById(R.id.tv_patient_name);
            tvLastVisitDate = itemView.findViewById(R.id.tv_patient_last_visit_date);
            cbSelected = itemView.findViewById(R.id.checkbox_select);
        }
    }
}
