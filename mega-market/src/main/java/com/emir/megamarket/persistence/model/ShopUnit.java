package com.emir.megamarket.persistence.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShopUnit {

    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    // example: 2022-05-28T21:12:01.000Z
    private String date;

    private String parentId;

    private ShopUnitType type;

    private int price;

//    @ManyToOne
//    private ShopUnit parentShopUnit;
//
//    @OneToMany(mappedBy = "parentShopUnit")
//    private List<ShopUnit> children;

    public ShopUnit() {
    }

    public ShopUnit(String name, String date, String parentId, ShopUnitType type, int price) {
        this.name = name;
        this.date = date;
        this.parentId = parentId;
        this.type = type;
        this.price = price;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

//    public ShopUnit getParentShopUnit() {
//        return parentShopUnit;
//    }
//
//    public void setParentShopUnit(ShopUnit parentShopUnit) {
//        this.parentShopUnit = parentShopUnit;
//    }
//
//    public List<ShopUnit> getChildren() {
//        return children;
//    }
//
//    public void setChildren(List<ShopUnit> children) {
//        this.children = children;
//    }
}

