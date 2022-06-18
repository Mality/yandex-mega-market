package com.emir.megamarket.web.controller;

import com.emir.megamarket.service.ShopUnitService;
import com.emir.megamarket.service.ShopUnitStatisticService;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.emir.megamarket.web.validation.ValidDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class ShopUnitRESTController {

    Logger logger = LoggerFactory.getLogger(ShopUnitRESTController.class);

    private final ShopUnitService shopUnitService;

    private final ShopUnitStatisticService statisticService;

    public ShopUnitRESTController(ShopUnitService shopUnitService, ShopUnitStatisticService statisticService) {
        this.shopUnitService = shopUnitService;
        this.statisticService = statisticService;
    }

    @PostMapping("/imports")
    public ResponseEntity<Object> importShopUnit(@RequestBody @Valid ShopUnitImportRequest shopUnitImportRequest) {
        shopUnitService.save(shopUnitImportRequest);
        logger.info("Saved " + shopUnitImportRequest.getItems().size() + " items at " + shopUnitImportRequest.getUpdateDate());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        shopUnitService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nodes/{id}")
    public ResponseEntity<Object> get(@PathVariable String id) {
        return ResponseEntity.ok(shopUnitService.get(id));
    }

    @GetMapping("/sales")
    public ResponseEntity<Object> sales(@RequestParam @ValidDate String date) {
        return ResponseEntity.ok().body(shopUnitService.getSales(date));
    }

    @GetMapping("/node/{id}/statistic")
    public ResponseEntity<Object> nodeStatistic(@PathVariable String id, @RequestParam @ValidDate String dateStart, @RequestParam @ValidDate String dateEnd) {
        return ResponseEntity.ok().body(statisticService.getShopUnitStatistic(id, dateStart, dateEnd));
    }
}
