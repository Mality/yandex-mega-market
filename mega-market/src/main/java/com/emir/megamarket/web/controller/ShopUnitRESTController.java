package com.emir.megamarket.web.controller;

import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.service.ShopUnitService;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.emir.megamarket.web.error.ImportValidationException;
import com.emir.megamarket.web.error.ShopUnitNotFoundException;
import com.emir.megamarket.web.validation.ValidDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.emir.megamarket.web.error.Error;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
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
    public ResponseEntity<Error> importShopUnit(@RequestBody @Valid ShopUnitImportRequest shopUnitImportRequest) {
        try {
            shopUnitService.save(shopUnitImportRequest);
            logger.info("Saved " + shopUnitImportRequest.getItems().size() + " items at " + shopUnitImportRequest.getUpdateDate());
            return ResponseEntity.ok().build();
        } catch (ImportValidationException ex) {
            logger.info("Import validation failed: " + ex.getMessage());
            return ResponseEntity.status(200).body(new Error(200, "Validation Failed"));
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

    @GetMapping("/sales")
    public ResponseEntity<Object> sales(@RequestParam @ValidDate String date) {
        return null;
    }
}
