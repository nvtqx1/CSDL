package com.CSDL.servlet.client;

import com.CSDL.beans.Product;
import com.CSDL.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "AllBookServlet", value = "/allbook")
public class AllBookServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    private static final int PRODUCTS_PER_PAGE = 16;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy số trang từ tham số "page" trong yêu cầu, mặc định là 1
        String pageParam = request.getParameter("page");
        int page = 1; // Mặc định là trang 1

        // Kiểm tra và parse trang
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1; // Nếu không thể parse, dùng trang mặc định là 1
            }
        }

        // Lấy tổng số sách trong cơ sở dữ liệu
        int totalProducts = productService.count(); // Gọi phương thức đếm tổng số sách từ service
        int totalPages = totalProducts / PRODUCTS_PER_PAGE + (totalProducts % PRODUCTS_PER_PAGE != 0 ? 1 : 0);

        // Kiểm tra nếu số trang hợp lệ
        if (page < 1 || page > totalPages) {
            page = 1; // Nếu số trang không hợp lệ, chuyển về trang 1
        }

        int offset = (page - 1) * PRODUCTS_PER_PAGE; // Tính offset cho truy vấn phân trang

        // Lấy danh sách sách phân trang từ service
        List<Product> products = productService.getOrderedPart(PRODUCTS_PER_PAGE, offset, "id", "DESC");

        // Gửi các thông tin tới JSP
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", page);
        request.setAttribute("products", products);

        // Chuyển tiếp tới view JSP
        request.getRequestDispatcher("/WEB-INF/views/allBookView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Gọi lại doGet khi nhận yêu cầu POST
        doGet(request, response);
    }
}