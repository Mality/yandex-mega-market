package com.emir.megamarket.service.data;

public class ShopUnitSubunitsData {

    private int childrenOffersCount;
    private int childrenOffersPriceSum;

    public ShopUnitSubunitsData() {
    }

    public ShopUnitSubunitsData(int childrenOffersCount, int childrenOffersPriceSum) {
        this.childrenOffersCount = childrenOffersCount;
        this.childrenOffersPriceSum = childrenOffersPriceSum;
    }

    public int getChildrenOffersCount() {
        return childrenOffersCount;
    }

    public void setChildrenOffersCount(int childrenOffersCount) {
        this.childrenOffersCount = childrenOffersCount;
    }

    public int getChildrenOffersPriceSum() {
        return childrenOffersPriceSum;
    }

    public void setChildrenOffersPriceSum(int childrenOffersPriceSum) {
        this.childrenOffersPriceSum = childrenOffersPriceSum;
    }
}
