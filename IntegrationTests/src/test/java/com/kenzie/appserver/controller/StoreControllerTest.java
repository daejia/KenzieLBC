package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.service.ExampleService;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.appserver.service.model.Store;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ExampleService storeService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void getById_Exists() throws Exception {
        String id = UUID.randomUUID().toString();
        String name = mockNeat.strings().valStr();
        String address = mockNeat.addresses().valStr();
        String city = mockNeat.cities().us().valStr();
        String state = mockNeat.usStates().valStr();
        String zip = UUID.randomUUID().toString();

        Store store = new Store(id, name, address, city, state, zip, true);
        Example presistedStore = storeService.findById(id);


        mvc.perform(get("/store/{id}", presistedStore.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(jsonPath("address")
                        .value(is(address)))
                .andExpect(jsonPath("city")
                        .value(is(city)))
                .andExpect(jsonPath("state")
                        .value(is(state)))
                .andExpect(jsonPath("zip")
                        .value(is(zip)))
                .andExpect(status().isOk());

    }
}
