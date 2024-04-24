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
    public ResponseEntity<?> getFollowedSellersList(@PathVariable int userId) {
        return new ResponseEntity<>(iUserService.getFollowedSellers(userId), HttpStatus.OK);
    }

    //metodo GET para el us-0003
    @GetMapping("/{userId}/followers/list")
    ResponseEntity<?>followersList(@PathVariable int userId){
        return new ResponseEntity<>( iUserService.getFollowersList(userId), HttpStatus.OK);
    }
    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollow(@PathVariable int userId, @PathVariable int userIdToUnfollow) {
        return new ResponseEntity<>(iUserService.unfollow(userId, userIdToUnfollow), HttpStatus.OK);
    }
}