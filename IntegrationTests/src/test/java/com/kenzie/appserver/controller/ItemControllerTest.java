package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ItemCreateRequest;
import com.kenzie.appserver.service.ItemService;
import com.kenzie.appserver.service.model.*;
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
public class ItemControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ItemService itemService;

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
        Double price = mockNeat.doubles().val();

        Store store = new Store(id, name, address, city, state, zip);

        Item item = new Item(id, store, BrandType.NAME_BRAND, name, Category.BABY, price, true);

        Item persistedItem = itemService.addNewItem(item);

        mvc.perform(get("/item/{id}", persistedItem.getId())
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
                .andExpect(jsonPath("isInStock")
                        .value(is(true)))
                .andExpect(status().isOk());
    }

    @Test
    public void getById_NotFound() throws Exception {
        String id = UUID.randomUUID().toString();

        mvc.perform(get("/item/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createItem_CreateSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();

        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setName(name);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/item")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(itemCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(status().isCreated());
    }

}
