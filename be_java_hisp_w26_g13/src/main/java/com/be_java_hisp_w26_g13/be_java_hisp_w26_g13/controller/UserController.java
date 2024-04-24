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

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followUser(@PathVariable Integer userId, @PathVariable Integer userIdToFollow) {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.followUser(userId, userIdToFollow));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.retrieveAllUsers());
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollow(@PathVariable int userId, @PathVariable int userIdToUnfollow) {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.unfollow(userId, userIdToUnfollow));
    }


    //metodo GET para el us-0003
    @GetMapping("/{userId}/followers/list")
    ResponseEntity<?> followersList(@PathVariable int userId) {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.getFollowersList(userId));
    }
}
