package com.example.bt2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private final List<Room> roomList;
    private final OnRoomClickListener listener;

    // Interface để MainActivity xử lý Click (Sửa) và LongClick (Xóa) [cite: 36, 39]
    public interface OnRoomClickListener {
        void onItemClick(Room room, int position);
        void onItemLongClick(Room room, int position);
    }

    public RoomAdapter(List<Room> roomList, OnRoomClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);

        holder.tvRoomName.setText("Phòng: " + room.getTenPhong()); // Hiển thị tên phòng [cite: 29]
        holder.tvPrice.setText(String.format("Giá: %,.0f VNĐ", room.getGiaThue())); // Hiển thị giá [cite: 30]

        // Xử lý tô màu theo tình trạng [cite: 31, 32]
        if (room.isDaThue()) {
            holder.tvStatus.setText("Tình trạng: Đã thuê");
            holder.tvStatus.setTextColor(Color.RED); // Đỏ -> Đã thuê [cite: 34]
        } else {
            holder.tvStatus.setText("Tình trạng: Còn trống");
            holder.tvStatus.setTextColor(Color.GREEN); // Xanh -> Còn trống [cite: 33]
        }

        // Sự kiện Click để sửa [cite: 36]
        holder.itemView.setOnClickListener(v -> listener.onItemClick(room, position));

        // Sự kiện Long Click để xóa [cite: 39]
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(room, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvPrice, tvStatus;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}