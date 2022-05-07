package com.example.demo.controller;

import com.example.demo.dto.PlaceOrderRequest;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/flashMonk")
public class DemoController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/showCatalog")
    public String showCatalog() throws JsonProcessingException {
        return productService.getAll();
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest){
        return orderService.placeOrder(placeOrderRequest);
    }

    @DeleteMapping("/cancelOrder/{id}")
    public String cancelOrder(@PathVariable("id") int id){
        return orderService.cancelOrder(id);
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody ProductRequest productRequest){
        return productService.addProduct(productRequest);
    }

    @GetMapping("fetchOrders/{id}")
    public List<Order> fetchOrders(@PathVariable("id") int customerId){
        return orderService.fetchOrders(customerId);
    }

}
