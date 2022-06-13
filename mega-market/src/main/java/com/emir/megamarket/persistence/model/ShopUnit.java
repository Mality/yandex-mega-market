package com.emir.megamarket.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ShopUnit {

    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String date;

    private String parentId;

    private ShopUnitType type;

    private Integer price;

    @JsonIgnore
    private int childrenOffersCount;

    @JsonIgnore
    private int childrenOffersSum;

    @JsonIgnore
    @ManyToOne
    private ShopUnit parentShopUnit;

    @OneToMany(mappedBy = "parentShopUnit", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<ShopUnit> children = new ArrayList<>();

    public ShopUnit() {
    }

    public ShopUnit(String id, String name, String date, String parentId, ShopUnitType type, Integer price) {
        this.id = id;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ShopUnit getParentShopUnit() {
        return parentShopUnit;
    }

    public void setParentShopUnit(ShopUnit parentShopUnit) {
        this.parentShopUnit = parentShopUnit;
    }

    public List<ShopUnit> getChildren() {
        return children;
    }

    public void setChildren(List<ShopUnit> children) {
        this.children = children;
    }

    public int getChildrenOffersCount() {
        return childrenOffersCount;
    }

    public void setChildrenOffersCount(int childrenOffersCount) {
        this.childrenOffersCount = childrenOffersCount;
    }

    public int getChildrenOffersSum() {
        return childrenOffersSum;
    }

    public void setChildrenOffersSum(int childrenOffersSum) {
        this.childrenOffersSum = childrenOffersSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopUnit shopUnit = (ShopUnit) o;
        return Objects.equals(id, shopUnit.id) && Objects.equals(name, shopUnit.name) && Objects.equals(date, shopUnit.date) && Objects.equals(parentId, shopUnit.parentId) && type == shopUnit.type && Objects.equals(price, shopUnit.price) && Objects.equals(childrenOffersCount, shopUnit.childrenOffersCount) && Objects.equals(childrenOffersSum, shopUnit.childrenOffersSum) && Objects.equals(parentShopUnit, shopUnit.parentShopUnit) && Objects.equals(children, shopUnit.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, parentId, type, price, childrenOffersCount, childrenOffersSum, parentShopUnit, children);
    }

    @Override
    public String toString() {
        return "ShopUnit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", parentId='" + parentId + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", childrenOffersCount=" + childrenOffersCount +
                ", childrenOffersSum=" + childrenOffersSum +
                ", children=" + children +
                '}';
    }
}

