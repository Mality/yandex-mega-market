package com.emir.megamarket.web.dto;

import com.emir.megamarket.web.validation.ValidDate;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ShopUnitImportRequest {

    @NotNull(message = "ShopUnitImportRequest items couldn't be null")
    private List<ShopUnitImport> items;

    @ValidDate
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
