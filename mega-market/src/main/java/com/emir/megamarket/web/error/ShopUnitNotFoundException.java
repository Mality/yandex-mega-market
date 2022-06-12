package com.emir.megamarket.web.error;

public class ShopUnitNotFoundException extends RuntimeException {

    public ShopUnitNotFoundException() {
        super();
    }

    public ShopUnitNotFoundException(String message) {
        super(message);
    }
}
