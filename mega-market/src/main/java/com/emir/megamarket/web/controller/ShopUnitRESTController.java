package com.emir.megamarket.web.controller;

import com.emir.megamarket.persistence.dao.ShopUnitRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.service.ShopUnitService;
import com.emir.megamarket.web.dto.ShopUnitImport;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.emir.megamarket.web.error.ImportValidationException;
import com.emir.megamarket.web.error.ShopUnitNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.emir.megamarket.web.error.Error;

import java.util.List;

@RestController
public class ShopUnitRESTController {

    Logger logger = LoggerFactory.getLogger(ShopUnitRESTController.class);

    private final ShopUnitService shopUnitService;

    public ShopUnitRESTController(ShopUnitService shopUnitService) {
        this.shopUnitService = shopUnitService;
    }

    // for testing
    @GetMapping("/imports")
    public List<ShopUnit> getImports() {
        return shopUnitService.get();
    }

    @PostMapping("/imports")
    public ResponseEntity<Error> importShopUnit(@RequestBody ShopUnitImportRequest shopUnitImportRequest) {
        try {
            shopUnitService.save(shopUnitImportRequest);
            logger.info("Saved " + shopUnitImportRequest.getItems().size() + " items at " + shopUnitImportRequest.getUpdateDate());
            return ResponseEntity.ok().build();
        } catch (ImportValidationException ex) {
            return ResponseEntity.status(400).body(new Error(400, "Validation Failed"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Error> delete(@PathVariable String id) {
        try {
            shopUnitService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ShopUnitNotFoundException ex) {
            return ResponseEntity.status(404).body(new Error(404, "Item not found"));
        }
    }

    @GetMapping("/nodes/{id}")
    public ResponseEntity<Object> get(@PathVariable String id) {
        try {
            return ResponseEntity.ok(shopUnitService.get(id));
        } catch (ShopUnitNotFoundException ex) {
            return ResponseEntity.status(404).body(new Error(404, "Item not found"));
        }
    }
}
