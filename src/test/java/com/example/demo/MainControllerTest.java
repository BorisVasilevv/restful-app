package com.example.demo;


import com.example.demo.controller.MainController;
import com.example.demo.database.JDBC;
import com.example.demo.model.Product;
import com.example.demo.model.ProductStatus;
import com.example.demo.service.SomeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MainController controller;

    @Test
    public void addProductTest() throws Exception{
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":8,\"price\":33,\"name\":\"test\",\"status\":\"In_order\"}"))
                .andDo(print())
                .andExpect(status().isOk());


    }


    @Test
    public void notFoundPageTest() throws Exception{
        this.mockMvc.perform(get("/product/9999999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void elemExistTest() throws Exception{
        this.mockMvc.perform(get("/product/3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void statusOfElemPageTest() throws Exception{
        String elemNumber="7";
        boolean elemExist=JDBC.isElemExist(elemNumber);
        this.mockMvc.perform(get("/product/" + elemNumber))
                .andDo(print())
                .andExpect(elemExist
                        ?status().isOk()
                        :status().isNotFound());

    }



}
