package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.FullUserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseUserFollowersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.UserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.UserMinimalData;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.BadRequestException;
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

    @Autowired
    ObjectMapper mapper;

    private void addFollower(User follower, User userToFollow) {

        UserMinimalData followerMinimal = new UserMinimalData(follower.getUserId(), follower.getUserName());
        UserMinimalData userToFollowMinimal = new UserMinimalData(userToFollow.getUserId(), userToFollow.getUserName());

        List<UserMinimalData> followedList = follower.getFollowed();
        List<UserMinimalData> followerList = userToFollow.getFollowers();

        if (!followedList.contains(userToFollowMinimal) && !followerList.contains(followerMinimal)) {
            if (userToFollow.isVendor()) {
                followedList.add(userToFollowMinimal);
                follower.setFollowed(followedList);
                followerList.add(followerMinimal);
                userToFollow.setFollowers(followerList);
            } else {
                throw new BadRequestException("Cannot follow user that is not a vendor.");
            }
        } else {
            throw new BadRequestException("User with id " + follower.getUserId()
                    + " is already following user with id " + userToFollow.getUserId());
        }
    }


    private void validateFollowUserData(Integer userId, Integer userIdToFollow) {
        if (userId == null || userIdToFollow == null) {
            throw new BadRequestException("The input data is not correctly formatted.");
        } else if (userId < 0 || userIdToFollow < 0) {
            throw new BadRequestException("User IDs cannot be negative.");
        } else if (userId.equals(userIdToFollow)) {
            throw new BadRequestException("User cannot follow itself.");
        }
    }

    @Override
    public ResponseFollowDTO followUser(Integer userId, Integer userIdToFollow) {
        validateFollowUserData(userId, userIdToFollow);
        User followerUser = userRepository.findById(userId);
        User followedUser = userRepository.findById(userIdToFollow);
        if (followerUser == null) {
            throw new NotFoundException("User with id " + userId + " does not exist.");
        }
        if (followedUser == null) {
            throw new NotFoundException("User to follow with id " + userIdToFollow + " does not exist.");
        }
        addFollower(followerUser, followedUser);

        return new ResponseFollowDTO(userId, "You are now following user " + followedUser.getUserName());
    }

    @Override
    public List<FullUserDTO> retrieveAllUsers() {
        List<User> users = userRepository.getAll();
        List<FullUserDTO> fullUsersDTO = new ArrayList<>();
        for (User user : users) {
            List<UserDTO> followerList = new ArrayList<>();
            for (UserMinimalData follower : user.getFollowers()) {
                followerList.add(new UserDTO(follower.getUserId(), follower.getUserName()));
            }
            List<UserDTO> followedList = new ArrayList<>();
            for (UserMinimalData followed : user.getFollowed()) {
                followedList.add(new UserDTO(followed.getUserId(), followed.getUserName()));
            }
            fullUsersDTO.add(new FullUserDTO(user.getUserId(), user.getUserName(), followerList, followedList));
        }

        return fullUsersDTO;
    }


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
}
