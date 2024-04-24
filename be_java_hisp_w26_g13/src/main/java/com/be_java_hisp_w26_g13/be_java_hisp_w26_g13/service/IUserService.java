package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.FullUserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseUserFollowersDTO;
import java.util.List;

public interface IUserService {
    ResponseFollowDTO followUser(int userId, int userIdToFollow);

    List<FullUserDTO> retrieveAllUsers();

    ResponseUserFollowersDTO getFollowersList(int userId);
}
