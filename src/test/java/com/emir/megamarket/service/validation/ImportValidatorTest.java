package com.emir.megamarket.service.validation;

import com.emir.megamarket.persistence.dao.ShopUnitRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.persistence.model.ShopUnitType;
import com.emir.megamarket.web.dto.ShopUnitImport;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.emir.megamarket.web.error.ShopUnitAlreadyExistException;
import com.emir.megamarket.web.error.ShopUnitImportRequestValidationException;
import com.emir.megamarket.web.error.ShopUnitValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class ImportValidatorTest {

    @Mock
    private ShopUnitRepository repository;

    @InjectMocks
    private ImportValidator validator;

    @Test
    public void givenShopUnitWithNonUniqueId_whenValidate_thenThrowShopUnitAlreadyExistException() {
        ShopUnit shopUnit = new ShopUnit();
        shopUnit.setId("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
        shopUnit.setName("unit");
        shopUnit.setDate("2022-02-02T12:00:00.000Z");
        shopUnit.setType(ShopUnitType.OFFER);
        shopUnit.setPrice(79999);

        when(repository.findById(shopUnit.getId())).thenReturn(java.util.Optional.of(shopUnit));

        assertThatThrownBy(() -> validator.validateShopUnit(shopUnit))
                .isInstanceOf(ShopUnitAlreadyExistException.class)
                .hasMessage("ShopUnit with id: " + shopUnit.getId() + " already exist");
    }

    @Test
    public void givenShopUnitWithOfferParent_whenValidate_thenThrowShopUnitValidationException() {
        ShopUnit shopUnit = new ShopUnit();
        shopUnit.setId("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
        shopUnit.setName("unit");
        shopUnit.setDate("2022-02-02T12:00:00.000Z");
        shopUnit.setType(ShopUnitType.OFFER);
        shopUnit.setPrice(79999);

        ShopUnit parentShopUnit = new ShopUnit();
        parentShopUnit.setId("1cc0129a-2bfe-474c-9ee6-d435bf5fc8f2");
        parentShopUnit.setName("parent");
        parentShopUnit.setDate("2022-02-02T12:00:00.000Z");
        parentShopUnit.setType(ShopUnitType.OFFER);
        parentShopUnit.setPrice(79999);

        shopUnit.setParentId(parentShopUnit.getId());
        shopUnit.setParentShopUnit(parentShopUnit);

        assertThatThrownBy(() -> validator.validateShopUnit(shopUnit))
                .isInstanceOf(ShopUnitValidationException.class)
                .hasMessage("ShopUnit couldn't have offer parent");
    }

    @Test
    public void givenShopUnitWithNullName_whenValidate_thenThrowShopUnitValidationException() {
        ShopUnit shopUnit = new ShopUnit();
        shopUnit.setId("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
        shopUnit.setName(null);
        shopUnit.setDate("2022-02-02T12:00:00.000Z");
        shopUnit.setType(ShopUnitType.OFFER);
        shopUnit.setPrice(79999);

        assertThatThrownBy(() -> validator.validateShopUnit(shopUnit))
                .isInstanceOf(ShopUnitValidationException.class)
                .hasMessage("ShopUnit couldn't have null name");
    }

    @Test
    public void givenCategoryWithNonNullPrice_whenValidate_thenThrowShopUnitValidationException() {
        ShopUnit shopUnit = new ShopUnit();
        shopUnit.setId("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
        shopUnit.setName("category");
        shopUnit.setDate("2022-02-02T12:00:00.000Z");
        shopUnit.setType(ShopUnitType.CATEGORY);
        shopUnit.setPrice(79999);

        assertThatThrownBy(() -> validator.validateShopUnit(shopUnit))
                .isInstanceOf(ShopUnitValidationException.class)
                .hasMessage("Category should have price null");
    }

    @Test
    public void givenOfferWithNullPrice_whenValidate_thenThrowShopUnitValidationException() {
        ShopUnit shopUnit = new ShopUnit();
        shopUnit.setId("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
        shopUnit.setName("offer");
        shopUnit.setDate("2022-02-02T12:00:00.000Z");
        shopUnit.setType(ShopUnitType.OFFER);
        shopUnit.setPrice(null);

        assertThatThrownBy(() -> validator.validateShopUnit(shopUnit))
                .isInstanceOf(ShopUnitValidationException.class)
                .hasMessage("Offer couldn't have price null");
    }

    @Test
    public void givenOfferWithPriceLessThanZero_whenValidate_thenThrowShopUnitValidationException() {
        ShopUnit shopUnit = new ShopUnit();
        shopUnit.setId("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
        shopUnit.setName("offer");
        shopUnit.setDate("2022-02-02T12:00:00.000Z");
        shopUnit.setType(ShopUnitType.OFFER);
        shopUnit.setPrice(-100);

        assertThatThrownBy(() -> validator.validateShopUnit(shopUnit))
                .isInstanceOf(ShopUnitValidationException.class)
                .hasMessage("Offer should have price greater or equal to zero");
    }

    @Test
    public void givenShopUnitImportRequestWithNullItems_whenValidate_thenThrowShopUnitImportRequestValidationException() {
        ShopUnitImport shopUnitImport = new ShopUnitImport();
        shopUnitImport.setId("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
        shopUnitImport.setName("offer");
        shopUnitImport.setType(ShopUnitType.OFFER);
        shopUnitImport.setPrice(100);

        ShopUnitImportRequest importRequest = new ShopUnitImportRequest();
        importRequest.setItems(Arrays.asList(shopUnitImport, null));
        importRequest.setUpdateDate("2022-02-02T12:00:00.000Z");

        assertThatThrownBy(() -> validator.validateShopUnitImportRequest(importRequest))
                .isInstanceOf(ShopUnitImportRequestValidationException.class)
                .hasMessage("ShopUnitImportRequest item couldn't be null");
    }

    @Test
    public void givenShopUnitImportRequestWithNonUniqueId_whenValidate_thenThrowShopUnitImportRequestValidationException() {
        ShopUnitImport shopUnitImport1 = new ShopUnitImport();
        shopUnitImport1.setId("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
        shopUnitImport1.setName("offer");
        shopUnitImport1.setType(ShopUnitType.OFFER);
        shopUnitImport1.setPrice(100);

        ShopUnitImport shopUnitImport2 = new ShopUnitImport();
        shopUnitImport2.setId("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
        shopUnitImport2.setName("category");
        shopUnitImport2.setType(ShopUnitType.CATEGORY);

        ShopUnitImportRequest importRequest = new ShopUnitImportRequest();
        importRequest.setItems(Arrays.asList(shopUnitImport1, shopUnitImport2));
        importRequest.setUpdateDate("2022-02-02T12:00:00.000Z");

        assertThatThrownBy(() -> validator.validateShopUnitImportRequest(importRequest))
                .isInstanceOf(ShopUnitImportRequestValidationException.class)
                .hasMessage("ShopUnits should have unique ids");
    }

    @Test
    public void givenShopUnitImportRequestWithIllegalDateTimeFormat_whenValidate_thenThrowShopUnitImportRequestValidationException() {
        ShopUnitImport shopUnitImport = new ShopUnitImport();
        shopUnitImport.setId("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
        shopUnitImport.setName("offer");
        shopUnitImport.setType(ShopUnitType.OFFER);
        shopUnitImport.setPrice(100);

        ShopUnitImportRequest importRequest = new ShopUnitImportRequest();
        importRequest.setItems(List.of(shopUnitImport));
        importRequest.setUpdateDate("2022-02-02T12:00:00.000");

        assertThatThrownBy(() -> validator.validateShopUnitImportRequest(importRequest))
                .isInstanceOf(ShopUnitImportRequestValidationException.class)
                .hasMessage("Illegal dateTime format");
    }
}
