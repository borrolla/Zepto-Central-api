package com.central.zepto.central_api.Util;

import com.central.zepto.central_api.models.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class DatabaseAPIUtil extends ApiUtilImpl{

    @Value("${db.api.url}")
    String dbApiUrl;
    @Autowired
    ModelMapper mapper;

    public AppUser callCreateUserEndpoint(AppUser user){
        //creation of url
///        String url = "http://localhost:8081" + dbApiUrl + "/user/save";
///        URI finalUrl = URI.create(url);
///        //create request entity
///        RequestEntity<AppUser> request = RequestEntity.post(finalUrl).body(user);
///        // Create rest Template
///        RestTemplate restTemplate = new RestTemplate();
///        ResponseEntity<AppUser> response =restTemplate.exchange(url, HttpMethod.POST, request, AppUser.class);
///        return response.getBody();
        Object resp = makePostCall(dbApiUrl, "/user/save", new HashMap<>(), user);
        return mapper.map(resp, AppUser.class);
    }

    public Product callCreateProductEndPoint(Product product){

///        String url = "http://localhost:8081" + dbApiUrl + "/product/save";
///        URI finalUrl = URI.create(url);
///       RequestEntity<Product> request = RequestEntity.post(finalUrl).body(product);
///
///        RestTemplate restTemplate = new RestTemplate();
///        ResponseEntity<Product> response = restTemplate.exchange(finalUrl, HttpMethod.POST, request, Product.class);
///       return response.getBody();
        Object resp = makePostCall(dbApiUrl,"/product/save", new HashMap<>(), product);
        return mapper.map(resp, Product.class);

    }

    public AppUser getUserByEmail(String email){
       String endPoint =  "/user/email/" + email;
       Object resp  = makeGetCall(dbApiUrl, endPoint, new HashMap<>());
      return mapper.map(resp, AppUser.class);
    }

    public AppUser getUserByUserId(UUID userId){
        // need to make get call to dbApi
        String endPoint = "/user/" + userId.toString();
        Object resp = makeGetCall(dbApiUrl,endPoint, new HashMap<>());
        return mapper.map(resp, AppUser.class);
    }

    public WareHouse createWareHouse(WareHouse wareHouse){
        String endPoint = "/warehouse/save";
        Object resp = makePostCall(dbApiUrl, endPoint, new HashMap<>(), wareHouse);
        return mapper.map(resp, WareHouse.class);
    }

    public WareHouseProducts createWareHouseProducts(WareHouseProducts wareHouseProducts){

        String endPoint = "/warehouse/product/save";
        Object resp = makePostCall(dbApiUrl,endPoint, new HashMap<>(), wareHouseProducts);
        return mapper.map(resp,WareHouseProducts.class);
    }

    public WareHouse getWareHouseByPincode(int pincode){
        String endPoint = "/warehouse/pincode/" + pincode;
        Object resp = makeGetCall(dbApiUrl, endPoint, new HashMap<>());
        if(resp == null){
            return null;
        }
        return mapper.map(resp, WareHouse.class);
    }

    public List<WareHouseProducts> getProductsByWareHouseId(UUID wid){
        String endPoint = "/warehouse/product/" + wid.toString();
        Object resp  = makeGetCall(dbApiUrl, endPoint, new HashMap<>());
        Type listType = new TypeToken<List<WareHouseProducts>>(){}.getType();
        // Object -> .class
        //  Object -> List<.class>
        // As we are getting List<WareHouseProducts> in object reference we need to map that object
        // reference to List<WareHouseProducts>
        // But the map method of mapper ask the type with which we need to map the response
        // if we need to map the response directly to a class we use.class
        // but here we are getting List<WareHouseProducts> so can we pass the type
        // So we are using typeToken class from model mapper library to map the response
        // to List<WareHouseProducts>
        return mapper.map(resp, listType);
    }

    public Product getProductByProductId(UUID pid){
        String endPoint = "/product/" + pid.toString();
        Object resp = makeGetCall(dbApiUrl, endPoint, new HashMap<>());
        return mapper.map(resp, Product.class);
    }

    public WareHouseProducts getProductByWidPid(UUID wid, UUID pid){
        String endPoint = "/warehouse/product/" + wid.toString() + "/" + pid.toString();
       Object resp =  makeGetCall(dbApiUrl,endPoint,new HashMap<>());
       return mapper.map(resp, WareHouseProducts.class);
    }

    public AppOrder saveOrder(AppOrder order){
        String endPoint = "/order/save";
        Object resp = makePostCall(dbApiUrl, endPoint, new HashMap<>(), order);
        return mapper.map(resp, AppOrder.class);

    }

    public List<AppUser> getDeliveryPartnerByPincode(int pincode){
        String endPoint = "/user/delivery-partner/" + pincode;
        Object resp = makeGetCall(dbApiUrl,endPoint,new HashMap<>());
        Type listType = new TypeToken<List<AppUser>>(){}.getType();
        return mapper.map(resp, listType);

    }

    public AppOrder getOrderByOrderId(UUID orderId){
        String endPoint = "/order/" + orderId.toString();
       Object resp =  makeGetCall(dbApiUrl,endPoint,new HashMap<>());
       return mapper.map(resp, AppOrder.class);




    }
}
