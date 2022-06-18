package com.emir.megamarket.service;

import com.emir.megamarket.persistence.dao.ShopUnitRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.web.dto.ShopUnitImport;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
class ShopUnitServiceTest {

    @Autowired
    private ShopUnitRepository shopUnitRepository;

    @Autowired
    private ShopUnitService shopUnitService;

    @BeforeEach
    private void clearRepository() {
        shopUnitRepository.deleteAll();
    }

    @Test
    void save() {

    }

    @Test
    void givenShopUnitImportRequest_whenUuidAlreadyExist_thenShouldThrowAlreadyExistException() {
        ShopUnitImport shopUnitImport = new ShopUnitImport();


        ShopUnitImportRequest shopUnitImportRequest = new ShopUnitImportRequest();
    }

    @Test
    void get() {
    }

    @Test
    void delete() {
    }

    @Test
    void getSales() {
    }
}