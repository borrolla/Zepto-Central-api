package com.central.zepto.central_api.Util;

import com.central.zepto.central_api.requestdto.RequestOrderDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MailUtil extends  ApiUtilImpl{

    @Value("${mail.api.url}")
    String mailApiUrl;

    public void sendOrderNotification(RequestOrderDTO orderDTO){

          String endPoint = "/delivery-partner/order/notify";
         Object resp =  makePutCall(mailApiUrl, endPoint, new HashMap<>(), orderDTO);
    }

    public void sendAcceptOrderNotification(RequestOrderDTO orderDTO){

        String endPoint = "/delivery-Partner/order/accept/notify";
       Object resp =  makePutCall(mailApiUrl, endPoint, new HashMap<>(), orderDTO);

    }

}
