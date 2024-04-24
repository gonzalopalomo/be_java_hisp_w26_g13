package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.FullUserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowedByUserDTO;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.UserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseUserFollowersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.UserMinimalData;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.BadRequestException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.NotFoundException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IUserRepository;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;



@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;
    @Override
    public ResponseFollowedByUserDTO getFollowedSellers(int userId) {


        User user = userRepository.findById(userId);

        if(user == null){
            throw new NotFoundException("User with id " + userId + " does not exist.");
        }
        List<UserMinimalData> sellers = user.getFollowed();

        ResponseFollowedByUserDTO response = new ResponseFollowedByUserDTO(userId, user.getUserName());

        for (UserMinimalData seller : sellers) {
            UserDTO userDTO = new UserDTO(seller.getUserId(),seller.getUserName());
            response.getFollowed().add(userDTO);
        }

        return response;
    }

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

    /**
     * Performs a follow from the user with id userId to the user with id userIdToFollow.
     * Both IDs must be non-null positive integers (or zero).
     * This method returns a ResponseFollowDTO containing the follower user Id and a
     * message describing the action. It checks if the user was already following the
     * other user or if it tries to follow itself, as well as if the user to follow
     * is a vendor. In those cases, a BadRequestException is thrown.
     * A NotFoundException will be thrown if the users with the received IDs do not
     * exist in the UserRepository.
     *
     * @param  userId  an absolute URL giving the base location of the image
     * @param  userIdToFollow the location of the image, relative to the url argument
     * @return      the image at the specified URL
     * @see         ResponseFollowDTO
     * @see         BadRequestException
     * @see         NotFoundException
     */
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

    /**
     * Retrieves all users from the UserRepository and returns a list containing the
     * corresponding users mapped to a FullUserDTO.
     * @return a list containing all the users in the repository
     * @see IUserRepository
     * @see FullUserDTO
     * @see UserDTO
     */
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
    public ResponseFollowDTO unfollow(int userId, int userIdToUnfollow) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("No se encontro el usuario");
        }

        UserMinimalData userFolled = userRepository.findFollowedById(user, userIdToUnfollow);
        if (userFolled == null) {
            throw new NotFoundException("No se encontro el seguidor");
        }

        userRepository.unfollowFollowed(user, userFolled);

        return new ResponseFollowDTO(userIdToUnfollow, "Se dejo de seguir");
    }
    /**
     * Use case us-0003's method
     * It searches in userRepository if there are some user with the userId parameter
     * if there aren't any, then throws a not found exception
     * if that user hasn't any post, then throws a bad request exception
     * else add the user id, userName and a list of followed users in followers array from ResponseUserFollowersDTO
     * @author miaramosml
     * @param userId int
     * @return ResponseUserFollowersDTO
     * @exception NotFoundException on user not found
     * @exception BadRequestException on user is not a vendor
     */
    public ResponseUserFollowersDTO getFollowersList(int userId) {
        //compruebo que exista el user, sino tiro una excepcion
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("User have not been found");
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
