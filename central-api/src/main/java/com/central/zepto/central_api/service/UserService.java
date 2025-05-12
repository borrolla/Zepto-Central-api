package com.central.zepto.central_api.service;

import com.central.zepto.central_api.Util.Adapter;
import com.central.zepto.central_api.Util.DatabaseAPIUtil;
import com.central.zepto.central_api.models.AppUser;
import com.central.zepto.central_api.requestdto.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    DatabaseAPIUtil databaseApiUtil;

    @Autowired
    Adapter adapter;


    public AppUser createUser(RegisterUserDTO user){

        AppUser appUser = adapter.mapUserRequestBodyToAppUser(user);

        AppUser response  = databaseApiUtil.callCreateUserEndpoint(appUser);

        return response;
    }

    public List<AppUser> getDeliveryPartnerByPincode(int pincode){
         return databaseApiUtil.getDeliveryPartnerByPincode(pincode);
    }
}
