package com.emir.megamarket.service;

import com.emir.megamarket.persistence.dao.ShopUnitRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.persistence.model.ShopUnitType;
import com.emir.megamarket.web.dto.ShopUnitImport;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.emir.megamarket.web.error.ShopUnitAlreadyExistException;
import com.emir.megamarket.web.error.ShopUnitImportRequestValidationException;
import com.emir.megamarket.web.error.ShopUnitNotFoundException;
import com.emir.megamarket.web.error.ShopUnitValidationException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class ShopUnitService {

    private final ShopUnitRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    private final Logger logger = LoggerFactory.getLogger(ShopUnitService.class);

    public ShopUnitService(ShopUnitRepository repository) {
        this.repository = repository;
    }

    public void save(ShopUnitImportRequest shopUnitImportRequest) {
        validateShopUnitImportRequest(shopUnitImportRequest);
        for (ShopUnitImport shopUnitImport : shopUnitImportRequest.getItems()) {
            ShopUnit shopUnit = convertToModel(shopUnitImport);
            shopUnit.setDate(shopUnitImportRequest.getUpdateDate());
            if (shopUnit.getParentId() != null) {
                shopUnit.setParentShopUnit(get(shopUnit.getParentId()));
            }
            if (shopUnit.getType() == ShopUnitType.OFFER) {
                updateParentCategories(shopUnit, shopUnitImportRequest.getUpdateDate(), true);
            }
            validateShopUnit(shopUnit);
            repository.save(shopUnit);
        }
    }

    private void validateShopUnit(ShopUnit shopUnit) {
        if (shopUnit.getId() == null) {
            throw new ShopUnitValidationException("ShopUnit id couldn't be null");
        }
        if (shopUnit.getName() == null) {
            throw new ShopUnitValidationException("ShopUnit name couldn't be null");
        }
        if (shopUnit.getType() == null) {
            throw new ShopUnitValidationException("ShopUnit type couldn't be null");
        }
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

    private void validateShopUnitImportRequest(ShopUnitImportRequest shopUnitImportRequest) {
        if (shopUnitImportRequest.getUpdateDate() == null) {
            throw new ShopUnitImportRequestValidationException("ShopUnitImportRequest updateDate couldn't be null");
        }
        if (shopUnitImportRequest.getItems() == null) {
            throw new ShopUnitImportRequestValidationException("ShopUnitImportRequest items couldn't be null");
        }
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
//            LocalDateTime.parse(shopUnitImportRequest.getUpdateDate());
        } catch (DateTimeParseException e) {
            throw new ShopUnitImportRequestValidationException("Illegal dateTime format");
        }
    }

    public ShopUnit get(String id) {
        ShopUnit shopUnit = repository.findById(id).orElseThrow(() -> new ShopUnitNotFoundException(id));

        Queue<ShopUnit> queue = new ArrayDeque<>();
        queue.add(shopUnit);
        while (!queue.isEmpty()) {
            ShopUnit curShopUnit = queue.peek();
            queue.remove();
            if (curShopUnit.getChildren() != null) {
                if (curShopUnit.getChildren().isEmpty()) {
                    curShopUnit.setChildren(null);
                } else {
                    queue.addAll(curShopUnit.getChildren());
                }
            }
        }

        return shopUnit;
    }

    // for testing
    public List<ShopUnit> get() {
        return repository.findAll();
    }

    public void delete(String id) {
        ShopUnit shopUnit = repository.findById(id).orElseThrow(() -> new ShopUnitNotFoundException(id));
        recalculatePriceWhenDeleted(shopUnit);
        repository.delete(shopUnit);
    }

    private void recalculatePriceWhenDeleted(ShopUnit shopUnit) {
        if (shopUnit.getType() == ShopUnitType.OFFER) {
            updateParentCategories(shopUnit, null, false);
            return;
        }
        for (ShopUnit item : shopUnit.getChildren()) {
            recalculatePriceWhenDeleted(item);
        }
    }

    private void updateParentCategories(ShopUnit shopUnit, String updateDate, boolean add) {
        ShopUnit currentShopUnit = shopUnit;
        while (currentShopUnit.getParentShopUnit() != null) {
            ShopUnit parentShopUnit = currentShopUnit.getParentShopUnit();
            if (add) {
                parentShopUnit.setChildrenOffersCount(parentShopUnit.getChildrenOffersCount() + 1);
                parentShopUnit.setChildrenOffersSum(parentShopUnit.getChildrenOffersSum() + shopUnit.getPrice());
                parentShopUnit.setDate(updateDate);
            } else {
                parentShopUnit.setChildrenOffersCount(parentShopUnit.getChildrenOffersCount() - 1);
                parentShopUnit.setChildrenOffersSum(parentShopUnit.getChildrenOffersSum() - shopUnit.getPrice());
            }
            if (parentShopUnit.getChildrenOffersCount() == 0) {
                parentShopUnit.setPrice(null);
            } else {
                parentShopUnit.setPrice(parentShopUnit.getChildrenOffersSum() / parentShopUnit.getChildrenOffersCount());
            }
            repository.save(parentShopUnit);
            currentShopUnit = parentShopUnit;
        }
    }

    private ShopUnit convertToModel(ShopUnitImport shopUnitImport) {
        return mapper.map(shopUnitImport, ShopUnit.class);
    }

    private ShopUnitImport convertToDto(ShopUnit shopUnit) {
        return mapper.map(shopUnit, ShopUnitImport.class);
    }
}
