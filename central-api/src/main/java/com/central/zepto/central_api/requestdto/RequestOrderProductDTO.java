package com.central.zepto.central_api.requestdto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestOrderProductDTO {
    UUID pid;
    int quantity;

}
