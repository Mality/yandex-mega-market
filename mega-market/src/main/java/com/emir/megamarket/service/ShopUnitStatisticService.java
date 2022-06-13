package com.emir.megamarket.service;

import com.emir.megamarket.persistence.dao.ShopUnitStatisticRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.persistence.model.ShopUnitStatisticUnit;
import com.emir.megamarket.web.dto.ShopUnitStatisticResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ShopUnitStatisticService {

    private ShopUnitStatisticRepository statisticRepository;

    private final ModelMapper mapper = new ModelMapper();

    public ShopUnitStatisticService(ShopUnitStatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public void addStatisticRecord(ShopUnit shopUnit) {
        statisticRepository.save(convertToStatisticUnit(shopUnit));
    }

    public ShopUnitStatisticResponse getShopUnitStatistic(String id, String startDate, String endDate) {
        return new ShopUnitStatisticResponse(statisticRepository.getAllByIdAndDateGreaterThanEqualAndDateLessThanEqual(id, startDate, endDate));
    }

    private ShopUnitStatisticUnit convertToStatisticUnit(ShopUnit shopUnit) {
        return mapper.map(shopUnit, ShopUnitStatisticUnit.class);
    }
}
