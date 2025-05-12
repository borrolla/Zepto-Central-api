package com.central.zepto.central_api.Util;

import com.central.zepto.central_api.enums.UserType;
import com.central.zepto.central_api.exceptions.UserNotFoundException;
import com.central.zepto.central_api.models.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    public boolean isZeptoApplicationAdmin(AppUser user){
        if(user == null){
            throw new UserNotFoundException("User does not exist");
        }
        return user.getUserType().equals(UserType.APPLICATION_ADMIN.toString());
    }

    public boolean isZeptoWareHouseManager(AppUser user){
        if(user == null){
            throw new UserNotFoundException("User does not exist");
        }
        return user.getUserType().equals(UserType.WAREHOUSE_MANAGER.toString());
    }
}
