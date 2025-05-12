package com.central.zepto.central_api.service;

import com.central.zepto.central_api.Util.DatabaseAPIUtil;
import com.central.zepto.central_api.Util.MailUtil;
import com.central.zepto.central_api.enums.DeliveryPartnerStatus;
import com.central.zepto.central_api.exceptions.ProductNotPresentException;
import com.central.zepto.central_api.exceptions.UserNotFoundException;
import com.central.zepto.central_api.exceptions.WareHouseNotAvailableException;
import com.central.zepto.central_api.models.*;
import com.central.zepto.central_api.requestdto.RequestOrderDTO;
import com.central.zepto.central_api.requestdto.RequestOrderProductDTO;
import com.central.zepto.central_api.responsedto.ResponseBillDTO;
import com.central.zepto.central_api.responsedto.ResponseBillProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {


    DatabaseAPIUtil databaseAPIUtil;

    WareHouseService wareHouseService;

    MailUtil mailUtil;

    @Autowired
    public OrderService(DatabaseAPIUtil databaseAPIUtil, WareHouseService wareHouseService, MailUtil mailUtil) {
        this.databaseAPIUtil = databaseAPIUtil;
        this.wareHouseService = wareHouseService;
        this.mailUtil = mailUtil;
    }

    public double getPriceAfterDiscount(int amount, int discount){
        double offAmount = amount*((double) discount /100);
        return amount - offAmount;
    }

    public List<AppUser> getDeliveryPartnerByPincode(int pincode){
            return  databaseAPIUtil.getDeliveryPartnerByPincode(pincode);

    }

    public void notifyDeliveryPartner(int pincode, AppUser customer, ResponseBillDTO bill){
            List<AppUser> dp = getDeliveryPartnerByPincode(pincode);
            for(AppUser partner : dp)
            {
                // we will mail all the delivery partner and delivery partner will accept
                // amd reject the orders.
                // We have developed another api for sending mail
                // we will call mail api to send order notification to delivery doubts
                RequestOrderDTO order = new RequestOrderDTO();
                order.setCustomer(customer);
                order.setDeliveryPartner(partner);
                order.setBill(bill);
                mailUtil.sendOrderNotification(order);

            }
    }

    public ResponseBillDTO placeOrder(List<RequestOrderProductDTO> products,
                                       UUID userId){

      AppUser user =  databaseAPIUtil.getUserByUserId(userId);

      if(user == null)
      {
          throw new UserNotFoundException(String.format("User with id %s does not exist", userId));
      }

      int pincode = user.getPincode();

        WareHouse wareHouse = databaseAPIUtil.getWareHouseByPincode(pincode);
        if(wareHouse == null)
        {
            throw new WareHouseNotAvailableException(String.format("We regret to inform you wareHouse is not present at your pincode %d", pincode));
        }

        ResponseBillDTO bill = new ResponseBillDTO();
        List<ResponseBillProductDTO> billProducts = new ArrayList<>();
        AppOrder order = new AppOrder();
        List<Product> orderProducts = new ArrayList<>();
        double totalAmount = 0.0;

        //if wareHouse is present we will see  whatever the user is ordering it is present in that wareHouse or not
        for(RequestOrderProductDTO product : products)
        {
              UUID pid = product.getPid();
              //we  will check that this pid is present in that warehouse or not
            WareHouseProducts wareHouseProduct = wareHouseService.getProductByWidPid(wareHouse.getId(),pid);

            if(wareHouseProduct.getTotalQuantity() <= product.getQuantity())
            {
                throw new ProductNotPresentException(String.format("Product with pid %s does not have enough quantity", pid.toString()));
            }
             //we will start placing order
           Product oP =  wareHouseService.getProductById(pid);
            orderProducts.add(oP);
            ResponseBillProductDTO billProductDTO = new ResponseBillProductDTO();
            billProductDTO.setProductId(pid);
            billProductDTO.setQuantity(product.getQuantity());
            double priceAfterDiscount = this.getPriceAfterDiscount(oP.getProductPrice(), wareHouseProduct.getDiscount());
            billProductDTO.setAmount(product.getQuantity() * priceAfterDiscount);
            billProductDTO.setProductName(oP.getProductName());
            billProducts.add(billProductDTO);
            totalAmount += priceAfterDiscount;

        }

        order.setCustomer(user);
        order.setPlacedTime(LocalDateTime.now());
        order.setProducts(orderProducts);
        order.setTotalAmount((int) totalAmount);

        // Save this order in database

        order = databaseAPIUtil.saveOrder(order);

        //we need to notify all the delivery partners working at the pincode from which order is basically placed


        bill.setOrderId(order.getId());
        bill.setOrderPlacedTime(LocalDateTime.now());
        bill.setTotalBillPayed(totalAmount);
        bill.setProducts(billProducts);

        notifyDeliveryPartner(wareHouse.getPincode(), user, bill);

        return bill;

    }
    public void acceptOrder(UUID orderId, UUID deliveryPartnerId){
      /// we need to get order by order id
         AppOrder order = databaseAPIUtil.getOrderByOrderId(orderId);


        if (order == null) {
            // Log and handle case where order does not exist
            return;
        }


        if(order.getDeliveryPartner() != null){
             //notify delivery partner that order is already assigned
            return;
         }

        AppUser deliveryPartner = databaseAPIUtil. getUserByUserId(deliveryPartnerId);

        if (deliveryPartner == null) {
            // Log and handle case where delivery partner does not exist
            return;
        }

         if(deliveryPartner.getStatus().equals(DeliveryPartnerStatus.OCCUPIED.toString()))
         {
             //notify delivery partner hey you are already occupied
             return;
         }

         deliveryPartner.setStatus(DeliveryPartnerStatus.OCCUPIED.toString());
         order.setDeliveryPartner(deliveryPartner);

        //mail user hey we have assigned your order to the delivery partner you will receive delivery in 10 minutes

        RequestOrderDTO orderRb = new RequestOrderDTO();
        orderRb.setOrder(order);
        orderRb.setCustomer(orderRb.getCustomer());
        orderRb.setDeliveryPartner(deliveryPartner);


        mailUtil.sendOrderNotification(orderRb);

    }
}
