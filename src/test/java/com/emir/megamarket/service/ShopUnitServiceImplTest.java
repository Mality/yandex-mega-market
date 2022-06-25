package com.emir.megamarket.service;

import com.emir.megamarket.persistence.dao.ShopUnitRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.persistence.model.ShopUnitType;
import com.emir.megamarket.service.validation.ImportValidator;
import com.emir.megamarket.web.dto.ShopUnitImport;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ShopUnitServiceImplTest {

    @Mock
    private ShopUnitStatisticService statisticService;

    @Mock
    private ShopUnitRepository repository;

    @Mock
    private ImportValidator validator;

    @InjectMocks
    private ShopUnitServiceImpl shopUnitService;

    @Test
    public void givenShopUnitImportRequest_whenSave_thenSaveToRepositoryCorrectShopUnit() {
        ShopUnitImportRequest importRequest = new ShopUnitImportRequest();
        ShopUnitImport shopUnitImport = new ShopUnitImport();
        shopUnitImport.setId("73bc3b36-02d1-4245-ab35-3106c9ee1c65");
        shopUnitImport.setName("Goldstar 65\" LED UHD LOL Very Smart");
        shopUnitImport.setType(ShopUnitType.OFFER);
        shopUnitImport.setPrice(69999);

        ShopUnit shopUnit = new ShopUnit();
        shopUnit.setId("73bc3b36-02d1-4245-ab35-3106c9ee1c65");
        shopUnit.setName("Goldstar 65\" LED UHD LOL Very Smart");
        shopUnit.setType(ShopUnitType.OFFER);
        shopUnit.setPrice(69999);
        shopUnit.setDate("2022-02-03T15:00:00.000Z");

        importRequest.setItems(List.of(shopUnitImport));
        importRequest.setUpdateDate("2022-02-03T15:00:00.000Z");

        shopUnitService.save(importRequest);

        verify(repository).save(shopUnit);

        verify(validator).validateShopUnitImportRequest(importRequest);
        verify(validator).validateShopUnit(shopUnit);

        verify(statisticService).addStatisticRecord(shopUnit);
    }
}
