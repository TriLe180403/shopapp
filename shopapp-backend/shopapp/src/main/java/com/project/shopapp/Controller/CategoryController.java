package com.project.shopapp.Controller;

import com.project.shopapp.Dto.CategoryDto;
import com.project.shopapp.models.Category;
import com.project.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//dependency injection
@RequiredArgsConstructor
@RequestMapping ("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("")
    //nếu tham số truyền vào là 1 object thì => data transfer object = request object
    public ResponseEntity<?> createCategory(
            @Valid
            @RequestBody CategoryDto categoryDto,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        categoryService.createCategory(categoryDto);
        return ResponseEntity.ok("insert category successfully");
    }
    @GetMapping("")
    public ResponseEntity< List<Category>> getAllCategory(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto categoryDto
            ) {
        categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok("Update category successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("deleteCategory with id = "+id+" successfully");
    }
}
