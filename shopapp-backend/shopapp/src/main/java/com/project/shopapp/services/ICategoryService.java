package com.project.shopapp.services;

import com.project.shopapp.Dto.CategoryDto;
import com.project.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDto category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId,CategoryDto category);

    void deleteCategory(long id);
}
