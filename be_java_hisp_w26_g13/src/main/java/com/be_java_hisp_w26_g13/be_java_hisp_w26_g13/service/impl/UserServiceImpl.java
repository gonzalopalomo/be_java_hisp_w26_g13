package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.UserMinimalData;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.NotFoundException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IUserRepository;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;

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

        return new ResponseFollowDTO(userIdToUnfollow,"Se dejo de seguir");
    }
}
