package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.PostDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.PostsByFollowedUsersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Post;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.UserMinimalData;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.BadRequestException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.NotFoundException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IPostRepository;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IUserRepository;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    IPostRepository postRepository;

    @Override
    public PostsByFollowedUsersDTO getPostByFollowedUsers(int userId) {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        User user = userRepository.findById(userId);
        if(user == null){
            throw new NotFoundException("User with id " + userId + " does not exist.");
        }
        List<UserMinimalData> followedVendors = user.getFollowed();
        if(followedVendors.isEmpty()){
            throw new BadRequestException("User with id  " + userId  + " has not followed vendors");
        }
        List<PostDTO>followedVendorsPostList = new ArrayList<>();
        LocalDate twoWeeksAgo = LocalDate.now().minusDays(14);
        for (UserMinimalData vendor : followedVendors){
            postRepository.getPostBy(vendor.getUserId()).stream()
                    .filter(post -> post.getDate().isAfter(twoWeeksAgo)&&post.getDate().isBefore(LocalDate.now()))
                    .forEach(post ->{
                        followedVendorsPostList.add(mapper.convertValue(post, PostDTO.class));
                    });
        }
        PostsByFollowedUsersDTO postsByFollowedUsersDTO = new PostsByFollowedUsersDTO(userId, followedVendorsPostList);

        return postsByFollowedUsersDTO;
    }
}
