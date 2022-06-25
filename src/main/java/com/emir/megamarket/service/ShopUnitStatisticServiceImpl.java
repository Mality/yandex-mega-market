package com.emir.megamarket.service;

import com.emir.megamarket.persistence.dao.ShopUnitStatisticRepository;
import com.emir.megamarket.persistence.model.ShopUnit;
import com.emir.megamarket.persistence.model.ShopUnitStatisticUnit;
import com.emir.megamarket.web.dto.ShopUnitStatisticResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ShopUnitStatisticServiceImpl implements ShopUnitStatisticService {

    private final ShopUnitStatisticRepository statisticRepository;

    private final ModelMapper mapper = new ModelMapper();

    public ShopUnitStatisticServiceImpl(ShopUnitStatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    public void addStatisticRecord(ShopUnit shopUnit) {
        statisticRepository.save(convertToStatisticUnit(shopUnit));
    }

    @Override
    public ShopUnitStatisticResponse getShopUnitStatistic(String id, String startDate, String endDate) {
        return new ShopUnitStatisticResponse(statisticRepository.getAllByIdAndDateGreaterThanEqualAndDateLessThanEqual(id, startDate, endDate));
    }

    private ShopUnitStatisticUnit convertToStatisticUnit(ShopUnit shopUnit) {
        return mapper.map(shopUnit, ShopUnitStatisticUnit.class);
    }
}
