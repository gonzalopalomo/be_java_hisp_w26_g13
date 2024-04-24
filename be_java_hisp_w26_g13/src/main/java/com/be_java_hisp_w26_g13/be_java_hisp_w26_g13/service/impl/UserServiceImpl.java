package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowedByUserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseUserFollowersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.UserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.UserMinimalData;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.NotFoundException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IUserRepository;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;

    /**
     * Use case us-0003's method
     * It searches in userRepository if there are some user with the userId parameter
     * if there aren't any, then throws a not found exception
     * if that user hasn't any post, then throws a bad request exception
     * else add the user id, userName and a list of followed users in followers array from ResponseUserFollowersDTO
     * @param userId
     * @return responseDTO
     */
    @Override
    public ResponseUserFollowersDTO getFollowersList(int userId) {
        //compruebo que exista el user, sino tiro una excepcion
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("User have not been found in out database");
        }
        if (!user.isVendor()){
            throw new NotFoundException("User is not a vendor");
        }
        //creo la lista de DTOs de followers para ese user
        List<UserDTO> followerDTOList = new ArrayList<>();
        for (UserMinimalData follower : user.getFollowers()) {
            UserDTO followerDTO = new UserDTO(follower.getUserId(), follower.getUserName());
            followerDTOList.add(followerDTO);
        }
        // creo el DTO de la response
        ResponseUserFollowersDTO responseDTO = new ResponseUserFollowersDTO();
        responseDTO.setUserId(user.getUserId());
        responseDTO.setUserName(user.getUserName());
        responseDTO.setFollowers(followerDTOList);

        return responseDTO;
    }
}
