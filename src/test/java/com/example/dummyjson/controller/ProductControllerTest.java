package com.example.dummyjson.controller;

import com.example.dummyjson.dto.Product;
import com.example.dummyjson.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductControllerTest {

    private static final String PRODUCT_1 = "Product 1";
    private static final String PRODUCT_2 = "Product 2";
    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductService productService;

    @TestConfiguration
    static class MockProductServiceConfig {

        @Bean
        public ProductService productService(WebClient.Builder builder) {
            return new ProductService(builder.build()) {
                @Override
                public List<Product> getAllProducts() {
                    Product product1 = new Product();
                    product1.setId(ID_1);
                    product1.setTitle(PRODUCT_1);

                    Product product2 = new Product();
                    product2.setId(ID_2);
                    product2.setTitle(PRODUCT_2);

                    return List.of(product1, product2);
                }

                @Override
                public Product getProductById(Long id) {
                    if (id == ID_1) {
                        Product product1 = new Product();
                        product1.setId(ID_1);
                        product1.setTitle(PRODUCT_1);
                        return product1;
                    } else if (id == ID_2) {
                        Product product2 = new Product();
                        product2.setId(ID_2);
                        product2.setTitle(PRODUCT_2);
                        return product2;
                    }
                    return null;
                }
            };
        }
    }

    @Test
    public void testGetAllProducts() {
        List<Product> result = productController.getAllProducts();
        assertEquals(2, result.size());
        assertEquals(PRODUCT_1, result.get(0).getTitle());
    }

    @Test
    public void testGetProductById() {
        Product result = productController.getProductById(1L);
        assertEquals(PRODUCT_1, result.getTitle());
    }
}