package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.utils;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Post;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Product;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.UserMinimalData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomUtils {
    private static final LocalDate fourteenDaysAgo = LocalDate.now().minusDays(13);
    public static final LocalDate fifteenDaysAgo = LocalDate.now().minusDays(14);
    public static final LocalDate tomorrow = LocalDate.now().plusDays(1);

    /**
     * @return  a mocked vendor with userId = 1
     * followed by userId = 15
     */
    public static User newMockedVendor() {
        User mockedVendor = new User(1, "Alice Morrison");
        UserMinimalData minimalUser = new UserMinimalData(15, "Oscar Lee");
        mockedVendor.setFollowers(new ArrayList<>(List.of(minimalUser)));
        return mockedVendor;
    }

    /**
     * @return a mocked User with userId = 15
     * following userId = 15
     */
    public static User newMockedUser() {
        User mockedUser = new User(15, "Oscar Lee");
        UserMinimalData minimalVendor = new UserMinimalData(1, "Alice Morrison");
        mockedUser.setFollowed(new ArrayList<>(List.of(minimalVendor)));
        return mockedUser;
    }


    /**
     * @return a list of post for vendor userId = 1
     */
    public static List<Post> newMockedFollowedVendorPostList() {
        User mockedVendor = newMockedVendor();

        Product product = new Product(1,
                "HyperX Cloud II",
                "Headset",
                "HyperX",
                "Red",
                "Excellent noise canceling");

        Post beforeRangePost = new Post(mockedVendor.getUserId(), fifteenDaysAgo, product, 1, 3000.0);
        Post firstPost = new Post(mockedVendor.getUserId(), fourteenDaysAgo, product, 3, 2500.0);
        Post todayPost = new Post(mockedVendor.getUserId(), LocalDate.now(), product, 2, 2000.0);
        Post tomorrowPost = new Post(mockedVendor.getUserId(), tomorrow, product, 1, 3000.0);
        List<Post> mockedFollowedVendorsPostList = new ArrayList<>();

        mockedFollowedVendorsPostList.add(beforeRangePost);
        mockedFollowedVendorsPostList.add(firstPost);
        mockedFollowedVendorsPostList.add(todayPost);
        mockedFollowedVendorsPostList.add(tomorrowPost);
        return mockedFollowedVendorsPostList;
    }
}
