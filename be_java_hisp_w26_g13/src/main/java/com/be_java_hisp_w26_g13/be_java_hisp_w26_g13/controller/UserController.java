package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.controller;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IUserRepository;
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
    @Autowired
    IUserRepository iUserRepository;

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollow(@PathVariable int userId,@PathVariable int userIdToUnfollow)
    {
        return new ResponseEntity<>(iUserService.unfollow(userId,userIdToUnfollow),HttpStatus.OK);
    }

    @GetMapping("test")
    public ResponseEntity<?> test()
    {
        return new ResponseEntity<>(iUserRepository.getAll(),HttpStatus.OK);
    }

}
