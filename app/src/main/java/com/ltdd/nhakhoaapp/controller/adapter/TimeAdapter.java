package com.ltdd.nhakhoaapp.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.model.domain.Time;

import java.util.List;


public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder>{
    private Context context;
    private List<Time> times;
    private boolean[] isSelected;
    private DoctorAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(DoctorAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public TimeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Time> times) {
        this.times = times;
        isSelected = new boolean[times.size()];
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_time_item, parent,false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        Time time = times.get(position);
        if (time == null) {
            return;
        }
        holder.txtTime.setText(time.getPeriod());
        holder.bind(time, isSelected[position]);

    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public class TimeViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTime;
        private CardView cardView;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txt_time);
            cardView = itemView.findViewById(R.id.card_time);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                            isSelected[position] = !isSelected[position];
                        }
                    }
                }
            });
        }
        void bind(Time time, boolean isSelected) {
            txtTime.setText(time.getPeriod());

            if (isSelected) {
                txtTime.setTextColor(Color.RED);
                txtTime.setBackgroundColor(Color.TRANSPARENT);
            } else {
                txtTime.setTextColor(Color.BLACK);
                txtTime.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }
}
