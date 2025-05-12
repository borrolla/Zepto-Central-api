package com.central.zepto.central_api.models;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class WareHouse {
    UUID id;
    String name;
    String address;
    String email;
    int pincode;
    AppUser manager;
}
