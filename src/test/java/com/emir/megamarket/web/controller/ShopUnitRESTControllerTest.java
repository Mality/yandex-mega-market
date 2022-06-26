package com.emir.megamarket.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.persistence.model.ShopUnitType;
import com.emir.megamarket.web.dto.ShopUnitImport;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShopUnitRESTControllerTest {

    private static final String NOT_FOUND_ERROR =
            "{\"code\":404,\"message\":\"Item not found\"}";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ShopUnitImportRequest shopUnitImportRequest;

    private final ShopUnit shopUnit;

    ShopUnitRESTControllerTest() {
        shopUnitImportRequest = new ShopUnitImportRequest();
        ShopUnitImport shopUnitImport = new ShopUnitImport();
        shopUnitImport.setId("3fa85f64-5717-4562-b3fc-2c963f66a444");
        shopUnitImport.setName("offer");
        shopUnitImport.setType(ShopUnitType.OFFER);
        shopUnitImport.setPrice(123);
        shopUnitImportRequest.setItems(List.of(shopUnitImport));
        shopUnitImportRequest.setUpdateDate("2022-05-28T21:12:01.000Z");

        shopUnit = new ShopUnit();
        shopUnit.setId("3fa85f64-5717-4562-b3fc-2c963f66a444");
        shopUnit.setName("offer");
        shopUnit.setDate("2022-05-28T21:12:01.000Z");
        shopUnit.setType(ShopUnitType.OFFER);
        shopUnit.setPrice(123);
    }

    @Test
    @Order(1)
    void givenNotExistingUnitId_whenGet_thenReturnError404() throws Exception {
        String id = "3fa85f64-5717-4562-b3fc-2c963f66a444";
        mockMvc.perform(get("/nodes/" + id)).andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(NOT_FOUND_ERROR));
    }

    @Test
    @Order(2)
    void givenShopUnitImportRequest_whenImportShopUnit_thenReturnStatus200() throws Exception {
        String requestJson = objectMapper.writeValueAsString(shopUnitImportRequest);
        mockMvc.perform(post("/imports").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void givenUnitId_whenGet_thenReturnShopUnit() throws Exception {
        String expectShopUnit = objectMapper.writeValueAsString(shopUnit);
        mockMvc.perform(get("/nodes/" + shopUnit.getId())).andExpect(status().isOk()).andExpect(content().string(expectShopUnit));
    }

    @Test
    @Order(4)
    void givenUnitId_whenDelete_thenReturnStatusOk() throws Exception {
        String id = "3fa85f64-5717-4562-b3fc-2c963f66a444";
        mockMvc.perform(delete("/delete/" + id)).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void givenUnitId_whenGetDeletedShopUnit_thenReturnStatusNotFoundAndError() throws Exception {
        String id = "3fa85f64-5717-4562-b3fc-2c963f66a444";
        mockMvc.perform(get("/nodes/" + id)).andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(NOT_FOUND_ERROR));
    }
}
