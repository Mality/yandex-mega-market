package com.emir.megamarket.service.validation;

import com.emir.megamarket.persistence.dao.ShopUnitRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.persistence.model.ShopUnitType;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.emir.megamarket.web.error.ShopUnitAlreadyExistException;
import com.emir.megamarket.web.error.ShopUnitImportRequestValidationException;
import com.emir.megamarket.web.error.ShopUnitValidationException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

public class ImportValidator {

    private final ShopUnitRepository repository;

    public ImportValidator(ShopUnitRepository repository) {
        this.repository = repository;
    }

    public void validateShopUnit(ShopUnit shopUnit) {
        if (repository.findById(shopUnit.getId()).isPresent()) {
            throw new ShopUnitAlreadyExistException(shopUnit.getId());
        }
        if (shopUnit.getParentShopUnit() != null && shopUnit.getParentShopUnit().getType() == ShopUnitType.OFFER) {
            throw new ShopUnitValidationException("ShopUnit couldn't have offer parent");
        }
        if (shopUnit.getName() == null) {
            throw new ShopUnitValidationException("ShopUnit couldn't have null name");
        }
        if (shopUnit.getType() == ShopUnitType.CATEGORY && shopUnit.getPrice() != null) {
            throw new ShopUnitValidationException("Category should have price null");
        }
        if (shopUnit.getType() == ShopUnitType.OFFER) {
            if (shopUnit.getPrice() == null) {
                throw new ShopUnitValidationException("Offer couldn't have price null");
            }
            if (shopUnit.getPrice() < 0) {
                throw new ShopUnitValidationException("Offer should have price greater or equal to zero");
            }
        }
    }

    public void validateShopUnitImportRequest(ShopUnitImportRequest shopUnitImportRequest) {
        shopUnitImportRequest.getItems().forEach(item -> {
            if (item == null) {
                throw new ShopUnitImportRequestValidationException("ShopUnitImportRequest item couldn't be null");
            }
        });
        Set<String> itemIds = new HashSet<>();
        shopUnitImportRequest.getItems().forEach(item -> itemIds.add(item.getId()));
        if (itemIds.size() != shopUnitImportRequest.getItems().size()) {
            throw new ShopUnitImportRequestValidationException("ShopUnits should have unique ids");
        }
        try {
            OffsetDateTime.parse(shopUnitImportRequest.getUpdateDate());
        } catch (DateTimeParseException ex) {
            throw new ShopUnitImportRequestValidationException("Illegal dateTime format");
        }
    }
}
