package com.CSDL.service;

import com.CSDL.beans.Category;
import com.CSDL.dao.CategoryDAO;

import java.util.Optional;

public class CategoryService extends Service<Category, CategoryDAO> implements CategoryDAO {
    public CategoryService() {
        super(CategoryDAO.class);
    }

    @Override
    public Optional<Category> getByProductId(long productId) {
        return jdbi.withExtension(CategoryDAO.class, dao -> dao.getByProductId(productId));
    }

    @Override
    public int count() {
        return jdbi.withExtension(CategoryDAO.class, CategoryDAO::count);
    }
}
