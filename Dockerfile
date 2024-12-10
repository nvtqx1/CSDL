# Bước 1: Tạo từ hình ảnh Maven với OpenJDK 17 để xây dựng ứng dụng Java
FROM maven:3.8.5-openjdk-17 AS build

# Sao chép tất cả các file từ thư mục hiện tại vào trong hình ảnh
COPY . .

# Sử dụng Maven để làm sạch và tạo gói ứng dụng
RUN mvn clean package

# Bước 2: Sử dụng hình ảnh Tomcat runtime chính thức làm hình ảnh cha
FROM tomcat:9-jdk21-openjdk

# Sao chép file .war đã build từ quá trình build trước đó vào thư mục webapps của Tomcat
COPY --from=build target/BookShopWeb-1.0-SNAPSHOT/. /usr/local/tomcat/webapps/ROOT/

# Sao chép thư mục chứa hình ảnh cơ sở dữ liệu từ host vào container
# Đảm bảo thư mục `CSDL/image` trên máy chủ được gắn vào /image trong container
COPY CSDL/image /image

# Mở cổng 8080 để có thể truy cập ứng dụng từ bên ngoài
EXPOSE 8080

# Chạy Tomcat
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
