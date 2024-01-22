package com.ltdd.nhakhoaapp.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.model.domain.Doctor;

import java.util.List;

public class DoctorDialogAdapter extends RecyclerView.Adapter<DoctorDialogAdapter.DoctorDialogViewHolder> {

    private Context context;
    private List<Doctor> doctors;

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public DoctorDialogAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Doctor> doctors) {
        this.doctors = doctors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DoctorDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_doctor_item, parent, false);
        return new DoctorDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorDialogViewHolder holder, int position) {
        Doctor doctor = doctors.get(position);
        if (doctor == null) {
            return;
        }
       holder.txtDtor.setText(doctor.getName());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return (doctors == null) ? 0:doctors.size();
    }

    public class DoctorDialogViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDtor;
        private CardView carDtor;
        public DoctorDialogViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDtor = itemView.findViewById(R.id.txt_dtor);
            carDtor = itemView.findViewById(R.id.card_dtor);
            carDtor.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
