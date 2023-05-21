package ru.itis.easttrade.services.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.itis.easttrade.dto.*;
import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Product;
import ru.itis.easttrade.repositories.AccountsRepository;
import ru.itis.easttrade.repositories.ProductsRepository;
import ru.itis.easttrade.services.ProductsService;
import ru.itis.easttrade.utils.RightsResolver;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    private final ProductsRepository productsRepository;
    private final AccountsRepository accountsRepository;
    private final RightsResolver rightsResolver;
    @Override
    public ProductDto getProductById(Integer id) {
        Product product = productsRepository.findById(id).orElseThrow(() -> new NotFoundException("Product with id <" + id + "> not found"));
        return ProductDto.from(product);
    }

    @Override
    public ProductDto getProductByName(String name) {
        Product product = productsRepository.findByName(name).orElseThrow(() -> new NotFoundException("Product with name <" + name + "> not found"));
        return ProductDto.from(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return ProductDto.from(productsRepository.findAll());
    }

    @Override
    public List<ProductDto> getAllProductsOrderByDateAsc() {
        return ProductDto.from(productsRepository.findAllByOrderByPublishDateAsc());
    }

    @Override
    public List<ProductDto> getAllProductsOrderByDateDesc() {
        return ProductDto.from(productsRepository.findAllByOrderByPublishDateAsc());
    }

    @Override
    public void updateProductById(Integer id, UpdateProductDto product, Authentication authentication) {
        productsRepository.findById(id).orElseThrow(() -> new NotFoundException("Product with id <" + id + "> not found"));
        if (rightsResolver.resolveProductAction(id, authentication)) {
            String newTitle = Jsoup.clean(product.getName(), Safelist.basic());
            String newContent = Jsoup.clean(product.getDescription(), Safelist.basic());
            Double newPrice = product.getPrice();
            productsRepository.updateById(id, newTitle, newContent, newPrice);
        } else {
            throw new AccessDeniedException("You don't have rights to do that");
        }
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto, Authentication authentication) {
        String email = authentication.getName();
        Account account = accountsRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Account with email <" + email + "> not found"));

        Product product= Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .description(Jsoup.clean(productDto.getDescription(), Safelist.basic()))
                .account(account)
                .price(productDto.getPrice())
                .publishDate(new Date())
                .build();
        return ProductDto.from(productsRepository.save(product));
    }

    @Override
    public void deleteProductById(Integer id, Authentication authentication) {
        productsRepository.findById(id).orElseThrow(() -> new NotFoundException("Article with id <" + id + "> not found"));
        if (rightsResolver.resolveArticleAction(id,authentication)) {
            productsRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("You don't have rights to do that");
        }
    }

    @Override
    public List<ProductDto> getProductsByAccount(AccountDto accountDto) {
        Account account = accountsRepository.findByEmail(accountDto.getEmail()).orElseThrow(() -> new NotFoundException("Account with email <" + accountDto.getEmail() + "> not found"));
        return ProductDto.from(productsRepository.findAllByAccount(account));
    }

    @Override
    public List<ProductDto> getAllProductsForToday() {
        return ProductDto.from(productsRepository.findAllForToday());
    }
}
