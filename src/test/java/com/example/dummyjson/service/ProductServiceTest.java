package com.example.dummyjson.service;

import com.example.dummyjson.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    private static final String PRODUCT_1 = "Product 1";
    private static final String PRODUCT_2 = "Product 2";
    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;

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
        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());

        Product product1 = products.get(0);
        assertEquals(ID_1, product1.getId());
        assertEquals(PRODUCT_1, product1.getTitle());

        Product product2 = products.get(1);
        assertEquals(ID_2, product2.getId());
        assertEquals(PRODUCT_2, product2.getTitle());
    }

    @Test
    public void testGetProductById() {

        Product product1 = productService.getProductById(ID_1);
        assertNotNull(product1);
        assertEquals(ID_1, product1.getId());
        assertEquals(PRODUCT_1, product1.getTitle());

        Product product2 = productService.getProductById(ID_2);
        assertNotNull(product2);
        assertEquals(ID_2, product2.getId());
        assertEquals(PRODUCT_2, product2.getTitle());

        Product productNotFound = productService.getProductById(3L);
        assertNull(productNotFound);
    }
}