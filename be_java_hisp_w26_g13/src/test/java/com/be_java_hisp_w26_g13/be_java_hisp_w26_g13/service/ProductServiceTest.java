package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.PostDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.PostsByFollowedUsersDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Post;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Product;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.UserMinimalData;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.BadRequestException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.NotFoundException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.impl.PostRepositoryImpl;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.impl.UserRepositoryImpl;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl.ProductServiceImpl;
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


    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            .build();

    @Test
    @DisplayName("Check if the retrieved post of the desired vendor are into the two weeks range")
    public void checkTimePeriodOfRetrievedPost() {
        int userIdParam = 15;
        String orderParam = "date_desc";

        User mockedVendor = new User(1, "Alice Morrison");
        User mockedUser = new User(15, "Oscar Lee");
        UserMinimalData minimalVendor = new UserMinimalData(1, "Alice Morrison");
        UserMinimalData minimalUser = new UserMinimalData(15, "Oscar Lee");
        mockedVendor.setFollowers(new ArrayList<>(List.of(minimalUser)));
        mockedUser.setFollowed(new ArrayList<>(List.of(minimalVendor)));

        List<Post> mockedFollowedVendorsPostList = new ArrayList<>();

        Product product = new Product(1,
                "HyperX Cloud II",
                "Headset",
                "HyperX",
                "Red",
                "Excellent noise canceling");
        LocalDate fourteenDaysAgo = LocalDate.now().minusDays(13);
        LocalDate fifteenDaysAgo = LocalDate.now().minusDays(14);
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        Post beforeRangePost = new Post(mockedVendor.getUserId(), fifteenDaysAgo, product, 1, 3000.0);
        Post firstPost = new Post(mockedVendor.getUserId(), fourteenDaysAgo, product, 3, 2500.0);
        Post todayPost = new Post(mockedVendor.getUserId(), LocalDate.now(), product, 2, 2000.0);
        Post tomorrowPost = new Post(mockedVendor.getUserId(), tomorrow, product, 1, 3000.0);

        mockedFollowedVendorsPostList.add(beforeRangePost);
        mockedFollowedVendorsPostList.add(firstPost);
        mockedFollowedVendorsPostList.add(todayPost);
        mockedFollowedVendorsPostList.add(tomorrowPost);

        Mockito.when(userRepository.findById(15)).thenReturn(mockedUser);

        Mockito.when(postRepository.getPostBy(mockedVendor.getUserId()))
                .thenReturn(mockedFollowedVendorsPostList);

        PostsByFollowedUsersDTO postsByFollowedUsersDTO = productService
                .getPostByFollowedUsers(userIdParam, orderParam);

        for (PostDTO postDTO: postsByFollowedUsersDTO.getPosts()){
            Post post = mapper.convertValue(postDTO, Post.class);
            Assertions.assertTrue(post.getDate().isAfter(fifteenDaysAgo)
                    && post.getDate().isBefore(tomorrow)
            );
        }

    }

    @Test
    @DisplayName("T-0005: Verificar que el tipo de ordenamiento por fecha exista - Exception")
    public void getPostByFollowedUsersBadPathTest() {
        int userIdParam = 15;
        String orderParam = "date_desc";

        User mockedVendor = new User(1, "Alice Morrison");
        User mockedUser = new User(15, "Oscar Lee");

        UserMinimalData minimalVendor = new UserMinimalData(1, "Alice Morrison");
        UserMinimalData minimalUser = new UserMinimalData(15, "Oscar Lee");

        mockedVendor.setFollowers(new ArrayList<>(List.of(minimalUser)));
        mockedUser.setFollowed(new ArrayList<>(List.of(minimalVendor)));

        List<Post> mockedFollowedVendorsPostList = new ArrayList<>();


        Mockito.when(userRepository.findById(15)).thenReturn(mockedUser);

        Mockito.when(postRepository.getPostBy(mockedVendor.getUserId()))
                .thenReturn(mockedFollowedVendorsPostList);

        Assertions.assertThrows(NotFoundException.class, () -> {
            productService.getPostByFollowedUsers(userIdParam, orderParam);
        });
        }

    @Test
    @DisplayName("T-0005: Verificar que el tipo de ordenamiento por fecha exista (US-0009) date_asc , date_desc")
    public void getPostByFollowedUsersTest() {

    }


}
