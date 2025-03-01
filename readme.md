# Modern Calculator

Một ứng dụng máy tính hiện đại được xây dựng bằng Java Swing với giao diện người dùng theo phong cách macOS.

## Ảnh minh họa

### Giao diện chính
<img src="https://hebbkx1anhila5yf.public.blob.vercel-storage.com/A%CC%89nh%20ma%CC%80n%20hi%CC%80nh%202025-03-02%20lu%CC%81c%2003.40.06-iCcpNSo1QwAmCfIjS3nUbCJlVX809i.png" alt="Calculator Main" width="320"/>

### Thực hiện phép tính
<img src="https://hebbkx1anhila5yf.public.blob.vercel-storage.com/A%CC%89nh%20ma%CC%80n%20hi%CC%80nh%202025-03-02%20lu%CC%81c%2003.40.38-9IlHZOk0v4UTU83dWGC4atvnOh10rL.png" alt="Calculator Operation" width="320"/>

### Phép tính nâng cao (Căn bậc hai)
<img src="https://hebbkx1anhila5yf.public.blob.vercel-storage.com/A%CC%89nh%20ma%CC%80n%20hi%CC%80nh%202025-03-02%20lu%CC%81c%2003.40.52-Aly3RM1d8AV8hH6LwBJeJBEkqOrVOK.png" alt="Calculator Square Root" width="320"/>

## Tính năng

### Phép toán cơ bản

- Cộng (+)
- Trừ (-)
- Nhân (×)
- Chia (÷)


### Phép toán nâng cao

- Lũy thừa (xⁿ)
- Căn bậc hai (√)
- Phần trăm (%)


### Chức năng điều khiển

- Xóa một ký tự (⌫)
- Xóa toàn bộ phép tính (C)
- Đổi dấu số (±)
- Dấu thập phân (.)
- Dấu ngoặc ( )


### Hiển thị

- Hiển thị phép tính đang thực hiện
- Hiển thị kết quả tức thì
- Lịch sử các phép tính
- Ký hiệu toán học chuẩn


### Xử lý lỗi

- Thông báo khi chia cho 0
- Thông báo khi tính căn bậc hai của số âm
- Thông báo khi nhập sai dữ liệu


## Yêu cầu hệ thống

- Java Development Kit (JDK) 17 trở lên
- Hệ điều hành hỗ trợ giao diện đồ họa


## Cài đặt và Chạy

1. Tạo file ModernCalculator.java và copy code vào
2. Biên dịch:


```shellscript
javac --add-modules java.desktop ModernCalculator.java
```

3. Chạy chương trình:


```shellscript
java --add-modules java.desktop ModernCalculator
```

## Hướng dẫn sử dụng

### Thực hiện phép tính cơ bản

1. Nhập số đầu tiên
2. Chọn phép tính (+, -, ×, ÷)
3. Nhập số thứ hai
4. Nhấn = để xem kết quả


### Sử dụng phép tính nâng cao

- **Lũy thừa**: Nhập số cơ sở → Nhấn xⁿ → Nhập số mũ → Nhấn =
- **Căn bậc hai**: Nhập số → Nhấn √
- **Phần trăm**: Nhập số → Nhấn %


### Xem lịch sử tính toán

- Lịch sử phép tính được hiển thị trong khung bên dưới màn hình
- Các phép tính mới nhất sẽ tự động cuộn xuống


## Giao diện

### Thiết kế

- Giao diện trong suốt (Glassmorphism)
- Bo tròn các góc
- Hiệu ứng hover trên các nút
- Màu sắc theo phong cách macOS
- Nút điều khiển cửa sổ kiểu macOS


### Bố cục

- Thanh tiêu đề với nút đóng
- Màn hình hiển thị phép tính
- Màn hình hiển thị kết quả
- Khu vực lịch sử tính toán
- Bàn phím số và phép tính


## Màu sắc sử dụng

```java
// Màu nền chính
BACKGROUND_COLOR = new Color(28, 28, 30)

// Màu nút số
BUTTON_DARK = new Color(44, 44, 46)

// Màu nút phép tính
BUTTON_OPERATOR = new Color(255, 159, 10)

// Màu nút chức năng
BUTTON_FUNCTION = new Color(72, 72, 74)

// Màu nút bằng
BUTTON_EQUAL = new Color(10, 132, 255)

// Màu chữ
TEXT_LIGHT = new Color(255, 255, 255)
TEXT_SECONDARY = new Color(174, 174, 178)
```

## Xử lý lỗi

Chương trình xử lý các trường hợp lỗi sau:

- Chia cho 0
- Căn bậc hai của số âm
- Nhập sai định dạng số
- Các lỗi tính toán khác


## Tính năng đặc biệt

1. **Giao diện trong suốt**: Sử dụng hiệu ứng glassmorphism hiện đại
2. **Hiệu ứng hover**: Các nút có hiệu ứng khi di chuột qua
3. **Cửa sổ có thể di chuyển**: Kéo thả cửa sổ tự do
4. **Lịch sử tính toán**: Lưu trữ và hiển thị các phép tính trước đó
5. **Ký hiệu toán học chuẩn**: Sử dụng các ký hiệu Unicode cho các phép toán


## Tác giả

Trương Công Đạt

## Phiên bản

1.0.0

## Giấy phép

MIT License
