package com.emir.megamarket.web.dto;

import java.util.List;

public class ShopUnitImportRequest {

    private List<ShopUnitImport> items;

    private String updateDate;

    public ShopUnitImportRequest() {
    }

    public ShopUnitImportRequest(List<ShopUnitImport> items, String updateDate) {
        this.items = items;
        this.updateDate = updateDate;
    }

    public List<ShopUnitImport> getItems() {
        return items;
    }

    public void setItems(List<ShopUnitImport> items) {
        this.items = items;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
