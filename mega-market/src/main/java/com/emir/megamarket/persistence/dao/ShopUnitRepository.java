package com.emir.megamarket.persistence.dao;

import com.emir.megamarket.persistence.model.ShopUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopUnitRepository extends JpaRepository<ShopUnit, String> {

    List<ShopUnit> findAllByDateIsGreaterThanEqualAndDateLessThanEqual(String dateAfter, String dateBefore);
}
