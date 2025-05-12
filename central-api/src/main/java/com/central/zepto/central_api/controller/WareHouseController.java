package com.central.zepto.central_api.controller;

import com.central.zepto.central_api.models.WareHouse;
import com.central.zepto.central_api.models.WareHouseProducts;
import com.central.zepto.central_api.requestdto.RegisterWareHouseProductDTO;
import com.central.zepto.central_api.requestdto.RequestWarehouseDTO;
import com.central.zepto.central_api.service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/warehouse")
public class WareHouseController {

    @Autowired
    WareHouseService wareHouseService;

    @PostMapping("/create")
    public WareHouse createWareHouse(@RequestParam UUID userId, @RequestBody RequestWarehouseDTO warehouseDTO)
    {
        return wareHouseService.createWareHouse(userId, warehouseDTO);
    }

    @PostMapping("/addproduct")
    public WareHouseProducts addProductsToWareHouse(@RequestParam UUID userId,
                                                    @RequestBody RegisterWareHouseProductDTO wareHouseProductDTO){
        return wareHouseService.addProductsToWareHouse(wareHouseProductDTO, userId);
    }

}
