package com.emir.megamarket.service;

import com.emir.megamarket.persistence.dao.ShopUnitRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.persistence.model.ShopUnitType;
import com.emir.megamarket.web.dto.ShopUnitImport;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.emir.megamarket.web.error.ShopUnitNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
class ShopUnitServiceIntegrationTest {

    @Autowired
    private ShopUnitRepository shopUnitRepository;

    @Autowired
    private ShopUnitService shopUnitService;

    @BeforeEach
    private void clearRepository() {
        shopUnitRepository.deleteAll();
    }

    @DisplayName("JUnit test for deleting shopUnit")
    @Test
    public void givenShopUnits_whenDeleteParent_thenChildrenShouldBeRemoved() {
        ShopUnitImport category1 = getCategory1();
        ShopUnitImport category2 = getCategory2();
        ShopUnitImport offer1 = getOffer1();
        ShopUnitImport offer2 = getOffer2();

        category2.setParentId(category1.getId());
        offer1.setParentId(category2.getId());
        offer2.setParentId(category2.getId());

        shopUnitService.save(new ShopUnitImportRequest(List.of(category1, category2, offer1, offer2), "2022-05-28T21:12:01.000Z"));

        System.out.println(shopUnitService.get(category2.getId()));

        shopUnitService.delete(category2.getId());

        shopUnitRepository.findById(category1.getId()).orElseThrow(() -> new ShopUnitNotFoundException(category1.getId()));

        assertThatThrownBy(() -> shopUnitService.get(category2.getId()))
                .isInstanceOf(ShopUnitNotFoundException.class)
                .hasMessage("Shop unit with id: " + category2.getId() + " not found");

        assertThatThrownBy(() -> shopUnitService.get(offer1.getId()))
                .isInstanceOf(ShopUnitNotFoundException.class)
                .hasMessage("Shop unit with id: " + offer1.getId() + " not found");

        assertThatThrownBy(() -> shopUnitService.get(offer2.getId()))
                .isInstanceOf(ShopUnitNotFoundException.class)
                .hasMessage("Shop unit with id: " + offer2.getId() + " not found");
    }

    @Test
    public void givenShopUnits_whenAddedNewOffer_thenParentCategoryShouldUpdatePrice() {
        ShopUnitImport category1 = getCategory1();
        ShopUnitImport category2 = getCategory2();
        ShopUnitImport offer1 = getOffer1();
        ShopUnitImport offer2 = getOffer2();

        category2.setParentId(category1.getId());
        offer1.setParentId(category1.getId());
        offer2.setParentId(category2.getId());

        shopUnitService.save(new ShopUnitImportRequest(List.of(category1, category2, offer1, offer2), "2022-05-28T21:12:01.000Z"));

        ShopUnit resultCategory1 = shopUnitRepository.findById(category1.getId()).orElseThrow(() -> new ShopUnitNotFoundException(category1.getId()));
        ShopUnit resultCategory2 = shopUnitRepository.findById(category2.getId()).orElseThrow(() -> new ShopUnitNotFoundException(category2.getId()));

        assertThat(resultCategory2.getPrice()).isEqualTo(offer2.getPrice());
        assertThat(resultCategory1.getPrice()).isEqualTo((offer1.getPrice() + offer2.getPrice()) / 2);
    }

    private ShopUnitImport getCategory1() {
        ShopUnitImport category = new ShopUnitImport();
        category.setId("d3363aa5-d56d-443a-9a9b-78f50fbc2df4");
        category.setName("Техника");
        category.setType(ShopUnitType.CATEGORY);
        return category;
    }

    private ShopUnitImport getCategory2() {
        ShopUnitImport category = new ShopUnitImport();
        category.setId("f80f0eaf-a709-4173-8878-f0d11d68523a");
        category.setName("Телефоны");
        category.setType(ShopUnitType.CATEGORY);
        return category;
    }

    private ShopUnitImport getOffer1() {
        ShopUnitImport offer = new ShopUnitImport();
        offer.setId("f729087b-e9f5-41b7-b376-442b7b438158");
        offer.setName("Iphone");
        offer.setType(ShopUnitType.OFFER);
        offer.setPrice(55000);
        return offer;
    }

    private ShopUnitImport getOffer2() {
        ShopUnitImport offer = new ShopUnitImport();
        offer.setId("bf71a8e0-298c-4802-991a-982a571fa2d3");
        offer.setName("Samsung");
        offer.setType(ShopUnitType.OFFER);
        offer.setPrice(70000);
        return offer;
    }
}