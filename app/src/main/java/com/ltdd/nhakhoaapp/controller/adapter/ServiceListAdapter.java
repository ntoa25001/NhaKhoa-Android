package com.ltdd.nhakhoaapp.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.model.domain.Service;

import java.util.List;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceListViewHolder> {

    private Context context;
    private List<Service> serviceList;

    public ServiceListAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Service> services) {
        this.serviceList = services;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceListAdapter.ServiceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_service_item_detail, parent, false);
        return new ServiceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListAdapter.ServiceListViewHolder holder, int position) {

        holder.txtServiceDetal.setText(serviceList.get(position).getDetail());
        holder.txtServiceNameDetal.setText(serviceList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ServiceListViewHolder extends RecyclerView.ViewHolder {

        TextView txtServiceNameDetal, txtServiceDetal;
        public ServiceListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtServiceDetal = itemView.findViewById(R.id.txt_servicedetal);
            txtServiceNameDetal = itemView.findViewById(R.id.txt_nameservice);
        }
    }
}
