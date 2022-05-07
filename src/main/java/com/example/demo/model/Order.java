package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer order_id;

    @Column(name="user_id")
    private Integer user_id;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "product_id")
    private Integer product_id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "price")
    private String price;

    @Column(name="brand")
    private String brand;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private Date createdAt;

    @Column(name="status")
    private String status;
}
