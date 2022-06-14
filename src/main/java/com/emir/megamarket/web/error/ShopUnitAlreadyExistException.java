package com.emir.megamarket.web.error;

public class ShopUnitAlreadyExistException extends ShopUnitValidationException {

    public ShopUnitAlreadyExistException() {
        super();
    }

    public ShopUnitAlreadyExistException(String id) {
        super("ShopUnit with id: " + id + " already exist");
    }
}
