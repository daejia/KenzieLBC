package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.StoreCreateRequest;
import com.kenzie.appserver.service.StoreService;
import com.kenzie.appserver.service.model.Store;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class StoreControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    StoreService storeService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void getById_Exists() throws Exception {
        //GIVEN
        String id = UUID.randomUUID().toString();
        String name = mockNeat.strings().valStr();
        String address = mockNeat.addresses().valStr();
        String city = mockNeat.cities().us().valStr();
        String state = mockNeat.usStates().valStr();
        String zip = UUID.randomUUID().toString();

        Store store =new Store(id, name, address, city, state, zip);
        Store persistedStore = storeService.addNewStore(store);

        //WHEN
        mvc.perform(get("/store/{id}", persistedStore.getId())
                        .accept(MediaType.APPLICATION_JSON))
        //THEN
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

    @Test
    public void getAllStores_GetSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();
        String address = mockNeat.addresses().valStr();
        String city = mockNeat.cities().us().valStr();
        String state = mockNeat.usStates().valStr();
        String zip = UUID.randomUUID().toString();

        StoreCreateRequest storeCreateRequest = new StoreCreateRequest();
        storeCreateRequest.setName(name);
        storeCreateRequest.setAddress(address);
        storeCreateRequest.setCity(city);
        storeCreateRequest.setState(state);
        storeCreateRequest.setZip(zip);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/store")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(storeCreateRequest)))
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
                .andExpect(status().isCreated());
    }
}
