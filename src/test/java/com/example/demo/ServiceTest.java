package com.example.demo;

import com.example.demo.database.JDBC;
import com.example.demo.model.Product;
import com.example.demo.model.ProductStatus;
import com.example.demo.service.SomeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTest {
    @Autowired
    private SomeService service;

    @MockBean
    JDBC database;

    @Test
    public void createProductTest() throws Exception{
        Product p=service.createProduct();
        Assert.assertNotNull(p);
        Assert.assertNotEquals(p.getId(), 0);
        Assert.assertEquals(p.getStatus(), ProductStatus.In_order);

        Mockito.verify(database ,Mockito.times(1)).addPurchase(p);

    }

    @Test
    public void uniqueIdProductsTest() throws Exception{
        Product p=service.createProduct();
        Product p1=service.createProduct();
        Product p2=service.createProduct();


        Assert.assertNotEquals(p.getId(),p1.getId());
        Assert.assertNotEquals(p1.getId(),p2.getId());
        Assert.assertNotEquals(p.getId(),p2.getId());
    }

    @Test
    public void getAllPurchasesTest() {
        ArrayList<Product> purchases = new ArrayList<Product>();
        Product product1 = new Product();
        product1.setName("p1");
        product1.setPrice(1);
        product1.setStatus(ProductStatus.In_order);

        Product product2 = new Product();
        product2.setName("p2");
        product2.setPrice(2);
        product2.setStatus(ProductStatus.In_order);


        purchases.add(product1);
        purchases.add(product2);


        Mockito.when(service.getAllProduct()).thenReturn(purchases);

        ArrayList<Product> listResult = service.getAllProduct();

        Assert.assertEquals(listResult.size(), purchases.size());
        for (int i = 0; i < listResult.size(); i++) {
            Assert.assertEquals(listResult.get(i).getId(), purchases.get(i).getId());
        }
    }




}
