package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.FullUserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowDTO;
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
            followedList.add(userToFollowMinimal);
            follower.setFollowed(followedList);
            followerList.add(followerMinimal);
            userToFollow.setFollowers(followerList);
        }
    }

    @Override
    public ResponseFollowDTO followUser(int userId, int userIdToFollow) {
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
}
