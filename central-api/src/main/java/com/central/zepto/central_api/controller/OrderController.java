package com.central.zepto.central_api.controller;

import com.central.zepto.central_api.exceptions.ProductNotPresentException;
import com.central.zepto.central_api.exceptions.UserNotFoundException;
import com.central.zepto.central_api.exceptions.WareHouseNotAvailableException;
import com.central.zepto.central_api.requestdto.RequestOrderProductDTO;
import com.central.zepto.central_api.responsedto.ResponseBillDTO;
import com.central.zepto.central_api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/order")
public class OrderController {


    @Autowired
    OrderService orderService;
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody List<RequestOrderProductDTO> products,
                                     @RequestParam UUID userId) throws Exception {
        // order service
        try{
            ResponseBillDTO bill = orderService.placeOrder(products, userId);
            return new ResponseEntity<>(bill, HttpStatus.CREATED);
        }catch (ProductNotPresentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (WareHouseNotAvailableException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/accept/{partnerId}/{orderId}")
    public void acceptOrder(@PathVariable UUID partnerId, @PathVariable UUID orderId){
            //service
          orderService.acceptOrder(orderId,partnerId);
    }
}
