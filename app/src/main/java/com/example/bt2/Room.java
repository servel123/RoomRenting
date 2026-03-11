package com.example.bt2;

public class Room {
    private String maPhong;       // Mã phòng [cite: 13]
    private String tenPhong;     // Tên phòng [cite: 14]
    private double giaThue;      // Giá thuê [cite: 15]
    private boolean daThue;      // Tình trạng (true: Đã thuê, false: Còn trống) [cite: 16]
    private String tenNguoiThue; // Tên người thuê [cite: 17]
    private String soDienThoai;  // Số điện thoại [cite: 18]

    // Constructor để khởi tạo nhanh một phòng mới [cite: 21]
    public Room(String maPhong, String tenPhong, double giaThue, boolean daThue, String tenNguoiThue, String soDienThoai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.giaThue = giaThue;
        this.daThue = daThue;
        this.tenNguoiThue = tenNguoiThue;
        this.soDienThoai = soDienThoai;
    }

    // Các hàm Getter và Setter để truy xuất dữ liệu [cite: 8]
    public String getMaPhong() { return maPhong; }
    public String getTenPhong() { return tenPhong; }
    public double getGiaThue() { return giaThue; }
    public boolean isDaThue() { return daThue; }
    public String getTenNguoiThue() { return tenNguoiThue; }
    public String getSoDienThoai() { return soDienThoai; }

    public void setTenPhong(String tenPhong) { this.tenPhong = tenPhong; }
    public void setGiaThue(double giaThue) { this.giaThue = giaThue; }
    public void setDaThue(boolean daThue) { this.daThue = daThue; }

    public void setTenNguoiThue(String tenant) {
        this.tenNguoiThue = tenant;
    }

    public void setSoDienThoai(String phone) {
        this.soDienThoai = phone;

    }
}