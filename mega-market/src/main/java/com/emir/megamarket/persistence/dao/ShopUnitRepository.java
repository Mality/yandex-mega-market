package com.emir.megamarket.persistence.dao;

import com.emir.megamarket.persistence.model.ShopUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopUnitRepository extends JpaRepository<ShopUnit, String> {
}
