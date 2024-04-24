package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowedByUserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.UserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.NotFoundException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IUserRepository;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Override
    public ResponseFollowedByUserDTO getFollowedSellers(int userId) {

        User user = userRepository.findById(userId);
        String userName = user.getUserName();

        if(user == null){
            throw new NotFoundException("User not found");
        }
        List<User> sellers = user.getFollowed();

        ResponseFollowedByUserDTO response = new ResponseFollowedByUserDTO(userId, userName);

        for (User seller : sellers) {
            UserDTO userDTO = convertUserToUserDTO(seller);
            response.getFollowed().add(userDTO);
        }

        return response;
    }

    public UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());

        return userDTO;
    }
}
