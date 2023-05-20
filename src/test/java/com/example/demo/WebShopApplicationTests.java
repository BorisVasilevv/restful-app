package com.example.demo;

import com.example.demo.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebShopApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private MainController controller;
	@Test
	public void test() throws Exception{
		this.mockMvc.perform(get("/product"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(""));
	}

	@Test
	public void test1() throws Exception{

	}
}
