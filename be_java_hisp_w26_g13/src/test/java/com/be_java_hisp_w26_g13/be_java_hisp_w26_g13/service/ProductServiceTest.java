package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.PostsByFollowedUsersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Post;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.BadRequestException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.impl.PostRepositoryImpl;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.impl.UserRepositoryImpl;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl.ProductServiceImpl;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.utils.CustomUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    UserRepositoryImpl userRepository;

    @Mock
    PostRepositoryImpl postRepository;

    @InjectMocks
    ProductServiceImpl productService;


    @Test
    @DisplayName("Check if the retrieved post of the desired vendor are into the two weeks range")
    public void checkTimePeriodOfRetrievedPost() {
        int userIdParam = 15;

        User mockedVendor = CustomUtils.newMockedVendor();
        User mockedUser = CustomUtils.newMockedUser();
        LocalDate fifteenDaysAgo = CustomUtils.fifteenDaysAgo;
        LocalDate tomorrow = CustomUtils.tomorrow;
        List<Post> mockedFollowedVendorsPostList = CustomUtils.newMockedFollowedVendorPostList();

        Mockito.when(userRepository.findById(15)).thenReturn(mockedUser);

        Mockito.when(postRepository.getPostBy(mockedVendor.getUserId()))
                .thenReturn(mockedFollowedVendorsPostList);

        PostsByFollowedUsersDTO postsByFollowedUsersDTO = productService
                .getPostByFollowedUsers(userIdParam, null);

        LocalDate firstPostDate = postsByFollowedUsersDTO.getPosts().get(0).getDate();
        LocalDate secondPostDate = postsByFollowedUsersDTO.getPosts().get(1).getDate();

        Assertions.assertTrue(firstPostDate.isAfter(fifteenDaysAgo));
        Assertions.assertTrue(firstPostDate.isBefore(tomorrow));
        Assertions.assertTrue(secondPostDate.isAfter(fifteenDaysAgo));
        Assertions.assertTrue(secondPostDate.isBefore(tomorrow));

    }
    @Test
    @DisplayName("Verify that the date sorting type exists - Success")
    public void getPostByFollowedUsersTest() {
        int userIdParam = 15;
        String order = "date_asc";

        User mockedVendor = CustomUtils.newMockedVendor();
        User mockedUser = CustomUtils.newMockedUser();

        List<Post> mockedFollowedVendorsPostList = CustomUtils.newMockedFollowedVendorPostList();

        Mockito.when(userRepository.findById(15)).thenReturn(mockedUser);

        Mockito.when(postRepository.getPostBy(mockedVendor.getUserId()))
                .thenReturn(mockedFollowedVendorsPostList);

        Assertions.assertDoesNotThrow(()->productService.getPostByFollowedUsers(userIdParam,order));
    }
    @Test
    @DisplayName("Verify that the date sorting type exists - Failure")
    public void getPostByFollowedUsersBadPathTest() {
        int userIdParam = 15;
        String order = "error";

        User mockedVendor = CustomUtils.newMockedVendor();
        User mockedUser = CustomUtils.newMockedUser();

        List<Post> mockedFollowedVendorsPostList = CustomUtils.newMockedFollowedVendorPostList();

        Mockito.when(userRepository.findById(15)).thenReturn(mockedUser);

        Mockito.when(postRepository.getPostBy(mockedVendor.getUserId()))
                .thenReturn(mockedFollowedVendorsPostList);

        Assertions.assertThrows(BadRequestException.class, ()->productService.getPostByFollowedUsers(userIdParam,order));
    }


}
