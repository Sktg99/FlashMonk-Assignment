package com.example.demo.service;

import com.example.demo.dto.PlaceOrderRequest;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repo.OrderRepository;
import com.example.demo.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager entityManager;
    public String placeOrder(PlaceOrderRequest placeOrderRequest) {
        List<Product> productList=placeOrderRequest.getProducts();
        Boolean done=true;

        for(Product i:productList){
            Integer id=i.getId();
            Optional<Product> search=productRepository.findById(id);
            if(search.isPresent()){

            }else{
                done=false;
                return "Invalid Request";
            }
        }
        for(Product i:productList){
            Optional<Product> search=productRepository.findById(i.getId());
            if(search.isPresent()){
                if(search.get().getQuantities()==1){
                    productRepository.deleteById(i.getId());
                }
                else {
                    productRepository.save(Product.builder()
                            .id(i.getId())
                            .brand(i.getBrand())
                            .name(i.getName())
                            .price(i.getPrice())
                            .quantities(search.get().getQuantities() - 1)
                            .build());
                }
            }else{
                productRepository.save(Product.builder()
                        .id(i.getId())
                        .brand(i.getBrand())
                        .name(i.getName())
                        .price(i.getPrice())
                        .quantities(1)
                        .build());
            }

            Order order=Order.builder()
                    .product_id(i.getId())
                    .price(i.getPrice())
                    .brand(i.getBrand())
                    .status("buy")
                    .product_name(i.getName())
                    .user_name(placeOrderRequest.getUserName())
                    .user_id(placeOrderRequest.getUserId())
                    .build();

            orderRepository.save(order);
        }
        return "Order placed";
    }

    public String cancelOrder(int id) {
        Optional<Order> checkOrder=orderRepository.findById(id);
        if(checkOrder.isPresent()){
            orderRepository.deleteById(id);
            Optional<Product> checkProduct=productRepository.findById(id);
            if(checkProduct.isPresent()){
                productRepository.save(Product.builder()
                        .id(checkProduct.get().getId())
                        .brand(checkProduct.get().getBrand())
                        .name(checkProduct.get().getName())
                        .price(checkProduct.get().getPrice())
                        .quantities(checkProduct.get().getQuantities() + 1)
                        .build());
            }
            else{
                productRepository.save(Product.builder()
                        .brand(checkProduct.get().getBrand())
                        .name(checkProduct.get().getName())
                        .price(checkProduct.get().getPrice())
                        .quantities(1)
                        .build());
            }
            return "order cancelled";
        }
        else{
            return "invalid request";
        }
    }

    public List<Order> fetchOrders(int customerId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> sm = query.from(Order.class);
        query.where(cb.equal(sm.get("user_id"), customerId));
        TypedQuery<Order> query1 = entityManager.createQuery(query);
        List<Order> results = query1.getResultList();
        return results;
    }
}
