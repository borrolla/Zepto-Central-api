package com.central.zepto.central_api.requestdto;

import lombok.Data;

@Data
public class RegisterProductDTO {
    String productName;
    int productPrice;
    String details;
    String manufacturerEmail;
    Double rating;
    int weight;
    int totalPurchase;

}
