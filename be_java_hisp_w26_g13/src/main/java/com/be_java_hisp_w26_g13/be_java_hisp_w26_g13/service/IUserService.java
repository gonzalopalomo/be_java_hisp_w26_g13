package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.FullUserDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseUserFollowersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowedByUserDTO;
import java.util.List;

public interface IUserService {
    ResponseFollowDTO followUser(Integer userId, Integer userIdToFollow);
    List<FullUserDTO> retrieveAllUsers();
    ResponseFollowDTO unfollow(int userId, int userIdToUnfollow);
    ResponseFollowedByUserDTO getFollowedSellers(int userId);
    ResponseUserFollowersDTO getFollowersList(int userId);
}
