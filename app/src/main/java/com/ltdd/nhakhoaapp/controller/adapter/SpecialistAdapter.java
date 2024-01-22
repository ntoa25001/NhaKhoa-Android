package com.ltdd.nhakhoaapp.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.model.domain.Specialist;

import java.util.List;

public class SpecialistAdapter extends RecyclerView.Adapter<SpecialistAdapter.SpecialistViewHolder> {

    private Context context;
    private List<Specialist> specialists;

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SpecialistAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Specialist> specialists) {
        this.specialists = specialists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SpecialistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_specialist_item, parent,false);
        return new SpecialistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialistViewHolder holder, int position) {

        Specialist specialist = specialists.get(position);
        if (specialist == null) {
            return;
        }
        holder.txtSpecial.setText(specialist.getSpecialistName());
    }

    @Override
    public int getItemCount() {
        return specialists.size();
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public  class SpecialistViewHolder extends RecyclerView.ViewHolder {
        private  TextView txtSpecial;
        private  CardView cardView;
        public SpecialistViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSpecial = itemView.findViewById(R.id.txt_special);
            cardView = itemView.findViewById(R.id.card_spec);

            cardView.setOnClickListener(v -> {
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
