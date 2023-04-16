package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.ProductStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SomeService {

    public int counter =5;

    ArrayList<Product> products=new ArrayList<Product>(){{
        add(new Product(1, "f", 9));
        add(new Product(2, "2f", 29));
        add(new Product(3, "f4", 94));
        add(new Product(4, "2f4", 249));}};

    public List<Product> getAllProduct(){
        return products;
    }
    public Product createProduct(){
        return new Product(counter++,"ffff",978);
    }

    public Product setId(Product product){
        product.setId(counter++);
        return product;
    }

}
