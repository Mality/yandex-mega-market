package com.emir.megamarket.service;

import com.emir.megamarket.persistence.dao.ShopUnitRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.persistence.model.ShopUnitType;
import com.emir.megamarket.web.error.ShopUnitNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class ShopUnitServiceTest {

    @Autowired
    private ShopUnitRepository shopUnitRepository;

    @Autowired
    private ShopUnitService shopUnitService;

    @DisplayName("JUnit test for deleting shopUnit")
    @Test
    public void givenShopUnits_whenDeleteParent_thenChildrenShouldBeRemoved() {
        ShopUnit category1 = getCategory1();
        ShopUnit category2 = getCategory2();
        ShopUnit offer1 = getOffer1();
        ShopUnit offer2 = getOffer2();

        category2.setParentId(category1.getId());
        category2.setParentShopUnit(category1);

        offer1.setParentId(category2.getId());
        offer1.setParentShopUnit(category2);
        offer2.setParentId(category2.getId());
        offer2.setParentShopUnit(category2);


        shopUnitRepository.save(category1);
        shopUnitRepository.save(category2);
        shopUnitRepository.save(offer1);
        shopUnitRepository.save(offer2);

        shopUnitService.delete(category2.getId());

        ShopUnit resultCategory1 = shopUnitRepository.findById(category1.getId()).orElseThrow(() -> new ShopUnitNotFoundException(category1.getId()));
        assertThat(category1).isEqualTo(resultCategory1);

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

    private ShopUnit getCategory1() {
        ShopUnit category = new ShopUnit();
        category.setId("d3363aa5-d56d-443a-9a9b-78f50fbc2df4");
        category.setName("Техника");
        category.setDate(LocalDateTime.now().toString());
        category.setType(ShopUnitType.CATEGORY);
        return category;
    }

    private ShopUnit getCategory2() {
        ShopUnit category = new ShopUnit();
        category.setId("f80f0eaf-a709-4173-8878-f0d11d68523a");
        category.setName("Телефоны");
        category.setDate(LocalDateTime.now().toString());
        category.setType(ShopUnitType.CATEGORY);
        return category;
    }

    private ShopUnit getOffer1() {
        ShopUnit offer = new ShopUnit();
        offer.setId("f729087b-e9f5-41b7-b376-442b7b438158");
        offer.setName("Iphone");
        offer.setDate(LocalDateTime.now().toString());
        offer.setType(ShopUnitType.OFFER);
        offer.setPrice(55000);
        return offer;
    }

    private ShopUnit getOffer2() {
        ShopUnit offer = new ShopUnit();
        offer.setId("bf71a8e0-298c-4802-991a-982a571fa2d3");
        offer.setName("Samsung");
        offer.setDate(LocalDateTime.now().toString());
        offer.setType(ShopUnitType.OFFER);
        offer.setPrice(70000);
        return offer;
    }
}