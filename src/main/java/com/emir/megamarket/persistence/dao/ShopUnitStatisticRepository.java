package com.emir.megamarket.persistence.dao;

import com.emir.megamarket.persistence.model.ShopUnitStatisticUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopUnitStatisticRepository extends JpaRepository<ShopUnitStatisticUnit, String> {

    List<ShopUnitStatisticUnit> getAllByIdAndDateGreaterThanEqualAndDateLessThanEqual(String id, String startDate, String endDate);
}
