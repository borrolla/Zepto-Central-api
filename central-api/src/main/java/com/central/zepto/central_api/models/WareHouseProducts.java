package com.central.zepto.central_api.models;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class WareHouseProducts {
    UUID id;
    UUID wid;
    UUID pid;
    int discount;
    int totalQuantity;
}
