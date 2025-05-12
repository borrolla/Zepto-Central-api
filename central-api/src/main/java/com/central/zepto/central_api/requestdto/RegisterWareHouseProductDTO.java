package com.central.zepto.central_api.requestdto;

import lombok.Data;

import java.util.UUID;

@Data
public class RegisterWareHouseProductDTO {
    UUID wid;
    UUID pid;
    int discount;
    int totalQuantity;
}
