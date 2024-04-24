package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseUserFollowersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowDTO;

public interface IUserService {
    public ResponseFollowDTO unfollow(int userId, int userIdToUnfollow);
    ResponseUserFollowersDTO getFollowersList(int userId);
}