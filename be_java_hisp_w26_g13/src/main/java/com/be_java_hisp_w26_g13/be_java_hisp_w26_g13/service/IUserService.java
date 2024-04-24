package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseUserFollowersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowedByUserDTO;

public interface IUserService {


    ResponseFollowedByUserDTO getFollowedSellers(int userId);
    public ResponseFollowDTO unfollow(int userId, int userIdToUnfollow);
    ResponseUserFollowersDTO getFollowersList(int userId);
}
