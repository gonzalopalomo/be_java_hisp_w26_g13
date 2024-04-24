package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseUserFollowersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.UserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.UserMinimalData;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.NotFoundException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IUserRepository;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.IUserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;


    /**
     * Performs the action of unfollowing a user.
     * @param userId The ID of the user who wants to unfollow another user.
     * @param userIdToUnfollow The ID of the user to unfollow.
     * @return ResponseFollowDTO object indicating the success of the operation.
     */
    @Override
    public ResponseFollowDTO unfollow(int userId, int userIdToUnfollow) {

        unfollowed(userId,userIdToUnfollow);
        deleteFollower(userId,userIdToUnfollow);
        return new ResponseFollowDTO(userIdToUnfollow, "Unfollowed");
    }

    /**
     * Performs the action of validating if a user exists and if that user has the followed,
     * and removes it from the list of followed
     * @param userId The ID of the user who wants to unfollow another user.
     * @param userIdToUnfollow The ID of the user to unfollow.
     */
    private void unfollowed(int userId, int userIdToUnfollow) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("User with id " + userId + " does not exist.");
        }

        UserMinimalData userFollowed = userRepository.findFollowedById(user, userIdToUnfollow);
        if (userFollowed == null) {
            throw new BadRequestException("User has not followed");
        }

        userRepository.unfollowed(user, userFollowed);
    }

    /**
     * Performs the action of validating if a user exists and if that user has the follower,
     * and removes it from the list of follower
     * @param userId The ID of the user who wants to unfollow another user.
     * @param userIdUnfollower The ID of the user to unfollow.
     */
    private void deleteFollower(int userId, int userIdUnfollower) {
        User userFollower = userRepository.findById(userIdUnfollower);
        UserMinimalData userFollowerMinimal = userRepository.findFollowerById(userFollower, userId);

        if (userFollower == null) {
            throw new NotFoundException("User with id " + userId + " does not exist.");
        }

        if (userFollowerMinimal == null) {
            throw new BadRequestException("User has not follower");
        }

        userRepository.deleteFollower(userFollower, userFollowerMinimal);
    }


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
}
