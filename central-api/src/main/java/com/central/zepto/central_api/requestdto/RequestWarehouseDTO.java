package com.central.zepto.central_api.requestdto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestWarehouseDTO {
    String name;
    String address;
    String email;
    int pincode;
    UUID managerId;

}
