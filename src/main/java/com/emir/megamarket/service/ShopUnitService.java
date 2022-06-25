package com.emir.megamarket.service;

import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.persistence.model.ShopUnitStatisticUnit;
import com.emir.megamarket.service.data.ShopUnitSubunitsData;
import com.emir.megamarket.web.dto.ShopUnitImport;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.emir.megamarket.web.dto.ShopUnitStatisticResponse;

import java.util.List;

public interface ShopUnitService {

    void save(ShopUnitImportRequest shopUnitImportRequest);

    ShopUnit get(String id);

    void delete(String id);

    ShopUnitStatisticResponse getSales(String date);
}
