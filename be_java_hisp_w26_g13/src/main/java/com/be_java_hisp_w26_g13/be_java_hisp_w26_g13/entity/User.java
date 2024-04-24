package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String userName;
    private List<UserMinimalData> followers;
    private List<UserMinimalData> followed;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.followers = new ArrayList<>();
        this.followed = new ArrayList<>();
    }
}
