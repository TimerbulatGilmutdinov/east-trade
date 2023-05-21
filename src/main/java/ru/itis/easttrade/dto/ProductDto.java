package ru.itis.easttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.easttrade.models.Product;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer id;
    private String name;
    private String description;
    private Date publishDate;
    private Double price;
    private String accountEmail;
    private String accountInfo;

    public static ProductDto from(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .publishDate(product.getPublishDate())
                .accountEmail(product.getAccount().getEmail())
                .accountInfo(product.getAccount().toString())
                .build();
    }

    public static List<ProductDto> from(List<Product> products) {
        return products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }
}