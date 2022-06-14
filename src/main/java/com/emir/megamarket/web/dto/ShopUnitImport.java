package com.emir.megamarket.web.dto;

import com.emir.megamarket.persistence.model.ShopUnitType;

import javax.validation.constraints.NotNull;

public class ShopUnitImport {

    @NotNull(message = "ShopUnit id couldn't be null")
    private String id;

    @NotNull(message = "ShopUnit name couldn't be null")
    private String name;

    private String parentId;

    @NotNull(message = "ShopUnit type couldn't be null")
    private ShopUnitType type;

    private Integer price;

    public ShopUnitImport() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ShopUnitType getType() {
        return type;
    }

    public void setType(ShopUnitType type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
