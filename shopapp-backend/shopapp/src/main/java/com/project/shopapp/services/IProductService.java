package com.project.shopapp.services;

import com.project.shopapp.Dto.ProductDto;
import com.project.shopapp.Dto.ProductImageDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;



public interface IProductService {
    Product createProduct(ProductDto productDto) throws Exception;
    Product getProductById(long id) throws Exception;
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
    Product updateProduct(long id, ProductDto productDto) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(Long productId,
                                    ProductImageDto productImageDto)
            throws DataNotFoundException, InvalidParamException;
}
