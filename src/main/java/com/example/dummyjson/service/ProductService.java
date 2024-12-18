package com.example.dummyjson.service;

import com.example.dummyjson.config.DummyJsonConfig;
import com.example.dummyjson.dto.Product;
import com.example.dummyjson.dto.ProductListResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final WebClient webClient;

    @Autowired
    public ProductService(WebClient.Builder webClient, DummyJsonConfig dummyJsonConfig) {
        this.webClient = webClient.baseUrl(dummyJsonConfig.getBaseUrl()).build();
    }

    public ProductService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Product> getAllProducts() {
        String response = webClient.get()
                .uri("/products")
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.isError(), clientResponse -> Mono.error(new RuntimeException("API error!")))
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            ProductListResponse test = objectMapper.readValue(response,  ProductListResponse.class);
            return test.getProducts();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }

    public Product getProductById(Long id) {
        String response = webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.isError(), clientResponse -> Mono.error(new RuntimeException("API error!")))
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(response, new TypeReference<Product>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }

//    public List<Product> getAllProducts() {
//        Product[] products = webClient.get()
//                .uri("/products")
//                .retrieve()
//                .bodyToMono(Product[].class)
//                .block();
//
//        return Arrays.asList(products);
//    }
//
//    public Product getProductById(Long id) {
//        return webClient.get()
//                .uri("/products/{id}", id)
//                .retrieve()
//                .bodyToMono(Product.class)
//                .block();
//    }
}
