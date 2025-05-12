package com.central.zepto.central_api.Util;

import com.central.zepto.central_api.models.AppUser;
import com.central.zepto.central_api.models.Product;
import com.central.zepto.central_api.models.WareHouse;
import com.central.zepto.central_api.models.WareHouseProducts;
import com.central.zepto.central_api.requestdto.RegisterProductDTO;
import com.central.zepto.central_api.requestdto.RegisterUserDTO;
import com.central.zepto.central_api.requestdto.RegisterWareHouseProductDTO;
import com.central.zepto.central_api.requestdto.RequestWarehouseDTO;
import org.springframework.stereotype.Component;

@Component
public class Adapter {
    public AppUser mapUserRequestBodyToAppUser(RegisterUserDTO user){
        return AppUser.builder()
               .name(user.getName())
               .userType(user.getUserType().toString())
               .email(user.getEmail())
               .address(user.getAddress())
               .pincode(user.getPincode())
               .status("ACTIVE")
               .password(user.getPassword())
               .phoneNumber(user.getPhoneNumber())
               .build();
    }

    public Product mapRegisterProductDTOToProduct(RegisterProductDTO productDTO) {
        return Product.builder()
                .productName(productDTO.getProductName())
                .productPrice(productDTO.getProductPrice())
                .details(productDTO.getDetails())
                .weight(productDTO.getWeight())
                .rating(0.0)
                .manufacturerEmail(productDTO.getManufacturerEmail())
                .totalPurchase(0)
                .build();
    }

    public WareHouse mapRegisterWareHouseDTOToWareHouse(RequestWarehouseDTO wareHouseDTO, AppUser manager){
        return WareHouse.builder()
                .name(wareHouseDTO.getName())
                .pincode(wareHouseDTO.getPincode())
                .address(wareHouseDTO.getAddress())
                .email(wareHouseDTO.getEmail())
                .manager(manager)
                .build();
    }

    public WareHouseProducts mapWareHouseProductDTOToWareHouseProducts(RegisterWareHouseProductDTO wareHouseProductDTO){
        return WareHouseProducts.builder()
                .wid(wareHouseProductDTO.getWid())
                .pid(wareHouseProductDTO.getPid())
                .discount(wareHouseProductDTO.getDiscount())
                .totalQuantity(wareHouseProductDTO.getTotalQuantity())
                .build();
    }
}
