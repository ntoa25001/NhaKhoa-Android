package com.ltdd.nhakhoaapp.controller.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.model.domain.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private Context context;
    private List<Doctor> doctors;

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public DoctorAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Doctor> doctors) {
        this.doctors = doctors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctors.get(position);
        if (doctor == null) {
            return;
        }
        String base64Image = doctor.getAvatar(); // Lấy chuỗi Base64 từ đối tượng Doctor

        // Giải mã chuỗi Base64 thành dữ liệu ảnh
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        // Hiển thị ảnh giải mã vào ImageView
        holder.imgAvatar.setImageBitmap(decodedBitmap);
//        Glide.with(context).load(doctor.getAvatar()).into(holder.imgAvatar);
        holder.txtName.setText(doctor.getName());
        holder.txtPhone.setText(doctor.getPhone());
        holder.txtDay.setText(doctor.getDay());
        holder.txtSpec.setText(doctor.getSpecialist().getSpecialistName());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return (doctors == null) ? 0:doctors.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView imgAvatar;
        private TextView txtName,txtPhone,txtDay,txtSpec,txtDtor;
        private CardView cardDoctor, carDtor;
        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_doctor);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDay = itemView.findViewById(R.id.txt_day);
            txtPhone = itemView.findViewById(R.id.txt_phone);
            txtSpec = itemView.findViewById(R.id.txt_spec);
            cardDoctor = itemView.findViewById(R.id.card_doctor);
            cardDoctor.setOnClickListener(v -> {
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
