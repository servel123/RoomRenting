package com.example.bt2;

import android.os.Bundle;
import android.text.InputType;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RoomAdapter adapter;
    private List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        setupRecyclerView();
        setupAddButton();
    }

    private void initData() {
        roomList = new ArrayList<>();
        roomList.add(new Room("P01", "Phòng 101", 2500000, false, "", ""));
        roomList.add(new Room("P02", "Phòng 102", 3000000, true, "Lê Huy", "0123456789"));
    }

    private void setupRecyclerView() {
        RecyclerView rvRooms = findViewById(R.id.rvRooms);

        adapter = new RoomAdapter(roomList, new RoomAdapter.OnRoomClickListener() {
            @Override
            public void onItemClick(Room room, int position) {
                showRoomDialog(room, position);
            }

            @Override
            public void onItemLongClick(Room room, int position) {
                showDeleteConfirmDialog(position);
            }
        });

        rvRooms.setLayoutManager(new LinearLayoutManager(this));
        rvRooms.setAdapter(adapter);
    }

    private void setupAddButton() {
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> showRoomDialog(null, -1));
    }

    /**
     * Shows a dialog to either add a new room or edit an existing one.
     * @param room The room to edit, or null to add a new one.
     * @param position The position in the list, or -1 for new.
     */
    private void showRoomDialog(Room room, int position) {
        boolean isEdit = (room != null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(isEdit ? "Chỉnh sửa thông tin phòng" : "Thêm phòng mới");

        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        final EditText etName = createEditText("Tên phòng", isEdit ? room.getTenPhong() : "");
        final EditText etPrice = createEditText("Giá thuê", isEdit ? String.valueOf(room.getGiaThue()) : "");
        etPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        final EditText etTenant = createEditText("Tên người thuê", isEdit ? room.getTenNguoiThue() : "");
        final EditText etPhone = createEditText("Số điện thoại", isEdit ? room.getSoDienThoai() : "");
        etPhone.setInputType(InputType.TYPE_CLASS_PHONE);

        final CheckBox cbRented = new CheckBox(this);
        cbRented.setText("Đã thuê");
        cbRented.setChecked(isEdit && room.isDaThue());

        layout.addView(etName);
        layout.addView(etPrice);
        layout.addView(etTenant);
        layout.addView(etPhone);
        layout.addView(cbRented);

        scrollView.addView(layout);
        builder.setView(scrollView);

        builder.setPositiveButton(isEdit ? "Cập nhật" : "Thêm", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ Tên và Giá!", Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá thuê không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            String tenant = etTenant.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            boolean rented = cbRented.isChecked();

            if (isEdit) {
                room.setTenPhong(name);
                room.setGiaThue(price);
                room.setTenNguoiThue(tenant);
                room.setSoDienThoai(phone);
                room.setDaThue(rented);
                adapter.notifyItemChanged(position);
                Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
            } else {
                String id = "P" + (roomList.size() + 1);
                Room newRoom = new Room(id, name, price, rented, tenant, phone);
                roomList.add(newRoom);
                adapter.notifyItemInserted(roomList.size() - 1);
                Toast.makeText(this, "Đã thêm phòng mới", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private EditText createEditText(String hint, String initialText) {
        EditText editText = new EditText(this);
        editText.setHint(hint);
        editText.setText(initialText);
        return editText;
    }

    private void showDeleteConfirmDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa phòng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    roomList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}