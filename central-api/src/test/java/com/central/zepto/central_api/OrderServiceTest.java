package com.central.zepto.central_api;

import com.central.zepto.central_api.Util.DatabaseAPIUtil;
import com.central.zepto.central_api.Util.MailUtil;

import com.central.zepto.central_api.service.OrderService;
import com.central.zepto.central_api.service.WareHouseService;

import org.mockito.InjectMocks;
import org.mockito.Mock;



public class OrderServiceTest {
    @Mock
    DatabaseAPIUtil databaseAPIUtil;

    @Mock
    WareHouseService wareHouseService;

    @Mock
    MailUtil mailUtil;

    @InjectMocks
    OrderService orderService;

    //    @Test
//    public void acceptOrder(){
//        UUID orderId = UUID.randomUUID();
//        UUID deliveryPId = UUID.randomUUID();
//
//        AppUser deliveryPartner = AppUser.builder().id(deliveryPId).status("Available").build();
//        AppOrder order  = AppOrder.builder().id(orderId).build();
//
//        when(databaseAPIUtil.getOrderByOrderId(orderId)).thenReturn(order);
//        when(databaseAPIUtil.getUserByUserId(deliveryPId)).thenReturn(deliveryPartner);
//        orderService.acceptOrder(orderId, deliveryPId);
//        verify(mailUtil, times(1)).sendAcceptOrderNotification(new RequestOrderDTO());
//    }
}

