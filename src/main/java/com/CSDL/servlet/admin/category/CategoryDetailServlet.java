package com.CSDL.servlet.admin.category;

import com.CSDL.beans.Category;
import com.CSDL.service.CategoryService;
import com.CSDL.utils.Protector;
import com.CSDL.utils.TextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "CategoryDetailServlet", value = "/admin/categoryManager/detail")
public class CategoryDetailServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Protector.of(() -> Long.parseLong(request.getParameter("id"))).get(0L);
        Optional<Category> categoryFromServer = Protector.of(() -> categoryService.getById(id)).get(Optional::empty);

        if (categoryFromServer.isPresent()) {
            Category category = categoryFromServer.get();
            category.setDescription(TextUtils.toParagraph(Optional.ofNullable(category.getDescription()).orElse("")));
            request.setAttribute("category", category);
            request.getRequestDispatcher("/WEB-INF/views/categoryDetailView.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/categoryManager");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}