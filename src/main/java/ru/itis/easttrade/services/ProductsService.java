package ru.itis.easttrade.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.easttrade.dto.*;

import java.util.List;

public interface ProductsService {
    ProductDto getProductById(Integer id);
    ProductDto getProductByName(String name);
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductsOrderByDateAsc();
    List<ProductDto> getAllProductsOrderByDateDesc();
    void updateProductById(@RequestParam("id") Integer id, UpdateProductDto product, Authentication authentication);
    ProductDto saveProduct(ProductDto productDto, Authentication authentication);
    ResponseEntity<String> deleteProductById(Integer id, Authentication authentication);
    List<ProductDto> getProductsByAccount(AccountDto accountDto);
    List<ProductDto> getAllProductsForToday();
}
