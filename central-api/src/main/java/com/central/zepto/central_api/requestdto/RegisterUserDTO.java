package com.central.zepto.central_api.requestdto;

import com.central.zepto.central_api.enums.UserType;
import lombok.Data;

@Data
public class RegisterUserDTO {
    String name;
    String email;
    String password;
    Long phoneNumber;
    int pincode;
    String address;
    UserType userType;
}
