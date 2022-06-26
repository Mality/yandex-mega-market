package com.emir.megamarket.service;

import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.emir.megamarket.web.dto.ShopUnitStatisticResponse;

public interface ShopUnitService {

    void save(ShopUnitImportRequest shopUnitImportRequest);

    ShopUnit get(String id);

    void delete(String id);

    ShopUnitStatisticResponse getSales(String date);
}
