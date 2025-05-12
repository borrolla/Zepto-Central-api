package com.central.zepto.central_api.service;

import com.central.zepto.central_api.Util.Adapter;
import com.central.zepto.central_api.Util.DatabaseAPIUtil;
import com.central.zepto.central_api.Util.UserUtil;
import com.central.zepto.central_api.enums.UserType;
import com.central.zepto.central_api.exceptions.ProductNotPresentException;
import com.central.zepto.central_api.exceptions.UnAuthorized;
import com.central.zepto.central_api.exceptions.UserNotFoundException;
import com.central.zepto.central_api.exceptions.WareHouseNotAvailableException;
import com.central.zepto.central_api.models.AppUser;
import com.central.zepto.central_api.models.Product;
import com.central.zepto.central_api.models.WareHouse;
import com.central.zepto.central_api.models.WareHouseProducts;
import com.central.zepto.central_api.requestdto.RegisterWareHouseProductDTO;
import com.central.zepto.central_api.requestdto.RequestWarehouseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WareHouseService {
    @Autowired
    DatabaseAPIUtil databaseAPIUtil;

    @Autowired
    Adapter adapter;

    @Autowired
    UserUtil userUtil;


    public WareHouse createWareHouse(UUID userId, RequestWarehouseDTO warehouseDTO){

        // We have userId ->
        // With the help of userId we will try to get user from dbApi

        AppUser user = databaseAPIUtil.getUserByUserId(userId);

        if(user == null){
            throw new UserNotFoundException(String.format("User with id %s does not exists in system", userId));
        }

        if(!user.getUserType().equals(UserType.APPLICATION_ADMIN.toString())){
            throw new UnAuthorized(String.format("User wih id %s does not have access to create warehouse", userId));
        }

        // We will start creating warehouse into the system.

        // we need to call database api util that will hit dbApi url to create warehouse

        UUID managerId = warehouseDTO.getManagerId();

        AppUser manager = databaseAPIUtil.getUserByUserId(managerId);

        WareHouse wareHouse = adapter.mapRegisterWareHouseDTOToWareHouse(warehouseDTO,manager);

        // Will call database api to create warehouse record in the database

        return databaseAPIUtil.createWareHouse(wareHouse);
    }

    public WareHouseProducts addProductsToWareHouse(RegisterWareHouseProductDTO wareHouseProductDTO,
                                                    UUID userId){
        AppUser user = databaseAPIUtil.getUserByUserId(userId);

        if(!userUtil.isZeptoWareHouseManager(user)
                && !userUtil.isZeptoApplicationAdmin(user)){
            throw new UnAuthorized(String.format("User with id %s does not have access to add products into system", userId));
        }

        // we need to call database api to create new record into the warehouse product table

        WareHouseProducts wareHouseProducts = adapter.mapWareHouseProductDTOToWareHouseProducts(wareHouseProductDTO);

        // database api

        return databaseAPIUtil.createWareHouseProducts(wareHouseProducts);
    }

    public Product getProductById(UUID pid){
        return databaseAPIUtil.getProductByProductId(pid);
    }

    public List<WareHouseProducts> getWareHouseProductsByWid(UUID wid){
        return databaseAPIUtil.getProductsByWareHouseId(wid);
    }

    public List<Product> getWareHouseProducts(UUID userId) {
        // we need to check this user id is registered in our system or not
        AppUser user = databaseAPIUtil.getUserByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException(String.format("User with id %s does not" +
                    "exist in system", userId));
        }

        int pincode = user.getPincode();

        // we need to find is there any warehouse present at that pincode

        WareHouse wareHouse = databaseAPIUtil.getWareHouseByPincode(pincode);

        if (wareHouse == null) {
            throw new WareHouseNotAvailableException("We regret that we don't provide any service in your region");
        }

        // We need to get all the products that are present in the warehouse.

        List<WareHouseProducts> wareHouseProducts = this.getWareHouseProductsByWid(wareHouse.getId());
        List<Product> products = new ArrayList<>();
        for (WareHouseProducts wp : wareHouseProducts) {
            UUID pid = wp.getPid();
            Product p = this.getProductById(pid);
            products.add(p);
        }

        return products;

    }

    public WareHouseProducts getProductByWidPid(UUID wid, UUID pid){
        //This  method will call your databaseAPiUtil
       WareHouseProducts wareHouseProduct = databaseAPIUtil.getProductByWidPid(wid,pid);
       if(wareHouseProduct == null)
       {
           throw new ProductNotPresentException(String.format("Product with ProductId with %s does not present in wareHouse with wid %s", pid, wid));
       }
       return wareHouseProduct;
    }
}
