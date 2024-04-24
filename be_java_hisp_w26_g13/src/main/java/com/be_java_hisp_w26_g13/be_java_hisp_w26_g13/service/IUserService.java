package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ResponseFollowDTO;
import org.springframework.web.bind.annotation.PathVariable;

public interface IUserService {
    public ResponseFollowDTO unfollow(int userId, int userIdToUnfollow);
}
