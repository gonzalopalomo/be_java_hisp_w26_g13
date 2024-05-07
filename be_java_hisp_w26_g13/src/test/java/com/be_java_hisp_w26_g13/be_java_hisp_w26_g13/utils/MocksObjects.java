package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.utils;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Post;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Product;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.UserMinimalData;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class MocksObjects {
    private static List<Post> mockedFollowedVendorsPostList = new ArrayList<>();

    MocksObjects(){
        loadClass();
    }
    static void loadClass() {
        User mockedVendor = new User(1, "Alice Morrison");
        User mockedUser = new User(15, "Oscar Lee");
        UserMinimalData minimalVendor = new UserMinimalData(1, "Alice Morrison");
        UserMinimalData minimalUser = new UserMinimalData(15, "Oscar Lee");
        mockedVendor.setFollowers(new ArrayList<>(List.of(minimalUser)));
        mockedUser.setFollowed(new ArrayList<>(List.of(minimalVendor)));


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
    }
}
