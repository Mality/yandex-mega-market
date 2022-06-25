package com.emir.megamarket.service;

import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.web.dto.ShopUnitStatisticResponse;

public interface ShopUnitStatisticService {

    public abstract void addStatisticRecord(ShopUnit shopUnit);

    public abstract ShopUnitStatisticResponse getShopUnitStatistic(String id, String startDate, String endDate);
}
