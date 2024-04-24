package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowedByUserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowersCountDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseUserFollowersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.UserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.UserMinimalData;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.InvalidOperation;
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

    @Override
    public ResponseUserFollowersDTO getFollowersList(int userId) {
        //compruebo que exista el user, sino tiro una excepcion
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("El usuario no existe en nuestra base de datos");
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

    /**
     * Retrieves the count of followers for a specified user if they are a vendor.
     *
     * @param userId The ID of the user for whom to retrieve the follower count.
     * @return A {@link ResponseFollowersCountDTO} object containing the user's ID, user's name, and the count of followers.
     * @throws NotFoundException if no user is found with the given userId.
     * @throws InvalidOperation if the user identified by userId is not a vendor.
     */
    @Override
    public ResponseFollowersCountDTO getFollowersCount(int userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("Given userId does not exist");
        }

        if(!user.isVendor()){
            throw new InvalidOperation("Cannot follow a non-vendor user");
        }

        ResponseFollowersCountDTO dto = new ResponseFollowersCountDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setFollowersCount(user.getFollowers().size());

        return dto;
    }
}
