package com.central.zepto.central_api.controller;

import com.central.zepto.central_api.exceptions.UnAuthorized;
import com.central.zepto.central_api.exceptions.UserNotFoundException;
import com.central.zepto.central_api.models.Product;
import com.central.zepto.central_api.requestdto.RegisterProductDTO;
import com.central.zepto.central_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/central/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/save")
    public ResponseEntity createProduct(@RequestBody RegisterProductDTO product,
                                        @RequestParam String userEmail){
        try{
            Product productResp  = productService.createProduct(product, userEmail);
            return new ResponseEntity(productResp, HttpStatus.CREATED);
        }catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UnAuthorized e){
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
