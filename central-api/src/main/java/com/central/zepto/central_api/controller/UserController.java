package com.central.zepto.central_api.controller;

import com.central.zepto.central_api.Security.JwtTokenUtil;
import com.central.zepto.central_api.exceptions.UserNotFoundException;
import com.central.zepto.central_api.exceptions.WareHouseNotAvailableException;
import com.central.zepto.central_api.models.AppUser;
import com.central.zepto.central_api.models.Product;
import com.central.zepto.central_api.requestdto.RegisterUserDTO;
import com.central.zepto.central_api.service.UserService;
import com.central.zepto.central_api.service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping("/register")
    public String createUser(@RequestBody RegisterUserDTO user){
        AppUser response = userService.createUser(user);
        String credentials = response.getEmail() + ":" + response.getPassword();
        String token = jwtTokenUtil.generateJwtToken(credentials);
        return token;
    }


    @GetMapping("/products")
    public ResponseEntity<?> getProductsByPincode(@RequestParam UUID userId) {

        try{

            List<Product> products = wareHouseService.getWareHouseProducts(userId);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch (WareHouseNotAvailableException wareHouseNotAvailableException)
        {
            return new ResponseEntity<>(wareHouseNotAvailableException.getMessage(), HttpStatus.OK);
        }
        catch (UserNotFoundException userNotFoundException)
        {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
