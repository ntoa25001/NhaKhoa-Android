package com.ltdd.nhakhoaapp.controller.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.model.api.RetrofitClient;
import com.ltdd.nhakhoaapp.model.domain.Appointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppViewHolder>{
    private Context context;
    private List<Appointment> appointments;

    public AppointmentAdapter(Context context) {
        this.context = context;
    }

   public void setData(List<Appointment> appointments) {
        this.appointments =appointments;
        notifyDataSetChanged();
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_appointment, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Appointment appointment = appointments.get(position);
        if(appointment == null) {
            return;
        }
        holder.txtDate.setText(appointment.getAppointmentDate());
        holder.txtBacsi.setText(appointment.getPeriod());
        holder.txtStatus.setText(appointment.getStatus());

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi API xóa
                showDeleteConfirmationDialog(appointment, position);
            }

        });
        String status = appointment.getStatus();
        switch (status) {
            case "pending":
                holder.txtStatus.setText("Đang chờ");
                holder.txtStatus.setTextColor(Color.parseColor("#FFA500"));
                break;
            case "cancel":
                holder.txtStatus.setText("Đã hủy");
                holder.txtStatus.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "success":
                holder.txtStatus.setText("Đã hoàn thành");
                holder.txtStatus.setTextColor(Color.parseColor("#008000"));
                break;
            default:
                holder.txtStatus.setText("");
                break;
        }


    }

    @Override
    public int getItemCount() {
        return (appointments == null) ? 0:appointments.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public class AppViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDate, txtTime ,txtStatus , txtBacsi;
        private CardView cardView;

        private ImageView imageDelete;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txt_dateC);
            cardView = itemView.findViewById(R.id.card_calendar);
            txtBacsi = itemView.findViewById(R.id.txt_bacsi);
            txtStatus = itemView.findViewById(R.id.tv_booking_status);
            imageDelete = itemView.findViewById(R.id.btn_delete);


            txtDate.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
            txtBacsi.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

        }
    }

    private void showDeleteConfirmationDialog(Appointment appointment, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa?");

        // Nút Xóa
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Gọi phương thức xóa
                deleteItem(appointment.getId(), position);
            }
        });

        // Nút Hủy
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hủy bỏ cửa sổ nhỏ
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem(Long id, int position) {

        RetrofitClient.getRetrofit().deleteAppointmentId(id).enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                Appointment appointment = response.body();
                notifyDataSetChanged();
                Toast.makeText(context, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
