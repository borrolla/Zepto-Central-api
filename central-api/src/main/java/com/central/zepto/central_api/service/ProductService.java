package com.central.zepto.central_api.service;

import com.central.zepto.central_api.Util.Adapter;
import com.central.zepto.central_api.Util.DatabaseAPIUtil;
import com.central.zepto.central_api.enums.UserType;
import com.central.zepto.central_api.exceptions.UnAuthorized;
import com.central.zepto.central_api.exceptions.UserNotFoundException;
import com.central.zepto.central_api.models.AppUser;
import com.central.zepto.central_api.models.Product;
import com.central.zepto.central_api.requestdto.RegisterProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    Adapter adapter;

    @Autowired
    DatabaseAPIUtil databaseAPIUtil;

    public Product createProduct(RegisterProductDTO product, String userEmail){
        // map registerProductDto to productModel
        Product productObj = adapter.mapRegisterProductDTOToProduct(product);

        AppUser user = databaseAPIUtil.getUserByEmail(userEmail);
        if(user == null){
            throw new UserNotFoundException(String.format("User with email %s does not exist", userEmail));
        }
        if(!user.getUserType().equals(UserType.APPLICATION_ADMIN.toString())){
            throw new UnAuthorized(String.format("User with email %s does not have access to create product", userEmail));
        }
        return databaseAPIUtil.callCreateProductEndPoint(productObj);
    }
}
