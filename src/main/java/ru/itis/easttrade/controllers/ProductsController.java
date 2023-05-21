package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.dto.*;
import ru.itis.easttrade.models.Topic;
import ru.itis.easttrade.services.AccountsService;
import ru.itis.easttrade.services.ProductsService;
import ru.itis.easttrade.utils.CurrencyHelper;
import ru.itis.easttrade.utils.RightsResolver;
import ru.itis.easttrade.utils.RoleChecker;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;
    private final AccountsService accountsService;
    private final RoleChecker roleChecker;
    private final RightsResolver rightsResolver;
    private final CurrencyHelper currencyHelper;

    @GetMapping("/products/{id}")
    public String getProductById(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        ProductDto product = productsService.getProductById(id);
        model.addAttribute("hasEnoughAuthority", rightsResolver.resolveProductAction(id, authentication));
        model.addAttribute("product", product);
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String secondResult = decimalFormat.format(product.getPrice() / currencyHelper.getCurrency("USD"));
        model.addAttribute("USD", secondResult);
        return "product";
    }

    @GetMapping("/products/today")
    public String getProductsForToday(Model model) {
        model.addAttribute("products", productsService.getAllProductsForToday());
        return "products";
    }

    @GetMapping("/create-product")
    public String getCreateProduct(Model model) {
        model.addAttribute("product", new UpdateProductDto());
        return "create-product";
    }

    @PostMapping("/create-product")
    public String createProduct(@ModelAttribute("product") ProductDto productDto, Authentication authentication) {
        int id = productsService.saveProduct(productDto, authentication).getId();
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProductById").arg(0, id).build();
    }

    @GetMapping("/my-products")
    public String myTasks(@RequestParam(name = "sort", defaultValue = "new") String sort, Model model, Authentication authentication) {
        AccountDto accountDto = accountsService.getAccountByEmail(authentication.getName());
        List<ProductDto> products = productsService.getProductsByAccount(accountDto);

        if (sort.equals("old")) {
            products.sort(Comparator.comparing(ProductDto::getPublishDate));
        }
        model.addAttribute("sorted", sort);
        model.addAttribute("products", products);

        return "my-products";
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") Integer id, Authentication authentication) {
        productsService.deleteProductById(id, authentication);
    }

    @GetMapping("/products/{id}/update")
    public String getUpdateProduct(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        ProductDto product = productsService.getProductById(id);
        model.addAttribute("product", product);
        return "update-product";

    }

    @PostMapping("/products/{id}/update")
    public String updateProduct(@PathVariable("id") Integer id, @ModelAttribute UpdateProductDto taskDto, Authentication authentication) {
        productsService.updateProductById(id, taskDto, authentication);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProductById").arg(0, id).build();
    }

    @GetMapping(value = "/products")
    public String getAllProducts(@RequestParam(name = "sort", defaultValue = "new") String sort, Model model) {
        List<ProductDto> tasks = productsService.getAllProductsOrderByDateDesc();
        if (sort.equals("old")) {
            tasks.sort(Comparator.comparing(ProductDto::getPublishDate));
        }

        model.addAttribute("sorted", sort);
        model.addAttribute("tasks", tasks);
        return "products";
    }
}
