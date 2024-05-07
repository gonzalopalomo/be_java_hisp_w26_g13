package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.PostDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.PostsByFollowedUsersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ProductDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Post;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.impl.PostRepositoryImpl;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.impl.UserRepositoryImpl;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl.ProductServiceImpl;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.utils.CustomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
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
        LocalDate fourteenDaysAgo = CustomUtils.fourteenDaysAgo;
        ProductDTO productDTO = CustomUtils.newMockedProductDTO();
        int userIdParam = 15;

        List<PostDTO>expectedPostsList = new ArrayList<>();
        expectedPostsList.add(new PostDTO(2,1, fourteenDaysAgo, productDTO, 3, 2500.0));
        expectedPostsList.add( new PostDTO(3,1, LocalDate.now(), productDTO, 2, 2000.0));

        User mockedVendor = CustomUtils.newMockedVendor();
        User mockedUser = CustomUtils.newMockedUser();
        List<Post> mockedFollowedVendorsPostList = CustomUtils.newMockedFollowedVendorPostList();

        Mockito.when(userRepository.findById(15)).thenReturn(mockedUser);

        Mockito.when(postRepository.getPostBy(mockedVendor.getUserId()))
                .thenReturn(mockedFollowedVendorsPostList);

        PostsByFollowedUsersDTO actualPostList = productService
                .getPostByFollowedUsers(userIdParam, null);

        Assertions.assertEquals(expectedPostsList, actualPostList.getPosts());

    }

}
