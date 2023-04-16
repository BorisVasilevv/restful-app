package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "price")
    Integer price;
    @Column(name = "name")
    String name;
    @Column(name = "status")
    ProductStatus status;

    public Product(int id, String name, Integer price) {
        this.id = id;
        this.name=name;
        this.price=price;
        status=ProductStatus.In_order;
    }

    public Product(){};

    public Product(String name, Integer price){
        this.name=name;
        this.price=price;
    }

    public Product (int id, Product product){
        this.id=id;
        this.name=product.getName();
        this.price=product.getPrice();
        this.status=product.getStatus();
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }
}
