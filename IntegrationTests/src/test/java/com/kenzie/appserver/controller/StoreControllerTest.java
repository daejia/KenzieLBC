package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.service.ExampleService;
import com.kenzie.appserver.service.ItemService;
import com.kenzie.appserver.service.StoreService;
import com.kenzie.appserver.service.model.Example;
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
    ExampleService exampleService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_Store() throws Exception {
        String id = UUID.randomUUID().toString();
        String name = mockNeat.strings().valStr();
        String address = mockNeat.addresses().valStr();
        String city = mockNeat.cities().us().valStr();
        String state = mockNeat.usStates().valStr();
        String zip = UUID.randomUUID().toString();


        Example example = new Example(id, name);
        Example persistedExample = exampleService.addNewExample(example);
        mvc.perform(get("/example/{id}", persistedExample.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(status().isOk());
    }
}
