package com.ltdd.nhakhoaapp.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.activity.MedicineActivity;
import com.ltdd.nhakhoaapp.activity.PrescriptionActivity;
import com.ltdd.nhakhoaapp.model.domain.Prescription;

import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder> {

    private List<Prescription> prescriptionList;
    private Context context;

    public PrescriptionAdapter(List<Prescription> prescriptionList, Context context) {
        this.prescriptionList = prescriptionList;
        this.context = context;
    }

    @NonNull
    @Override
    public PrescriptionAdapter.PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_prescription_item, parent, false);
        return new PrescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionAdapter.PrescriptionViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtPrescriptionDetail.setText(prescriptionList.get(position).getDetail());
        holder.txtPrescriptionName.setText(prescriptionList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = prescriptionList.get(position).getName();
                Intent intent = new Intent(context, MedicineActivity.class);
                intent.putExtra("prescription", name);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }

    public class PrescriptionViewHolder extends RecyclerView.ViewHolder {

        TextView txtPrescriptionName, txtPrescriptionDetail;
        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPrescriptionName = itemView.findViewById(R.id.txt_prescription);
            txtPrescriptionDetail = itemView.findViewById(R.id.txt_prescription_detail);
        }
    }
}
