package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {
    private int userId;
    private String userName;
    private List<User> followers;
    private List<User> followed;
}
