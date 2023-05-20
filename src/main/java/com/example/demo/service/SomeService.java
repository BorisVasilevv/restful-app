package com.example.demo.service;

import com.example.demo.database.JDBC;
import com.example.demo.model.Product;
import com.example.demo.model.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SomeService {

    @Autowired
    JDBC database;

    public ArrayList<Product> getAllProduct(){
        return database.selectAllPurchase();
    }
    public Product createProduct(){
        Product product= new Product();
        generateAndSetId(product);
        product.setStatus(ProductStatus.In_order);
        product.setName("test");
        product.setPrice(0);
        database.addPurchase(product);
        return product;
    }

    public void RemoveProduct(Product product){
        database.removePurchase(Integer.toString(product.getId()));
    }

    public void generateAndSetId(Product product){
        int maxId=0;
        for (Product p:getAllProduct()) {
            if( p.getId()>maxId) maxId= p.getId();
        }
        maxId++;
        product.setId(maxId);
    }



}
