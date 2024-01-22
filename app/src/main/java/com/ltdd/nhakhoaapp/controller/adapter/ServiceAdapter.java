package com.ltdd.nhakhoaapp.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.model.domain.Service;


import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{

    private Context context;
    private List<Service> serviceList;

    private ServiceAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(ServiceAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    private OnCheckedChangeListener onCheckedChangeListener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }
    public ServiceAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Service> services) {
        this.serviceList = services;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceAdapter.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_service_item, parent,false);
        return new ServiceAdapter.ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
//        if (service == null) {
//            return;
//        }
//        holder.txtService.setText(service.getName());
        holder.checkBox.setChecked(service.getSelected());
        holder.txtService.setText(service.getName());

        // Lắng nghe sự kiện khi CheckBox được chọn/deselect
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChange(position, isChecked);
            }
        });
    }



    @Override
    public int getItemCount() {
        return serviceList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private TextView txtService;
        private CheckBox checkBox;
        private CardView cardView;
        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtService = itemView.findViewById(R.id.txt_service);
            cardView = itemView.findViewById(R.id.card_service);
            checkBox = itemView.findViewById(R.id.checkBox_service);

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
