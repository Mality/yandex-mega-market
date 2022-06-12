package com.emir.megamarket.web.dto;

import java.util.List;

public class ShopUnitStatisticResponse {

    private List<ShopUnitStatisticUnit> items;

    public ShopUnitStatisticResponse() {
    }

    public ShopUnitStatisticResponse(List<ShopUnitStatisticUnit> items) {
        this.items = items;
    }

    public List<ShopUnitStatisticUnit> getItems() {
        return items;
    }

    public void setItems(List<ShopUnitStatisticUnit> items) {
        this.items = items;
    }
}
