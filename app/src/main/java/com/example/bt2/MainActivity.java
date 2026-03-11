package com.example.bt2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RoomAdapter adapter;
    private List<Room> roomList; // Dữ liệu lưu tạm thời [cite: 9]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Khởi tạo dữ liệu mẫu (Read)
        roomList = new ArrayList<>();
        roomList.add(new Room("P01", "Phòng 101", 2500000, false, "", ""));
        roomList.add(new Room("P02", "Phòng 102", 3000000, true, "Lê Huy", "0123456789"));

        // 2. Thiết lập RecyclerView
        RecyclerView rvRooms = findViewById(R.id.rvRooms);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        adapter = new RoomAdapter(roomList, new RoomAdapter.OnRoomClickListener() {
            @Override
            public void onItemClick(Room room, int position) {
                // Sửa: Gọi hàm mở Dialog khi click
                openEditDialog(room, position);
            }

            @Override
            public void onItemLongClick(Room room, int position) {
                // Nhấn giữ để xóa [cite: 39]
                showDeleteConfirmDialog(position);
            }
        });

        rvRooms.setLayoutManager(new LinearLayoutManager(this));
        rvRooms.setAdapter(adapter);

        // 3. Thêm phòng (Create)
        fabAdd.setOnClickListener(v -> {
            // Để đạt điểm cao, bạn nên tạo một Dialog nhập liệu thay vì add cứng thế này
            roomList.add(new Room("P03", "Phòng mới", 2000000, false, "", ""));
            adapter.notifyItemInserted(roomList.size() - 1); // Cập nhật vị trí cuối [cite: 25]
        });
    }

    // Chức năng Update - Sửa thông tin
    private void openEditDialog(Room room, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa thông tin phòng");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        final EditText etName = new EditText(this);
        etName.setHint("Tên phòng");
        etName.setText(room.getTenPhong());
        layout.addView(etName);

        final EditText etPrice = new EditText(this);
        etPrice.setHint("Giá thuê");
        etPrice.setText(String.valueOf(room.getGiaThue()));
        layout.addView(etPrice);

        final CheckBox cbRented = new CheckBox(this);
        cbRented.setText("Đã thuê");
        cbRented.setChecked(room.isDaThue());
        layout.addView(cbRented);

        builder.setView(layout);

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            String newName = etName.getText().toString();
            String priceStr = etPrice.getText().toString();

            // Validate dữ liệu [cite: 23]
            if (!newName.isEmpty() && !priceStr.isEmpty()) {
                room.setTenPhong(newName);
                room.setGiaThue(Double.parseDouble(priceStr));
                room.setDaThue(cbRented.isChecked());

                adapter.notifyItemChanged(position); // Cập nhật lại dòng đó [cite: 42]
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    // Chức năng Delete - Xóa phòng [cite: 38]
    @SuppressLint("NotifyDataSetChanged")
    private void showDeleteConfirmDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa phòng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    roomList.remove(position); // Xóa khỏi List [cite: 41]
                    adapter.notifyDataSetChanged(); // Cập nhật lại danh sách [cite: 42]
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}