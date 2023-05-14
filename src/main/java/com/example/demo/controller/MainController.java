package com.example.demo.controller;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.ProductStatus;
import com.example.demo.service.SomeService;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "product")
public class MainController {
    @Autowired
    SomeService service;



    @GetMapping("{id}")
    public Product oneProduct(@PathVariable String id) {
        return getProduct(id);
    }

    private Product getProduct(String id) {

        return service.getAllProduct().stream()
                .filter(product -> product.getId() == Integer.parseInt(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);

    }


    @GetMapping
    public List<Product> allProduct(){
        return service.getAllProduct();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        service.generateAndSetId(product);
        service.getAllProduct().add(product);
        return product;
    }

    @PutMapping("{id}")
    public Product update(@PathVariable String id,@RequestBody Product product){
        product.setId(Integer.parseInt(id));
        Product oldProduct=getProduct(id);
        service.getAllProduct().set(service.getAllProduct().indexOf(oldProduct), product);
        return product;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Product product=getProduct(id);
        service.getAllProduct().remove(product);
    }


    
    @GetMapping("add")
    public RedirectView addProduct(@RequestParam(name = "name") String name, @RequestParam(name = "price") String price){
        Product product=new Product(name, Integer.parseInt(price));
        service.generateAndSetId(product);
        product.setStatus(ProductStatus.In_order);
        service.getAllProduct().add(product);
        return new RedirectView("/product");
    }

    @GetMapping("delete/{id}")
    public RedirectView del(@PathVariable String id){
        Product product=getProduct(id);
        service.getAllProduct().remove(product);
        return new RedirectView("/product");
    }

    @GetMapping("update/{id}")
    public RedirectView update(@PathVariable String id){
        Product product=getProduct(id);
        if(product.getStatus()==ProductStatus.In_order) product.setStatus(ProductStatus.Bought);
        return new RedirectView("/product");
    }
}
