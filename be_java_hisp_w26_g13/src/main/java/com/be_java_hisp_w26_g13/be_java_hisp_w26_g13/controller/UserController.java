package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.controller;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService iUserService;

    //US 0004: Obtener  un listado de todos los vendedores a los cuales sigue un determinado usuario
    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<?> getFollowedSellersList(@PathVariable int userId){
        return new ResponseEntity<>(iUserService.getFollowedSellers(userId), HttpStatus.OK);
    }

}
