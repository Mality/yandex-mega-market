package com.emir.megamarket.web.dto;

import com.emir.megamarket.persistence.model.ShopUnitType;

public class ShopUnitStatisticUnit {

    private String id;

    private String name;

    private String parentId;

    private ShopUnitType type;

    private Integer price;

    // example: 2022-05-28T21:12:01.000Z
    private String date;

    public ShopUnitStatisticUnit() {
    }

    public ShopUnitStatisticUnit(String id, String name, String parentId, ShopUnitType type, Integer price, String date) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.type = type;
        this.price = price;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
