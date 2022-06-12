package com.emir.megamarket.service;

import com.emir.megamarket.persistence.dao.ShopUnitRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.web.dto.ShopUnitImport;
import com.emir.megamarket.web.dto.ShopUnitImportRequest;
import com.emir.megamarket.web.error.ShopUnitNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopUnitService {

    private final ShopUnitRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    public ShopUnitService(ShopUnitRepository repository) {
        this.repository = repository;
    }

    public void save(ShopUnitImportRequest shopUnitImportRequest) {
        for (ShopUnitImport shopUnitImport : shopUnitImportRequest.getItems()) {
            ShopUnit shopUnit = convertToModel(shopUnitImport);
            shopUnit.setDate(shopUnitImportRequest.getUpdateDate());
            repository.save(shopUnit);
        }
    }

    public ShopUnit get(String id) {
        return repository.findById(id).orElseThrow(() -> new ShopUnitNotFoundException("Shop unit with id: " + id + " not found"));
    }

    // for testing
    public List<ShopUnit> get() {
        return repository.findAll();
    }

    public void delete(String id) {
        ShopUnit shopUnit = repository.findById(id).orElseThrow(() -> new ShopUnitNotFoundException("Shop unit with id: " + id + " not found"));
//        for (ShopUnit children : shopUnit.getChildren()) {
            // ...
//        }
        repository.deleteById(id);
    }

    private ShopUnit convertToModel(ShopUnitImport shopUnitImport) {
        return mapper.map(shopUnitImport, ShopUnit.class);
    }

    private ShopUnitImport convertToDto(ShopUnit shopUnit) {
        return mapper.map(shopUnit, ShopUnitImport.class);
    }
}
