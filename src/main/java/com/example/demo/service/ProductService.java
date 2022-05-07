package com.example.demo.service;

import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    public String getAll() throws JsonProcessingException {

        List<Product> productList = productRepository.findAll();

        if(productList.size() > 0){
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(productList);
            return json;
        }else{
            return "No Product Found";
        }
    }

    public String addProduct(ProductRequest productRequest) {
        Optional<Product> check=productRepository.findById(productRequest.getProductId());
        if(check.isPresent()){
            productRepository.save(Product
                    .builder()
                            .id(check.get().getId())
                            .name(check.get().getName())
                            .brand(check.get().getBrand())
                            .quantities(check.get().getQuantities()+productRequest.getQuantities())
                            .price(check.get().getPrice())
                    .build());
            return "product updated";
        }
        else{
            productRepository.save(Product
                    .builder()
                            .id(productRequest.getProductId())
                            .name(productRequest.getProductName())
                            .price(productRequest.getPrice())
                            .quantities(productRequest.getQuantities())
                            .brand(productRequest.getBrand())
                    .build());
            return "product added";
        }
    }
}
