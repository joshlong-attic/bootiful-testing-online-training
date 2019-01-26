package com.example.customerservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
	* @author <a href="josh@joshlong.com">Josh Long</a>
	*/
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CustomerRestControllerTest {

	@MockBean
	private CustomerRepository customerRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void customerByIdShouldReturnAJsonCustomer() throws Exception {

		Mockito
			.when(this.customerRepository.findById(1L))
			.thenReturn(Optional.of(new Customer(1L, "first", "last", "email@email.com")));

		this.mockMvc.perform(get("/customers/1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("@.id").value(1L))
			.andReturn();
	}

	@Test
	public void customersShouldReturnAllCustomers() throws Exception {

		List<Customer> customerList = Arrays.asList(
			new Customer(1L, "first", "last", "email@email.com"),
			new Customer(2L, "first", "last", "email@email.com"));

		Mockito.when(this.customerRepository.findAll()).thenReturn(customerList);

		this.mockMvc
			.perform(get("/customers"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("@.[0].id").value(1L))
			.andExpect(jsonPath("@.[0].firstName").value("first"))
			.andExpect(jsonPath("@.[1].id").value(2L));

	}

}
